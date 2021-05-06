package com.quan.workbench.dao;

import com.quan.workbench.domain.ClueRemark;

import java.util.List;

public interface ClueRemarkDao {
    List<ClueRemark> getByCId(String clueId);

    int delete(ClueRemark clueRemark);
}
