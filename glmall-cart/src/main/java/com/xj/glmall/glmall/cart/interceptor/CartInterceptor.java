package com.xj.glmall.glmall.cart.interceptor;

import com.xj.glmall.common.constant.AuthServerConstant;
import com.xj.glmall.common.constant.CartConstant;
import com.xj.glmall.common.vo.MemberResVo;
import com.xj.glmall.glmall.cart.to.UserInfoTo;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.UUID;

@Component
public class CartInterceptor implements HandlerInterceptor {
    public static ThreadLocal<UserInfoTo> threadLocal = new ThreadLocal<>();
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        UserInfoTo userInfoTo = new UserInfoTo();
        HttpSession session = request.getSession();
        MemberResVo member = (MemberResVo) session.getAttribute(AuthServerConstant.LOGIN_USER);
        //session中存储了用户信息，说明用户已经登录
        if (member != null) {
           userInfoTo.setUserId(member.getId()+"");
        }else {

            userInfoTo.setTempUser(true);
        }
        //如果没有临时用户给浏览器分配一个临时用户
        if (StringUtils.isEmpty(userInfoTo.getUserKey())){
            String uuid = UUID.randomUUID().toString();
            userInfoTo.setUserKey(uuid);
        }
        Cookie[] cookies = request.getCookies();
        if (cookies != null && cookies.length > 0) {
            for (Cookie cookie : cookies) {
                String name = cookie.getName();
                if (CartConstant.TEMP_USER_COOLIE_NAME.equals(name)){
                    userInfoTo.setUserKey(cookie.getValue());
                    userInfoTo.setFirst(false);
                }
            }
        }

        threadLocal.set(userInfoTo);
        return true;
    }

    /**
     * 执行controller之后执行该方法，视图未渲染之前
     * @param request
     * @param response
     * @param handler
     * @param modelAndView
     * @throws Exception
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        UserInfoTo userInfoTo = threadLocal.get();
        //非临时用户或第一次访问的临时用户续期
        if (!userInfoTo.getTempUser() || userInfoTo.getFirst()) {
            Cookie cookie = new Cookie(CartConstant.TEMP_USER_COOLIE_NAME,userInfoTo.getUserKey());
            cookie.setDomain("glmall.com");
            cookie.setMaxAge(CartConstant.TEMP_USER_COOLIE_TIMEOUT);
            response.addCookie(cookie);
        }
    }
}
