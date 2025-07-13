package com.example.tomatomall.vo;

import com.example.tomatomall.enums.Role;
import com.example.tomatomall.po.Product;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ProductVO {
    private Integer id;
    private String title;
    private BigDecimal price;
    private BigDecimal discountPrice;
    private Double rate;
    private String description;
    private String cover;
    private String detail;
    // 注意可以填写规格信息
    private List<SpecificationVO> specifications;

    public Product toPO(){
        Product product = new Product();
        product.setId(this.id);
        product.setTitle(this.title);
        product.setPrice(this.price);
        product.setDiscountPrice(this.discountPrice);
        product.setRate(this.rate);
        product.setDescription(this.description);
        product.setCover(this.cover);
        product.setDetail(this.detail);
        return product;
    }
}