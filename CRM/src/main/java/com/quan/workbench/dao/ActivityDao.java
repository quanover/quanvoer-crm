package com.quan.workbench.dao;

import com.quan.workbench.domain.Activity;

import java.util.List;
import java.util.Map;

public interface ActivityDao {
    int save(Activity activity);

    List<Activity> pageListByLike(Map<String, Object> map);

    int getTotalByLike(Map<String, Object> map);

    int deleteById(String[] id);

    Activity getById(String id);

    int update(Activity activity);

    Activity getAcById(String id);

    List<Activity> getAcBycId(String cid);

    List<Activity> getAcLikeNameAndNoByClueId(String activityName, String clueId);

    List<Activity> getActivityLikeName(String name);
}
