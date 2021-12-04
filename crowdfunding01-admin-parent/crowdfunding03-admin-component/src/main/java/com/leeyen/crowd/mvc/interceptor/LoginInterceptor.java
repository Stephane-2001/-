package com.leeyen.crowd.mvc.interceptor;


import com.leeyen.crowd.constant.CrowdConstant;
import com.leeyen.crowd.entity.Admin;
import com.leeyen.crowd.exception.AccessForbiddenException;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginInterceptor extends HandlerInterceptorAdapter {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        // 1.获取session对象
        HttpSession session = request.getSession();
        // 2.尝试从session中获取admin对象
        Admin admin = (Admin) session.getAttribute(CrowdConstant.ATTR_NAME_LOGIN_ADMIN);
        // 3.判断admin是否为空
        if (admin == null){
            // 4.抛出一个异常
            throw new AccessForbiddenException(CrowdConstant.MESSAGE_ACCESS_FORBIDEN);
        }
        // 5.不为空 则返回true放行
        return true;
    }
}
