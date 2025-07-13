package com.example.tomatomall.repository;

import com.example.tomatomall.po.CategoryProductRelation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryProductRelationRepository extends JpaRepository<CategoryProductRelation, Integer> {

    CategoryProductRelation findByCategoryIdAndProductId(Integer categoryId, Integer productId);

    List<CategoryProductRelation> findByCategoryId(Integer categoryId);

    List<CategoryProductRelation> findByProductId(Integer productId);
}
