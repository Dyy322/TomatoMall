package com.example.tomatomall.vo;

import com.example.tomatomall.enums.OrderStatus;

import com.example.tomatomall.po.Order;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.math.BigDecimal;
import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
public class OrderVO {
    private Integer orderId;

    private AccountVO accountVO;

    private BigDecimal totalAmount;
    private String paymentMethod;
    private OrderStatus status;

    Timestamp createTime;

    public Order toPO(){
        Order order = new Order();
        order.setOrderId(this.orderId);
        order.setAccount(this.accountVO.toPO());
        order.setTotalAmount(this.totalAmount);
        order.setStatus(this.status);
        order.setCreateTime(this.createTime);
        order.setPaymentMethod(this.paymentMethod);
        return order;
    }
}
