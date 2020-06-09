package com.sutong.transflow.model;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * null
 * 
 * @author wcyong
 * 
 * @date 2019-12-22
 */
@Data
@ToString
public class AuditPayBackFeeFlow implements Serializable{
    /**
     * 主键ID，格式：UUID+时间戳
     */
    private String flowId;

    /**
     * 补费流水号，系统生成
     */
    private String payBackFeeId;

    /**
     * 工单编号（从H5获取）
     */
    private String orderId;

    /**
     * 工单类型，1为非历史工单补费；2为历史工单补费
     */
    private String orderType;

    /**
     * 车牌号（从H5获取）
     */
    private String vehicleNumber;

    /**
     * 车牌颜色（从H5获取）
     */
    private String vehicleColour;

    /**
     * 补费总金额
     */
    private Integer payBackSum;

    /**
     * 用户id（客户号，从H5获取）
     */
    private String uid;

    /**
     * 用户token（从H5获取）
     */
    private String token;

    /**
     * 用户名（收银台用户名，从H5获取）
     */
    private String userName;

    /**
     * 用户身份证号（从H5获取）
     */
    private String idNumber;

    /**
     * 处理方省中心 Id， 表示交易文件是由哪个处理方省中心生成的
     */
    private String tollProvinceId;

    /**
     * 处理方省中心生成的文件 Id，同一处理方省中心内保证唯一，固定长度为 16 位，编码为省域编号+顺序码(14 位顺序递增，不足 14 位采取前位补 0 处理)。
     */
    private Long messageId;

    /**
     * 车辆车牌号码+颜色，格式为：车牌号码+间隔符+车牌颜色编码，间隔符：“_”；例：京 A12345_1 
     */
    private String vehicleId;

    /**
     * 通行标识ID，通行标识ID=通行介质ID(OBU序号编码/CPC卡编码)+入口时间（YYYYMMDDHHmmss）
     */
    private String passId;

    /**
     * 补费人姓名（从H5获取）
     */
    private String payBackUser;

    /**
     * 补费人联系方式（从H5获取）
     */
    private String payBackUserPhone;

    /**
     * 补费类型，1、ETC交易；2、其他交易
     */
    private Integer transactionType;

    /**
     * 用户卡编号，不超过20位，当需要电子发票时必填
     */
    private String etcCardId;

    /**
     * 补费渠道，补交该笔费用的入口方式 1：部中心 2：省中心 3：发行方 4：路段
     */
    private Integer payBackType;

    /**
     * 经办人姓名
     */
    private String operator;

    /**
     * 经办单位,当补费渠道=3发行方时，填写相应的发行方代码；当补费渠道=路段时，可不填
     */
    private String operateOrg;

    /**
     * 经办路段，当补费渠道=路段时，填写相应的路段编码
     */
    private String operateRoad;

    /**
     * 经办站，当补费渠道=路段时，填写相应的收费站编码
     */
    private String operateStation;

    /**
     * 应缴金额，单位：分
     */
    private Integer oweFee;

    /**
     * 此次补费的金额，单位：分
     */
    private Integer payBackFee;

    /**
     * 生成交易流水时间，YYYY-MM-DDTHH:mm:ss
     */
    private String flowCreateTime;

    private AuditPayEnd auditPayEnd;
}