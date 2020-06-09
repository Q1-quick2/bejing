package com.sutong.transflow.model;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigInteger;

/**
 * @Description: 补费流水查询model类
 * @ClassName: PaybackFeeFlowModel
 * @author： FengJingBo
 * @date: 2019/12/16 10:27
 * @Version： 1.0
 */
@Data
@ToString
public class AuditPaybackFeeFlowModel implements Serializable{

    private String flowId;//主键ID
    private String paybackFeeId;//交易流水号
    private String orderId	;//工单编号
    private String orderType;//工单类型
    private String vehicleNumber;//车牌号
    private String vehicleColour;//车牌颜色
    private Integer payBackSum;//补费总金额
    private String uid;//用户id/客户号
    private String token;//用户token
    private String userName;//用户名
    private String idNumber;//用户身份证号
    private String tollProvinceId	;//处理方省中心 Id
    private BigInteger messageId;//处理方省中心生成的文件 Id
    private String vehicleId;//车辆车牌号码+颜色
    private String passId;//通行标识ID
    private String payBackUser;//补费人姓名
    private String payBackUserPhone;//补费人联系方式
    private Integer transactionType;//补费类型
    private String etcCardId;//用户卡编号
    private Integer payBackType;//补费渠道
    private String operator;//经办人姓名
    private String operateOrg;//经办单位
    private String operateRoad;//经办路段
    private String operateStation;//经办站
    private Integer oweFee;//应缴金额
    private Integer payBackFee;//此次补费的金额
    private String flowCreateTime;//生成交易流水时间

    private String payhistory;//历史/非历史
    private String paybegintime;//起始时间（仅用于查询）
    private String payendtime;//截至时间（仅用于查询）

}