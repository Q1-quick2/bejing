package com.sutong.pay.service;

import com.sutong.bjstjh.entity.PayEndInformModel;

/**
 * Created by 王磊 on 2019/12/21.
 * APP支付结果查询Service
 */
public interface AppPayResultService {

    //调用支付平台接口查询支付结果
    PayEndInformModel doCallAppPayResult(String orderNo) throws Exception;


}
