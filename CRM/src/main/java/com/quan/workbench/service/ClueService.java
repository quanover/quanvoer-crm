package com.quan.workbench.service;

import com.quan.vo.PageVO;
import com.quan.workbench.domain.Clue;
import com.quan.workbench.domain.Tran;

import java.util.Map;

public interface ClueService {
    boolean saveClue(Clue clue);

    PageVO<Clue> pageList(Map<String, Object> map);

    Clue detail(String id);

    Boolean disassociate(String id);

    boolean relate(String clueId, String[] activityId);


    boolean change(String clueId, Tran tran, String createBy);

    Map<String, Object> getCharts();
}
