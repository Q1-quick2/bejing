package com.sutong.bjstjh.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * Created by main on 2019/12/18.
 */
@Data
@ToString
public class ConfirmationEntity implements Serializable{

    @ApiModelProperty(value = "主键(编号)")
    private String payId;
    @ApiModelProperty(value = "处理时间 (当前系统时间)")
    private String payDisposeTime;
    @ApiModelProperty(value = "处理地点")
    private String payDisposeSite;
    @ApiModelProperty(value = "车牌号码")
    private String payPlateNumber;
    @ApiModelProperty(value = "车型（轴）")
    private String payCarType;
    @ApiModelProperty(value = "车辆类别        □客 车  □货 车      □专项作业车")
    private String payCarCategory;
    @ApiModelProperty(value = "车牌颜色")
    private String payPlateNumberColor;
    @ApiModelProperty(value = "车辆所有人")
    private String payVehicleOwner;
    @ApiModelProperty(value = "驾驶员姓名")
    private String payDriverName;
    @ApiModelProperty(value = "电子标签（表面号） (obo)")
    private String payRfid;
    @ApiModelProperty(value = "发动机号")
    private String payEngineNumber;
    @ApiModelProperty(value = "驾驶员身份证号")
    private String payDriverNumber;
    @ApiModelProperty(value = "非现金卡号/通行卡卡号   (ETC)")
    private String payCardNumber;
    @ApiModelProperty(value = "涉及路段与站点")
    private String paySite;
    @ApiModelProperty(value = "  年  月  日至  年  月  日期间")
    private String payTime;
    @ApiModelProperty(value = "□  改变车型逃费 □ 改变缴费路经逃费 □ 利用优免政策逃费□其他情况")
    private String payInfo;
    @ApiModelProperty(value = "共计__次")
    private String payCount;
    @ApiModelProperty(value = "当事人（签名）：___")
    private String payName;
    @ApiModelProperty(value = "联系电话")
    private String payPhone;
    @ApiModelProperty(value = "  年 月 日 (用户填写)")
    private String payDate;
    @ApiModelProperty(value = "合计：（大写）")
    private String payTotal;
    @ApiModelProperty(value = "处理人：")
    private String payConductor;
    @ApiModelProperty(value = "处理单位：")
    private String payProcessingUnit;
    @ApiModelProperty(value = "通行费")
    private String payPike;
    @ApiModelProperty(value = "至 什么什么 (时间)")
    private String payEndTime;


    @ApiModelProperty(value = "办理时车牌号")
    private String doVehicleId;
    @ApiModelProperty(value = "办理时车型")
    private String doVehicleType;
    @ApiModelProperty(value = "base64  签名")
    private String auditPayConfirmation;


    // 业务字段
    @ApiModelProperty(value = "涉及路段与站点a")
    private String raid1;
    @ApiModelProperty(value = "涉及路段与站点b")
    private String raid2;




}
