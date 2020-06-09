package com.sutong.pay.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.sutong.bjstjh.entity.pay.LogModel;
import com.sutong.pay.mapper.LogMapper;
import com.sutong.pay.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

/**
 * Created by main on 2019/12/24.
 */
@Service
@Component
public class LogServiceImpl implements LogService {

    @Autowired
    private LogMapper logMapper;

    // AOP切面记录日志
    @Override
    public void doAddAspectj(LogModel logModel) {
        logMapper.doAddAspectj(logModel);
    }



    // 定时清理15天前日志
    @Scheduled(cron = "0 15 10 ? * *")
    public void timer(){
        // 获取15天前的日期
        LocalDateTime now = LocalDateTime.now();
        now = now.minus(15, ChronoUnit.DAYS);
        String date = now.toString().substring(0,10);
        logMapper.doDelete(date);
    }


}
