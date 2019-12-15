package com.sxsduki.blog.interceptor;

import com.sxsduki.blog.pojo.User;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 */

public class LoginInterceptor extends HandlerInterceptorAdapter {


    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {
        User user = (User) request.getSession().getAttribute("user");

        if (request.getSession().getAttribute("user") == null){
            response.sendRedirect("/login");
            return false;
        }
        if (!"ADMIN".equals(user.getRole())){
            response.sendRedirect("/");
            return false;
        }
        return true;
    }
}
