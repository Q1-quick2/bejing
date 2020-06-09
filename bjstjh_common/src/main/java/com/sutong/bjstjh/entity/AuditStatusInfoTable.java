package com.sutong.bjstjh.entity;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * @ClassName: AuditStatusInfoTable
 * @Description: 通用状态表
 * @author: lichengquan
 * @date: 2019年12月27日 17:15
 * @Version: 1.0
 */
@Data
@ToString
public class AuditStatusInfoTable implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * 主键
     */
    private String keyId;

    /**
     * 字典类型
     */
    private String statusType;

    /**
     * 类型序号
     */
    private Integer typeSerialNumber;

    /**
     * 类型名称
     */
    private String typeName;

    /**
     * 父节点
     */
    private Integer typeFatherNumber;
}
