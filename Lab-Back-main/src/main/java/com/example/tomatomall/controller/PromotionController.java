package com.example.tomatomall.controller;

import com.example.tomatomall.service.PromotionService;
import com.example.tomatomall.vo.PromotionVO;
import com.example.tomatomall.vo.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/promotions")
public class PromotionController {
    @Autowired
    PromotionService promotionService;

    @PostMapping
    public Response<PromotionVO> createPromotion(@RequestBody PromotionVO promotionVO) {
        return Response.buildSuccess(promotionService.createPromotion(promotionVO));
    }

    @GetMapping
    public Response<List<PromotionVO>> getAllPromotions() {
        return Response.buildSuccess(promotionService.getAllPromotions());
    }

    @GetMapping("/{id}")
    public Response<PromotionVO> getPromotionById(@PathVariable Integer id) {
        return Response.buildSuccess(promotionService.getPromotionById(id));
    }

    @PutMapping
    public Response<String> updatePromotion(@RequestBody PromotionVO promotionVO) {
        return promotionService.updatePromotion(promotionVO) ? Response.buildSuccess("更新成功") : Response.buildFailure("更新失败", "400");
    }

    @DeleteMapping("/{id}")
    public Response<String> deletePromotion(@PathVariable Integer id) {
        return promotionService.deletePromotion(id) ? Response.buildSuccess("删除成功") : Response.buildFailure("删除失败", "400");
    }
}
