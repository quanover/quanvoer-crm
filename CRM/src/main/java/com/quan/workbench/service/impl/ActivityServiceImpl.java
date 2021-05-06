package com.quan.workbench.service.impl;

import com.quan.settings.dao.UserDao;
import com.quan.settings.domain.User;
import com.quan.vo.PageVO;
import com.quan.workbench.dao.ActivityDao;
import com.quan.workbench.dao.ActivityRemarkDao;
import com.quan.workbench.domain.Activity;
import com.quan.workbench.domain.ActivityRemark;
import com.quan.workbench.service.ActivityService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class ActivityServiceImpl implements ActivityService {

    @Resource
    private ActivityDao activityDao;

    @Resource
    private ActivityRemarkDao activityRemarkDao;

    @Resource
    private UserDao userDao;

    @Override
    public Boolean save(Activity activity) {
        boolean flag=true;
        int num=activityDao.save(activity);

        if(num!=1){
            flag=false;
        }

        return flag;
    }

    @Override
    public PageVO<Activity> pageList(Map<String, Object> map) {
        PageVO<Activity> vo=new PageVO<>();
        int total=activityDao.getTotalByLike(map);
        List<Activity> dataList=activityDao.pageListByLike(map);

        vo.setDataList(dataList);
        vo.setTotal(total);

        return vo;
    }

    @Override
    public boolean delete(String[] id) {
        boolean flag=true;

        //查询应该删除的备注数量
        int count1=activityRemarkDao.getCountByaId(id);
        //实际删除备注数量
        int count2=activityRemarkDao.deleteByaId(id);
        if (count1!=count2){
            flag=false;
        }

        //删除市场活动
        int count3=activityDao.deleteById(id);
        if(count3!=id.length){
            flag=false;
        }

        return flag;
    }

    @Override
    public Map<String, Object> getUserListAndActivity(String id) {
        Map<String,Object> map=new HashMap<>();

        List<User> userList = userDao.getUserList();
        Activity activity=activityDao.getById(id);

        map.put("userList",userList);
        map.put("a",activity);


        return map;
    }

    @Override
    public Boolean update(Activity activity) {
        boolean flag=true;
        int num=activityDao.update(activity);

        if(num!=1){
            flag=false;
        }

        return flag;
    }


    @Override
    public Activity getAcById(String id) {

        return activityDao.getAcById(id);
    }

    @Override
    public List<ActivityRemark> getRemark(String id) {
        return activityRemarkDao.getRemark(id);
    }

    @Override
    public Boolean deleteRemark(String id) {
        boolean flag=true;

        int num=activityRemarkDao.deleteRemark(id);
        if(num!=1){
            flag=false;
        }

        return flag;
    }

    @Override
    public Map<String, Object> updateRemark(ActivityRemark activityRemark) {
        Map<String,Object> map=new HashMap<>();

        boolean flag=true;
        int num=activityRemarkDao.updateRemark(activityRemark);
        if(num!=1){
         flag=false;
        }

        map.put("remark",activityRemark);
        map.put("success",flag);
        return map;
    }

    @Override
    public Map<String, Object> saveRemark(ActivityRemark activityRemark) {
        Map<String,Object> map=new HashMap<>();

        boolean flag=true;
        int num=activityRemarkDao.saveRemark(activityRemark);
        if(num!=1){
            flag=false;
        }

        map.put("remark",activityRemark);
        map.put("success",flag);
        return map;
    }

    @Override
    public List<Activity> getAcBycId(String cid) {
        return activityDao.getAcBycId(cid);
    }

    @Override
    public List<Activity> getAcLikeNameAndNoByClueId(String activityName, String clueId) {
        return activityDao.getAcLikeNameAndNoByClueId(activityName,clueId);
    }

    @Override
    public List<Activity> getActivityLikeName(String name) {
        return activityDao.getActivityLikeName(name);
    }
}
