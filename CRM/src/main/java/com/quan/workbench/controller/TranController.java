package com.quan.workbench.controller;

import com.quan.settings.domain.User;
import com.quan.settings.service.UserService;
import com.quan.util.DateTimeUtil;
import com.quan.util.UUIDUtil;
import com.quan.workbench.domain.Activity;
import com.quan.workbench.domain.Contacts;
import com.quan.workbench.domain.Tran;
import com.quan.workbench.domain.TranHistory;
import com.quan.workbench.service.ActivityService;
import com.quan.workbench.service.ContactsService;
import com.quan.workbench.service.CustomerService;
import com.quan.workbench.service.TranService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/transaction")
public class TranController {

    @Resource
    private UserService userService;
    @Resource
    private ActivityService activityService;
    @Resource
    private ContactsService contactsService;
    @Resource
    private CustomerService customerService;
    @Resource
    private TranService tranService;

    @RequestMapping("/add.do")
    public ModelAndView add(){
        ModelAndView mv=new ModelAndView();
        List<User> userList=userService.getUserList();

        mv.addObject("userList",userList);
        mv.setViewName("transaction/save");

        return mv;
    }

    @RequestMapping("/getActivityLikeName.do")
    @ResponseBody
    public List<Activity> getActivityLikeName(String name){
        return activityService.getActivityLikeName(name);
    }


    @RequestMapping("/getContactsLikeName.do")
    @ResponseBody
    public List<Contacts> getContactsLikeName(String name){
        return contactsService.getContactsLikeName(name);
    }


    @RequestMapping("/getCustomerName.do")
    @ResponseBody
    public List<String> getCustomerName(String name){
        return customerService.getCustomerName(name);
    }

    @RequestMapping("/save.do")
    public ModelAndView save(HttpSession session, Tran tran, String customerName ){
        ModelAndView mv = new ModelAndView();
        tran.setId(UUIDUtil.getUUID());
        tran.setCreateTime(DateTimeUtil.getSysTime());
        tran.setCreateBy(((User)session.getAttribute("user")).getName());
        boolean flag=tranService.save(tran,customerName);

        if (flag ) {
            mv.setViewName("redirect:/workbench/transaction/index.jsp");
        }
        return mv;
    }

    @RequestMapping("/detail.do")
    public ModelAndView detail(String id){
        ModelAndView mv = new ModelAndView();
        Tran tran=tranService.detail(id);

        mv.addObject("tran",tran);
        mv.setViewName("transaction/detail");

        return mv;
    }

    @RequestMapping("/getHistory.do")
    @ResponseBody
    public List<TranHistory> getHistoryByTranId(String tranId){
        return tranService.getHistoryByTranId(tranId);
    }
}
