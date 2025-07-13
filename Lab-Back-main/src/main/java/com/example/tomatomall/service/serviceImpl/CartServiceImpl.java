package com.example.tomatomall.service.serviceImpl;

import com.example.tomatomall.exception.TomatoMallException;
import com.example.tomatomall.po.Cart;
import com.example.tomatomall.po.Product;
import com.example.tomatomall.po.Stockpile;
import com.example.tomatomall.repository.CartRepository;
import com.example.tomatomall.repository.ProductRepository;
import com.example.tomatomall.repository.StockpileRepository;
import com.example.tomatomall.service.CartService;
import com.example.tomatomall.util.SecurityUtil;
import com.example.tomatomall.vo.CartVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private StockpileRepository stockpileRepository;
    
    @Autowired
    private SecurityUtil securityUtil;

    @Override
    @Transactional
    public CartVO addToCart(Integer productId, Integer quantity) {
        // 获取当前登录用户ID
        Integer userId = securityUtil.getCurrentAccount().getId();

        Optional<Product> productOptional = productRepository.findById(productId);
        if (!productOptional.isPresent()) {
            throw TomatoMallException.productNotExists();
        }
        Product product = productOptional.get();

        Stockpile stockpile = stockpileRepository.findByProductId(productId);
        if (stockpile == null || stockpile.getAmount() < quantity) {
            throw TomatoMallException.stockNotEnough();
        }
        Optional<Cart> existingCartItem = cartRepository.findByUserIdAndProductId(userId, productId);
        Cart cart;
        cart = existingCartItem.get();
        int newQuantity = cart.getQuantity() + quantity;
        cart.setQuantity(newQuantity);
        cart = cartRepository.save(cart);

        CartVO cartVO = cart.toCartVO();
        // 添加商品信息
        cartVO.setTitle(product.getTitle());
        cartVO.setPrice(product.getPrice());
        cartVO.setCover(product.getCover());
        cartVO.setDetail(product.getDetail());
        cartVO.setDescription(product.getDescription());
        return cartVO;
    }

    @Override
    @Transactional
    public void removeFromCart(Integer cartItemId) {
        // 获取当前登录用户ID
        Integer userId = securityUtil.getCurrentAccount().getId();
        cartRepository.deleteByCartItemId(cartItemId);
    }

    @Override
    @Transactional
    public void updateCartQuantity(Integer cartItemId, Integer quantity) {
        // 获取当前登录用户ID
        Integer userId = securityUtil.getCurrentAccount().getId();
        
        // 1. 检查购物车项是否存在且属于当前用户
        Optional<Cart> cartOptional = cartRepository.findByCartItemIdAndUserId(cartItemId, userId);
        if (!cartOptional.isPresent()) {
            throw TomatoMallException.cartItemNotExists();
        }
        Cart cart = cartOptional.get();

        // 2. 检查库存是否足够
        Stockpile stockpile = stockpileRepository.findByProductId(cart.getProductId());
        if (stockpile == null || stockpile.getAmount() < quantity) {
            throw TomatoMallException.stockNotEnough();
        }

        // 3. 更新数量
        cart.setQuantity(quantity);
        cartRepository.save(cart);
    }

    @Override
    public List<CartVO> getCartItems() {
        // 获取当前登录用户ID
        Integer userId = securityUtil.getCurrentAccount().getId();
        
        // 1. 获取用户的所有购物车项
        List<Cart> cartItems = cartRepository.findByUserId(userId);
        List<CartVO> result = new ArrayList<>();

        // 2. 遍历购物车项，获取商品信息
        for (Cart cart : cartItems) {
            Optional<Product> productOptional = productRepository.findById(cart.getProductId());
            if (productOptional.isPresent()) {
                Product product = productOptional.get();
                
                // 创建购物车VO
                CartVO cartVO = cart.toCartVO();
                cartVO.setTitle(product.getTitle());
                cartVO.setPrice(product.getPrice());
                cartVO.setCover(product.getCover());
                cartVO.setDetail(product.getDetail());
                cartVO.setDescription(product.getDescription());

                result.add(cartVO);
            }
        }

        return result;
    }

    @Override
    public CartVO getCartItem(Integer cartItemId) {
        return cartRepository.findByCartItemId(cartItemId).toCartVO();
    }


} 