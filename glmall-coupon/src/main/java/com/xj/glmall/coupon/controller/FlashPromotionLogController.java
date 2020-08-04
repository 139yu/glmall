package com.xj.glmall.coupon.controller;

import java.util.Arrays;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.xj.glmall.coupon.entity.FlashPromotionLogEntity;
import com.xj.glmall.coupon.service.FlashPromotionLogService;
import com.xj.glmall.common.utils.PageUtils;
import com.xj.glmall.common.utils.R;



/**
 * 限时购通知记录
 *
 * @author yu
 * @email ${email}
 * @date 2020-06-23 23:32:29
 */
@RestController
@RequestMapping("coupon/flashpromotionlog")
public class FlashPromotionLogController {
    @Autowired
    private FlashPromotionLogService flashPromotionLogService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = flashPromotionLogService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Integer id){
		FlashPromotionLogEntity flashPromotionLog = flashPromotionLogService.getById(id);

        return R.ok().put("flashPromotionLog", flashPromotionLog);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody FlashPromotionLogEntity flashPromotionLog){
		flashPromotionLogService.save(flashPromotionLog);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody FlashPromotionLogEntity flashPromotionLog){
		flashPromotionLogService.updateById(flashPromotionLog);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Integer[] ids){
		flashPromotionLogService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
