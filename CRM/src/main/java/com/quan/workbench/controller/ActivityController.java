package com.quan.workbench.controller;


import com.quan.settings.domain.User;
import com.quan.settings.service.UserService;
import com.quan.util.DateTimeUtil;
import com.quan.util.UUIDUtil;
import com.quan.vo.PageVO;
import com.quan.workbench.domain.Activity;
import com.quan.workbench.domain.ActivityRemark;
import com.quan.workbench.service.ActivityService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/activity")
public class ActivityController {

    @Resource
    private UserService userService;

    @Resource
    private ActivityService activityService;

    @RequestMapping("/getUserList.do")
    @ResponseBody
    public List<User> getUserList(){
        return userService.getUserList();
    }

    @RequestMapping("/saveActivity.do")
    @ResponseBody
    public Boolean saveActivity(HttpServletRequest request,Activity activity){
        System.out.println(activity);
        String id= UUIDUtil.getUUID();
        String createTime= DateTimeUtil.getSysTime();
        String createBy= ((User)request.getSession().getAttribute("user")).getName();
        activity.setId(id);
        activity.setCreateTime(createTime);
        activity.setCreateBy(createBy);
        System.out.println(activity);

        Boolean flag=activityService.save(activity);

        return flag;
    }


    @RequestMapping("/pageList.do")
    @ResponseBody
    public PageVO<Activity> pageList(Activity activity,Integer pageNo,Integer pageSize){

        //System.out.println(pageNo);
        //System.out.println(pageSize);
        //System.out.println(activity);
        int startIndex=(pageNo-1)*pageSize;
        Map<String,Object> map=new HashMap<>();
        map.put("startIndex",startIndex);
        map.put("pageSize",pageSize);
        map.put("name",activity.getName());
        map.put("owner",activity.getOwner());
        map.put("startDate",activity.getStartDate());
        map.put("endDate",activity.getEndDate());


        return activityService.pageList(map);
    }

    @RequestMapping("/delete.do")
    @ResponseBody
    public boolean delete(String[] id){
        return activityService.delete(id);
    }

    @RequestMapping("/getOne.do")
    @ResponseBody
    public Map<String,Object> getOne(String id){

        return activityService.getUserListAndActivity(id);
    }

    @RequestMapping("/update.do")
    @ResponseBody
    public Boolean update(HttpServletRequest request,Activity activity){

        String editTime= DateTimeUtil.getSysTime();
        String editBy= ((User)request.getSession().getAttribute("user")).getName();
        activity.setEditTime(editTime);
        activity.setEditBy(editBy);
        //System.out.println(activity);

        return activityService.update(activity);
    }


    @RequestMapping("/detail.do")
    public ModelAndView detail(String id){
        ModelAndView mv=new ModelAndView();

        Activity activity=activityService.getAcById(id);
        mv.addObject("a",activity);
        mv.setViewName("activity/detail");


        return mv;
    }

    @RequestMapping("/getRemark.do")
    @ResponseBody
    public List<ActivityRemark> getRemark(String id){
        return activityService.getRemark(id);
    }

    @RequestMapping("/deleteRemark.do")
    @ResponseBody
    public Boolean deleteRemark(String id){

        return activityService.deleteRemark(id);
    }

    @RequestMapping("/updateRemark.do")
    @ResponseBody
    public Map<String,Object> updateRemark(HttpSession session, ActivityRemark activityRemark){
//        System.out.println(activityRemark);
        String editTime=DateTimeUtil.getSysTime();
        String editBy=((User)session.getAttribute("user")).getName();
        activityRemark.setEditTime(editTime);
        activityRemark.setEditBy(editBy);
        activityRemark.setEditFlag("1");

        return activityService.updateRemark(activityRemark);

    }


    @RequestMapping("/saveRemark.do")
    @ResponseBody
    public  Map<String,Object> saveRemark(HttpSession session, ActivityRemark activityRemark){
//       System.out.println(activityRemark);
        String id=UUIDUtil.getUUID();
        String createTime=DateTimeUtil.getSysTime();
        String createBy=((User)session.getAttribute("user")).getName();
        activityRemark.setId(id);
        activityRemark.setCreateTime(createTime);
        activityRemark.setCreateBy(createBy);
        activityRemark.setEditFlag("0");

        return activityService.saveRemark(activityRemark);

    }
}
