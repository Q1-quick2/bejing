package com.sutong.auditRoadData.model;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * @Description: 路方数据实体
 * @author： Mr.Kong
 * @date: 2019/12/19 14:58
 */

@Data
@ToString
public class AuditRoadData implements Serializable {

    /**
     * 工单ID
     */
    private String orderId;

    /**
     * 车牌号码+颜色
     */
    private String vehicleId;

    /**
     * 车型
     */
    private String carType;

    /**
     * 车种
     */
    private String carModel;

    /**
     * 通行标识 ID=通行介质ID(OBU 序号编码/CPC 卡编码)+入口时间（YYYYMMDDHHmmss）
     */
    private String passid;

    /**
     * 通行介质类型：1：OBU；2：CPC 卡；3：纸券；9：无通行介质
     */
    private String mediatype;

    /**
     * 不超过 20 个字符，当通行介质类型=1OBU时必填
     */
    private String obuid;

    /**
     * CPC 卡 或 ETC卡的编号，不超过 20 位无介质通行为：030纸为： 01+纸券标识(不超过20位)
     */
    private String cardid;

    /**
     * 疑似逃费类型，由两级构成，两级之间用“-”分割，多个原因以“|”分割，具体编码参考：表十五，例如：1-2|3-6
     */
    private String escapetype;

    /**
     * 入口处理时间 YYYY-MM-DDTHH:mm:ss
     */
    private String entime;

    /**
     * 入口车道
     */
    private String enlaneid;

    /**
     * 入口站
     */
    private String enstation;

    /**
     * 出口处理时间 YYYY-MM-DDTHH:mm:ss
     */
    private String extime;

    /**
     * 出口车道
     */
    private String exlaneid;

    /**
     * 出口站
     */
    private String exstation;

    /**
     * 通行路段
     */
    private String roadsection;

    /**
     * 产生费用
     */
    private String productionCost;

    /**
     * 证据文件
     */
    private String auditRoadResult;

    /**
     * 客户违规时间
     */
    private String customerViolationTime;
}
