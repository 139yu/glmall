package com.xj.glmall.glmall.cart.service;

import com.xj.glmall.glmall.cart.vo.Cart;
import com.xj.glmall.glmall.cart.vo.CartItem;

import java.util.concurrent.ExecutionException;

public interface CartService {
    public CartItem addToCart(Long skuId, Integer num) throws ExecutionException, InterruptedException;

    CartItem getCartItem(Long skuId);

    Cart getCart() throws ExecutionException, InterruptedException;

    void ClearCart(String key);

    void checkItem(Long skuId, Integer check);

    void setCount(String skuId, Integer num);

    void deleteItem(String skuId);
}
