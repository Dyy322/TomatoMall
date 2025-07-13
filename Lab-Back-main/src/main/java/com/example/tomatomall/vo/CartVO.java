package com.example.tomatomall.vo;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

import com.example.tomatomall.po.Cart;

@Getter
@Setter
@Data
@NoArgsConstructor
public class CartVO {
    private Integer cartItemId;
    private Integer userId;
    private Integer productId;
    private Integer quantity;

    // 详见product.java，用于返回给前端
    private String detail; // 商品详情
    private String description; // 商品描述
    private String title;       // 商品标题
    private BigDecimal price;   // 商品价格
    private String cover;       // 商品封面图

    public Cart toCart(){
        Cart cart = new Cart();
        cart.setCartItemId(this.cartItemId);
        cart.setUserId(this.userId);
        cart.setProductId(this.productId);
        cart.setQuantity(this.quantity);
        return cart;
    }
} 