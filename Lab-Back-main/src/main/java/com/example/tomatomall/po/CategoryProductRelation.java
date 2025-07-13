package com.example.tomatomall.po;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Table(name = "category_product_relation")
@NoArgsConstructor
@Entity
public class CategoryProductRelation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Column(name = "category_id")
    private Integer categoryId;
    @Column(name = "product_id")
    private Integer productId;

}
