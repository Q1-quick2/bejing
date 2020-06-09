
package com.sutong.auditMainResult.model;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * @Description: 稽核结论信息实体
 * @author： Mr.Kong
 * @date: 2019/12/15 15:00
 */
@Data
@ToString
public class AuditMainResult implements Serializable {

    /**
     * 稽核结论ID
     */
    private String auditResultId;

    /**
     * 处理方省中心 Id， 表示交易文件是由 哪个处理方省中心 生成的
     */
    private String tollProvinceId;

    /**
     * 工单编码
     */
    private String orderId;

    /**
     * 稽核人
     */
    private String processer;

    /**
     * 稽核时间,YYYY-MM-DDTHH:mm:ss
     */
    private String processTime;

    /**
     * 稽核方类型，1：发行方 2：收费公路运营单位
     */
    private String processType;

    /**
     * 发行服务机构编号，当稽核方类型为1发行方时需填写
     */
    private String issuerId;

    /**
     * 稽核路段，当稽核方类型为2收费公路运营单位时需填写
     */
    private String audRoadId;

    /**
     * 结论，1：是，2：否，当稽核方类型=1时，结论为：车辆信息与发行信息是否一致；当稽核方类型=2时，结论为：是否欠费
     */
    private Short result;

    /**
     * 卡类型，1：obu，2：ETC卡，当稽核方类型=1时，必填；用于区分是对obu的结论还是ETC卡 的结论；
     */
    private Short cardType;

    /**
     * 卡编号，不超过 20 位，当cardType=1时填写obu序号编码；当cardType=2时填写ETC卡编号
     */
    private String cardId;

    /**
     * 责任主体，1：发行方责任；2：路段单位责任；3：客户责任，当稽核方类型=1时，责任主体不得选 2；当稽核方类型=2时，责任主体不得选1
     */
    private Short responsor;

    /**
     * 证据是否充足1：证据充足 2：证据不充足
     */
    private Short evidenceEnough;

    /**
     * 确认逃费行为.由两级构成，两级之间用“-”分割， 多个原因以“|”分割，具体编码参 考：表十五，例如：1-2|3-6 当是否欠费=1欠费&责任主体=3客 户责任时必填
     */
    private String comfirmedEscapeType;

    /**
     * 稽核备注
     */
    private String audResultMemo;

    /**
     * 1：欠费 2：不欠费
     */
    private Short audIsLessFee;

    /**
     * 多个收费单元以“|”分割 当是否欠费=1时必填
     */
    private String tollIntervals;

    /**
     * 证据链，稽核结果ID+1位序列号,当有证据文件时，必填。当是否欠费=1欠费&责任主体=3客 户责任&证据是否充足=1充足时必填
     */
    private String audEvidenceId;

    /**
     * 欠费明细记录ID，工单ID号+1位工单子表类型+1位明细表序列号，工单字表类型：1：稽核结果；2：欠费明细。当有欠费明细，需要将欠费明细插入欠费明细表，并且在此字段生成ID。
     */
    private String oweFeeDetailId;
}
