package com.xj.glmall.ware.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xj.glmall.common.utils.PageUtils;
import com.xj.glmall.ware.entity.WareOrderTaskEntity;

import java.util.Map;

/**
 * 库存工作单表 库存工作单表
 *
 * @author yu
 * @email yu
 * @date 2020-06-24 20:15:57
 */
public interface WareOrderTaskService extends IService<WareOrderTaskEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

