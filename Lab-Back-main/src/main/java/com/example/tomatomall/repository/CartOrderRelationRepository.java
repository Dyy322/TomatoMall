package com.example.tomatomall.repository;

import com.example.tomatomall.po.CartOrdersRelation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartOrderRelationRepository extends JpaRepository<CartOrdersRelation, Integer> {
    List<CartOrdersRelation> findByOrderId(Integer orderId);
}
