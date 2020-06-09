/**
 * @Description: 历史补费流水表实体类
 * @ClassName: PaybackFeeFlowPastEntity
 * @author： WangLei
 * @date: 2019/12/16 17:26
 * @Version： 1.0
 */
package com.sutong.bjstjh.entity.pay;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by 王磊 on 2019/12/16.
 */
@Data
public class PaybackFeeFlowPastEntity implements Serializable{

    private String flowId;//主键ID
    private String payBackFeeId;//补费流水号
    private String orderType;//工单类型
    private String vehicleNumber;//车牌号
    private Integer payBackSum;//补费总金额
    private String uid; //用户id（客户号）
    private String token; //用户token
    private String userName; //用户名（收银台用户名）
    private String idNumber;//身份证号
    private String payBackUser;//补费人姓名
    private String payBackUserPhone;//补费人联系方式
    private Integer transactionType;//补费类型
    private Integer payBackType;//补费渠道
    private String flowCreateTime;//生成交易流水时间

    private String customerName;//客户名称
    private String customerPhone;//客户联系电话
    private String obuId;//OBU号
    private String transNum;//交易次数
    private Integer transAllMoney;//交易总金额
    private Integer oweFee;//应缴总金额


}