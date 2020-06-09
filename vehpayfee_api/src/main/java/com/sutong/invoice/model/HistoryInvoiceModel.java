package com.sutong.invoice.model;

import lombok.Data;

import java.io.Serializable;

/**
 * @Description:历史发票导出
 * @ClassName: historyInvoice
 * @author： liyan
 * @date: 2019/12/25 14:56
 * @Version： 1.0
 */
@Data
public class HistoryInvoiceModel implements Serializable {
    /** OBU号*/
    private String obuId;
    /** 购买方名称*/
    private String  buyerName;
    /** 购买方纳税识别号*/
    private String buyerRatepayingNumber;
    /** 购买方电话*/
    private String buyerPhone;
    /** 购买方开户行及账号*/
    private String buyerBankNumber;
    /** *购买方补费合计*/
    private String buyerPaybackTotal;
    /** 销售方联系方式*/
    private String sellerPhone;
    /** 销售方纳税识别号*/
    private String sellerRatepayingNumber;
    /** 销售方开户行及账号*/
    private String sellerBankNumber;
    /** 收款人*/
    private String payee;
    /** 复核*/
    private String revier;
    /** 开票人*/
    private String drawer;
    /** 开票日期*/
    private String openInvoiceTime;
    /** 发票类型*/
    private String invoiceType;
    /** 订单号*/
    private String orderNumber;
    /** 第三方支付订单号*/
    private String commodityOrderNo;
    /** 补费时间*/
    private String feeTransTime;
    /** 收件人邮箱*/
    private String personalMailBox;
    /** 收件人电话*/
    private String personalPhone;
    /** 收件人姓名*/
    private String personalName;
    /** 收件人详细地址*/
    private String personalDetailedAdress;
    /** 车牌号+颜色*/
    private String invoiceVehicleId;
//    /** 车牌号*/
//    private String carNum;
//    /** 车牌颜色*/
//    private String carNumColor;
    /** 开始时间*/
    private String beginTime;
    /** 结束时间*/
    private String endTime;
}