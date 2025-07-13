package com.example.tomatomall.po;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Table(name = "ad_product_relation")
@NoArgsConstructor
@Entity
public class AdProductRelation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "advertisement_id")
    private Integer advertisementId;

    @Column(name = "product_id")
    private Integer productId;
}
