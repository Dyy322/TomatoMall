package com.example.tomatomall.service;

import com.example.tomatomall.po.Category;
import com.example.tomatomall.vo.CategoryVO;
import com.example.tomatomall.vo.ProductVO;

import java.util.List;

public interface CategoryService {

    Boolean createCategory(String categoryName);

    Boolean deleteCategory(Integer categoryId);

    List<CategoryVO> getAllCategories();

    Boolean addProductToCategory(Integer categoryId, Integer productId);

    Boolean removeProductFromCategory(Integer categoryId, Integer productId);

    List<CategoryVO> findCategoriesByProductId(Integer productId);

    List<ProductVO> findProductsByCategoryId(Integer categoryId);

}
