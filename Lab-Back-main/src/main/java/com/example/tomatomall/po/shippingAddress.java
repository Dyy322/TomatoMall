package com.example.tomatomall.po;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Table(name = "shipping_address")
@NoArgsConstructor
@Entity
public class shippingAddress {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id",nullable = false, insertable = false, updatable = false)
    private Account account;
    @Column(name = "name")
    private String name;
    @Column(name = "telephone")
    private String telephone;
    @Column(name = "location")
    private String location;
}
