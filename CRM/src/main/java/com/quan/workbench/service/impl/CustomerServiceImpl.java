package com.quan.workbench.service.impl;

import com.quan.workbench.dao.CustomerDao;
import com.quan.workbench.service.CustomerService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {


    @Resource
    private CustomerDao customerDao;

    @Override
    public List<String> getCustomerName(String name) {
        return customerDao.getLikeName(name);
    }
}
