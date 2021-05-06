package com.quan.listener;

import com.quan.settings.domain.DicValue;
import com.quan.settings.service.DicService;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.*;

public class DicInitListener implements ServletContextListener {



    @Override
    public void contextInitialized(ServletContextEvent sce) {

        ServletContext application=sce.getServletContext();
        //Listener、Filter不属于spring容器，无法使用注解
        DicService dicService= (DicService) WebApplicationContextUtils.getRequiredWebApplicationContext(application).getBean("dicService");

        Map<String, List<DicValue>> map=dicService.getAll();
        Set<String> set=map.keySet();
        for (String key : set) {
            application.setAttribute(key,map.get(key));
        }

        ResourceBundle bundle = ResourceBundle.getBundle("conf/Stage2Possibility");
        Enumeration<String> keys = bundle.getKeys();
        StringBuilder json = new StringBuilder();
        while (keys.hasMoreElements()) {
            String key = keys.nextElement();
            String value = bundle.getString(key);
            json.append("\""+key + "\":");
            json.append(value + ",");
        }
        json.deleteCharAt(json.length()-1);
        application.setAttribute("json",json);
    }
}
