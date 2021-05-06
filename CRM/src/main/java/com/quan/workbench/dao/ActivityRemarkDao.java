package com.quan.workbench.dao;

import com.quan.workbench.domain.ActivityRemark;

import java.util.List;

public interface ActivityRemarkDao {
    int getCountByaId(String[] id);

    int deleteByaId(String[] id);

    List<ActivityRemark> getRemark(String id);

    int deleteRemark(String id);

    int updateRemark(ActivityRemark activityRemark);

    int saveRemark(ActivityRemark activityRemark);
}
