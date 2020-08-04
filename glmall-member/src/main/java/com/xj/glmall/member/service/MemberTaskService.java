package com.xj.glmall.member.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xj.glmall.common.utils.PageUtils;
import com.xj.glmall.member.entity.MemberTaskEntity;

import java.util.Map;

/**
 * 会员任务表
 *
 * @author yu
 * @email yu
 * @date 2020-06-24 20:01:36
 */
public interface MemberTaskService extends IService<MemberTaskEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

