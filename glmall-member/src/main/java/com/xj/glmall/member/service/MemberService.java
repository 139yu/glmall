package com.xj.glmall.member.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xj.glmall.common.utils.PageUtils;
import com.xj.glmall.member.entity.MemberEntity;
import com.xj.glmall.member.exception.EmailExistException;
import com.xj.glmall.member.exception.UsernameExistException;
import com.xj.glmall.member.vo.SocialUser;
import com.xj.glmall.member.vo.UserLoginVo;
import com.xj.glmall.member.vo.UserRegisterVo;

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

    void register(UserRegisterVo registerVo) throws UsernameExistException, EmailExistException;

    MemberEntity login(UserLoginVo loginVo);

    MemberEntity authLogin(SocialUser socialUser) throws Exception;
}

