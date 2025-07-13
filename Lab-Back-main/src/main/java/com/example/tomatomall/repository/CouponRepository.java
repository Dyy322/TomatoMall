package com.example.tomatomall.repository;

import com.example.tomatomall.po.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CouponRepository extends JpaRepository<Coupon, Integer> {
    List<Coupon> findByPromotionId(Integer promotionId);
    
    @Query("SELECT c FROM Coupon c WHERE c.quantity > 0")
    List<Coupon> findAvailableForClaim();
} 