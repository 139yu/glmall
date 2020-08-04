package com.xj.glmall.member.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xj.glmall.common.utils.PageUtils;
import com.xj.glmall.member.entity.MemberProductCategoryRelationEntity;

import java.util.Map;

/**
 * 会员与产品分类关系表（用户喜欢的分类）
 *
 * @author yu
 * @email yu
 * @date 2020-06-24 20:01:36
 */
public interface MemberProductCategoryRelationService extends IService<MemberProductCategoryRelationEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

