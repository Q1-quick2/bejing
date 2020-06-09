package com.sutong.workorder.model;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;

/**
 * @ClassName: AuditStatusDTO
 * @Description: 状态字典出参
 * @author: lichengquan
 * @date: 2019年12月27日 20:18
 * @Version: 1.0
 */
@Data
@ApiModel
public class AuditStatusDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 节点（父节点-子节点,例：1001-1）
     */
    private String typeNumber;

    /**
     * 节点名称名称
     */
    private String typeName;
}
