package com.xj.glmall.glmall.cart.controller;

import com.xj.glmall.glmall.cart.interceptor.CartInterceptor;
import com.xj.glmall.glmall.cart.service.CartService;
import com.xj.glmall.glmall.cart.to.UserInfoTo;
import com.xj.glmall.glmall.cart.vo.Cart;
import com.xj.glmall.glmall.cart.vo.CartItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.concurrent.ExecutionException;

@Controller
public class CartController {

    @Autowired
    private CartService cartService;

    @GetMapping("deleteItem")
    public String deleteItem(@RequestParam("skuId") String skuId){
        cartService.deleteItem(skuId);
        return "redirect:http://cart.glmall.com/cart.html";
    }

    @GetMapping("/checkItem")
    public String checkItem(@RequestParam("skuId") Long skuId,@RequestParam("check") Integer check){
        cartService.checkItem(skuId,check);
        return "redirect:http://cart.glmall.com/cart.html";
    }
    @GetMapping("/setCount")
    public String setCount(@RequestParam("skuId") String skuId,@RequestParam("num") Integer num){
        cartService.setCount(skuId,num);
        return "redirect:http://cart.glmall.com/cart.html";
    }
    @GetMapping("cart.html")
    public String cartListPage(Model model) throws ExecutionException, InterruptedException {
        Cart cart = cartService.getCart();
        model.addAttribute("cart",cart);
        return "cartList";
    }

    /**
     *
     * @param skuId
     * @param num
     * @param attributes addFlashAttribute->将数据放在session，可以在页面取出，只能取一次。addAttribute->将数据添加再url后
     * @return
     * @throws ExecutionException
     * @throws InterruptedException
     */
    @GetMapping("/addToCart")
    public String addToCart(@RequestParam("skuId") Long skuId, @RequestParam("num") Integer num,
                            RedirectAttributes attributes) throws ExecutionException, InterruptedException {
        cartService.addToCart(skuId, num);
        attributes.addAttribute("skuId",skuId);
        return "redirect:http://cart.glmall.com/addToCartSuccess.html";
    }

    @GetMapping("/addToCartSuccess.html")
    public String addToCartSuccess(@RequestParam("skuId") Long skuId,Model model){
        CartItem cartItem = cartService.getCartItem(skuId);
        model.addAttribute("item",cartItem);
        return "success.html";
    }
}
