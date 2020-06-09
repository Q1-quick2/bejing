package com.sutong.bjstjh.entity;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

@ApiModel
@ToString
@Data
public class AuditIssueVeh implements Serializable {
    //    主键ID
    private String issueVehId;
    //    发行OBU编号
    private String obuNo;
    //    发行ETC号
    private String etcNo;
    //    发行车型
    private Integer vehType;
    //    发行车种
    private Integer vehClassTypeCode;
    //    车辆号码
    private String vehPlateNo;
    //    车辆颜色编码
    private Integer vehColorCode;
    //    发行车辆图片地址
    private  String vehImgAddress;
    //    发型车辆行驶证图片地址
    private String drivImgAddress;
    //    外键ID
    private String ministryWorkOrderForeignId;

    public AuditIssueVeh() {
    }

    public AuditIssueVeh(String issueVehId, String obuNo, String etcNo, Integer vehType, Integer vehClassTypeCode, String vehPlateNo, Integer vehColorCode, String vehImgAddress, String drivImgAddress, String ministryWorkOrderForeignId) {
        this.issueVehId = issueVehId;
        this.obuNo = obuNo;
        this.etcNo = etcNo;
        this.vehType = vehType;
        this.vehClassTypeCode = vehClassTypeCode;
        this.vehPlateNo = vehPlateNo;
        this.vehColorCode = vehColorCode;
        this.vehImgAddress = vehImgAddress;
        this.drivImgAddress = drivImgAddress;
        this.ministryWorkOrderForeignId = ministryWorkOrderForeignId;
    }
}
