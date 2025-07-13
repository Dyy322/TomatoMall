package com.example.tomatomall.repository;

import com.example.tomatomall.po.CouponAccountRelation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CouponAccountRelationRepository extends JpaRepository<CouponAccountRelation, Integer> {
    List<CouponAccountRelation> findByAccountId(Integer accountId);
}
