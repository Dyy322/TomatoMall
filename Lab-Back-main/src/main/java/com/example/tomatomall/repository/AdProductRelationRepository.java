package com.example.tomatomall.repository;

import com.example.tomatomall.po.AdProductRelation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AdProductRelationRepository extends JpaRepository<AdProductRelation, Integer> {
    List<AdProductRelation> findByAdvertisementId(Integer advertisementId);
}
