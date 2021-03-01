package com.xj.glmall.auth.controller;

import com.alibaba.fastjson.TypeReference;
import com.xj.glmall.auth.vo.UserLoginVo;
import com.xj.glmall.auth.vo.UserRegisterVo;
import com.xj.glmall.auth.feign.MemberFeignClient;
import com.xj.glmall.auth.feign.ThirdPartyFeignClient;
import com.xj.glmall.common.constant.AuthServerConstant;
import com.xj.glmall.common.exception.BizCodeEnum;
import com.xj.glmall.common.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Controller
public class LoginController {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private ThirdPartyFeignClient thirdPartyFeignClient;

    @Autowired
    private MemberFeignClient memberFeignClient;

    @PostMapping("/login")
    public String login(UserLoginVo loginVo,RedirectAttributes redirectAttributes){
        R r = memberFeignClient.login(loginVo);
        if (r.getCode() == 0) {
            return "redirect:http://glmall.com";
        }else {
            Map<String,String> errors = new HashMap<>();
            errors.put("msg", (String) r.getData("msg",new TypeReference<String>(){}));
            redirectAttributes.addFlashAttribute("errors",errors);
            return "redirect:http://auth.glmall.com/login.html";
        }
    }

    @ResponseBody
    @GetMapping("/sendCode")
    public R sendCode(@RequestParam("email") String email) {
        //防止频繁发送验证码
        String redisCode = redisTemplate.opsForValue().get(AuthServerConstant.SMS_CODE_CHECK_PREFIX + email);
        if (!StringUtils.isEmpty(redisCode)) {
            Long l = Long.valueOf(redisCode.split("_")[1]);
            //发送间隔小于5分钟
            if ((System.currentTimeMillis() - l) < (1000 * 60 * 5)) {
                return R.error(BizCodeEnum.SMS_CODE_EXCEPTION.getCode(), BizCodeEnum.SMS_CODE_EXCEPTION.getMessage());
            }
        }
        String code = UUID.randomUUID().toString().substring(0, 5);
        redisTemplate.opsForValue().set(AuthServerConstant.SMS_CODE_CHECK_PREFIX + email, code + "_" + System.currentTimeMillis(),5, TimeUnit.MINUTES);
        thirdPartyFeignClient.sendCode(email,code);
        return R.ok();

    }

    /**
     * @Valid 提交的数据要进行校验，
     * RedirectAttributes
     * @param userRegisterVo
     * @param result 存放校验的结果
     * @param redirectAttributes 将要返回的数据保存到session中
     * @return
     */
    @PostMapping("/register")
    public String register(@Valid UserRegisterVo userRegisterVo, BindingResult result, RedirectAttributes redirectAttributes){
        //提交参数有错，重定向到注册页
        if (result.hasErrors()) {
            Map<String, String> errors = result.getFieldErrors().stream().collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage));
            redirectAttributes.addFlashAttribute("errors",errors);
            return "redirect:http://auth.glmall.com/reg.html";
        }
        //提交数据无误，添加用户，注册成功后跳转到登录页
        //校验验证码
        String redisCode = redisTemplate.opsForValue().get(AuthServerConstant.SMS_CODE_CHECK_PREFIX + userRegisterVo.getEmail());
        if (!StringUtils.isEmpty(redisCode)){
            if (userRegisterVo.getCode().equalsIgnoreCase(redisCode.split("_")[0])){
                //验证通过删除redis中对应的数据
                redisTemplate.delete(AuthServerConstant.SMS_CODE_CHECK_PREFIX + userRegisterVo.getEmail());
                //TODO 注册
                R r = memberFeignClient.register(userRegisterVo);
                Integer code = r.getCode();
                if (code == 0) {
                    return "redirect:http://auth.glmall.com/login.html";
                }else {
                    Map<String,String> errors = new HashMap<>();

                    errors.put("msg", (String) r.getData("msg",new TypeReference<String>(){}));
                    redirectAttributes.addFlashAttribute("errors",errors);
                    return "redirect:http://auth.glmall.com/reg.html";
                }
            }else {
                Map<String,String> errors = new HashMap<>();
                errors.put("code","验证码错误");
                redirectAttributes.addFlashAttribute("errors",errors);
                return "redirect:http://auth.glmall.com/reg.html";
            }
        }else {
            Map<String,String> errors = new HashMap<>();
            errors.put("code","验证码错误");
            redirectAttributes.addFlashAttribute("errors",errors);
            return "redirect:http://auth.glmall.com/reg.html";
        }
    }
}
