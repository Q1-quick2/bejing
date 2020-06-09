package com.sutong.auditDoorFrame.model;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * @Description: 门架数据实体
 * @author： Mr.Kong
 * @date: 2019/12/23 11:23
 */
@Data
@ToString
public class AuditDoorFrame implements Serializable {

    /**
     * 工单编号
     */
    private String orderId;

    /**
     * 计费交易编号
     */
    private String billingTransactionNo;

    /**
     * 处理方省中心Id
     */
    private String tollProvinceId;

    /**
     * 路段
     */
    private String roadId;

    /**
     * 收费单元名称
     */
    private String collectingUnit;

    /**
     * 实际车牌号码
     */
    private String actualVehicleId;

    /**
     * 实际车牌颜色
     */
    private String actualVehicleColour;

    /**
     * 识别车牌
     */
    private String discernVehicleId;

    /**
     * 识别颜色
     */
    private String discerneColour;

    /**
     * 交易时间
     */
    private Date transactionTime;

    /**
     * 计费车型
     */
    private String chargingVehicle;

    /**
     * 识别车型
     */
    private String confirmedescapeType;

    /**
     * 通行介质编码
     */
    private String passCode;

    /**
     * CPC 卡 或 ETC 卡的编号
     */
    private String passId;

    /**
     * 车辆识别流水号
     */
    private String transactionId;

    /**
     * 通行介质类型
     */
    private String mediaType;

    /**
     * OBU 序号编码
     */
    private String obuId;

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
