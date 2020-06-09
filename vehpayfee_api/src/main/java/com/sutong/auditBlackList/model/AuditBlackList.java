package com.sutong.auditBlackList.model;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * @Description: 黑名单实体
 * @author： Mr.Kong
 * @date: 2019/12/20 16:28
 */
@Data
@ToString
public class AuditBlackList implements Serializable {
    /**
     * 主键
     */
    private String blacklistid;

    /**
     * 车牌号码
     */
    private String vehplateno;

    /**
     * 车牌颜色
     */
    private Short vehColor;

    /**
     * 黑名单原因（由两级构成，两级之间用“-”分割，多个原因以“|”分割，例如：1-2|3-6）
     */
    private String reason;

    /**
     * 黑名单状态(1 位数字
     */
    private Short status;

    /**
     * 黑名单类型
     */
    private Short type;

    /**
     * 版本号
     */
    private Integer version;

    /**
     * 黑名单生成时间（YYYY-MM-DDTHH:mm:ss）
     */
    private String creationTime;

    /**
     * 生效时间
     */
    private String effectiveTime;

    /**
     * 欠费金额(单位-分)
     */
    private Integer owefee;

    /**
     * 欠费行为次数（车辆欠费行为次数，已补费次数不计）
     */
    private Short evasionCount;

    /**
     * 车脸特征版本号（版 本 号（YYYYMMDD+2 位
     */
    private String vehicleFeatureVersion;

    /**
     * 车脸识别特征码
     */
    private String vehicleFeatureCode;

    /**
     * 数据删除标识(0:否，1:是)
     */
    private Short rmFlag;

    /**
     * 数据创建时间
     */
    private String createTime;

    /**
     * 数据更新时间
     */
    private String updateTime;
}
