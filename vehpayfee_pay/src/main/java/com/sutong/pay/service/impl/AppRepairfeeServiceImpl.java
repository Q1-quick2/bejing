package com.sutong.pay.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.sutong.bjstjh.entity.*;
import com.sutong.bjstjh.util.NotNullUtil;
import com.sutong.pay.mapper.*;
import com.sutong.pay.service.AppRepairfeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by main on 2019/12/14.
 */
@Component
@Service
public class AppRepairfeeServiceImpl implements AppRepairfeeService {

    Logger log = LoggerFactory.getLogger(AppRepairfeeServiceImpl.class);
    @Autowired
    private VoucherMapper voucherMapper;
    @Autowired
    private PayEndInformMapper payEndInformMapper;
    @Autowired
    private NotificationMapper notificationMapper;
    @Autowired
    private ConfirmationMapper confirmationMapper;
    @Autowired
    private AppPayMapper appPayMapper;

    //  补费结果获取
    @Override
    public PayEndInformModel doFindRepairfeeEnd(String paySerialNo) {
        try {
            PayEndInformModel payEndInformModel = payEndInformMapper.doFindRepairfeeEnd(paySerialNo);
        if(payEndInformModel!= null && payEndInformModel.getPaymentTime() != null && !payEndInformModel.getPaymentTime().equals("")){
            // 支付状态
            String resultCode = payEndInformModel.getResultCode();
            switch (resultCode){
                case "BD":
                    payEndInformModel.setResultCode("支付成功");
                    break;
                case "BF":
                    payEndInformModel.setResultCode("订单作废");
                    break;
                case "BB":
                    payEndInformModel.setResultCode("等待付款");
                    break;
                default:
                    payEndInformModel.setResultCode("正在支付(等待查询)");
                    break;
            }
            //  时间
            String paymentTime = payEndInformModel.getPaymentTime();
            String reg = "(\\d{4})(\\d{2})(\\d{2})(\\d{2})(\\d{2})(\\d{2})";
            paymentTime = paymentTime.replaceAll(reg, "$1-$2-$3 $4:$5:$6");
            payEndInformModel.setPaymentTime(paymentTime);
          }
            return payEndInformModel;
        }catch (Exception e){
            log.info("======补费结果获取 异常=========");
            return null;
        }

    }




    //     客户补费凭证
    @Override
    public VoucherModel doRepairfeeVoucher(String orderId){
        return voucherMapper.doRepairfeeVoucher(orderId);
    }


    //     支付结果通知
    @Override
    public PayEndInformModel AppPayEndInform(PayEndInformModel payEndInformModel) {
        // 流水订单号
        String paySerialNo = payEndInformModel.getPaySerialNo();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String requestTi = formatter.format(new Date());
        payEndInformModel.setRequestTime(requestTi);
        //  判断是否历史
        List<String> pay = payEndInformMapper.doFindHistory(paySerialNo);
        if(pay != null && !pay.equals("")&& pay.size() > 0){    //1.非历史
            log.info("非历史");
            // 一. 补费结果保存到数据库
            payEndInformModel.setHistory("1");
            try {
                payEndInformMapper.AppPayEndInform(payEndInformModel);
            }catch (Exception e){
                log.error("======补费结果表新增异常======");
            }
            // 二. 把 未缴费 状态 改成 缴费      2.未缴费   1.已缴费
            //        1. 通过订单号获取流水表
            List<String> orderId = payEndInformMapper.doFindRepairfeeWater(paySerialNo);
            //        2. 通过流水表orderId修改状态
            log.info("通过流水表orderId修改状态"+orderId);
            for (String s : orderId) {
            payEndInformMapper.AppUpdateStatus(s);
            }
            // 四. 推送至发行系统
            // 五. 补费流水 推送  清分系统
            // 六. 补费流水 推送  部中心
        }else{
                log.info("历史");
                payEndInformModel.setHistory("2");
                try {
                    payEndInformMapper.AppPayEndInform(payEndInformModel);
                }catch (Exception e){
                    log.error("======补费结果表新增异常======");
                }
                // 二. 把 未缴费 状态 改成 缴费      2.未缴费   1.已缴费
                //        1. 通过订单号获取流水表
                String obuId = payEndInformMapper.doFindObo(paySerialNo);
                if(obuId != null && !obuId.equals("")){
                    // 修改两个表状态
                    payEndInformMapper.doUpdateStatus(obuId);
                    //通行费差额明细单插入数据  且修改状态
                    appPayMapper.doUpdateFlowAndFee(paySerialNo,obuId);
                }
        }
        //  公共的   是否为历史都进行回调  七. 补费结果返回
        PayEndInformModel payEnd = payEndInformMapper.doFindReturn(paySerialNo);
        return payEnd;
    }



