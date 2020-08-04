package com.xj.glmall.member.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xj.glmall.common.utils.PageUtils;
import com.xj.glmall.member.entity.MemberMemberTagRelationEntity;

import java.util.Map;

/**
 * 用户和标签关系表
 *
 * @author yu
 * @email yu
 * @date 2020-06-24 20:01:36
 */
public interface MemberMemberTagRelationService extends IService<MemberMemberTagRelationEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

