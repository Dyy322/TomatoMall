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
public class CouponAccountRelation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "coupon_id")
    private Integer couponId;

    @Column(name = "account_id")
    private Integer accountId;
}
