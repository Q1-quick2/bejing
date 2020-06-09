package com.sutong.workorder.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @ClassName: AuditTransDTO
 * @Description: 门架数据查询-出参
 * @author: lichengquan
 * @date: 2020年01月14日 16:56
 * @Version: 1.0
 */
@Data
@ApiModel
public class AuditTransDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @ApiModelProperty(value = "主键")
    private String transId;

    /**
     * 计费交易编号
     */
    @ApiModelProperty(value = "计费交易编号")
    private String transCode;

    /**
     * 路段名称
     */
    @ApiModelProperty(value = "路段名称")
    private String roadName;

    /**
     * 收费单元编号
     */
    @ApiModelProperty(value = "收费单元编号")
    private String collectFeeModuelCode;

    /**
     * 收费单元名称
     */
    @ApiModelProperty(value = "收费单元名称")
    private String collectFeeModuelName;

    /**
     * 实际车牌号码
     */
    @ApiModelProperty(value = "实际车牌号码")
    private String realVehplateNo;

    /**
     * 实际车牌颜色
     */
    @ApiModelProperty(value = "实际车牌颜色")
    private Integer realVehColor;

    /**
     * 识别车牌
     */
    @ApiModelProperty(value = "识别车牌")
    private String identVehplateNo;

    /**
     * 识别颜色
     */
    @ApiModelProperty(value = "识别颜色")
    private Integer identVehColor;

    /**
     * 交易时间
     */
    @ApiModelProperty(value = "交易时间")
    private String transTime;

    /**
     * 计费车型
     */
    @ApiModelProperty(value = "计费车型")
    private Integer vehicleType;

    /**
     * 识别车型
     */
    @ApiModelProperty(value = "识别车型")
    private Integer identVehType;

    /**
     * 通行介质类型
     */
    @ApiModelProperty(value = "通行介质类型")
    private Integer passMediaType;

    /**
     * 通行介质编码
     */
    @ApiModelProperty(value = "通行介质编码")
    private String passMediaCode;

    /**
     * OBU序号编码
     */
    @ApiModelProperty(value = "OBU序号编码")
    private String obuNo;

    /**
     * CPC卡/ETC卡编号
     */
    @ApiModelProperty(value = "CPC卡/ETC卡编号")
    private String etcNoCpcNo;

    /**
     * 车辆识别流水号
     */
    @ApiModelProperty(value = "车辆识别流水号")
    private String vehDentificationCode;

    /**
     * 抓拍图像
     */
    @ApiModelProperty(value = "抓拍图像")
    private String imgAddress;

}
