package com.quan.filter;


import com.quan.settings.domain.User;

import javax.servlet.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;


public class LoginFilter implements Filter {

    //servlet依赖版本低的话需要重写三个
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request= (HttpServletRequest) servletRequest;
        HttpServletResponse response= (HttpServletResponse) servletResponse;

        String path=request.getServletPath();
        if("/login.jsp".equals(path)){
            filterChain.doFilter(servletRequest,servletResponse);
        }else {
            HttpSession session=request.getSession();
            User user= (User) session.getAttribute("user");
            if (user != null) {
                filterChain.doFilter(servletRequest,servletResponse);
            } else {
                //response重定向 特殊的后台路径 参照路径为服务器的根路径
                response.sendRedirect(request.getContextPath() + "/login.jsp");
            }
        }
    }
}
