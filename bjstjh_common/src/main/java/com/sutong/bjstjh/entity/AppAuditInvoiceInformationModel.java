package com.sutong.bjstjh.entity;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * Created by nt on 2019/12/24.
 */
@Data
@ToString
public class AppAuditInvoiceInformationModel implements Serializable{

    //历史补费发票信息主键
    private String invoicePastId;
    //当前补费发票信息主键
    private String invoiceId;
    //工单编码
    private String orderId;
    //OBU号
    private String obuId;
    //购买方名称
    private String buyerName;
    //购买方纳税识别号
    private String buyerRatepayingNumber;
    //购买方电话
    private String buyerPhone;
    //购买方地址
    private String purchasersAddress;
    //购买方开户行及账号
    private String buyerBankNumber;
    //购买方补费合计
    private String buyerPaybackTotal;
    //销售方联系方式
    private String sellerPhone;
    //销售方纳税识别号
    private String sellerRatepayingNumber;
    //销售方开户行及账号
    private String sellerBankNumber;
    //收款人
    private String payee;
    //复核
    private String revier;
    //开票人
    private String drawer;
    //开票日期
    private String openInvoiceTime;
    //发票类型
    private String invoiceType;
    //订单号
    private String orderNumber;
    //第三方支付订单号
    private String commodityOrderNo;
    //补费时间
    private String feeTransTime;
    //收件人邮箱
    private String personalMailBox;
    //收件人电话
    private String personalPhone;
    //收件人姓名
    private String personalName;
    //收件人地址
    private String personalDetailedAdress;
    //车辆车牌号+颜色
    private String invoiceVehicleId;
    //同步接口的交易编号
    private String transId;
    //补费流水
    private String flowId;
    //换票次数
    private String changeNumbers;
    //发票代码
    private String invoiceCode;
    //发票号
    private String invoiceNumber;
    //失效/生效
    private String invalidEffective;

    //开票开始时间
    private String invoicebegintime;
    //开票截止时间
    private String invoiceendtime;
    //查询条件
    private String conditional_query;
}
