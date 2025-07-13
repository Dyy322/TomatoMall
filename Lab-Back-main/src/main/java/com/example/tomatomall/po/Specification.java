package com.example.tomatomall.po;

import com.example.tomatomall.vo.SpecificationVO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity

public class Specification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "item", nullable = false, length = 50)
    private String item;

    @Column(name = "value", nullable = false, length = 255)
    private String value;

    @Column(name = "product_id", nullable = false)
    private Integer productId;

    /**
     * 将PO转换为VO
     * @return SpecificationVO 值对象
     */
    public SpecificationVO toVO() {
        SpecificationVO vo = new SpecificationVO();
        vo.setId(this.id);
        vo.setItem(this.item);
        vo.setValue(this.value);
        vo.setProductId(this.productId);
        return vo;
    }
}