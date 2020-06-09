package com.sutong.workorder.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * @ClassName: AuditExitEntrTableEntity
 * @Description: 省级稽核---出入口数据表Entity
 * @author: lichengquan
 * @date: 2020年01月13日 16:54
 * @Version: 1.0
 */
@Data
@ToString
@ApiModel
public class AuditExitEntrTableEntity implements Serializable {

    /**
     * 主键
     */
    @ApiModelProperty(value = "主键")
    private String exitEntrId;

    /**
     * 所属主表ID
     */
    @ApiModelProperty(value = "所属主表id")
    private String workOrderId;

    /**
     * 入口/出口类别
     * 0:出口 1:入口
     */
    @ApiModelProperty(value = "入口/出口")
    private Integer entrance;

    /**
     * 出入口编号
     */
    @ApiModelProperty(value = "出入口编号")
    private String entranceCode;

    /**
     * 通行介质类型
     */
    @ApiModelProperty(value = "通行介质类型")
    private Integer passMediaType;

    /**
     * OBU单/双片标识
     */
    @ApiModelProperty(value = "OBU单/双片标识")
    private Integer obuSign;

    /**
     * PSAM卡脱机交易序列号
     */
    @ApiModelProperty(value = "PSAM卡脱机交易序列号 ")
    private String terminalTransNo;

    /**
     * OBU序列编号
     */
    @ApiModelProperty(value = "OBU序列编号")
    private String obuNo;

    /**
     * CPC卡或ETC卡的编号
     */
    @ApiModelProperty(value = "CPC卡或ETC卡的编号")
    private String etcNoCpcNo;

    /**
     * 入口处理时间
     */
    @ApiModelProperty(value = "入口处理时间")
    private String entryTime;

    /**
     * 实际车牌号
     */
    @ApiModelProperty(value = "实际车牌号")
    private String realVehplateNo;

    /**
     * 实际车牌颜色
     */
    @ApiModelProperty(value = "实际车牌颜色")
    private Integer realVehColorCode;

    /**
     * 入口识别车牌号
     */
    @ApiModelProperty(value = "入口识别车牌号")
    private String enRealVehplateNo;

    /**
     * 入口识别车牌颜色
     */
    @ApiModelProperty(value = "入口识别车牌颜色")
    private Integer enRealVehColorCode;

    /**
     * 收费车型
     */
    @ApiModelProperty(value = "收费车型")
    private Integer collectFeeVehType;

    /**
     * 车种
     */
    @ApiModelProperty(value = "车种")
    private Integer vehClassTypeCode;

    /**
     * TAC码
     */
    @ApiModelProperty(value = "TAC码")
    private String tacNo;

    /**
     * 交易类型标识
     */
    @ApiModelProperty(value = "交易类型标识")
    private String transType;

    /**
     * 终端机编号
     */
    @ApiModelProperty(value = "终端机编号")
    private String terminalNo;

    /**
     * 入口重量
     */
    @ApiModelProperty(value = "入口重量")
    private String enWeight;

    /**
     * 入口轴数
     */
    @ApiModelProperty(value = "入口轴数")
    private Integer enAxleCount;

    /**
     * 单片机OBU/CPC卡电量百分比
     */
    @ApiModelProperty(value = "单片机OBU/CPC卡电量百分比")
    private String percentage;

    /**
     * 标记状态
     */
    @ApiModelProperty(value = "标记状态")
    private Integer labelStatus;

    /**
     * 对交易的文字解释
     */
    @ApiModelProperty(value = "对交易的文字解释")
    private String description;
}
