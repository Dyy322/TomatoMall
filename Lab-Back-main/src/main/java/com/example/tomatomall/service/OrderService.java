package com.example.tomatomall.service;

import com.example.tomatomall.po.Order;
import com.example.tomatomall.vo.OrderVO;
import com.example.tomatomall.vo.PayVO;
import com.example.tomatomall.vo.shippingAddressVO;

import java.util.List;
import java.util.Optional;

public interface OrderService {
    OrderVO createOrder(List<Integer> cartIds, shippingAddressVO shippingAddressVO, String paymentMethod);

    OrderVO getOrder(Integer orderId);

    List<OrderVO> getOrdersByUserId(Integer userId);

        void OrderStatusUpdate(Integer orderId, String status);

    PayVO payOrder(Integer orderId);
}
