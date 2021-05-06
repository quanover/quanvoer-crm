package com.quan.workbench.service;

import com.quan.vo.PageVO;
import com.quan.workbench.domain.Activity;
import com.quan.workbench.domain.ActivityRemark;

import java.util.List;
import java.util.Map;

public interface ActivityService {
    Boolean save(Activity activity);


    PageVO<Activity> pageList(Map<String, Object> map);

    boolean delete(String[] id);

    Map<String, Object> getUserListAndActivity(String id);

    Boolean update(Activity activity);

    Activity getAcById(String id);

    List<ActivityRemark> getRemark(String id);

    Boolean deleteRemark(String id);

    Map<String, Object> updateRemark(ActivityRemark activityRemark);

    Map<String, Object> saveRemark(ActivityRemark activityRemark);

    List<Activity> getAcBycId(String cid);

    List<Activity> getAcLikeNameAndNoByClueId(String activityName, String clueId);

    List<Activity> getActivityLikeName(String name);
}
