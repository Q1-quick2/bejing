package com.sutong.bjstjh.entity;


import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
@Data
@ApiModel
@ToString
public class AuditCheckResults implements Serializable {
//    主键ID
    private String checkResultsId;
//    OBU对比结果
    private Integer obuNoResults;
//    车牌号和车牌颜色对比结果
    private Integer vehResults;
//    ETC对比结果
    private Integer etcNoResults;
//    车型对比结果
    private Integer vehTypeResults;
//    车种对比结果
    private Integer vehClassResults;
//    是否为我发行责任
    private Integer issuerDuty;
//    责任主体

    private Integer responsor;
//    证据是否充足
    private Integer evidenceEnough;
//    稽核结论
    private String checkResult;
//    证据图片地址
    private String imgAddress;
//    创建时间
    private String createTime;
//    外键ID
    private String ministryWorkOrderForeignId;

    public AuditCheckResults() {
    }

    public AuditCheckResults(String checkResultsId, Integer obuNoResults, Integer vehResults, Integer etcNoResults, Integer vehTypeResults, Integer vehClassResults, Integer issuerDuty, Integer responsor, Integer evidenceEnough, String checkResult, String imgAddress, String createTime, String ministryWorkOrderForeignId) {
        this.checkResultsId = checkResultsId;
        this.obuNoResults = obuNoResults;
        this.vehResults = vehResults;
        this.etcNoResults = etcNoResults;
        this.vehTypeResults = vehTypeResults;
        this.vehClassResults = vehClassResults;
        this.issuerDuty = issuerDuty;
        this.responsor = responsor;
        this.evidenceEnough = evidenceEnough;
        this.checkResult = checkResult;
        this.imgAddress = imgAddress;
        this.createTime = createTime;
        this.ministryWorkOrderForeignId = ministryWorkOrderForeignId;
    }
}
