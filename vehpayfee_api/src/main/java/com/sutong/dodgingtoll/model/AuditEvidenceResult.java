package com.sutong.dodgingtoll.model;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * null
 * 
 * @author wcyong
 * 
 * @date 2019-12-28
 */
@Data
@ToString
public class AuditEvidenceResult  implements Serializable {
    /**
     * 工单编码
     */
    private String orderId;

    /**
     * 稽核类型，1 发行 2 路段
     */
    private String audType;

    /**
     * 证据名称
     */
    private String audFileName;

    /**
     * 证据类型,1:视频 2:音频 3:图像 4:文本文件 5:其他
     */
    private String audFileType;

    /**
     * 证据描述
     */
    private String audFilePath;

    /**
     * 证据地址
     */
    private String audFileDesc;


}