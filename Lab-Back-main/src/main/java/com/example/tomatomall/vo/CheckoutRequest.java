package com.example.tomatomall.vo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class CheckoutRequest {

    private List<Integer> cartItemIds;
    private shippingAddressVO shipping_address;
    private String payment_method;

}
