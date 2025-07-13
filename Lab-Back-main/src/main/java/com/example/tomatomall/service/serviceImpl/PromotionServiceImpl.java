package com.example.tomatomall.service.serviceImpl;

import com.example.tomatomall.controller.PromotionController;
import com.example.tomatomall.enums.CouponStatus;
import com.example.tomatomall.exception.TomatoMallException;
import com.example.tomatomall.po.Coupon;
import com.example.tomatomall.po.Product;
import com.example.tomatomall.po.Promotion;
import com.example.tomatomall.po.PromotionProductsRelation;
import com.example.tomatomall.repository.CouponRepository;
import com.example.tomatomall.repository.ProductRepository;
import com.example.tomatomall.repository.PromotionProductsRelationRepository;
import com.example.tomatomall.repository.PromotionRepository;
import com.example.tomatomall.service.PromotionService;
import com.example.tomatomall.vo.PromotionVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class
PromotionServiceImpl implements PromotionService {
    @Autowired
    PromotionRepository PromotionRepository;

    @Autowired
    PromotionProductsRelationRepository PromotionProductsRelationRepository;

    @Autowired
    private CouponRepository couponRepository;

    @Autowired
    private ProductRepository productRepository;

    @Override
    public PromotionVO createPromotion(PromotionVO promotionVO) {
        if (PromotionRepository.findByTitle(promotionVO.getTitle()) != null) {
            throw TomatoMallException.promotionNameExists();
        }
        final Promotion promotion = promotionVO.toPO();
        Promotion savePromotion = PromotionRepository.save(promotion);
        final Integer promotionId = savePromotion.getId();

        if (promotionVO.getProductIds() != null) {
            Integer[] productIds = promotionVO.getProductIds();
            for (Integer productId : productIds) {
                PromotionProductsRelation promotionProductsRelation = new PromotionProductsRelation();
                promotionProductsRelation.setPromotionId(promotionId);
                promotionProductsRelation.setProductId(productId);
                PromotionProductsRelationRepository.save(promotionProductsRelation);
            }

            if (promotionVO.getDiscount() != null) {
                for (Integer productId : productIds) {
                    Product product = productRepository.findById(productId).orElse(null);
                    if (product != null) {
                        BigDecimal discount = BigDecimal.valueOf(promotionVO.getDiscount());
                        BigDecimal price = product.getPrice();
                        BigDecimal discountedPrice = discount.multiply(price).divide(BigDecimal.valueOf(10), 2, RoundingMode.HALF_UP);

                        product.setDiscountPrice(discountedPrice);
                        productRepository.save(product);
                    }
                }
            }
        }

        if (promotionVO.getCoupon() != null) {
            List<Coupon> coupons = promotionVO.getCoupon().stream()
                    .map(couponVO ->{
                        Coupon coupon = couponVO.toPO();
                        coupon.setPromotionId(promotionId);
                        coupon.setStatus(CouponStatus.AVAILABLE);
                        return coupon;
                    })
                    .collect(Collectors.toList());
            couponRepository.saveAll(coupons);
        }

        return promotion.toVO();
    }

    @Override
    public Boolean updatePromotion(PromotionVO promotionVO) {
        Promotion promotion = PromotionRepository.findById(promotionVO.getId()).orElse(null);
        if (promotion != null) {
            promotion = promotionVO.toPO();
            PromotionRepository.save(promotion);

            PromotionProductsRelationRepository.deleteAll(PromotionProductsRelationRepository.findByPromotionId(promotion.getId()));
            if (promotionVO.getProductIds() != null) {
                Integer[] productIds = promotionVO.getProductIds();
                for (Integer productId : productIds) {
                    PromotionProductsRelation promotionProductsRelation = new PromotionProductsRelation();
                    promotionProductsRelation.setPromotionId(promotion.getId());
                    promotionProductsRelation.setProductId(productId);
                    PromotionProductsRelationRepository.save(promotionProductsRelation);
                }

                if (promotionVO.getDiscount() != null) {
                    for (Integer productId : productIds) {
                        Product product = productRepository.findById(productId).orElse(null);
                        if (product != null) {
                            BigDecimal discount = BigDecimal.valueOf(promotionVO.getDiscount());
                            BigDecimal price = product.getPrice();
                            BigDecimal discountedPrice = discount.multiply(price).divide(BigDecimal.valueOf(10), 2, RoundingMode.HALF_UP);

                            product.setDiscountPrice(discountedPrice);
                            productRepository.save(product);
                        }
                    }
                }
            }

            if (promotionVO.getCoupon() != null) {
                List<Coupon> oldCoupons = couponRepository.findByPromotionId(promotion.getId());
                couponRepository.deleteAll(oldCoupons);
                List<Coupon> coupons = promotionVO.getCoupon().stream()
                        .map(couponVO ->{
                            Coupon coupon = couponVO.toPO();
                            coupon.setPromotionId(promotionVO.getId());
                            coupon.setStatus(CouponStatus.AVAILABLE);
                            return coupon;
                        })
                        .collect(Collectors.toList());
                couponRepository.saveAll(coupons);
            }
        }else{
            throw TomatoMallException.promotionNotExists();
        }
        return true;
    }

    @Override
    public PromotionVO getPromotionById(Integer id) {
        Promotion promotion = PromotionRepository.findById(id).orElse(null);
        if (promotion == null) {
            throw TomatoMallException.promotionNotExists();
        }
        PromotionVO promotionVO = promotion.toVO();
        List<PromotionProductsRelation> promotionProductsRelations = PromotionProductsRelationRepository.findByPromotionId(id);
        if (promotionProductsRelations != null) {
            promotionVO.setProductIds(promotionProductsRelations.stream().map(PromotionProductsRelation::getProductId).toArray(Integer[]::new));
        }

        promotionVO.setCoupon(couponRepository.findByPromotionId(id).stream().map(Coupon::toVO).collect(Collectors.toList()));
        return promotionVO;
    }

    @Override
    public List<PromotionVO> getAllPromotions() {
        List<Promotion> promotions = PromotionRepository.findAll();

        List<PromotionVO> result = promotions.stream().map(promotion -> {
            PromotionVO promotionVO = promotion.toVO();
            List<PromotionProductsRelation> promotionProductsRelations = PromotionProductsRelationRepository.findByPromotionId(promotion.getId());
            if (promotionProductsRelations != null) {
                promotionVO.setProductIds(promotionProductsRelations.stream().map(PromotionProductsRelation::getProductId).toArray(Integer[]::new));
            }
            promotionVO.setCoupon(couponRepository.findByPromotionId(promotion.getId()).stream().map(Coupon::toVO).collect(Collectors.toList()));
            return promotionVO;
        }).collect(Collectors.toList());
        return result;
    }

    @Override
    public Boolean deletePromotion(Integer id) {
        if (PromotionRepository.findById(id).orElse(null) == null) {
            throw TomatoMallException.promotionNotExists();
        }

        List<PromotionProductsRelation> promotionProductsRelations = PromotionProductsRelationRepository.findByPromotionId(id);
        if (promotionProductsRelations != null) {
            PromotionProductsRelationRepository.deleteAll(promotionProductsRelations);
        }
        List<Coupon> coupons = couponRepository.findByPromotionId(id);
        if (coupons != null) {
            couponRepository.deleteAll(coupons);
        }
        PromotionRepository.deleteById(id);
        return true;
    }
}
