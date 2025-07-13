package com.example.tomatomall.controller;

import com.example.tomatomall.service.CouponService;
import com.example.tomatomall.vo.CouponVO;
import com.example.tomatomall.vo.Response;
import com.example.tomatomall.vo.UseCouponRequest;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/coupons")
public class CouponController {

    @Resource
    private CouponService couponService;

    /**
     * 发放优惠券
     */
    @PostMapping("/issue")
    public Response<String> issueCoupon(@RequestBody CouponVO couponVO) {
        if (couponService.issueCoupon(couponVO)) {
            return Response.buildSuccess("优惠券发放成功");
        }
        return Response.buildFailure("优惠券发放失败", "500");
    }

    /**
     * 领取优惠券
     */
    @PostMapping("/{couponId}/claim")
    public Response<String> claimCoupon(@PathVariable Integer couponId) {
        if (couponService.claimCoupon(couponId)) {
            return Response.buildSuccess("优惠券领取成功");
        }
        return Response.buildFailure("优惠券领取失败", "500");
    }

    /**
     * 查看某个用户所有优惠券
     */
    @GetMapping("/user")
    public Response<List<CouponVO>> getUserCoupons() {
        List<CouponVO> coupons = couponService.getUserCoupons();
        if (coupons != null) {
            return Response.buildSuccess(coupons);
        }
        return Response.buildFailure("获取用户优惠券失败", "500");
    }

    /**
     * 使用优惠券
     */
    @PostMapping("/use")
    public Response<String> useCoupon(@RequestBody UseCouponRequest request) {
        if (couponService.useCoupon(request.getCouponId(), request.getOrderAmount())) {
            return Response.buildSuccess("优惠券使用成功");
        }
        return Response.buildFailure("优惠券使用失败", "500");
    }

    /**
     * 获取可领取的优惠券列表
     */
    @GetMapping("/available")
    public Response<List<CouponVO>> getAvailableCoupons() {
        List<CouponVO> coupons = couponService.getAvailableCoupons();
        return Response.buildSuccess(coupons);
    }
} 