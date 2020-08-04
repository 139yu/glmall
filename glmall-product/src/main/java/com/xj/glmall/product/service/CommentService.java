package com.xj.glmall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xj.glmall.common.utils.PageUtils;
import com.xj.glmall.product.entity.CommentEntity;

import java.util.Map;

/**
 * 商品评价表
 *
 * @author yu
 * @email ${email}
 * @date 2020-07-02 22:57:13
 */
public interface CommentService extends IService<CommentEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

