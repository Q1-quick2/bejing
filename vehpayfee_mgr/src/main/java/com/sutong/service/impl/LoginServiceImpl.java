package com.sutong.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.sutong.bjstjh.entity.LoginModel;
import com.sutong.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by main on 2019/12/22.
 */
@Service
@Component
public class LoginServiceImpl implements LoginService {



    @Autowired
    private LoginServiceMapper loginServiceMapper;

    // 登录
    @Override
    public LoginModel login(String account, String password) {
        return loginServiceMapper.login(account,password);
    }
















}
