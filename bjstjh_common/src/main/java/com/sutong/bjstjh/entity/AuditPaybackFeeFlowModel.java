package com.sutong.bjstjh.entity;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;
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
    /* *非历史补费流水表* */
    private String flowId;//主键ID
    private String paybackFeeId;//交易流水号
    private String orderId	;//工单编号
    private String orderType;//工单类型
    private String vehicleNumber;//车牌号
    private String vehicleColour;//车牌颜色
    private BigDecimal payBackSum;//补费总金额
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
    private BigDecimal oweFee;//应缴金额
    private BigDecimal payBackFee;//此次补费的金额
    private String flowCreateTime;//生成交易流水时间

    /* *补费结果表* */
//    private String END_CHARSET;//"只能取以下枚举值00-GBK默认00-GBK"
//    private String END_VERSION;//"固定值：1.0
//    private String END_SERVERCERT;//"服务器端的签名证书
//    private String END_SERVERSIGN;//"服务器对报文签名的签名结果
//    private String END_SIGNTYPE;//"RSA
//    private String END_SERVICE;//"固定值：NoticeResult
//    private String END_MERCHANTID;//"由云中心分配的商户号
//    private String END_RESULTCODE;//"详细请见7.2支付结果列表;
//    private String  END_RESULTMSG;//"描述具体的支付错误信息
//    private String ORDERNO;//商户请求订单号
//    private long END_TXNAMT	BIGINT;//单位：分
    private String endPayorderno;//支付成功后，微信/支付宝的交易订单号
    private String endPaymenttime;//时间格式yyyyyMMddHHmmss
    private String endPaytype;//"WXPAY-微信支付    ALIPAY-支付宝支付    BDWPAY-百度钱包支付    ETCPAY-ETC卡支付    UPOPPAY-银联支付"
//    private String END_RMK;//
    private BigDecimal endActpayamt;//实际支付金额 单位：分
//    private long END_DISCOUNTSAMT	BIGINT					单位：分
//    private String END_REQUESTTIME;//响应时间
//    private long END_DISCOUNTSINFOLISTNUM	BIGINT
//    private String END_DISCOUNTSINFOLIST;//


    private String payhistory;//历史/非历史
    private String paybegintime;//起始时间（仅用于查询）
    private String payendtime;//截至时间（仅用于查询）

}