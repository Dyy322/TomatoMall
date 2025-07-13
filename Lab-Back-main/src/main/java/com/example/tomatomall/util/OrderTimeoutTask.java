package com.example.tomatomall.util;

import com.example.tomatomall.enums.OrderStatus;
import com.example.tomatomall.po.Cart;
import com.example.tomatomall.po.CartOrdersRelation;
import com.example.tomatomall.po.Order;
import com.example.tomatomall.repository.CartOrderRelationRepository;
import com.example.tomatomall.repository.OrderRepository;
import com.example.tomatomall.service.CartService;
import com.example.tomatomall.service.ProductService;
import com.example.tomatomall.vo.CartVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Component
public class OrderTimeoutTask {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    CartOrderRelationRepository cartOrderRelationRepository;

    @Autowired
    CartService cartService;

    @Autowired
    ProductService productService;

    @Scheduled(fixedRate = 5 * 60 * 1000)
    public void releaseTimedOutOrders() {
        Timestamp deadline = Timestamp.from(Instant.now().minus(30, ChronoUnit.MINUTES));

        List<Order> expiredOrders = orderRepository.findByStatusAndCreateTimeBefore(OrderStatus.PENDING, deadline);

        for (Order order : expiredOrders) {

            for (CartOrdersRelation relation : cartOrderRelationRepository.findByOrderId(order.getOrderId())) {
                CartVO cart = cartService.getCartItem(relation.getCartItemId());
                productService.unfrozenProduct(cart.getProductId(), cart.getQuantity());
            }

            order.setStatus(OrderStatus.TIMEOUT);

            orderRepository.save(order);
        }
    }
}