    //      高速公路通行费补费确认单   获取
    @Override
    public ConfirmationEntity doConfirmationInfo(String obu) {
        ConfirmationEntity confirmationa = confirmationMapper.doConfirmationInfo(obu);
        return confirmationa;
    }




    //     高速公路通行费补费确认单  保存
    @Override
    public Boolean doUpdateConfirmationInfo(ConfirmationEntity confirmationEntity) {
        ConfirmationEntity confirmation = confirmationMapper.doConfirmationInfo(confirmationEntity.getPayRfid());

        String payEndTime = confirmation.getPayEndTime();
        String a = payEndTime.substring(0,10);
        a = a.replace("/","-");
        confirmation.setPayEndTime(a);
        String payTime = confirmation.getPayTime();
        String b = payTime.substring(0,10);
        b = b.replace("/","-");
        confirmation.setPayTime(b);
        if(confirmation != null && !NotNullUtil.isEmpty(confirmation)){
            // 修改
            return confirmationMapper.doUpdateConfirmat(confirmationEntity);
        }else{
            // 新增
            return confirmationMapper.doUpdateConfirmationInfo(confirmationEntity);
        }
    }






    // 获取手机号
    @Override
    public UserModel doFindPhone(String paySerialNo) {
        UserModel userModel = payEndInformMapper.doFindPhoneHistory(paySerialNo);
        return userModel;
    }


    //     把短信信息保存到 数据库
    @Override
    public void doInsertNotification(NotificationModel notificationModel) {
        //      判断是否历史
        List<String> pay = payEndInformMapper.doFindHistory(notificationModel.getPayRecord());
        if(pay != null && !pay.equals("")&& pay.size() > 0){
            notificationModel.setHistory("1");      // 非历史
        }else{
            notificationModel.setHistory("2");       // 历史
        }
        notificationMapper.doInsertNotification(notificationModel);
    }

    //  获取确认单信息    车牌号   车型
    @Override
    public AuditPastOrderEntity doFindListQuery(String obu) {
        return confirmationMapper.doFindListQuery(obu);
    }

    //  获取确认单信息    车牌号   车型
    //  获取开始结束时间
    @Override
    public ConfirmationEntity doFindVehicle(String obu) {
        List<ConfirmationEntity> confirmationEntity =  confirmationMapper.doFindVehicle(obu);
        ConfirmationEntity confirmation = confirmationMapper.doFindTime(obu);
        String raid1 = confirmationEntity.get(0).getRaid1();
        String raid2 = confirmationEntity.get(0).getRaid2();
        confirmationEntity.get(0).setPaySite(raid1+"-"+raid2+"等");//  涉及路段与站点
        confirmationEntity.get(0).setPayTime(confirmation.getPayTime());//  年  月  日
        confirmationEntity.get(0).setPayEndTime(confirmation.getPayEndTime());//  至  年  月  日期间
        return confirmationEntity.get(0);
    }


    //获取 编号 ID
    @Override
    public ConfirmationEntity getConfirId(String obu)throws Exception {
            List<ConfirmationEntity> c = confirmationMapper.getConfirId(obu);
            return c.get(0);
    }




}
