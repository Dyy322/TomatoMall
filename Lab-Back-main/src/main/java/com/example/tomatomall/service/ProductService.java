package com.example.tomatomall.service;

import com.example.tomatomall.vo.ProductVO;
import com.example.tomatomall.vo.StockpileVO;

import java.util.List;

public interface ProductService {
    // 获取商品列表
    List<ProductVO> getAllProducts();
    // 获取指定商品信息
    ProductVO getProduct(Integer id);
    // 更新商品信息 
    Boolean updateProductInfo(ProductVO productVO);
    // 增加商品
    ProductVO create(ProductVO productVO);
    // 删除商品
    Boolean delete(Integer id);
    // 调整指定商品的库存
    Boolean alterProductStock(Integer id, Integer number);
    // 查询指定商品的库存 1
    StockpileVO getProductStock(Integer id);

    // 冻结指定商品的库存
    void frozenProduct(Integer id, Integer number);
    // 解冻指定商品的库存
    void unfrozenProduct(Integer id, Integer number);
    // 卖出指定商品的库存
    void sellProduct(Integer id, Integer number);

    List<ProductVO> search(String keyword);
}

