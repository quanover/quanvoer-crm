package com.quan.workbench.dao;

import com.quan.workbench.domain.Clue;

import java.util.List;
import java.util.Map;

public interface ClueDao {
    int saveClue(Clue clue);

    List<Clue> pageListByLike(Map<String, Object> map);

    int getTotalByLike(Map<String, Object> map);

    Clue detailById(String id);


    Clue getById(String clueId);

    int deleteById(String clueId);

    int getTotal();

    List<Map<String, Object>> getCharts();
}
