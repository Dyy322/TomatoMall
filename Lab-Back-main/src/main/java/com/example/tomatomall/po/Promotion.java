package com.example.tomatomall.po;

import com.example.tomatomall.vo.PromotionVO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;

@Getter
@Setter
@Table(name = "promotions")
@NoArgsConstructor
@Entity
public class Promotion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "title",nullable = false)
    private String title;

    @Column(name = "description",nullable = false)
    private String description;

    @Column(name = "img_url",nullable = false)
    private String imgUrl;

    @Column(name = "start_time",nullable = false, columnDefinition = "DATETIME")
    Timestamp startTime;

    @Column(name = "end_time",nullable = false, columnDefinition = "DATETIME")
    Timestamp endTime;

    @Column(name = "discount",nullable = false)
    private Integer discount;

    public PromotionVO toVO() {
        PromotionVO promotionVO = new PromotionVO();
        promotionVO.setId(id);
        promotionVO.setTitle(title);
        promotionVO.setDescription(description);
        promotionVO.setImgUrl(imgUrl);
        promotionVO.setStartTime(startTime.toString());
        promotionVO.setEndTime(endTime.toString());
        promotionVO.setDiscount(discount);
        return promotionVO;
    }
}
