/**
 * @Description: 客户逃费实体
 * @ClassName: RunFee
 * @author： Mr.Kong
 * @date: 2019/12/13 15:13
 * @Version： 1.0
 */
package com.sutong.history.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class PastHistoryModel implements Serializable {

    /** 工单编号*/
    private String pastOrderId;
    /** 客户名称*/
    private String customerName;
    /** 联系电话*/
    private String customerPhone;
    /** OBU号*/
    private String obuId;
    /** 交易次数*/
    private String transNum;
    /** 交易总金额*/
    private String transAllMoney;
    /** 应补缴总金额*/
    private String owFee;
    /** 是否所有的历史逃费记录都已补缴完成：1为已缴费；2为未缴费*/
    private String orderStatus;
}
