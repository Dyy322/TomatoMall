package com.example.tomatomall.vo;

import com.example.tomatomall.enums.CouponStatus;
import com.example.tomatomall.po.Coupon;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
public class CouponVO {
    private Integer id;
    private Integer promotionId;
    private BigDecimal amount;
    private Integer quantity;
    private CouponStatus status;
    private Integer accountId;
    private Timestamp startTime;
    private Timestamp endTime;
    private BigDecimal minAmount;

    public Coupon toPO() {
        Coupon coupon = new Coupon();
        coupon.setId(this.id);
        coupon.setPromotionId(this.promotionId);
        coupon.setAmount(this.amount);
        coupon.setQuantity(this.quantity);
        coupon.setStatus(this.status);
        coupon.setStartTime(this.startTime);
        coupon.setEndTime(this.endTime);
        coupon.setMinAmount(this.minAmount);
        return coupon;
    }
} 