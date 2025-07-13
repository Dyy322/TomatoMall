package com.example.tomatomall.repository;

import com.example.tomatomall.po.Promotion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PromotionRepository extends JpaRepository<Promotion, Integer> {
    Promotion findByTitle(String title);
}
