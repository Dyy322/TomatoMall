package com.example.tomatomall.repository;

import com.example.tomatomall.po.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product,Integer> {
    @Query(value = "select * from product where BINARY title = :name", nativeQuery = true)
    Product findByName(String name);

    @Query(value = "select * from product where BINARY title like %:keyword% or BINARY description like %:keyword% or BINARY detail like %:keyword%", nativeQuery = true)
    List<Product> search(String keyword);
}
