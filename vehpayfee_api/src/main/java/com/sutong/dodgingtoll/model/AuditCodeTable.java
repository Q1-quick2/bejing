package com.sutong.dodgingtoll.model;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * null
 * 
 * @author wcyong
 * 
 * @date 2020-01-07
 */

@Data
@ToString
public class AuditCodeTable implements Serializable{
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

    /**
     * 父编码
     */
    private String parentCode;

    /**
     * 编码类型
     */
    private String codeType;


}