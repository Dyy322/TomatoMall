package com.example.tomatomall.controller;

import com.example.tomatomall.service.CategoryService;
import com.example.tomatomall.vo.CategoryVO;
import com.example.tomatomall.vo.ProductVO;
import com.example.tomatomall.vo.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/category")
public class CategoryController {

    @Autowired
    CategoryService categoryService;

    @PostMapping("/create")
    public Response<Boolean> createCategory(@RequestParam("name") String name) {
        return Response.buildSuccess(categoryService.createCategory(name));
    }

    @PostMapping("/delete")
    public Response<Boolean> deleteCategory(@RequestParam("id") Integer id) {
        return Response.buildSuccess(categoryService.deleteCategory(id));
    }

    @GetMapping("/allCategories")
    public Response<List<CategoryVO>> getAllCategories() {
        return Response.buildSuccess(categoryService.getAllCategories());
    }

    @PostMapping("/addProduct2Category")
    public Response<Boolean> addProduct2Category(@RequestParam("categoryId") Integer categoryId, @RequestParam("productId") Integer productId) {
        return Response.buildSuccess(categoryService.addProductToCategory(categoryId, productId));
    }

    @PostMapping("/removeProductFromCategory")
    public Response<Boolean> removeProductFromCategory(@RequestParam("categoryId") Integer categoryId, @RequestParam("productId") Integer productId) {
        return Response.buildSuccess(categoryService.removeProductFromCategory(categoryId, productId));
    }

    @GetMapping("/getProductsByCategory")
    public Response<List<ProductVO>> getProductsByCategory(@RequestParam("categoryId") Integer categoryId) {
        return Response.buildSuccess(categoryService.findProductsByCategoryId(categoryId));
    }

    @GetMapping("/getCategoriesByProduct")
    public Response<List<CategoryVO>> getCategoriesByProduct(@RequestParam("productId") Integer productId) {
        return Response.buildSuccess(categoryService.findCategoriesByProductId(productId));
    }

}
