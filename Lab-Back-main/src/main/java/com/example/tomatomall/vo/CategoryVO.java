package com.example.tomatomall.vo;

import com.example.tomatomall.po.Category;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CategoryVO {

    private Integer id;
    private String name;

    public Category toPO(){
        Category category = new Category();
        category.setId(this.id);
        category.setName(this.name);
        return category;
    }
}
