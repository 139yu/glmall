package com.xj.glmall.member.controller;

import java.util.Arrays;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.xj.glmall.common.exception.BizCodeEnum;
import com.xj.glmall.member.exception.EmailExistException;
import com.xj.glmall.member.exception.UsernameExistException;
import com.xj.glmall.member.vo.SocialUser;
import com.xj.glmall.member.vo.UserLoginVo;
import com.xj.glmall.member.vo.UserRegisterVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.xj.glmall.member.entity.MemberEntity;
import com.xj.glmall.member.service.MemberService;
import com.xj.glmall.common.utils.PageUtils;
import com.xj.glmall.common.utils.R;



/**
 * 会员
 *
 * @author yu
 * @email ${email}
 * @date 2020-10-21 22:14:15
 */
@RestController
@RequestMapping("member/member")
public class MemberController {
    @Autowired
    private MemberService memberService;


    @PostMapping("/auth2/login")
    public R authLogin(@RequestBody SocialUser socialUser) throws Exception {
        MemberEntity memberEntity = memberService.authLogin(socialUser);
        if (memberEntity == null) {
            return R.error(BizCodeEnum.LOGIN_FAILED.getCode(),BizCodeEnum.LOGIN_FAILED.getMessage());
        }
        System.out.println(JSON.toJSONString(memberEntity));
        R r = R.ok().setData(memberEntity);
        return r;
    }

    @PostMapping(value = "/login")
    public R login(@RequestBody UserLoginVo loginVo){
        MemberEntity memberEntity = memberService.login(loginVo);
        if (memberEntity == null) {
            return R.error(BizCodeEnum.LOGIN_PASSWORD_OR_USERNAME_INVALID_EXCEPTION.getCode(),BizCodeEnum.LOGIN_PASSWORD_OR_USERNAME_INVALID_EXCEPTION.getMessage());
        }else {
            return R.ok();
        }
    }

    @PostMapping("/register")
    public R register(@RequestBody UserRegisterVo registerVo){
        try {
            memberService.register(registerVo);
        } catch (UsernameExistException e) {
            return R.error(BizCodeEnum.USER_EXIST_EXCEPTION.getCode(),BizCodeEnum.USER_EXIST_EXCEPTION.getMessage());
        } catch (EmailExistException e) {
            return R.error(BizCodeEnum.EMAIL_EXIST_EXCEPTION.getCode(),BizCodeEnum.EMAIL_EXIST_EXCEPTION.getMessage());
        }
        return R.ok();
    }

    /**
     * 列表
     */
    @GetMapping("/list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = memberService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @GetMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
		MemberEntity member = memberService.getById(id);

        return R.ok().put("member", member);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    public R save(@RequestBody MemberEntity member){
		memberService.save(member);

        return R.ok();
    }

    /**
     * 修改
     */
    @PostMapping("/update")
    public R update(@RequestBody MemberEntity member){
		memberService.updateById(member);

        return R.ok();
    }

    /**
     * 删除
     */
    @DeleteMapping("/delete")
    public R delete(@RequestBody Long[] ids){
		memberService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
