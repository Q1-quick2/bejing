package com.sutong.bjstjh.entity;

import java.util.Date;

/**   
 * @ClassName:  auditFileTable   
 * @Description:  文件表实体类
 * @author: pengwz
 * @date:   2019年12月26日 下午2:04:35      
 */
public class AuditFileTable implements java.io.Serializable{

	private static final long serialVersionUID = 7384631886672285728L;
	
	private String 	fileid;	//主键
	private String 	tableName;	//所属表名
	private String 	tableid;	//所属表名id
	private String 	filetype;	//文件类型
	private String 	filepath;	//文件路径
	private Date    createTime;	//创建时间
	private String 	tableColumn;	//所属表字段
	public String getFileid() {
		return fileid;
	}
	public void setFileid(String fileid) {
		this.fileid = fileid;
	}
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public String getTableid() {
		return tableid;
	}
	public void setTableid(String tableid) {
		this.tableid = tableid;
	}
	public String getFiletype() {
		return filetype;
	}
	public void setFiletype(String filetype) {
		this.filetype = filetype;
	}
	public String getFilepath() {
		return filepath;
	}
	public void setFilepath(String filepath) {
		this.filepath = filepath;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getTableColumn() {
		return tableColumn;
	}
	public void setTableColumn(String tableColumn) {
		this.tableColumn = tableColumn;
	}
	
	
}
