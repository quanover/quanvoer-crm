package com.quan.workbench.dao;

import com.quan.workbench.domain.Customer;

import java.util.List;

public interface CustomerDao {
    Customer getByName(String company);

    int save(Customer customer);

    List<String> getLikeName(String name);
}
