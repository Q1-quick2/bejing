package com.sutong.auditCodeTable.model;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * @Description: 字典数据实体
 * @author： Mr.Kong
 * @date: 2019/12/23 19:56
 */
@Data
@ToString
public class AuditCodeTable implements Serializable {
    /**
     * 主键ID
     */
    private String codeId;

    /**
     * 编码
     */
    private String code;

    /**
     * 名称
     */
    private String name;

    /**
     * 总称
     */
    private String generalName;

    /**
     * 描述
     */
    private String des;
}
