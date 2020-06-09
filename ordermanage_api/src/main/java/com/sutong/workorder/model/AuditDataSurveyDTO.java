package com.sutong.workorder.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @ClassName: AuditDataSurveyDTO
 * @Description: 外部稽核-数据概况查询-出参
 * @author: lichengquan
 * @date: 2020年01月13日 15:19
 * @Version: 1.0
 */
@Data
@ApiModel
public class AuditDataSurveyDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 数据编号
     */
    @ApiModelProperty(value = "数据编号")
    private String dataNumber;

    /**
     * 车牌号码
     */
    @ApiModelProperty(value = "车牌号码")
    private String vehicleNo;

    /**
     * 车牌颜色
     */
    @ApiModelProperty(value = "车牌颜色")
    private Integer vehColor;

    /**
     * 车型
     */
    @ApiModelProperty(value = "车型")
    private Integer vehType;

    /**
     * 车种
     */
    @ApiModelProperty(value = "车种")
    private Integer vehClass;

    /**
     * 疑似逃费行为
     */
    @ApiModelProperty(value = "疑似逃费行为")
    private String escape;

    /**
     * 通行介质类型
     */
    @ApiModelProperty(value = "通行介质类型")
    private Integer passMediaType;

    /**
     * 发行服务机构
     */
    @ApiModelProperty(value = "发行服务机构")
    private String issuerNo;

    /**
     * OBU编码
     */
    @ApiModelProperty(value = "OBU序列编号")
    private String obuNo;

    /**
     * ETC卡类别
     */
    @ApiModelProperty(value = "ETC卡类别")
    private Integer etcType;

    /**
     * ETC/CPC编码
     */
    @ApiModelProperty(value = "ETC/CPC卡编号")
    private String etcNoCpcNo;

    /**
     * 入口时间
     */
    @ApiModelProperty(value = "入口时间")
    private String entryTime;

    /**
     * 入口地点
     */
    @ApiModelProperty(value = "入口站")
    private String entryPlace;

    /**
     * 出口时间
     */
    @ApiModelProperty(value = "出口时间")
    private String exitTime;

    /**
     * 出口地点
     */
    @ApiModelProperty(value = "出口站")
    private String exitPlace;

    /**
     * 行径省份
     */
    @ApiModelProperty(value = "行径路径")
    private String passProvince;
}
