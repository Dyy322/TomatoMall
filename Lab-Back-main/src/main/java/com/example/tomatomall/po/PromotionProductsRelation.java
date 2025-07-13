package com.example.tomatomall.po;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Table(name = "promotion_products_relation")
@NoArgsConstructor
@Entity
public class PromotionProductsRelation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "promotion_id")
    private Integer promotionId;

    @Column(name = "product_id")
    private Integer productId;
}
