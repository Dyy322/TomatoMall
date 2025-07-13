package com.example.tomatomall.repository;

import com.example.tomatomall.po.PromotionProductsRelation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PromotionProductsRelationRepository extends JpaRepository<PromotionProductsRelation,Integer> {
    List<PromotionProductsRelation> findByPromotionId(Integer promotionId);
}
