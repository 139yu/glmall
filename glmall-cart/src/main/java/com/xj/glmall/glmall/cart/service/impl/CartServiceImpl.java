package com.xj.glmall.glmall.cart.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.xj.glmall.common.utils.R;
import com.xj.glmall.glmall.cart.feign.ProductFeignService;
import com.xj.glmall.glmall.cart.interceptor.CartInterceptor;
import com.xj.glmall.glmall.cart.service.CartService;
import com.xj.glmall.glmall.cart.to.UserInfoTo;
import com.xj.glmall.glmall.cart.vo.Cart;
import com.xj.glmall.glmall.cart.vo.CartItem;
import com.xj.glmall.glmall.cart.vo.SkuInfoEntity;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.stream.Collectors;

@Slf4j
@Service
public class CartServiceImpl implements CartService {
    @Autowired
    private ProductFeignService productFeignService;

    @Autowired
    private ThreadPoolExecutor executor;

    @Autowired
    private RedisTemplate redisTemplate;

    private static final String CART_PREFIX = "glmall:cart:";

    private BoundHashOperations<String,Object,Object> getCartOps(){
        UserInfoTo userInfoTo = CartInterceptor.threadLocal.get();
        String userKey = null;
        if (StringUtils.isEmpty(userInfoTo.getUserId())){
            userKey = CART_PREFIX + userInfoTo.getUserKey();
        }else {
            userKey = CART_PREFIX + userInfoTo.getUserId();
        }
        return redisTemplate.boundHashOps(userKey);
    }
    /**
     * 购物车信息在缓存中的key格式-> glmall:cart:用户id或临时用户uuid
     * 1.查看当前用户是否是临时用户
     * 2.查看购物车中是否已经添加过该商品，添加过则修改商品数量即可，无需再次查询商品信息
     * @param skuId
     * @param num
     * @return
     * @throws ExecutionException
     * @throws InterruptedException
     */
    @Override
    public CartItem addToCart(Long skuId, Integer num) throws ExecutionException, InterruptedException {
        BoundHashOperations<String,Object,Object> cartOps = getCartOps();
        //TODO 判断购物车中是否已有该商品
        String temp = (String) cartOps.get(skuId.toString());
        if (StringUtils.isEmpty(temp)) {
            //商品信息
            CartItem cartItem = new CartItem();
            cartItem.setSkuId(skuId);
            cartItem.setCount(num);
            cartItem.setCheck(true);
            CompletableFuture<Void> getSkuInfo = CompletableFuture.runAsync(() -> {
                R r = productFeignService.info(skuId);
                SkuInfoEntity skuInfoEntity  = (SkuInfoEntity) r.getData("skuInfo",new TypeReference<SkuInfoEntity>(){});
                cartItem.setPrice(skuInfoEntity.getPrice());
                cartItem.setTitle(skuInfoEntity.getSkuTitle());
                cartItem.setImage(skuInfoEntity.getSkuDefaultImg());
            }, executor);
            CompletableFuture<Void> getSaleAttr = CompletableFuture.runAsync(() -> {
                List<String> saleAttrs = productFeignService.listStringSaleAttr(skuId);
                cartItem.setSkuAttr(saleAttrs);
            }, executor);
            //商品销售属性信息
            CompletableFuture.allOf(getSkuInfo,getSaleAttr).get();
            String string = JSON.toJSONString(cartItem);
            cartOps.put(skuId.toString(),string);
            return cartItem;
        }else {
            CartItem cartItem = JSON.parseObject(temp, CartItem.class);
            cartItem.setCount(cartItem.getCount() + num);

            cartOps.put(skuId.toString(),JSON.toJSONString(cartItem));
            return cartItem;
        }

    }

    /**
     *
     * @param skuId
     * @return
     */
    @Override
    public CartItem getCartItem(Long skuId) {
        BoundHashOperations<String, Object, Object> cartOps = getCartOps();
        String o = (String) cartOps.get(skuId.toString());
        CartItem cartItem = JSON.parseObject(o, CartItem.class);
        return cartItem;
    }
    public List<CartItem> getCartItemList(String userKey){
        BoundHashOperations cartOps = redisTemplate.boundHashOps(userKey);
        List<Object> values = cartOps.values();
        if (!CollectionUtils.isEmpty(values)){
            List<CartItem> collect = values.stream().map(item -> {
                return JSON.parseObject((String) item, CartItem.class);
            }).collect(Collectors.toList());
            return collect;
        }
        return null;
    }
    /**
     * 获取购物车列表
     * @return
     */
    @Override
    public Cart getCart() throws ExecutionException, InterruptedException {
        UserInfoTo userInfoTo = CartInterceptor.threadLocal.get();
        Cart cart = new Cart();
        String tempCartKey = CART_PREFIX + userInfoTo.getUserKey();
        if (StringUtils.isEmpty(userInfoTo.getUserId())){//未登录
            List<CartItem> cartItemList = getCartItemList(tempCartKey);
            cart.setItems(cartItemList);
            return cart;
        }else {//已登录
            //将临时用户商品列表合并到当前用户中，然后返回
            List<CartItem> cartItemList = getCartItemList(tempCartKey);
            if (!CollectionUtils.isEmpty(cartItemList)){
                for (CartItem cartItem : cartItemList) {
                    addToCart(cartItem.getSkuId(),cartItem.getCount());
                }
            }
            //删除临时用户购物车信息
            ClearCart(tempCartKey);
            cart.setItems(getCartItemList(CART_PREFIX + userInfoTo.getUserId()));
            return cart;
        }
    }

    /**
     * 清空购物车
     * @param key
     */
    @Override
    public void ClearCart(String key) {
        redisTemplate.delete(key);
    }

    @Override
    public void checkItem(Long skuId, Integer check) {
        BoundHashOperations<String, Object, Object> cartOps = getCartOps();
        CartItem cartItem = JSON.parseObject((String) cartOps.get(skuId.toString()),CartItem.class);
        cartItem.setCheck(check == 1);
        cartOps.put(skuId + "",JSON.toJSONString(cartItem));
    }

    @Override
    public void setCount(String skuId, Integer num) {
        BoundHashOperations<String, Object, Object> cartOps = getCartOps();
        CartItem cartItem = JSON.parseObject((String) cartOps.get(skuId),CartItem.class);
        cartItem.setCount(num);
        cartOps.put(skuId,JSON.toJSONString(cartItem));
    }

    @Override
    public void deleteItem(String skuId) {
        BoundHashOperations<String, Object, Object> cartOps = getCartOps();
        cartOps.delete(skuId);
    }
}
