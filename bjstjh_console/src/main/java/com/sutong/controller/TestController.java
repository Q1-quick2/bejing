package com.sutong.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.sutong.service.TestService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class TestController {

    @Reference
    private TestService testService;


    @ResponseBody
    @RequestMapping("/test2")
    public String test(){
        return testService.getMsg();
    }




}
