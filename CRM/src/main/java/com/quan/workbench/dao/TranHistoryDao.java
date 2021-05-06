package com.quan.workbench.dao;

import com.quan.workbench.domain.TranHistory;

import java.util.List;

public interface TranHistoryDao {
    int save(TranHistory tranHistory);

    List<TranHistory> getHistoryByTranId(String tranId);
}
