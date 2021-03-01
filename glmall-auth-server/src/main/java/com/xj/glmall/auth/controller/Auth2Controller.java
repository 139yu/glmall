package com.xj.glmall.auth.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.xj.glmall.auth.feign.MemberFeignClient;
import com.xj.glmall.auth.vo.SocialUser;
import com.xj.glmall.common.utils.HttpUtils;
import com.xj.glmall.common.utils.R;
import com.xj.glmall.common.vo.MemberResVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Controller
public class Auth2Controller {

    @Autowired
    private MemberFeignClient memberFeignClient;

//https://api.weibo.com/oauth2/authorize?client_id=739319237&response_type=code&redirect_uri=http://auth.glmall.com/auth2/weibo/success
    @GetMapping("/auth2/weibo/success")
    public String weibo(@RequestParam("code")String code, RedirectAttributes redirectAttributes, HttpSession session){
        //https://api.weibo.com/oauth2/access_token?client_id=YOUR_CLIENT_ID&client_secret=YOUR_CLIENT_SECRET&grant_type=authorization_code&redirect_uri=YOUR_REGISTERED_REDIRECT_URI&code=CODE1
        Map<String,String> map = new HashMap<>();
        map.put("client_id","739319237");
        map.put("client_secret","8d0c7a896d86776d2b79bfe87303ec42");
        map.put("grant_type","authorization_code");
        map.put("redirect_uri","http://auth.glmall.com/auth2/weibo/success");
        map.put("code",code);
        Map<String,String> header = new HashMap<>();
        //header.put("Content-Type","application/json");
        try {
            HttpResponse response = HttpUtils.doPost("https://api.weibo.com", "/oauth2/access_token", "post",header, map,
                    "");
            if (response.getStatusLine().getStatusCode() == 200) {
                String s = EntityUtils.toString(response.getEntity());
                SocialUser socialUser = JSON.parseObject(s, SocialUser.class);
                //远程请求，判断此社交用户是登录还是注册
                R r = memberFeignClient.authLogin(socialUser);
                if (r.getCode() != 0){
                    Map<String,String> errors = new HashMap<>();
                    errors.put("msg", (String) r.getData("msg",new TypeReference<String>(){}));
                    redirectAttributes.addFlashAttribute("errors",errors);
                    return "redirect:http://auth.glmall.com/login.html";
                }else {
                    MemberResVo loginUser = (MemberResVo) r.getData("data",new TypeReference<MemberResVo>(){});
                    System.out.println(JSON.toJSONString(loginUser));
                    session.setAttribute("loginUser",loginUser);
                    log.info("登录成功：用户：{}",loginUser.toString());
                    return "redirect:http://glmall.com";
                }
            }else {
                return "redirect:http://auth.glmall.com/login.html";
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:http://auth.glmall.com/login.html";
    }
}
