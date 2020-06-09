/**
 * @Description: 通行费差额汇总表实体类
 * @ClassName: AuditPastOrderEntity
 * @author： WangLei
 * @date: 2019/12/18 19:13
 * @Version： 1.0
 */
package com.sutong.bjstjh.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by 王磊 on 2019/12/18.
 */
@Data
public class AuditPastOrderEntity implements Serializable {

    private String pastOrderId;//序号
    private String customerName;//客户名称
    private String customerPhone;//联系电话
    private String obuId;//OBU号
    private String transNum;//交易次数
    private String transAllMoney;//交易总金额（单位：分）
    private String oweFee;//应缴总金额（单位：分）
    private String orderStatus;//补缴状态，1：已缴费，2：未缴费

}
