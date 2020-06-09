package com.sutong.systemManagement.model.entity;


import com.sutong.bjstjh.annotation.Tables;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * @ClassName: AuditSystemOrganizationManage
 * @Description: 机构管理表
 */


@Data
@ToString
@ApiModel
@Tables(vopri = "organizationId")
public class AuditSystemOrganizationManage implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键组织机构id
     */
    private String organizationId;

    /**
     * 机构名称
     */
    private String organizationName;

    /**
     * 机构考核业务类型
     */
    private String organizationType;

    /**
     * 机构级别
     */
    private String organizationGrade;

    /**
     * 备注
     */
    private String remarks;


}
