package com.sutong.workorder.entity;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * @ClassName: AuditGrayListGlTableEntity
 * @Description: TODO
 * @author: lichengquan
 * @date: 2019年12月18日 19:27
 * @Version: 1.0
 */
@Data
@ToString
@ApiModel
public class AuditGrayListGlTableEntity implements Serializable {

    /**
     * 主键
     */
    private String GlId;

    /**
     * 外键，指向AUDIT_GRAYLIST_TABLE（灰名单）ID
     */
    private String graylistid;

    /**
     * 灰名单级别
     */
    private Integer graylistLevel;

    /**
     * 灰名单原因
     */
    private String graylistReason;
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
