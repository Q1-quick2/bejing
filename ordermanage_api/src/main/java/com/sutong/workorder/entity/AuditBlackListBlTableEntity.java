package com.sutong.workorder.entity;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * @ClassName: AuditBlackListBlTableEntity
 * @Description: 黑名单原因实体
 * @author: lichengquan
 * @date: 2019年12月18日 19:02
 * @Version: 1.0
 */
@Data
@ToString
@ApiModel
public class AuditBlackListBlTableEntity implements Serializable {

    /**
     * 主键
     */
    private String blId;

    /**
     * 外键，指向AUDIT_BLACKLIST_TABLE（黑名单）ID
     */
    private String blackListId;

    /**
     * 黑名单级别
     */
    private Integer blacklistLevel;

    /**
     * 黑名单原因
     */
    private String blacklistReason;

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
