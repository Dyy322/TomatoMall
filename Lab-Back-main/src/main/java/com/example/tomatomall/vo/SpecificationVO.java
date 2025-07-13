package com.example.tomatomall.vo;

import com.example.tomatomall.po.Specification;
import com.example.tomatomall.repository.SpecificationRepository;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SpecificationVO {
    private Integer id;
    private String item;
    private String value;
    private Integer productId;

    public Specification toPO() {
        Specification specification = new Specification();
        specification.setId(this.id);
        specification.setItem(this.item);
        specification.setValue(this.value);
        specification.setProductId(this.productId);
        return specification;
    }
}