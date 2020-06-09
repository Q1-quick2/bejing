package com.sutong.pay.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.sutong.bjstjh.entity.AppAuditInvoiceInformationModel;
import com.sutong.bjstjh.entity.AppAuditPastOrderModel;
import com.sutong.pay.mapper.AppAuditInvoiceInformationMapper;
import com.sutong.pay.mapper.AppAuditPastOrderMapper;
import com.sutong.pay.service.AppAuditPastOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;


@Component
@Service
public class AppAuditPastOrderServiceImpl implements AppAuditPastOrderService {

   /**
     * 引入历史补费mapper
     */
    @Autowired
    private AppAuditPastOrderMapper auditPastOrderMapper;
    /**
     * 引入发票信息mapper
     */
    @Autowired
    private AppAuditInvoiceInformationMapper informationMapper;

    /**
     * 查询历史补费流水详情信息
     */
    @Override
    public AppAuditPastOrderModel getAppAuditPastOrderInfoResult(AppAuditPastOrderModel auditPastOrderModel) {

        return auditPastOrderMapper.getAppAuditPastOrderInfoModel(auditPastOrderModel);
    }
    /**
     * 查询历史补费流水列表集合
     */
    @Override
    public List<AppAuditPastOrderModel> getAppAuditPastOrderResultList(AppAuditPastOrderModel auditPastOrderModel) {
        return auditPastOrderMapper.getAppAuditPastOrderModelList(auditPastOrderModel);
    }

    /**
     * 查询历史补费发票信息
     */
    @Override
    public AppAuditInvoiceInformationModel getAppAuditInvoiceInformationPastResult(AppAuditInvoiceInformationModel model) {

        return informationMapper.getAppAuditInvoiceInformationPastModel(model);
    }

    /**
     * 将历史补费发票信息存入数据库
     */
    @Override
    public int depositinappAuditInvoiceInformationPastService(Map<String,String> map) {
        int n = 0;
        String obuid = map.get("obuId");
        AppAuditPastOrderModel model1 = new AppAuditPastOrderModel();
        model1.setObuId(obuid);
        List<AppAuditPastOrderModel> list = auditPastOrderMapper.getAppAuditPastOrderModelList(model1);
        AppAuditPastOrderModel model = null;
        if (list != null && list.size() > 0) {
            model = list.get(0);
        }
        /*
         *生成历史补费流水发票信息
         */
        AppAuditInvoiceInformationModel appinformationModel = new AppAuditInvoiceInformationModel();
        if (model != null) {
            appinformationModel.setObuId(obuid);//OBU编号
            appinformationModel.setOrderNumber(model.getPayBackFeeIdPast()); //订单号
            appinformationModel.setCommodityOrderNo(model.getEndPayorderno()); //第三方支付订单号
            appinformationModel.setFeeTransTime(model.getEndPaymenttime());//补费时间
            appinformationModel.setInvoiceVehicleId("###");//车辆车牌号+颜色
            if (model.getEndActpayamt() != null && !"".equals(model.getEndActpayamt())) {
                appinformationModel.setBuyerPaybackTotal(
                        (model.getEndActpayamt().multiply(new BigDecimal("0.01"))).toString());//购买方补费合计 单位:元
            }else {
                appinformationModel.setBuyerPaybackTotal("###");
            }

        }

        appinformationModel.setBuyerName(map.get("buyerName"));//购买方名称
        appinformationModel.setBuyerRatepayingNumber(map.get("buyerRatepayingNumber"));//购买方纳税识别号
        appinformationModel.setBuyerPhone(map.get("buyerPhone"));//购买方电话
        appinformationModel.setPurchasersAddress(map.get("purchasersaddress"));//购买方地址
        appinformationModel.setBuyerBankNumber(map.get("buyerBankNumber")); //购买方开户行及账号
        appinformationModel.setInvoiceType(map.get("invoiceType"));//发票类型
        appinformationModel.setPersonalMailBox(map.get("personalMailBox")); //收件人邮箱
        appinformationModel.setPersonalPhone(map.get("personalPhone"));//收件人电话
        appinformationModel.setPersonalName(map.get("personalName"));//收件人姓名
        appinformationModel.setPersonalDetailedAdress(map.get("personalDetailedAdress"));//收件人地址


        appinformationModel.setSellerPhone("###");//销售方联系方式
        appinformationModel.setSellerRatepayingNumber("###");//销售方纳税识别号
        appinformationModel.setSellerBankNumber("###"); //销售方开户行及账号
        appinformationModel.setPayee("###");//收款人
        appinformationModel.setRevier("###"); //复核
        appinformationModel.setDrawer("###"); //开票人
        appinformationModel.setOpenInvoiceTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));//开票日期
        if (model != null && model.getObuId() != null && !"".equals(model.getObuId())) {
            n = informationMapper.insertAppAuditInvoiceInformationPast(appinformationModel);
        }
        return n;
    }


}