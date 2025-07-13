package com.example.tomatomall.controller;

import com.alipay.api.AlipayApiException;
import com.alipay.api.internal.util.AlipaySignature;
import com.example.tomatomall.enums.OrderStatus;
import com.example.tomatomall.po.Cart;
import com.example.tomatomall.po.CartOrdersRelation;
import com.example.tomatomall.repository.CartOrderRelationRepository;
import com.example.tomatomall.service.CartService;
import com.example.tomatomall.service.OrderService;
import com.example.tomatomall.service.ProductService;
import com.example.tomatomall.vo.CartVO;
import com.example.tomatomall.vo.OrderVO;
import com.example.tomatomall.vo.PayVO;
import com.example.tomatomall.vo.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private ProductService productService;

    @Autowired
    CartOrderRelationRepository cartOrderRelationRepository;

    @Autowired
    private CartService cartService;

    @Value("${alipay.alipayPublicKey}")
    private String alipayPublicKey;

    @PostMapping("/{orderId}/pay")
    public Response<PayVO> payOrder(@PathVariable Integer orderId) {
        System.out.println("==========支付==========");
        return Response.buildSuccess(orderService.payOrder(orderId));
    }

    @PostMapping("/notify")
    public void handleAlipayNotify(HttpServletRequest request, HttpServletResponse response) throws IOException, AlipayApiException {
        System.out.println("==========支付回调==========");
        // 1. 解析支付宝回调参数（通常是 application/x-www-form-urlencoded）
        Map<String, String> params = request.getParameterMap().entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, e -> e.getValue()[0]));

        // 2. 验证支付宝签名（防止伪造请求）
        boolean signVerified = AlipaySignature.rsaCheckV1(params, alipayPublicKey, "UTF-8", "RSA2");
        if (!signVerified) {
            response.getWriter().print("fail"); // 签名验证失败，返回 fail
            return;
        }

        // 3. 处理业务逻辑（更新订单、减库存等）
        String tradeStatus = params.get("trade_status");
        if ("TRADE_SUCCESS".equals(tradeStatus)) {
            Integer orderId = Integer.valueOf(params.get("out_trade_no")); // 您的订单号
            String alipayTradeNo = params.get("trade_no"); // 支付宝交易号
            String amount = params.get("total_amount"); // 支付金额

            // 更新订单状态（注意幂等性，防止重复处理）
            orderService.OrderStatusUpdate(orderId, OrderStatus.SUCCESS.toString());

            OrderVO orderVO = orderService.getOrder(orderId);
            // 扣减库存（建议加锁或乐观锁）
            //productService.sellProduct();

            for (CartOrdersRelation relation : cartOrderRelationRepository.findByOrderId(orderId)) {
                CartVO cartVO = cartService.getCartItem(relation.getCartItemId());
                productService.unfrozenProduct(cartVO.getProductId(), cartVO.getQuantity());
                productService.sellProduct(cartVO.getProductId(), cartVO.getQuantity());
            }
        }

        // 4. 必须返回纯文本的 "success"（支付宝要求）
        response.getWriter().print("success");
    }
}
