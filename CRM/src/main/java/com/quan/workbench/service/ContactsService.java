package com.quan.workbench.service;

import com.quan.workbench.domain.Contacts;

import java.util.List;

public interface ContactsService {
    List<Contacts> getContactsLikeName(String name);
}
