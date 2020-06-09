package com.sutong.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.sutong.bjstjh.entity.LoginModel;
import com.sutong.bjstjh.result.Result;
import com.sutong.bjstjh.util.NotNullUtil;
import com.sutong.service.LoginService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by main on 2019/12/22.
 */
@RestController
@Api(value = "登录")
@CrossOrigin
public class LoginController {



    @Reference
    private LoginService loginService;


    /**
     * @description: 登录
     * @auther: zhaodengzhuang
     * @date: 2019/12/22
     */
    @ApiOperation("登录")
    @PostMapping("/login")
    public Result login(@RequestBody LoginModel userModel){
        userModel.setPassword(NotNullUtil.Md5(userModel.getPassword()));
        LoginModel user = loginService.login(userModel.getUsername(),userModel.getPassword());
        if(user != null && !NotNullUtil.isEmpty(user)){
            return Result.ok();
        }else{
            return Result.error2();
        }
    }






}
