package com.sutong.bjstjh.util.enumerate;

/**
 * @ClassName: ConfigEnum
 * @Description:
 * @author: pengwz
 * @date: 2019年12月18日 上午09:15:23
 */
public enum FileConfigEnum {
	
	
	
    //	TEST(1,"TEST","C:\\test","测试"),
    ZIP_ROOT							(1, "ZIP_ROOT", "C:\\test\\zipfiles", "打包文件存放的根目录"),
    AUDIT_WORKORDER_HISTORY_TABLE		(2, "AUDIT_WORKORDER_HISTORY_TABLE", 
    										"C:\\test\\audit_workorder_history_table", "创建历史工单"),
    AUDIT_CHECK_RESULTS					(3, "AUDIT_CHECK_RESULTS", "C:\\test", "稽核结论证据表"),
    AUDIT_ISSUE_VEH_TABLE				(4, "AUDIT_ISSUE_VEH_TABLE", "C:\\test", "发行车辆信息表"),
    AUDIT_LICENSE_PLATE_DIFERENT		(5, "AUDIT_LICENSE_PLATE_DIFERENT", 
    										"C:\\test\\audit_workorder_history_table", "创建车标不符工单");

    private FileConfigEnum(Integer type, String tableName, String path, String remark) {
        this.type = type;
        this.tableName = tableName;
        this.path = path;
        this.remark = remark;
    }

    /**
     * 序列/类型，用于表示唯一
     */
    private Integer type;
    /**
     * 对应的物理表,须和枚举保持一致，方便调用
     */
    private String tableName;
    /**
     * 文件路径,不包含具体文件名
     */
    private String path;
    /**
     * 注释
     */
    private String remark;

    /**
     * @Description: 根据类型获取文件路径;如果匹配不到数据,返回null;
     * @author: pengwz
     * @date: 2019年12月18日 上午1:03:00
     */
    public static String getFilePath(Integer type) {
        for (FileConfigEnum fileConfigEnum : FileConfigEnum.values()) {
            if (fileConfigEnum.getType() == type)
                return fileConfigEnum.getPath();
        }
        return null;
    }

    /**
     * 根据表名获取文件路径
     *
     * @param tableName
     * @return
     * @Description
     * @author pengwz
     * @date 2019年12月23日 上午9:53:00
     */
    public static String getFilePath(String tableName) {
        for (FileConfigEnum fileConfigEnum : FileConfigEnum.values()) {
            if (tableName.trim().equalsIgnoreCase(fileConfigEnum.getTableName().trim()))
                return fileConfigEnum.getPath();
        }
        return null;
    }

    /**
     * 根据表名获取文件类型
     *
     * @param tableName
     * @return
     * @Description
     * @author pengwz
     * @date 2019年12月23日 上午9:54:44
     */
    public static Integer getFileType(String tableName) {
        for (FileConfigEnum fileConfigEnum : FileConfigEnum.values()) {
            if (tableName.trim().equalsIgnoreCase(fileConfigEnum.getTableName().trim()))
                return fileConfigEnum.getType();
        }
        return null;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

}
