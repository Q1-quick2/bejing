package com.sutong.pay.service;

import com.sutong.bjstjh.entity.*;

/**
 * Created by main on 2019/12/14.
 */
public interface AppRepairfeeService {

    // 获取补费结果
    PayEndInformModel doFindRepairfeeEnd(String userId);

    //客户补费凭证
    VoucherModel doRepairfeeVoucher(String orderId);

    //支付结果通知
    PayEndInformModel AppPayEndInform(PayEndInformModel payEndInformModel);

    //高速公路通行费补费确认单  获取
    ConfirmationEntity doConfirmationInfo(String obu);

    //高速公路通行费补费确认单  保存
    Boolean doUpdateConfirmationInfo(ConfirmationEntity confirmationEntity);

    // 发送短信 通知用户
    UserModel doFindPhone(String paySerialNo);

    // 把短信信息 保存到数据库
    void doInsertNotification(NotificationModel notificationModel);

    // 根据OUB获取确认单信息
    AuditPastOrderEntity doFindListQuery(String obu);

    //  获取确认单信息    车牌号   车型
    ConfirmationEntity doFindVehicle(String obu);

    // 编号 ID
    ConfirmationEntity getConfirId(String obu) throws Exception;
}
