package com.xj.glmall.member.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xj.glmall.common.utils.PageUtils;
import com.xj.glmall.member.entity.MemberEntity;

import java.util.Map;

/**
 * 会员
 *
 * @author yu
 * @email ${email}
 * @date 2020-10-21 22:14:15
 */
public interface MemberService extends IService<MemberEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

