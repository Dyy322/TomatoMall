package com.example.tomatomall.repository;

import com.example.tomatomall.po.Cart;
import com.example.tomatomall.vo.CartVO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, Integer> {
    /**
     * 根据用户ID查找所有购物车项
     */
    List<Cart> findByUserId(Integer userId);
    
    /**
     * 根据用户ID和商品ID查找购物车项
     */
    Optional<Cart> findByUserIdAndProductId(Integer userId, Integer productId);
    
    /**
     * 根据购物车项ID删除购物车项
     */
    void deleteByCartItemId(Integer cartItemId);
    
    /**
     * 根据购物车项ID和用户ID查找购物车项
     */
    Optional<Cart> findByCartItemIdAndUserId(Integer cartItemId, Integer userId);
    
    /**
     * 根据购物车项ID列表和用户ID查找购物车项列表
     */
    List<Cart> findByCartItemIdInAndUserId(List<Integer> cartItemIds, Integer userId);

    Cart findByCartItemId(Integer cartItemId);
}