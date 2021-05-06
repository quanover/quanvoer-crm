package com.quan.workbench.dao;

import com.quan.workbench.domain.Tran;
import com.quan.workbench.domain.TranHistory;

import java.util.List;

public interface TranDao {
    int save(Tran tran);

    Tran detail(String id);


}
