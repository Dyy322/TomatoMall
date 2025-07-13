package com.example.tomatomall.service.serviceImpl;

import com.example.tomatomall.po.Product;
import com.example.tomatomall.po.Specification;
import com.example.tomatomall.po.Stockpile;
import com.example.tomatomall.repository.ProductRepository;
import com.example.tomatomall.repository.SpecificationRepository;
import com.example.tomatomall.repository.StockpileRepository;
import com.example.tomatomall.service.ProductService;
import com.example.tomatomall.vo.ProductVO;
import com.example.tomatomall.exception.TomatoMallException;

import com.example.tomatomall.vo.SpecificationVO;
import com.example.tomatomall.vo.StockpileVO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductRepository productRepository;
    
    @Autowired
    StockpileRepository stockpileRepository;
    
    @Autowired
    SpecificationRepository specificationRepository;

    // 日志
    private static final Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);

    @Override
    public List<ProductVO> getAllProducts() {
        // 获取所有产品
        List<Product> products = productRepository.findAll();
        
        // 转换为VO并添加规格信息
        List<ProductVO> result = products.stream().map(product -> {
            ProductVO productVO = product.toVO();
            
            // 获取该产品的所有规格信息
            List<Specification> specifications = specificationRepository.findByProductId(product.getId());
            
            // 将规格信息转换为VO
            List<SpecificationVO> specificationVOs = specifications.stream()
                    .map(Specification::toVO)
                    .collect(Collectors.toList());
            
            // 设置规格信息
            productVO.setSpecifications(specificationVOs);
            
            return productVO;
        }).collect(Collectors.toList());
        
        return result;
    }

    @Override
    public ProductVO getProduct(Integer id){
        Product product = productRepository.findById(id).orElse(null);
        if (product == null) {
            throw TomatoMallException.productNotExists();
        }
        ProductVO productVO = product.toVO();
        
        // 获取该产品的所有规格信息
        List<Specification> specifications = specificationRepository.findByProductId(product.getId());
        
        // 将规格信息转换为VO
        List<SpecificationVO> specificationVOs = specifications.stream()
                .map(Specification::toVO)
                .collect(Collectors.toList());
        
        // 设置规格信息
        productVO.setSpecifications(specificationVOs);
        
        return productVO;
    }

    @Override
    public Boolean updateProductInfo(ProductVO productVO){
        // 检查商品是否存在
        // Optional<Product> 是一个容器对象，用于表示一个值可能存在也可能不存在
        Optional<Product> productOptional = productRepository.findById(productVO.getId());
        if (!productOptional.isPresent()) {
            throw TomatoMallException.productNotExists();
        }
        
        // 更新商品信息
        Product product = productVO.toPO();
        productRepository.save(product);
        
        // 如果有规格信息，更新规格信息
        // 防止规格信息为空
        if (productVO.getSpecifications() != null && !productVO.getSpecifications().isEmpty()) {
            // 删除原有规格信息
            List<Specification> oldSpecs = specificationRepository.findByProductId(productVO.getId());
            specificationRepository.deleteAll(oldSpecs);
            
            // 保存新规格信息
            List<Specification> newSpecs = productVO.getSpecifications().stream()
                    .map(specVO -> {
                        Specification spec = specVO.toPO();
                        //spec.setId(null); // 设置为null以便新建？？
                        spec.setProductId(productVO.getId());
                        return spec;
                    })
                    .collect(Collectors.toList());
            
            specificationRepository.saveAll(newSpecs);
        }
        
        logger.info("更新成功");
        return true;
    }

    @Override
    public ProductVO create(ProductVO productVO){
        if (productRepository.findByName(productVO.getTitle()) != null) {
            throw TomatoMallException.productNameAlreadyExists();
        }
        
        // 保存商品基本信息
        final Product product = productVO.toPO();
        Product savedProduct = productRepository.save(product);
        final Integer productId = savedProduct.getId();
        
        // 如果有规格信息，保存规格信息
        if (productVO.getSpecifications() != null && !productVO.getSpecifications().isEmpty()) {
            List<Specification> specs = productVO.getSpecifications().stream()
                    .map(specVO -> {
                        Specification spec = specVO.toPO();
                        //spec.setId(null); // 设置为null以便新建？？
                        spec.setProductId(productId);
                        return spec;
                    })
                    .collect(Collectors.toList());
            
            specificationRepository.saveAll(specs);
        }
        
        // 初始化库存
        Stockpile stockpile = new Stockpile();
        stockpile.setProductId(productId);
        stockpile.setAmount(0);
        stockpile.setFrozen(0);
        stockpileRepository.save(stockpile);
        
        // logger.info("新商品[{}]已创建", productVO.getTitle());
        return product.toVO();
    }

    @Override
    public Boolean delete(Integer id) {
        // 检查商品是否存在
        if (!productRepository.existsById(id)) {
            throw TomatoMallException.productNotExists();
        }
        
        // 删除商品相关的规格信息
        List<Specification> specs = specificationRepository.findByProductId(id);
        if (!specs.isEmpty()) {
            specificationRepository.deleteAll(specs);
        }
        
        // 删除商品库存信息
        Stockpile stockpile = stockpileRepository.findByProductId(id);
        if (stockpile != null) {
            stockpileRepository.delete(stockpile);
        }
        
        // 删除商品
        productRepository.deleteById(id);
        
        logger.info("商品删除");
        return true;
    }

    @Override
    @Transactional
    public Boolean alterProductStock(Integer id, Integer number) {
        // 检查商品是否存在
        if (!productRepository.existsById(id)) {
            throw TomatoMallException.productNotExists();
        }
        
        // 获取库存信息
        Stockpile stockpile = stockpileRepository.findByProductId(id);
        if (stockpile == null) {
            // 如果库存记录不存在，创建一个新的
            stockpile = new Stockpile();
            stockpile.setProductId(id);
            stockpile.setAmount(0);
            stockpile.setFrozen(0);
        }
        
        // 调整库存数量
        //int currentAmount = stockpile.getAmount();
        int newAmount = number;
        
        // 库存不能为负数
        if (newAmount < 0) {
            throw TomatoMallException.stockNotEnough();
        }
        
        stockpile.setAmount(newAmount);
        stockpileRepository.save(stockpile);
        
        logger.info("商品库存已调整");
        return true;
    }

    @Override
    public StockpileVO getProductStock(Integer productId){
        Stockpile stockpile = stockpileRepository.findByProductId(productId);
        if (stockpile == null) {
            throw TomatoMallException.productNotExists();
        }
        return stockpile.toVO();
    }

    @Override
    public void frozenProduct(Integer id, Integer number) {
        Stockpile stockpile = stockpileRepository.findByProductId(id);
        if (stockpile.getAmount() < number) {
            throw TomatoMallException.stockNotEnough();
        }
        stockpile.setFrozen(stockpile.getFrozen() + number);
        stockpile.setAmount(stockpile.getAmount() - number);
        stockpileRepository.save(stockpile);
    }

    @Override
    public void unfrozenProduct(Integer id, Integer number) {
        Stockpile stockpile = stockpileRepository.findByProductId(id);
        if (stockpile.getFrozen() < number) {
            throw TomatoMallException.stockNotEnough();
        }
        stockpile.setFrozen(stockpile.getFrozen() - number);
        stockpile.setAmount(stockpile.getAmount() + number);
        stockpileRepository.save(stockpile);
    }

    @Override
    public void sellProduct(Integer id, Integer number) {
        Stockpile stockpile = stockpileRepository.findByProductId(id);
        if (stockpile.getFrozen() < number) {
            throw TomatoMallException.stockNotEnough();
        }
        stockpile.setFrozen(stockpile.getFrozen() - number);
    }

    @Override
    public List<ProductVO> search(String keyword) {
        if (keyword != null && !keyword.isEmpty()) {
            return productRepository.search(keyword).stream()
                   .map(Product::toVO)
                   .collect(Collectors.toList());
        }
        return getAllProducts();
    }


}
