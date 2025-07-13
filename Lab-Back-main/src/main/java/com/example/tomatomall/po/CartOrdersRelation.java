package com.example.tomatomall.po;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Table(name = "cart_orders_relation")
@NoArgsConstructor
@Entity
public class CartOrdersRelation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Column(name = "cart_item_id")
    private Integer cartItemId;
    @Column(name = "order_id")
    private Integer orderId;
}
