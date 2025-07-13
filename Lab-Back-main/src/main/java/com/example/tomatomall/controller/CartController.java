package com.example.tomatomall.controller;

import com.example.tomatomall.exception.TomatoMallException;
import com.example.tomatomall.po.Account;
import com.example.tomatomall.service.CartService;
import com.example.tomatomall.service.OrderService;
import com.example.tomatomall.util.SecurityUtil;
import com.example.tomatomall.vo.CartVO;
import com.example.tomatomall.vo.CheckoutRequest;
import com.example.tomatomall.vo.OrderVO;
import com.example.tomatomall.vo.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @Autowired
    private SecurityUtil securityUtil;

    @Autowired
    private OrderService orderService;


    /**
     * 添加商品到购物车
     * POST /api/cart
     */
    @PostMapping
    public Response<CartVO> addToCart(@RequestBody Map<String, Object> params) {
        Account currentAccount = securityUtil.getCurrentAccount();
        if (currentAccount == null) {
            throw TomatoMallException.needLogin();
        }

        try {
            if (!params.containsKey("productId") || !params.containsKey("quantity")) {
                throw new TomatoMallException("商品ID和数量不能为空");
            }
            
            Integer productId = Integer.parseInt(params.get("productId").toString());
            Integer quantity = Integer.parseInt(params.get("quantity").toString());
            
            if (quantity <= 0) {
                throw new TomatoMallException("商品数量必须大于0");
            }

            CartVO cartVO = cartService.addToCart(productId, quantity);
            return Response.buildSuccess(cartVO);
        } catch (NumberFormatException e) {
            throw new TomatoMallException("商品ID和数量必须为数字");
        } catch (TomatoMallException e) {
            throw e;
        } catch (Exception e) {
            throw new TomatoMallException(e.getMessage());
        }
    }

    /**
     * 删除购物车商品
     * DELETE /api/cart/{cartItemId}
     */
    @DeleteMapping("/{cartItemId}")
    public Response<String> removeFromCart(@PathVariable Integer cartItemId) {
        Account currentAccount = securityUtil.getCurrentAccount();
        if (currentAccount == null) {
            throw TomatoMallException.needLogin();
        }

        try {
            // 尝试删除购物车商品，如果不存在会抛出异常
            try {
                cartService.removeFromCart(cartItemId);
            } catch (Exception e) {
                // 处理购物车商品不存在的情况
                if (e.getMessage() != null && e.getMessage().contains("购物车商品不存在")) {
                    throw TomatoMallException.cartItemNotExists();
                }
                throw e;
            }
            return Response.buildSuccess("删除成功");
        } catch (TomatoMallException e) {
            throw e;
        } catch (Exception e) {
            throw new TomatoMallException(e.getMessage());
        }
    }

    /**
     * 修改购物车商品数量
     * PATCH /api/cart/{cartItemId}
     */
    @PatchMapping("/{cartItemId}")
    public Response<String> updateCartQuantity(@PathVariable Integer cartItemId, @RequestBody Map<String, Object> params) {
        // 注意文档中要求使用json格式传递quantity，故使用@RequestBody Map<String, Object> params
        Account currentAccount = securityUtil.getCurrentAccount();
        if (currentAccount == null) {
            throw TomatoMallException.needLogin();
        }

        try {
            if (!params.containsKey("quantity")) {
                throw new TomatoMallException("数量不能为空");
            }
            
            Integer quantity = Integer.parseInt(params.get("quantity").toString());
            
            if (quantity <= 0) {
                throw new TomatoMallException("商品数量必须大于0");
            }
            
            // 尝试更新购物车商品数量，如果不存在会抛出异常
            try {
                cartService.updateCartQuantity(cartItemId, quantity);
            } catch (Exception e) {
                // 处理购物车商品不存在的情况
                if (e.getMessage() != null && e.getMessage().contains("购物车商品不存在")) {
                    throw TomatoMallException.cartItemNotExists();
                }
                throw e;
            }
            return Response.buildSuccess("修改数量成功");
        } catch (NumberFormatException e) {
            throw new TomatoMallException("数量必须为数字");
        } catch (TomatoMallException e) {
            throw e;
        } catch (Exception e) {
            throw new TomatoMallException(e.getMessage());
        }
    }

    /**
     * 获取购物车商品列表
     * GET /api/cart
     */
    @GetMapping
    public Response<List<CartVO>> getCartItems() {
        Account currentAccount = securityUtil.getCurrentAccount();
        if (currentAccount == null) {
            throw TomatoMallException.needLogin();
        }

        try {
            List<CartVO> cartItems = cartService.getCartItems();
            return Response.buildSuccess(cartItems);
        } catch (TomatoMallException e) {
            throw e;
        } catch (Exception e) {
            throw new TomatoMallException(e.getMessage());
        }
    }

    @PostMapping("/checkout")
    public Response<OrderVO> checkout(@RequestBody CheckoutRequest checkoutRequest) {
        return Response.buildSuccess(orderService.createOrder(checkoutRequest.getCartItemIds(), checkoutRequest.getShipping_address(), checkoutRequest.getPayment_method()));
    }
} 