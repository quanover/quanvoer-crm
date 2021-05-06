package com.quan.settings.controller;

import com.quan.exception.LoginException;
import com.quan.settings.domain.User;
import com.quan.settings.service.UserService;
import com.quan.util.MD5Util;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;

    @RequestMapping("/login.do")
    @ResponseBody
    public Map<String,Object> doLogin(HttpServletRequest request,String loginAct, String loginPwd) throws LoginException {
        Map<String,Object> map=new HashMap<>();
        loginPwd= MD5Util.getMD5(loginPwd);
        User loginUser=new User();
        loginUser.setLoginAct(loginAct);
        loginUser.setLoginPwd(loginPwd);

        //处理器方法包含四类参数
        //  HttpServletRequest
        //  HttpServletResponse
        //  HttpSession
        //  请求中所携带的请求参数
        User user=userService.userLogin(loginUser);
        request.getSession().setAttribute("user",user);
        map.put("success",true);

        return map;
    }

    //测试拦截器
    @RequestMapping("/some.do")
    public ModelAndView doSome(){
        System.out.println("doSome方法执行");
        ModelAndView mv=new ModelAndView();

        mv.addObject("one","hello springMVC");
        mv.addObject("two","doSome()");
        mv.setViewName("/test.jsp");
        return mv;
    }



}
