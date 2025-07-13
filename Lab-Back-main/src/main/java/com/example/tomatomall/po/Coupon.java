package com.example.tomatomall.po;

import com.example.tomatomall.enums.CouponStatus;
import com.example.tomatomall.vo.CouponVO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Coupon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "promotion_id")
    private Integer promotionId;

    @Column(name = "amount", nullable = false, precision = 10, scale = 2)
    private BigDecimal amount;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @Column(name = "status", nullable = false)
    private CouponStatus status;

    @Column(name = "start_time", nullable = false)
    private Timestamp startTime;

    @Column(name = "end_time", nullable = false)
    private Timestamp endTime;

    @Column(name = "min_amount", nullable = false, precision = 10, scale = 2)
    private BigDecimal minAmount;

    public CouponVO toVO() {
        CouponVO couponVO = new CouponVO();
        couponVO.setId(this.id);
        couponVO.setAmount(this.amount);
        couponVO.setQuantity(this.quantity);
        couponVO.setStatus(this.status);
        couponVO.setStartTime(this.startTime);
        couponVO.setEndTime(this.endTime);
        couponVO.setMinAmount(this.minAmount);
        return couponVO;
    }
} 