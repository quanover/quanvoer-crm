package com.quan.workbench.controller;


import com.quan.settings.domain.User;
import com.quan.settings.service.UserService;
import com.quan.util.DateTimeUtil;
import com.quan.util.UUIDUtil;
import com.quan.vo.PageVO;
import com.quan.workbench.domain.Activity;
import com.quan.workbench.domain.Clue;
import com.quan.workbench.domain.Tran;
import com.quan.workbench.service.ActivityService;
import com.quan.workbench.service.ClueService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/clue")
public class ClueController {

    @Resource
    private UserService userService;

    @Resource
    private ClueService clueService;

    @Resource
    private ActivityService activityService;

    @RequestMapping("/getUserList.do")
    @ResponseBody
    public List<User> getUserList(){
        return userService.getUserList();
    }

    @RequestMapping("/saveClue.do")
    @ResponseBody
    public boolean saveClue(HttpServletRequest request,Clue clue){
//        System.out.println(clue);
        String id= UUIDUtil.getUUID();
        String createTime= DateTimeUtil.getSysTime();
        String createBy= ((User)request.getSession().getAttribute("user")).getName();
        clue.setId(id);
        clue.setCreateBy(createBy);
        clue.setCreateTime(createTime);


        return clueService.saveClue(clue);


    }

    @RequestMapping("/pageList.do")
    @ResponseBody
    public PageVO<Clue> pageList(Integer pageNo, Integer pageSize, Clue clue){
        int index=(pageNo-1)*pageSize;
        Map<String,Object> map=new HashMap<>();
        map.put("index",index);
        map.put("pageSize",pageSize);
        map.put("fullname",clue.getFullname());
        map.put("company",clue.getCompany());
        map.put("phone",clue.getPhone());
        map.put("source",clue.getSource());
        map.put("owner",clue.getOwner());
        map.put("mphone",clue.getMphone());
        map.put("state",clue.getState());

        return clueService.pageList(map);
    }

    @RequestMapping("/detail.do")
    public ModelAndView detail(String id){
        ModelAndView mv=new ModelAndView();
        Clue clue=clueService.detail(id);

        mv.addObject("c",clue);
        mv.setViewName("clue/detail");
        return mv;
    }

    @RequestMapping("/getAcBycId.do")
    @ResponseBody
    public List<Activity> getAcBycId(String cid){
        return activityService.getAcBycId(cid);
    }

    @RequestMapping("/disassociate.do")
    @ResponseBody
    public Boolean disassociate(String id){
        return clueService.disassociate(id);
    }

    @RequestMapping("/getAcLikeNameAndNoByClueId.do")
    @ResponseBody
    public List<Activity> getAcLikeNameAndNoByClueId( String activityName, String clueId){
        return activityService.getAcLikeNameAndNoByClueId(activityName,clueId);
    }

    @RequestMapping("/relate.do")
    @ResponseBody
    public boolean relate(String clueId,String[] activityId){
        return clueService.relate(clueId,activityId);
    }

    @RequestMapping("/getActivityLikeName.do")
    @ResponseBody
    public List<Activity> getActivityLikeName(String name){
        return activityService.getActivityLikeName(name);
    }

    @RequestMapping(value = "/change.do",method = RequestMethod.POST )
    public ModelAndView change(HttpSession session,String clueId, Tran tran){

        ModelAndView mv=new ModelAndView();
        String createBy= ((User)session.getAttribute("user")).getName();
        tran.setId(UUIDUtil.getUUID());
        tran.setCreateTime(DateTimeUtil.getSysTime());
        tran.setCreateBy(createBy);

        boolean flag=clueService.change(clueId,tran,createBy);

        if (flag){
            mv.setViewName("redirect:/workbench/clue/index.jsp");
        }
        return mv;
    }

    @RequestMapping(value = "/change.do",method = RequestMethod.GET )
    public ModelAndView change(HttpSession session,String clueId){

        ModelAndView mv=new ModelAndView();
        String createBy= ((User)session.getAttribute("user")).getName();


        boolean flag=clueService.change(clueId,null,createBy);

        if (flag){
            mv.setViewName("redirect:/workbench/clue/index.jsp");
        }
        return mv;
    }


    @RequestMapping("/getCharts.do")
    @ResponseBody
    public Map<String,Object> getCharts(){
        return clueService.getCharts();
    }
}
