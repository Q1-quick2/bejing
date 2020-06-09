package com.sutong.workorder.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * @ClassName: AuditWorkOrderTableEntity
 * @Description: 省级稽核---外部稽核数据清单
 * @author: lichengquan
 * @date: 2020年01月08日 9:24
 * @Version: 1.0
 */
@Data
@ToString
@ApiModel
public class AuditWorkOrderTableEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @ApiModelProperty(value = "主键")
    private String workOrderId;

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
     * 车身颜色
     */
    @ApiModelProperty(value = "车身颜色")
    private Integer carColor;

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
     * 是否发起稽查
     */
    @ApiModelProperty(value = "是否发起稽查")
    private Integer whetherAudit;

    /**
     * 行径路段
     */
    @ApiModelProperty(value = "行径路段")
    private String passPath;

    /**
     * 疑似逃费行为
     */
    @ApiModelProperty(value = "疑似逃费行为")
    private String escape;

    /**
     * 逃费类型（一类）
     */
    @ApiModelProperty(value = "逃费类型（一类）")
    private Integer escapeTypeOne;

    /**
     * 逃费类型(二类)
     */
    @ApiModelProperty(value = "逃费类型(二类)")
    private Integer escapeTypeTwo;

    /**
     * 时间类型
     */
    @ApiModelProperty(value = "时间类型")
    private String timeType;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    private String createTime;

    /**
     * 车辆标识
     */
    @ApiModelProperty(value = "车辆标识")
    private String vehFlag;

    /**
     * 入口时间
     */
    @ApiModelProperty(value = "入口时间")
    private String entryTime;

    /**
     * 入口地点
     */
    @ApiModelProperty(value = "入口地点")
    private String entryPlace;

    /**
     * 出口时间
     */
    @ApiModelProperty(value = "出口时间")
    private String exitTime;

    /**
     * 出口地点
     */
    @ApiModelProperty(value = "出口地点")
    private String exitPlace;

    /**
     * 行径省份
     */
    @ApiModelProperty(value = "行径省份")
    private String passProvince;

    /**
     * 行径省份数
     */
    @ApiModelProperty(value = "行径省份数")
    private Integer passProvinceTotal;

    /**
     * 行径路段数
     */
    @ApiModelProperty(value = "行径路段数")
    private Integer passRoadTotal;

    /**
     * 通行介质类型
     */
    @ApiModelProperty(value = "通行介质类型")
    private Integer passMediaType;

    /**
     * OBU编码
     */
    @ApiModelProperty(value = "OBU编码")
    private String obuNo;

    /**
     * ETC/CPC编码
     */
    @ApiModelProperty(value = "ETC/CPC编码")
    private String etcNoCpcNo;

    /**
     * 特情类型
     */
    @ApiModelProperty(value = "特情类型")
    private Integer specialType;

    /**
     * 交易金额（单位：分）
     */
    @ApiModelProperty(value = "交易金额（单位：分）")
    private Integer ransTotal;

    /**
     * 发行服务机构
     */
    @ApiModelProperty(value = "发行服务机构")
    private String issuerNo;

    /**
     * ETC卡类别
     */
    @ApiModelProperty(value = "ETC卡类别")
    private Integer etcType;

    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    private String remark;

    /**
     * 工单标题
     */
    @ApiModelProperty(value = "工单标题")
    private String workOrderTitle;

    /**
     * 发起单位
     */
    @ApiModelProperty(value = "发起单位")
    private String workOrderUnit;

    /**
     * 发起时间
     */
    @ApiModelProperty(value = "发起时间")
    private String workOrderTime;

    /**
     * 协查单位进度
     */
    @ApiModelProperty(value = "协查单位进度")
    private String investigateProgress;

    /**
     * 数据稽核进度
     */
    @ApiModelProperty(value = " 数据稽核进度")
    private String dataProgress;

    /**
     * 工单状态(字典值)
     */
    @ApiModelProperty(value = "工单状态")
    private Integer workOrderStatus;

    /**
     * 发起人
     */
    @ApiModelProperty(value = "发起人")
    private String initiator;

    /**
     * 更新时间
     */
    @ApiModelProperty(value = "更新时间")
    private String updateTime;

    /**
     * 工单发起次数
     */
    @ApiModelProperty(value = "工单发起次数")
    private Integer workOrderCount;


    /**
     * 删除标识
     * 0:未删除 1:已删除
     */
    private Integer rmFlag;
}
