package com.quan;




import com.quan.settings.service.DicService;
import com.quan.util.DateTimeUtil;
import com.quan.util.UUIDUtil;
import com.quan.workbench.dao.ClueActivityRelationDao;
import com.quan.workbench.domain.Tran;
import com.quan.workbench.service.ClueService;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.annotation.Resource;
import java.util.Enumeration;
import java.util.List;
import java.util.ResourceBundle;


public class Test01 {

//    @Test
//    public  void test01(){
//        ApplicationContext ac=new ClassPathXmlApplicationContext("conf/applicationContext.xml");
//        String[] names=ac.getBeanDefinitionNames();
//        for (String name : names) {
//            System.out.println(name+"====="+ac.getBean(name));
//        }
//
//    }
//
//    @Test
//    public void test02(){
//        //Integer i1=new Integer(127);
//        //Integer i1=-128;
//        int i1=-128;
//        //Integer i2=new Integer(127);
//        Integer i2=-128;
//        System.out.println(i1==i2);
//    }
//
//
//
//
//    @Test
//    public void test03(){
//        ApplicationContext ac=new ClassPathXmlApplicationContext("conf/applicationContext.xml");
//        ClueService clueService=ac.getBean(ClueService.class);
//        String createBy="张三";
//        Tran tran=new Tran();
//        tran.setId(UUIDUtil.getUUID());
//        tran.setCreateTime(DateTimeUtil.getSysTime());
//        tran.setCreateBy(createBy);
//        tran.setMoney("1000");
//        tran.setName("送钱");
//
//        boolean b=clueService.change("561ebab9a2e4483fa0d5e677b10dc9e7",tran,createBy);
//        System.out.println(b);
//    }
//
//    @Test
//    public void test04() {
//        ResourceBundle bundle = ResourceBundle.getBundle("conf/Stage2Possibility");
//        Enumeration<String> keys = bundle.getKeys();
//        StringBuilder json = new StringBuilder();
//        while (keys.hasMoreElements()) {
//            String key = keys.nextElement();
//            String value = bundle.getString(key);
//            json.append("\""+key + "\":");
//            json.append(value + ",");
//        }
//        json.deleteCharAt(json.length()-1);
//        String str=json.substring(0);
//
//        System.out.println(str);
//
//    }
}
