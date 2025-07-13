package com.example.tomatomall.controller;

import com.example.tomatomall.service.ProductService;
import com.example.tomatomall.vo.ProductVO;
import com.example.tomatomall.vo.Response;
import com.example.tomatomall.vo.StockpileVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    ProductService productService;
    
    /**
     * 获取所有商品信息
     */
    @GetMapping
    public Response<List<ProductVO>> getAllProducts() {
        return Response.buildSuccess(productService.getAllProducts());
    }
    
    /**
     * 获取单个商品详情
     */
    @GetMapping("/{id}")
    public Response<ProductVO> getProduct(@PathVariable Integer id) {
        return Response.buildSuccess(productService.getProduct(id));
    }
    
    /**
     * 创建新商品
     */
    @PostMapping
    public Response<ProductVO> createProduct(@RequestBody ProductVO productVO) {
        ProductVO product = productService.create(productVO);
        if (product!=null) {
            return Response.buildSuccess(product);
        }
        return Response.buildFailure("商品创建失败", "400");
    }
    
    /**
     * 更新商品信息
     */
    @PutMapping("")
    public Response<String> updateProduct(@RequestBody ProductVO productVO) {
        if (productService.updateProductInfo(productVO)) {
            return Response.buildSuccess("更新成功");
        }
        return Response.buildFailure("商品不存在", "400");
    }
    
    /**
     * 删除商品
     */
    @DeleteMapping("/{id}")
    public Response<String> deleteProduct(@PathVariable Integer id) {
        if (productService.delete(id)) {
            return Response.buildSuccess("删除成功");
        }
        return Response.buildFailure("商品不存在", "400");
    }
    
    /**
     * 获取商品库存
     */
    @GetMapping("/stockpile/{id}")
    public Response<StockpileVO> getProductStock(@PathVariable Integer id) {
        return Response.buildSuccess(productService.getProductStock(id));
    }
    
    /**
     * 调整商品库存
     */
    @PatchMapping("/stockpile/{id}")
    public Response<String> alterProductStock(@PathVariable Integer id, @RequestBody StockpileVO stockpileVO) {
        if (productService.alterProductStock(id, stockpileVO.getAmount())) {
            return Response.buildSuccess("调整库存成功");
        }
        return Response.buildFailure("商品不存在", "400");
    }

    @GetMapping("/search")
    public Response<List<ProductVO>> searchProducts(@RequestParam("keyword") String keyword) {
        return Response.buildSuccess(productService.search(keyword));
    }
}
