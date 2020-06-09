package com.sutong.dodgingtoll.model;


import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
@Data
@ToString
public class AuditPastOrder implements Serializable{
    /*
     * 主键
     */
    private String pastOrderId;

    /*
     * 客户名称
     */
    private String customerName;

    /*
     * 联系电话
     */
    private String customerPhone;

    /*
     * OBU号
     */
    private String obuId;

    /*
     * 交易次数
     */
    private String transNum;

    /*
     * 交易总金额（单位：分）
     */
    private String transAllMoney;

    /*
     * 应缴总金额（单位：分）
     */
    private String oweFee;
    //缴费状态(补交所有：0，有未补交：1)
    private String orderStatus;


}