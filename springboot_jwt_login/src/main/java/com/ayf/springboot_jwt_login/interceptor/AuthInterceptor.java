package com.ayf.springboot_jwt_login.interceptor;

import com.ayf.springboot_jwt_login.exceptions.ZDException;
import com.ayf.springboot_jwt_login.service.RedisService;
import com.ayf.springboot_jwt_login.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

public class AuthInterceptor implements HandlerInterceptor {

    @Autowired
    private RedisService redisService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=utf-8");
        String token = request.getHeader("token");
        Object o = null;
        try {
            o = JwtUtil.analysisToken(token);
        }catch (ZDException e) {
            response.getWriter().print(e.getMessage());
            return false;
        } catch (Exception e) {
            response.getWriter().print("用户未登录，请登录后操作！");
            return false;
        }
        if (StringUtils.isEmpty(o)){
            response.getWriter().print("用户未登录，请登录后操作！");
            return false;
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
