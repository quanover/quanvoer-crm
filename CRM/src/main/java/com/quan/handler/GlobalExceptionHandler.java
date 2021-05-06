package com.quan.handler;


import com.quan.exception.LoginException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = LoginException.class)
    @ResponseBody
    public Map<String,Object> doLoginException(Exception e){
        //e.printStackTrace();
        Map<String,Object> map=new HashMap<>();
        String msg=e.getMessage();
        map.put("success",false);
        map.put("msg",msg);
        return map;
    }
}
