package com.quan.workbench.service.impl;

import com.quan.util.DateTimeUtil;
import com.quan.util.UUIDUtil;
import com.quan.workbench.dao.CustomerDao;
import com.quan.workbench.dao.TranDao;
import com.quan.workbench.dao.TranHistoryDao;
import com.quan.workbench.domain.Customer;
import com.quan.workbench.domain.Tran;
import com.quan.workbench.domain.TranHistory;
import com.quan.workbench.service.TranService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;


@Service
public class TranServiceImpl implements TranService {

    @Resource
    private TranDao tranDao;
    @Resource
    private TranHistoryDao tranHistoryDao;
    @Resource
    private CustomerDao customerDao;

    @Override
    public boolean save(Tran tran, String customerName) {
       boolean flag=true;
       //获取customer
        Customer customer=customerDao.getByName(customerName);

        if (customer == null) {
            customer=new Customer();
            customer.setId(UUIDUtil.getUUID());
            customer.setName(customerName);
            customer.setOwner(tran.getOwner());
            customer.setNextContactTime(tran.getNextContactTime());
            customer.setContactSummary(tran.getContactSummary());
            customer.setCreateTime(DateTimeUtil.getSysTime());
            customer.setCreateBy(tran.getCreateBy());
            int num1=customerDao.save(customer);
            if (num1 !=1) {
                flag=false;
            }
        }
        //添加交易
        tran.setCustomerId(customer.getId());
        int num2=tranDao.save(tran);
        if (num2 !=1) {
            flag=false;
        }

        //添加交易历史
        TranHistory tranHistory = new TranHistory();
        tranHistory.setStage(tran.getStage());
        tranHistory.setMoney(tran.getMoney());
        tranHistory.setCreateBy(tran.getCreateBy());
        tranHistory.setCreateTime(DateTimeUtil.getSysTime());
        tranHistory.setExpectedDate(tran.getExpectedDate());
        tranHistory.setId(UUIDUtil.getUUID());
        tranHistory.setTranId(tran.getId());
        int num3=tranHistoryDao.save(tranHistory);
        if (num3 !=1) {
            flag=false;
        }

        return flag;
    }

    @Override
    public Tran detail(String id) {
        return tranDao.detail(id);
    }

    @Override
    public List<TranHistory> getHistoryByTranId(String tranId) {
        return tranHistoryDao.getHistoryByTranId(tranId);
    }
}
