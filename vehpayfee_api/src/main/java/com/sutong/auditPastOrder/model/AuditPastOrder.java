package com.sutong.auditPastOrder.model;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * @Description: 通行费差额汇总单实体
 * @author： Mr.Kong
 * @date: 2019/12/18 9:45
 */
@Data
@ToString
public class AuditPastOrder implements Serializable {

    /**
     * 主键
     */
    private String pastOrderId;

    /**
     * 客户名称
     */
    private String customerName;

    /**
     * 联系电话
     */
    private String customerPhone;

    /**
     * OBU号
     */
    private String obuId;

    /**
     * 交易次数
     */
    private String transNum;

    /**
     * 交易总金额（单位：分）
     */
    private String transAllMoney;

    /**
     * 应缴总金额（单位：分）
     */
    private String oweFee;

    /**
     * 工单状态
     */
    private String orderStatus;

    /**
     * 交易时间
     */
    private Date transTime;

    //=============================
    /**
     * 开始时间
     */
    private String startTimeStr;
    /**
     * 结束时间
     */
    private String endTimeStr;
    /**
     * 开始时间
     */
    private Date startTimeDate;
    /**
     * 结束时间
     */
    private Date endTimeDate;

}
