package com.sutong.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.sutong.mapper.GetMsgMapper;
import com.sutong.model.UserModel;
import com.sutong.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 * Created by 70954 on 2019/12/10.
 */
@Component
@Service
public class GetMsgImpl implements TestService {

    @Autowired
    private GetMsgMapper getMsgMapper;

    @Override
    public String getMsg() {
        UserModel userModel =  getMsgMapper.query();
        String msg = userModel.getName();
        return msg;
    }


}
