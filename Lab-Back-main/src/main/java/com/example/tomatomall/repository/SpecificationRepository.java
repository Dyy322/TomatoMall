package com.example.tomatomall.repository;

import com.example.tomatomall.po.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SpecificationRepository extends JpaRepository<Specification, Integer> {
    @Query(value = "select * from specification where product_id = :ProductId", nativeQuery = true)
    List<Specification> findByProductId(Integer ProductId);
}
