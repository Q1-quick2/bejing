package com.sutong.feeRecord.model;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * @ClassName SupplementFeeRecordModel
 * @Description 补费流水查询实体类
 * @Author ly
 * @Date 2019/12/14 17:19
 */
@Data
@ToString
public class SupplementFeeRecordModel implements Serializable {
    /** 主键*/
    private Integer id;
    /**车牌号 */
    private String vehicleId;
    /**车牌颜色 */
    private String vehicleColour;
    /**交易流水号 */
    private String orderNo;
    /**姓名 */
    private String payBackUser;
    /**联系方式 */
    private String payBackUserPhone;
    /**补费方式 */
    private String transactionType;
    /**补费日期 */
    private String payBackTime;
    /**补费金额 */
    private Integer payBackFee;
    /**路段*/
    private String operateRoad;

}