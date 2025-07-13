package com.example.tomatomall.vo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class PayVO {
    private String paymentForm;
    private Integer orderId;
    private BigDecimal totalAmount;
    private String paymentMethod;

}
