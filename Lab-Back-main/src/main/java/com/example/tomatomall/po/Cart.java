package com.example.tomatomall.po;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

import com.example.tomatomall.vo.CartVO;
import com.example.tomatomall.repository.ProductRepository;

@Getter
@Setter
@Entity
@Table(name = "carts")
@Data
@NoArgsConstructor
public class Cart {
    @Id
    // 自动生成一个cartId
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cart_item_id")
    private Integer cartItemId;

    @Column(name = "user_id", nullable = false)
    private Integer userId;

    @Column(name = "product_id", nullable = false)
    private Integer productId;

    @Column(name = "quantity", nullable = false, columnDefinition = "INT DEFAULT 1")
    private Integer quantity;

    public CartVO toCartVO(){
        CartVO cartVO = new CartVO();
        cartVO.setCartItemId(this.cartItemId);
        cartVO.setUserId(this.userId);
        cartVO.setProductId(this.productId);
        cartVO.setQuantity(this.quantity);

        return cartVO;
    }
} 