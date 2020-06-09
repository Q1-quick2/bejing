package com.sutong.dodgingtoll.model;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * null
 * 
 * @author wcyong
 * 
 * @date 2020-01-05
 */

@Data
@ToString
public class AuditPayConfirmation implements Serializable{
    /**
     * 主键(编号)
     */
    private String payId;

    /**
     * 处理时间 (当前系统时间)
     */
    private String payDisposeTime;

    /**
     * 处理地点
     */
    private String payDisposeSite;

    /**
     * 车牌号码
     */
    private String payPlateNumber;

    /**
     * 车型（轴）
     */
    private String payCarType;

    /**
     * 车辆类别        □客 车	□货 车      □专项作业车
     */
    private String payCarCategory;

    /**
     * 车牌颜色
     */
    private String payPlateNumberColor;

    /**
     * 车辆所有人
     */
    private String payVehicleOwner;

    /**
     * 驾驶员姓名
     */
    private String payDriverName;

    /**
     * 电子标签（表面号） (obo)
     */
    private String payRfid;

    /**
     * 发动机号
     */
    private String payEngineNumber;

    /**
     * 驾驶员身份证号
     */
    private String payDriverNumber;

    /**
     * 非现金卡号/通行卡卡号   (ETC)
     */
    private String payCardNumber;

    /**
     * 涉及路段与站点
     */
    private String paySite;

    /**
     *   年  月  日至  年  月  日期间
     */
    private String payTime;

    /**
     * 共计__次
     */
    private String payCount;

    /**
     * □ 改变车型逃费 □ 改变缴费路经逃费 □ 利用优免政策逃费□其他情况
     */
    private String payInfo;

    /**
     * 当事人（签名）：___
     */
    private String payName;

    /**
     * 联系电话
     */
    private String payPhone;

    /**
     * 　年   月 	日 (用户填写)
     */
    private String payDate;

    /**
     * 合计：（大写）
     */
    private String payTotal;

    /**
     * 处理人：
     */
    private String payConductor;

    /**
     * 处理单位：
     */
    private String payProcessingUnit;

    private String payPaypike;

    /**
     * 至 什么什么 (时间)
     */
    private String payEndTime;

    /**
     * base64  签名
     */
    private String auditPayConfirmation;


}