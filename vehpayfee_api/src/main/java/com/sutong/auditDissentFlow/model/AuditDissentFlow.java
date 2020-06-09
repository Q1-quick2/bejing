package com.sutong.auditDissentFlow.model;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * @Description: 异议申请流水实体
 * @author： Mr.Kong
 * @date: 2019/12/19 14:56
 */
@Data
@ToString
public class AuditDissentFlow implements Serializable {
    /**
     * 异议流水ID
     */
    private String dissentId;

    /**
     * 工单编码
     */
    private String orderId;

    /**
     * 处理方省中心Id，表示交易文件是由哪个处理方省中心生成的
     */
    private String tollProvinceId;

    /**
     * 处理方省中心生成的文件 Id
     */
    private String messageId;

    /**
     * 发起方类型，1：部中心 2：省中心 3：发行方 4：路段
     */
    private String initiatorType;

    /**
     * 异议发起者
     */
    private String operator;

    /**
     * 异议发起时间，格式为：YYYY-MM-DDTHH:mm:ss
     */
    private Date operateTimes;

    /**
     * 工单发起单位。当发起方类型=发行方时，填写相应 的发行方代码；当发起方类型=路段 时，可不填。"
     */
    private String operateOrg;

    /**
     * 发起路段。当发起方类型=路段时，填写相应的 路段编码
     */
    private String operateRoad;

    /**
     * 客户名称
     */
    private String clientName;

    /**
     * 客户联系电话
     */
    private String clientPhone;

    /**
     * 车牌号码
     */
    private String vehicleId;

    /**
     * 车牌颜色
     */
    private String carColour;

    /**
     * 车型
     */
    private String vehicleType;

    /**
     * 车种
     */
    private String vehicleClass;

    /**
     * 证据文件链
     */
    private String dissentEvidence;

    /**
     * 有异议的通行 id
     */
    private String passids;
}
