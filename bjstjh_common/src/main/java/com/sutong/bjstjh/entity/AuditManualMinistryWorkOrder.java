package com.sutong.bjstjh.entity;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
@ApiModel
@Data
@ToString
public class AuditManualMinistryWorkOrder implements Serializable {

//    主键ID
    private String ministryWorkOrderId;
//    OBU编号
    private String obuNo;
//    ETC号
    private String etcNo;
//    定义车型
    private Integer vehType;
//    定义车种
    private Integer vehClassTypeCode;
//    车牌号码
    private String vehPlateNo;
//    车牌颜色编码
    private Integer vehColorCode;
//    通行标志
    private String passId;
//    工单编号
    private String workOrderId;
//    创建时间
    private String createTime;



    public AuditManualMinistryWorkOrder() {
    }

    public AuditManualMinistryWorkOrder(String ministryWorkOrderId, String obuNo, String etcNo, Integer vehType, Integer vehClassTypeCode, String vehPlateNo, Integer vehColorCode, String passId, String workOrderId, String createTime) {
        this.ministryWorkOrderId = ministryWorkOrderId;
        this.obuNo = obuNo;
        this.etcNo = etcNo;
        this.vehType = vehType;
        this.vehClassTypeCode = vehClassTypeCode;
        this.vehPlateNo = vehPlateNo;
        this.vehColorCode = vehColorCode;
        this.passId = passId;
        this.workOrderId = workOrderId;
        this.createTime = createTime;
    }
}
