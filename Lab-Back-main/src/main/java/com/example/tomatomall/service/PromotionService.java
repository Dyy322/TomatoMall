package com.example.tomatomall.service;

import com.example.tomatomall.vo.ProductVO;
import com.example.tomatomall.vo.PromotionVO;

import java.util.List;

public interface PromotionService {
    // 创建活动
    PromotionVO createPromotion(PromotionVO promotionVO);

    // 修改活动
    Boolean updatePromotion(PromotionVO promotionVO);

    // 获取指定活动信息
    PromotionVO getPromotionById(Integer id);

    // 获取所有活动信息
    List<PromotionVO> getAllPromotions();

    // 删除活动
    Boolean deletePromotion(Integer id);

}
