package com.sutong.historyEvidenceOrder.model;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * @Description: 历史工单证据实体
 *
 *
 */
@Data
@ToString
public class AuditFile implements Serializable {

    /**
     * 主键
     */
    private String fileId;

    /**
     * 所属表名
     */
    private String tableName;

    /**
     * 所属表名id
     */
    private String tableId;

    /**
     * 文件类型
     */
    private String audFileType;

    /**
     * 文件路径
     */
    private String filePath;

    /**
     * 创建时间
     */
    private Data createTime;

    /**
     * 所属表字段
     */
    private String tableColumn;
}
