package com.quan.settings.service.impl;

import com.quan.exception.LoginException;
import com.quan.settings.dao.UserDao;
import com.quan.settings.domain.User;
import com.quan.settings.service.UserService;
import com.quan.util.DateTimeUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {


    @Resource
    private UserDao userDao;
    @Override
    public User userLogin(User loginUser ) throws LoginException {
        User user=userDao.login(loginUser);

        if(user==null){
            throw new LoginException("账号密码错误");
        }
        String expireTime=user.getExpireTime();
        String nowTime= DateTimeUtil.getSysTime();

        if (expireTime.compareTo(nowTime)<0){
            throw new LoginException("账号已失效");
        }

        //锁定状态 0 锁定 1可用
        String lockState=user.getLockState();
        if ("0".equals(lockState)){
            throw new LoginException("账号被锁定");
        }

        return user;
    }


    @Override
    public List<User> getUserList() {
        return userDao.getUserList();
    }
}
