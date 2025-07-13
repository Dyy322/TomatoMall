package com.example.tomatomall.repository;


import com.example.tomatomall.enums.OrderStatus;
import com.example.tomatomall.po.Account;
import com.example.tomatomall.po.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.sql.Timestamp;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order,Integer> {
    List<Order> findByAccount(Account account);
    List<Order> findByStatusAndCreateTimeBefore(OrderStatus status, Timestamp deadline);
}
