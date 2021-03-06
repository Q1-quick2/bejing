
package com.sutong.runfee.model;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
/***
 * @Description: 客户逃费实体
 * @author： Mr.Kong
 * @date: 2019/12/13 15:13
 */
@Data
@ToString
public class RunFee implements Serializable {

    /*** 工单编码*/
    private String orderId;
    /*** 车牌号*/
    private String vehicleId;
    /*** 车牌颜色*/
    private String vehicleColour;
    /*** 时间*/
    private String timeNodes;
    /*** 信息*/
    private String info;
    /*** 接收时间*/
    private String receiveTime;
    /*** 车型*/
    private String vehicleType;
    /*** 车种*/
    private String vehicleClass;
    /*** 确认逃费行为*/
    private String confirmedescapeType;
    /*** 欠费总计*/
    private Integer owefee;
    /*** 通行标识 ID*/
    private String passid;
    /*** 出口交易编号*/
    private String transactionId;
    /*** 通行介质类型*/
    private String mediaType;
    /*** OBU 序号编码*/
    private String obuId;
    /*** CPC 卡 或 ETC 卡的编号*/
    private String cardId;
    /*** 入口处理时间*/
    private String entime;
    /*** 入口车道*/
    private String enlaneId;
    /*** 出口处理时间*/
    private String extime;
    /*** 出口车道*/
    private String exlaneId;
    /*** 实际入口时间*/
    private String realEntime;
    /*** 实际入口车道*/
    private String realEnlaneid;
    /*** 实际出口时间*/
    private String realExtime;
    /*** 实际出口车道*/
    private String realExlaneid;
    /*** 实际车种*/
    private String realVehicleClass;
    /*** 实际车型*/
    private String realVehicleType;
    /*** 欠费金额*/
    private Integer owefeee;
    /*** 补费状态 1 未补费 ，2 已补费*/
    private Integer paybackStatus;
    /*** 是否有未完成工单*/
    private String isStartDissent;
    /*** 稽核结论ID*/
    private String auditId;
    /*** 稽核人*/
    private String processer;
    /*** 稽核时间*/
    private String processTime;
    /*** 稽核路段*/
    private String audRoad;
    /*** 门架交易编号*/
    private String transcationId;
    /*** 收费单元编号*/
    private String tollintervalId;
    /*** 应收费用*/
    private Integer actualFee;
    /*** 实收费用*/
    private Integer expectFee;
    /**OBU单/双片标识*/
    private String obuPieceIdentification;
    /**PSAM卡脱机交易序列号*/
    private String psamCardNumber;
    /**TAC码*/
    private String tacCode;
    /**交易类型标识*/
    private String transactionType;
    /**终端机编号*/
    private String terminalNo;
    /**入口重量*/
    private String entryWeigth;
    /**出口重量*/
    private String exportWeigth;
    /**轴数*/
    private String axlesNumber;
    /**单片机OBU/CPC卡电量百分比*/
    private String powerPercent;
    /**标记状态*/
    private String markStatus;
    /**对交易的文字解释*/
    private String explanation;
    /**联系人姓名*/
    private String customerName;
    /**联系电话*/
    private String phoneNumber;
    /*发送通知状态*/
    private String sendSmsStatus; //1：成功 ，2：不成功
    /*发行服务机构*/
    private String distributionOrganization;
    /*ETC卡类别*/
    private String etcType;
    /*行径路径*/
    private String behaviorPath;
    /*入口编号*/
    private String entryNumber;


}
