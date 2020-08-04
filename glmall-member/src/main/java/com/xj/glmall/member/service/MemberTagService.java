package com.xj.glmall.member.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xj.glmall.common.utils.PageUtils;
import com.xj.glmall.member.entity.MemberTagEntity;

import java.util.Map;

/**
 * 用户标签表
 *
 * @author yu
 * @email yu
 * @date 2020-06-24 20:01:36
 */
public interface MemberTagService extends IService<MemberTagEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

