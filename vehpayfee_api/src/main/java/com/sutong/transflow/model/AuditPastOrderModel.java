package com.sutong.transflow.model;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * Created by nt on 2019/12/18.
 */
@Data
@ToString
public class AuditPastOrderModel implements Serializable {
    /* *历史补费流水* */
    private String flowId;//主键id，格式：uuid+时间戳
    private String payBackFeeId;//补费流水号，系统生成
    private String orderType;//工单类型，1为非历史工单补费；2为历史工单补费

    private Integer payBackSum;//补费总金额
    private String uid;//用户id（客户号，从h5获取）
    private String token;//用户token（从h5获取）
    private String userName;//用户名（收银台用户名，从h5获取）
    private String idNumber;//用户身份证号（从h5获取）
    private String payBackUser;//补费人姓名（从h5获取）
    private String payBackUserPhone;//补费人联系方式（从h5获取）
    private Integer transactionType;//补费类型，1、etc交易；2、其他交易
    private Integer payBackType;//补费渠道，补交该笔费用的入口方式 1：部中心 2：省中心 3：发行方 4：路段
    private String flowCreateTime;//生成交易流水时间，yyyy-mm-ddthh:mm:ss





    /* *通行费差额汇总单* */
    private  String pastOrderId;//序号
    private  String customerName;//客户名称
    private  String customerPhone;//联系电话
    private  String obuId;//obu号
    private  String transNum;//交易次数
    private  String transAllMoney;//交易总金额（单位：分）

    private  Integer oweFeeInfo;//应缴总金额（单位：分）
    private  String transTime;//
    private  String orderStatus;//是否所有的历史逃费记录都已补缴完成

    /* *通行费差额明细单* */
    private  String id;//主键
    private  String pastOrderIdInfo;//主键
    private  String enstation;//入口
    private  String exstation;//出口
    private  String transVehicleType;//交易时车型
    private  String doVehicleType;//办理时车型
    private  String vehicleType;//实际车型
    private  String transVehicleId;//交易时车牌号
    private  String doVehicleId;//办理时车牌号
    private  String  transSubtrFee;//交易时扣费（单位：分）
    private  String oweFee;//应扣费（单位：分）
    private  String subtrFee;//差额（单位：分）

    private  String transObuId;//交易时obu号
    private  String transCardId;//交易时卡号
    private  String orderStatusInfo;//此逃费记录是否已补费完成：0未完成，1已完成

    private  String payBackFeeReality;//实缴金额
    private  String endPaytype;//支付类型


    /* *时间查询条件* */
    private String begintime;//开始时间
    private String endtime;//结束时间
}
