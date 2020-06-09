package com.sutong.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.sutong.mapper.UserMapper;
import com.sutong.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public String getMsg() {
//        UserInfo userInfo = userMapper.query();
//        String msg = userInfo.getName();
//        return msg;
        return null;
    }

}
