package com.xj.glmall.member.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xj.glmall.common.utils.PageUtils;
import com.xj.glmall.member.entity.MemberLevelEntity;

import java.util.Map;

/**
 * 会员等级表
 *
 * @author yu
 * @email yu
 * @date 2020-06-24 20:01:37
 */
public interface MemberLevelService extends IService<MemberLevelEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

