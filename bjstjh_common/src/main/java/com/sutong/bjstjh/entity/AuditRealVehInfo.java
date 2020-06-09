package com.sutong.bjstjh.entity;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
@ApiModel
@Data
@ToString
public class AuditRealVehInfo implements Serializable {
    //    主键ID
    private String realVehInfoId;
    //    实际OBU编号
    private String obuNo;
    //    实际ETC号
    private String etcNo;
    //    实际车型
    private Integer vehType;
    //    实际车种
    private Integer vehClassTypeCode;
    //    车辆号码
    private String vehPlateNo;
    //    车辆颜色编码
    private Integer vehColorCode;
    //     外键ID
    private String ministryWorkOrderForeignId;

    public AuditRealVehInfo() {
    }

    public AuditRealVehInfo(String realVehInfoId, String obuNo, String etcNo, Integer vehType, Integer vehClassTypeCode, String vehPlateNo, Integer vehColorCode, String ministryWorkOrderForeignId) {
        this.realVehInfoId = realVehInfoId;
        this.obuNo = obuNo;
        this.etcNo = etcNo;
        this.vehType = vehType;
        this.vehClassTypeCode = vehClassTypeCode;
        this.vehPlateNo = vehPlateNo;
        this.vehColorCode = vehColorCode;
        this.ministryWorkOrderForeignId = ministryWorkOrderForeignId;
    }
}
