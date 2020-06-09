package com.sutong.workorder.entity;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * @ClassName: AuditGrayListTableEntity
 * @Description: 灰名单实体类
 * @author: lichengquan
 * @date: 2019年12月18日 19:13
 * @Version: 1.0
 */
@Data
@ToString
@ApiModel
public class AuditGrayListTableEntity implements Serializable {

    /**
     * 主键
     */
    private String grayListId;

    /**
     * 车牌号码
     */
    private String vehPlateNo;

    /**
     * 车牌颜色
     */
    private Integer vehColor;

    /**
     * 灰名单原因
     */
    private String reason;

    /**
     * 灰名单状态
     */
    private Integer status;

    /**
     * 生成时间(YYYY-MM-DDTHH:mm:ss)
     */
    private String creationTime;

    /**
     * 录入省份ID
     */
    private Integer enteredProvinceId;

    /**
     * 录入省份名称
     */
    private String enteredProvince;

    /**
     * 版本号
     */
    private Integer version;

    /**
     * 证据
     */
    private String proof;
    /**
     * 删除标识(0:否，1:是)
     */
    private Integer rmFlag;
    /**
     * 创建时间
     */
    private String createTime;
    /**
     * 更新时间
     */
    private String updateTime;
}

