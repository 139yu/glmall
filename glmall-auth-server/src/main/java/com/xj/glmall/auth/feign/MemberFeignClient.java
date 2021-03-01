package com.xj.glmall.auth.feign;

import com.xj.glmall.auth.vo.SocialUser;
import com.xj.glmall.auth.vo.UserLoginVo;
import com.xj.glmall.auth.vo.UserRegisterVo;
import com.xj.glmall.common.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient("glmall-member")
public interface MemberFeignClient {
    @PostMapping("/member/member/register")
    public R register(@RequestBody UserRegisterVo registerVo);

    @PostMapping("/member/member/login")
    public R login(@RequestBody UserLoginVo loginVo);

    @PostMapping("member/member/auth2/login")
    public R authLogin(@RequestBody SocialUser socialUser);
}
