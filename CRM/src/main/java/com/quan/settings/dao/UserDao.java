package com.quan.settings.dao;

import com.quan.settings.domain.User;

import java.util.List;

public interface UserDao {

    User login(User user);

    List<User> getUserList();
}
