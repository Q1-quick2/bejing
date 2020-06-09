package com.sutong.dodgingtoll.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * @Description:
 * @ClassName: QueryDodgingListVo
 * @author： lzq
 * @date: 2019/12/13 16:24
 * @Version： 1.0
 */
@ApiModel(value="查询条件对象",description="查询条件")
@Data
@ToString
public class QueryDodgingListVo implements Serializable{
    @ApiModelProperty(value="起止日期",name="beginDate",example="2019-09-09")
    private Date beginDate;//起止日期 格式：yyyyMMdd
    @ApiModelProperty(value="截止日期",name="endDate",example="2019-09-09")
    private Date endDate;//截止日期 格式：yyyyMMdd
    @ApiModelProperty(value="姓名",name="userName")
    private String userName;//姓名
    @ApiModelProperty(value="身份证号",name="userCard")
    private String userCard;//身份证号
    @ApiModelProperty(value="车牌号",name="vehicleId")
    private String vehicleId;//车牌号
    @ApiModelProperty(value="车牌颜色",name="numColor",example="1")
    private String numColor;//车牌颜色 （字典码，1，2，3，代表颜色）

    //private Boolean timeNodes;//时间，查询2020.01.01年之前的还是之后的 0代表之前，1代表之后
    //private String transVehicleId;
    @ApiModelProperty(value="obu号码",name="obuId",example="1")
    private String obuId;
    @ApiModelProperty(value="证件类型",name="certificateType")
    private String certificateType;

}
