package com.example.tomatomall.po;

import com.example.tomatomall.vo.CategoryVO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name",nullable = false)
    private String name;


    public CategoryVO toVO(){
        CategoryVO categoryVO = new CategoryVO();
        categoryVO.setId(this.id);
        categoryVO.setName(this.name);
        return categoryVO;
    }

}

