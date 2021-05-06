package com.quan.workbench.service.impl;

import com.quan.workbench.dao.ContactsDao;
import com.quan.workbench.domain.Contacts;
import com.quan.workbench.service.ContactsService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ContactsServiceImpl implements ContactsService {


    @Resource
    private ContactsDao contactsDao;

    @Override
    public List<Contacts> getContactsLikeName(String name) {



        return contactsDao.getContactsLikeName(name);
    }
}
