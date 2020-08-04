package com.xj.glmall.coupon.controller;

import java.util.Arrays;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.xj.glmall.coupon.entity.FlashPromotionSessionEntity;
import com.xj.glmall.coupon.service.FlashPromotionSessionService;
import com.xj.glmall.common.utils.PageUtils;
import com.xj.glmall.common.utils.R;



/**
 * 限时购场次表
 *
 * @author yu
 * @email ${email}
 * @date 2020-06-23 23:32:29
 */
@RestController
@RequestMapping("coupon/flashpromotionsession")
public class FlashPromotionSessionController {
    @Autowired
    private FlashPromotionSessionService flashPromotionSessionService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = flashPromotionSessionService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
		FlashPromotionSessionEntity flashPromotionSession = flashPromotionSessionService.getById(id);

        return R.ok().put("flashPromotionSession", flashPromotionSession);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody FlashPromotionSessionEntity flashPromotionSession){
		flashPromotionSessionService.save(flashPromotionSession);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody FlashPromotionSessionEntity flashPromotionSession){
		flashPromotionSessionService.updateById(flashPromotionSession);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] ids){
		flashPromotionSessionService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
