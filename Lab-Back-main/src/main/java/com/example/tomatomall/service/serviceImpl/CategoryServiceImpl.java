package com.example.tomatomall.service.serviceImpl;

import com.example.tomatomall.exception.TomatoMallException;
import com.example.tomatomall.po.Category;
import com.example.tomatomall.po.CategoryProductRelation;
import com.example.tomatomall.repository.CategoryProductRelationRepository;
import com.example.tomatomall.repository.CategoryRepository;
import com.example.tomatomall.repository.ProductRepository;
import com.example.tomatomall.service.CategoryService;
import com.example.tomatomall.vo.CategoryVO;
import com.example.tomatomall.vo.ProductVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    CategoryProductRelationRepository categoryProductRelationRepository;

    @Autowired
    ProductRepository productRepository;

    @Override
    public Boolean createCategory(String categoryName) {
        if(categoryRepository.findByName(categoryName)!=null){
            throw TomatoMallException.categoryAlreadyExists();
        }
        Category category = new Category();
        category.setName(categoryName);
        categoryRepository.save(category);
        return true;
    }

    @Override
    public Boolean deleteCategory(Integer categoryId) {
        if(categoryRepository.findById(categoryId).isPresent()){

            List<CategoryProductRelation> categoryProductRelations = categoryProductRelationRepository.findByCategoryId(categoryId);
            for (CategoryProductRelation categoryProductRelation:categoryProductRelations){
                categoryProductRelationRepository.deleteById(categoryProductRelation.getId());
            }
            categoryRepository.deleteById(categoryId);
            return true;
        }
        return true;
    }

    @Override
    public List<CategoryVO> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();

        return categories.stream().map(Category::toVO).collect(Collectors.toList());
    }

    @Override
    public Boolean addProductToCategory(Integer categoryId, Integer productId) {
        if(!categoryRepository.findById(categoryId).isPresent()){
            throw TomatoMallException.categoryNotExists();
        }
        if(!productRepository.findById(productId).isPresent()){
            throw TomatoMallException.productNotExists();
        }
        if(categoryProductRelationRepository.findByCategoryIdAndProductId(categoryId,productId)!=null){
            throw TomatoMallException.categoryProductRelationAlreadyExists();
        }
        CategoryProductRelation categoryProductRelation = new CategoryProductRelation();
        categoryProductRelation.setCategoryId(categoryId);
        categoryProductRelation.setProductId(productId);
        categoryProductRelationRepository.save(categoryProductRelation);
        return true;
    }

    @Override
    public Boolean removeProductFromCategory(Integer categoryId, Integer productId) {
        if(categoryProductRelationRepository.findByCategoryIdAndProductId(categoryId,productId)!=null){
            Integer id = categoryProductRelationRepository.findByCategoryIdAndProductId(categoryId,productId).getId();
            categoryProductRelationRepository.deleteById(id);
            return true;
        }
        return true;
    }

    @Override
    public List<CategoryVO> findCategoriesByProductId(Integer productId) {
        List<CategoryProductRelation> categoryProductRelations = categoryProductRelationRepository.findByProductId(productId);
        List<CategoryVO> categoryVOS = new ArrayList<>();
        for(CategoryProductRelation categoryProductRelation:categoryProductRelations){
            Integer categoryId = categoryProductRelation.getCategoryId();
            Category category = categoryRepository.findById(categoryId).get();
            CategoryVO categoryVO = category.toVO();
            categoryVOS.add(categoryVO);
        }
        return categoryVOS;
    }

    @Override
    public List<ProductVO> findProductsByCategoryId(Integer categoryId) {
        List<CategoryProductRelation> categoryProductRelations = categoryProductRelationRepository.findByCategoryId(categoryId);
        List<ProductVO> productVOS = new ArrayList<>();
        for(CategoryProductRelation categoryProductRelation:categoryProductRelations){
            Integer productId = categoryProductRelation.getProductId();
            ProductVO productVO = productRepository.findById(productId).get().toVO();
            productVOS.add(productVO);
        }
        return productVOS;
    }
}
