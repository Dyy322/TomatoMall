package com.example.tomatomall.service;

import com.example.tomatomall.vo.CartVO;
import java.util.List;

public interface CartService {
    
    /**
     * 添加商品到购物车
     * @param productId 商品ID
     * @param quantity 商品数量
     * @return 添加到购物车的商品信息
     */
    CartVO addToCart(Integer productId, Integer quantity);
    
    /**
     * 从购物车中删除商品
     * @param cartItemId 购物车商品ID
     */
    void removeFromCart(Integer cartItemId);
    
    /**
     * 修改购物车商品数量
     * @param cartItemId 购物车商品ID
     * @param quantity 新的商品数量
     */
    void updateCartQuantity(Integer cartItemId, Integer quantity);
    
    /**
     * 获取用户购物车商品列表
     * @return 购物车信息，包含商品列表、总数量和总金额
     */
    List<CartVO> getCartItems();

    CartVO getCartItem(Integer cartItemId);
    
} 