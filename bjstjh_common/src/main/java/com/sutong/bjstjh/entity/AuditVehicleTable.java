package com.sutong.bjstjh.entity;

/**   
 * @ClassName:  AUDIT_VEHICLE_TABLE   
 * @Description:  车辆信息表
 * @author: pengwz
 * @date:   2020年1月17日 下午4:26:34      
 */
public class AuditVehicleTable implements java.io.Serializable{
	
	private static final long serialVersionUID = -4273614837094352100L;
	/** ID */
	private String 	vehplatenoid;
	/** 所属工单主表ID */
	private String 	workorderid;
	/** 车型 */
	private Integer vehType;
	/** 车牌 */
	private String 	vehplateno;
	/** 车牌颜色 */
	private Integer vehColor;
	/** 车种 */
	private Integer vehClass;
	/** 车身颜色 */
	private Integer carColor;
	
	public String getVehplatenoid() {
		return vehplatenoid;
	}
	public void setVehplatenoid(String vehplatenoid) {
		this.vehplatenoid = vehplatenoid;
	}
	public String getWorkorderid() {
		return workorderid;
	}
	public void setWorkorderid(String workorderid) {
		this.workorderid = workorderid;
	}
	public Integer getVehType() {
		return vehType;
	}
	public void setVehType(Integer vehType) {
		this.vehType = vehType;
	}
	public String getVehplateno() {
		return vehplateno;
	}
	public void setVehplateno(String vehplateno) {
		this.vehplateno = vehplateno;
	}
	public Integer getVehColor() {
		return vehColor;
	}
	public void setVehColor(Integer vehColor) {
		this.vehColor = vehColor;
	}
	public Integer getVehClass() {
		return vehClass;
	}
	public void setVehClass(Integer vehClass) {
		this.vehClass = vehClass;
	}
	public Integer getCarColor() {
		return carColor;
	}
	public void setCarColor(Integer carColor) {
		this.carColor = carColor;
	}
	
}
