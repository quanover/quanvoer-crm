package com.quan.settings.service;

import com.quan.exception.LoginException;
import com.quan.settings.domain.User;

import java.util.List;

public interface UserService {

    User userLogin(User user) throws LoginException;

    List<User> getUserList();
}
