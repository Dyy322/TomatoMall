package com.example.tomatomall.service.serviceImpl;

import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.example.tomatomall.enums.OrderStatus;
import com.example.tomatomall.exception.TomatoMallException;
import com.example.tomatomall.po.CartOrdersRelation;
import com.example.tomatomall.po.Order;
import com.example.tomatomall.repository.CartOrderRelationRepository;
import com.example.tomatomall.repository.OrderRepository;
import com.example.tomatomall.service.AccountService;
import com.example.tomatomall.service.CartService;
import com.example.tomatomall.service.OrderService;
import com.example.tomatomall.service.ProductService;
import com.example.tomatomall.vo.CartVO;
import com.example.tomatomall.vo.OrderVO;
import com.example.tomatomall.vo.PayVO;
import com.example.tomatomall.vo.shippingAddressVO;
import org.hibernate.resource.beans.internal.BeansMessageLogger_$logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    AccountService AccountService;

    @Autowired
    ProductService productService;

    @Autowired
    CartService cartService;

    @Autowired
    CartOrderRelationRepository cartOrderRelationRepository;

    @Value("${alipay.appId}")
    private String appId;
    @Value("${alipay.appPrivateKey}")
    private String privateKey;
    @Value("${alipay.alipayPublicKey}")
    private String alipayPublicKey;
    @Value("${alipay.serverUrl}")
    private String serverUrl;
    @Value("${alipay.notifyUrl}")
    private String notifyUrl;
    @Value("${alipay.returnUrl}")
    private String returnUrl;



    @Override
    public OrderVO createOrder(List<Integer> cartIds, shippingAddressVO shippingAddressVO, String paymentMethod) {
        OrderVO orderVO = new OrderVO();
        orderVO.setAccountVO(AccountService.getCurrentAccountInfo());
        orderVO.setPaymentMethod(paymentMethod);
        orderVO.setStatus(OrderStatus.PENDING);
        orderVO.setCreateTime(new Timestamp(System.currentTimeMillis()));
        BigDecimal totalAmount = new BigDecimal(0);
        for (Integer cartId : cartIds) {
            CartVO cartVO = cartService.getCartItem(cartId);
            if (cartVO.getQuantity()>productService.getProductStock(cartVO.getProductId()).getAvailable()) {
                throw TomatoMallException.stockNotEnough();
            }
            //productService.frozenProduct(cartVO.getProductId(), cartVO.getQuantity());
            //totalAmount.add(productService.getProduct(cartVO.getProductId()).getPrice());
            totalAmount = totalAmount.add(productService.getProduct(cartVO.getProductId()).getPrice().multiply(new BigDecimal(cartVO.getQuantity())));

        }

        for (Integer cartId : cartIds) {
            CartVO cartVO = cartService.getCartItem(cartId);
            productService.frozenProduct(cartVO.getProductId(), cartVO.getQuantity());
        }
        orderVO.setTotalAmount(totalAmount);
        Order orderPO = orderVO.toPO();
        orderRepository.save(orderPO);
        for (Integer cartId : cartIds) {
            CartOrdersRelation cartOrdersRelation = new CartOrdersRelation();
            cartOrdersRelation.setCartItemId(cartId);
            cartOrdersRelation.setOrderId(orderPO.toVO().getOrderId());
            cartOrderRelationRepository.save(cartOrdersRelation);
        }

        return orderPO.toVO();
    }

    @Override
    public OrderVO getOrder(Integer orderId) {
        return orderRepository.findById(orderId).get().toVO();
    }

    @Override
    public List<OrderVO> getOrdersByUserId(Integer userId) {
        return orderRepository.findByAccount(AccountService.getAccountInfo(userId).toPO()).stream().map(Order::toVO).collect(Collectors.toList());
    }

    @Override
    public void OrderStatusUpdate(Integer orderId, String status) {
        Order order = orderRepository.findById(orderId).get();
        order.setStatus(OrderStatus.valueOf(status));
        orderRepository.save(order);
    }

    @Override
    public PayVO payOrder(Integer orderId) {
        Order order = orderRepository.findById(orderId).get();
        PayVO payVO = new PayVO();
        payVO.setOrderId(orderId);
        payVO.setTotalAmount(order.getTotalAmount());
        payVO.setPaymentMethod(order.getPaymentMethod());


        // 1. 创建Client，通用SDK提供的Client，负责调用支付宝的API
        AlipayClient alipayClient = new DefaultAlipayClient(serverUrl, appId,
                privateKey, "json", "utf-8", alipayPublicKey, "RSA2");
        // 2. 创建 Request并设置Request参数
        AlipayTradePagePayRequest request = new AlipayTradePagePayRequest();  // 发送请求的 Request类
        request.setNotifyUrl(notifyUrl);
        request.setReturnUrl(returnUrl);

        JSONObject bizContent = new JSONObject();
        bizContent.put("out_trade_no", order.getOrderId());  // 我们自己生成的订单编号
        bizContent.put("total_amount", order.getTotalAmount()); // 订单的总金额
        bizContent.put("subject", "订单支付：" + order.getOrderId());
        bizContent.put("product_code", "FAST_INSTANT_TRADE_PAY");  // 固定配置
        request.setBizContent(bizContent.toString());
        // 执行请求，拿到响应的结果，返回给浏览器
        String form = "";
        try {
            form = alipayClient.pageExecute(request).getBody(); // 调用SDK生成表单
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        payVO.setPaymentForm(form);

        return payVO;
    }
}
