package com.quan.handler;

import com.quan.settings.domain.User;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String path=request.getServletPath();
        if("/user/login.do".equals(path)){
            return true;
        }else {

            HttpSession session = request.getSession();
            User user = (User) session.getAttribute("user");

            if (user != null) {
                return true;
            } else {

                response.sendRedirect(request.getContextPath() + "/login.jsp");
                return false;
            }
        }

    }
}
