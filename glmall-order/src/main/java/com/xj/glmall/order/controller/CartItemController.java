package com.xj.glmall.order.controller;

import java.util.Arrays;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.xj.glmall.order.entity.CartItemEntity;
import com.xj.glmall.order.service.CartItemService;
import com.xj.glmall.common.utils.PageUtils;
import com.xj.glmall.common.utils.R;



/**
 * 购物车表
 *
 * @author yu
 * @email ${email}
 * @date 2020-06-24 00:05:20
 */
@RestController
@RequestMapping("order/cartitem")
public class CartItemController {
    @Autowired
    private CartItemService cartItemService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = cartItemService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
		CartItemEntity cartItem = cartItemService.getById(id);

        return R.ok().put("cartItem", cartItem);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody CartItemEntity cartItem){
		cartItemService.save(cartItem);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody CartItemEntity cartItem){
		cartItemService.updateById(cartItem);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] ids){
		cartItemService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
