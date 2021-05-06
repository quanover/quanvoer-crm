package com.quan.workbench.dao;

import com.quan.workbench.domain.Contacts;

import java.util.List;

public interface ContactsDao {
    int save(Contacts contacts);

    List<Contacts> getContactsLikeName(String name);
}
