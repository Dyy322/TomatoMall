package com.example.tomatomall.service;

import com.example.tomatomall.vo.CouponVO;

import java.math.BigDecimal;
import java.util.List;

public interface CouponService {
    
    /**
     * 发放优惠券
     */
    boolean issueCoupon(CouponVO couponVO);
    
    /**
     * 领取优惠券
     */
    boolean claimCoupon(Integer couponId);
    
    /**
     * 查看某个用户所有优惠券
     */
    List<CouponVO> getUserCoupons();
    
    /**
     * 使用优惠券
     */
    boolean useCoupon(Integer couponId, BigDecimal orderAmount);
    
    /**
     * 获取可领取的优惠券列表
     */
    List<CouponVO> getAvailableCoupons();
} 