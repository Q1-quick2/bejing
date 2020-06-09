package com.sutong.service;

import com.sutong.bjstjh.entity.LoginModel;

/**
 * Created by main on 2019/12/22.
 */
public interface LoginService {

    // 登录
    LoginModel login(String account, String password);
}
