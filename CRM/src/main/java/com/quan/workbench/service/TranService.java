package com.quan.workbench.service;


import com.quan.workbench.domain.Tran;
import com.quan.workbench.domain.TranHistory;
import org.springframework.stereotype.Service;

import java.util.List;


public interface TranService {
    boolean save(Tran tran, String customerName);

    Tran detail(String id);

    List<TranHistory> getHistoryByTranId(String tranId);
}
