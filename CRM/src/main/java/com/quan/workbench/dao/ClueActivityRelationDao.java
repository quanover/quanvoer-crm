package com.quan.workbench.dao;

import com.quan.workbench.domain.ClueActivityRelation;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ClueActivityRelationDao {
    int deleteById(String id);

    int relate(ClueActivityRelation clueActivityRelation);


    List<String> getByClueId(String clueId);

    int deleteByAcId(String activityId);
}
