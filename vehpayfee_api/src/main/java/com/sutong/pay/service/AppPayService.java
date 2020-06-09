package com.sutong.pay.service;

import com.sutong.bjstjh.entity.pay.AppPayParameterVo;
import com.sutong.bjstjh.entity.pay.AppPayRequestVo;

/**
 * Created by 王磊 on 2019/12/15.
 * APP支付service
 */
public interface AppPayService {

    //判断非历史逃费补费状态
    String doFindOrderStatus(String orderId);

    //判断历史逃费补费状态
    String doFindOrderStatusPast(String obuId);

    //调用“支付平台”APP支付预下单接口
    AppPayParameterVo doCallAppPayPreOrder(AppPayRequestVo requestVo) throws Exception;


}