package com.sutong.auditDissentEvidence.model;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * @Description: 异议申请流水证据实体
 * @author： Mr.Kong
 * @date: 2019/12/19 14:57
 */
@Data
@ToString
public class AuditDissentEvidence implements Serializable {
    /**
     * 异议编码
     */
    private String dissentId;

    /**
     * 证据文件名
     */
    private String fileName;

    /**
     * 证据文件类型，用1位数字表示 1：视频 2：音频 3：图像 4：文本文件 5：其他
     */
    private Short fileType;

    /**
     * 文件获取/保存路径或者URL
     */
    private String locationUrl;

    /**
     * 证据描述
     */
    private String evidenceDesc;
}
