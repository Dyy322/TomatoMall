package com.example.tomatomall.po;

import com.example.tomatomall.vo.StockpileVO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Stockpile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "product_id", nullable = false)
    private Integer productId;

    @Column(name = "amount", nullable = false)
    private Integer amount;

    @Column(name = "frozen", nullable = false)
    private Integer frozen;

    /**
     * 转换为VO对象
     * @return StockpileVO
     */
    public StockpileVO toVO() {
        StockpileVO vo = new StockpileVO();
        vo.setId(this.id);
        vo.setProductId(this.productId);
        vo.setAmount(this.amount);
        vo.setFrozen(this.frozen);
        return vo;
    }
}
