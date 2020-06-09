package com.sutong.pay.service;

import com.sutong.bjstjh.entity.pay.LogModel;

/**
 * Created by main on 2019/12/24.
 * AOP切面日志
 */
public interface LogService {
    //记录日志
    void doAddAspectj(LogModel logModel);
}
