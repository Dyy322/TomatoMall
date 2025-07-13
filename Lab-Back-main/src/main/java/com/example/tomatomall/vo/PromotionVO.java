package com.example.tomatomall.vo;

import com.example.tomatomall.po.Promotion;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class PromotionVO {
    private Integer id;
    private String title;
    private String description;
    private String imgUrl;
    private String startTime;
    private String endTime;
    private Integer discount;
    private List<CouponVO> coupon;
    private Integer[] productIds;

    public Promotion toPO() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime localStartTime = LocalDateTime.parse(startTime);
        LocalDateTime localEndTime = LocalDateTime.parse(endTime);

        Promotion promotion = new Promotion();
        promotion.setId(id);
        promotion.setTitle(title);
        promotion.setDescription(description);
        promotion.setImgUrl(imgUrl);
        promotion.setStartTime(Timestamp.valueOf(localStartTime));
        promotion.setEndTime(Timestamp.valueOf(localEndTime));
        promotion.setDiscount(discount);
        return promotion;
    }
}
