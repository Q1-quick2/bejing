/**
 * @Description: 非历史补费流水表实体类
 * @ClassName: PaybackFeeFlowEntity
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
public class PaybackFeeFlowEntity implements Serializable{

    private String flowId;//主键ID
    private String payBackFeeId;//补费流水号
    private String orderId;//工单编码
    private String orderType;//工单类型
    private String vehicleNumber;//车牌号
    private String vehicleColour;//车牌颜色
    private Integer payBackSum;//补费总金额
    private String uid; //用户id（客户号）
    private String token; //用户token
    private String userName; //用户名（收银台用户名）
    private String idNumber;//身份证号

    private String tollProvinceId;//处理方省中心ID
    private Long messageId;//处理方省中心生成的文件ID
    private String vehicleId;//车辆编号
    private String passId;//通行标识
    private String payBackUser;//补费人姓名
    private String payBackUserPhone;//补费人联系方式
    private Integer transactionType;//补费类型
    private String etcCardId;//用户卡编号
    private Integer payBackType;//补费渠道
    private String operator;//经办人姓名
    private String operateOrg;//经办人单位
    private String operateRoad;//经办路段
    private String operateStation;//经办站
    private Integer oweFee;//应缴金额
    private Integer payBackFee;//此次补费金额
    private String flowCreateTime;//生成交易流水时间

}