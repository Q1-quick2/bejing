package com.sutong.bjstjh.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**   
 * @ClassName:  AuditWorkorderTable   
 * @Description:  稽核数据清单（工单）
 * @author: pengwz
 * @date:   2019年12月19日 上午10:36:26      
 */
@ApiModel
public class AuditWorkorderTable implements java.io.Serializable{
	
	private static final long serialVersionUID = -2409139908921812424L;
	
	/** 主键，UUID 24位 */
	@ApiModelProperty(value = "主键，UUID 24位")
	private String	workorderid;
	/** 数据(工单)编号 */
	private String workorderNumber;
	/** 车牌号码 */
	@ApiModelProperty(value ="车牌号码")
	private String	vehicleno;
	/**  行径路径*/
	@ApiModelProperty(value ="行径路径")
	private String	passpath;
	/** 疑似逃费行为 */
	@ApiModelProperty(value ="疑似逃费行为")
	private String	escape;
	/** 逃费类型 1  */
	@ApiModelProperty(value ="逃费类型 1")
	private Integer	escapeTypeOne;
	/** 逃费类型 2  */
	@ApiModelProperty(value ="逃费类型 2")
	private String	escapeTypeTwo;
	/** 时间类型 */
	@ApiModelProperty(value ="时间类型")
	private String	timeType;
	/** 创建时间 */
	@ApiModelProperty(value ="创建时间")
	private String	createTime;
	/** 车辆标识 */
	@ApiModelProperty(value ="车辆标识")
	private String	vehFlag;
	/** 入口时间 */
	@ApiModelProperty(value ="入口时间")
	private String	entryTime;
	/** 入口地点*/
	@ApiModelProperty(value =" 入口地点")
	private String	entryPlace;
	/** 出口时间  */
	@ApiModelProperty(value ="出口时间")
	private String	exitTime;
	/** 出口地点 */
	@ApiModelProperty(value ="出口地点")
	private String	exitPlace;
	/** 行径省份 */
	@ApiModelProperty(value ="行径省份")
	private String	passProvince;
	/**  OBU编码*/
	@ApiModelProperty(value ="OBU编码")
	private String	obuno;
	/** ETC/CPC编码 */
	@ApiModelProperty(value ="ETC/CPC编码")
	private String	etcnoCpcno;
	/** 发行服务机构 */
	@ApiModelProperty(value ="发行服务机构")
	private String	issuerno;
	/** 备注 */
	@ApiModelProperty(value ="备注")
	private String	remark;
	/** 工单标题 */
	@ApiModelProperty(value ="工单标题")
	private String	workorderTitle;
	/** 发起时间 */
	@ApiModelProperty(value ="发起时间")
	private String	workorderTime;
	/** 协查单位进度 */
	@ApiModelProperty(value ="协查单位进度")
	private String	investigateProgress;
	/** 数据稽核进度 */
	@ApiModelProperty(value ="数据稽核进度")
	private String	dataProgress;
	/** 发起人 */
	@ApiModelProperty(value ="发起人")
	private String	initiator;
	/** 更新时间 */
	@ApiModelProperty(value ="更新时间 ")
	private String	updatetime;
	/** 车牌颜色 */
	@ApiModelProperty(value ="车牌颜色")
	private Integer	vehColor;
	/**  车身颜色*/
	@ApiModelProperty(value ="车身颜色")
	private Integer	carColor;
	/**  车型*/
	@ApiModelProperty(value =" 车型")
	private Integer	vehType;
	/** 车种 */
	@ApiModelProperty(value ="车种")
	private Integer	vehClass;
	/** 是否发起稽查 */
	@ApiModelProperty(value ="是否发起稽查")
	private Integer	whetherAudit;
	/** 行径省份数 */
	@ApiModelProperty(value ="行径省份数")
	private Integer	passProvinceTotal;
	/** 行径路段数 */
	@ApiModelProperty(value ="行径路段数")
	private Integer	passRoadTotal;
	/** 通行介质类型 */
	@ApiModelProperty(value ="通行介质类型")
	private Integer	passMediaType;
	/** 特情类型 */
	@ApiModelProperty(value ="特情类型")
	private Integer	specialType;
	/** ETC卡类别 */
	@ApiModelProperty(value ="ETC卡类别")
	private Integer	etctype;
	/** 发起单位 */
	@ApiModelProperty(value ="发起单位")
	private Integer	workorderUnit;
	/** 工单状态 */
	@ApiModelProperty(value ="工单状态")
	private Integer	workorderStatus;
	/** 工单发起次数 */
	@ApiModelProperty(value ="工单发起次数")
	private Integer	workoderCount;
	/** 交易金额 */
	@ApiModelProperty(value ="交易金额")
	private Double	ransTotal;
	
//    /** 当前页码 */
//    @ApiModelProperty(value = "当前页码")
//    private Integer pageIndex;
//    /** 分页条数 */
//    @ApiModelProperty(value = "分页条数")
//    private Integer pageSize;

    
    
	public String getWorkorderNumber() {
		return workorderNumber;
	}
	public void setWorkorderNumber(String workorderNumber) {
		this.workorderNumber = workorderNumber;
	}
	public String getWorkorderid() {
		return workorderid;
	}
	public void setWorkorderid(String workorderid) {
		this.workorderid = workorderid;
	}
	public String getVehicleno() {
		return vehicleno;
	}
	public void setVehicleno(String vehicleno) {
		this.vehicleno = vehicleno;
	}
	public String getPasspath() {
		return passpath;
	}
	public void setPasspath(String passpath) {
		this.passpath = passpath;
	}
	public String getEscape() {
		return escape;
	}
	public void setEscape(String escape) {
		this.escape = escape;
	}
	public Integer getEscapeTypeOne() {
		return escapeTypeOne;
	}
	public void setEscapeTypeOne(Integer escapeTypeOne) {
		this.escapeTypeOne = escapeTypeOne;
	}
	public String getEscapeTypeTwo() {
		return escapeTypeTwo;
	}
	public void setEscapeTypeTwo(String escapeTypeTwo) {
		this.escapeTypeTwo = escapeTypeTwo;
	}
	public String getTimeType() {
		return timeType;
	}
	public void setTimeType(String timeType) {
		this.timeType = timeType;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getVehFlag() {
		return vehFlag;
	}
	public void setVehFlag(String vehFlag) {
		this.vehFlag = vehFlag;
	}
	public String getEntryTime() {
		return entryTime;
	}
	public void setEntryTime(String entryTime) {
		this.entryTime = entryTime;
	}
	public String getEntryPlace() {
		return entryPlace;
	}
	public void setEntryPlace(String entryPlace) {
		this.entryPlace = entryPlace;
	}
	public String getExitTime() {
		return exitTime;
	}
	public void setExitTime(String exitTime) {
		this.exitTime = exitTime;
	}
	public String getExitPlace() {
		return exitPlace;
	}
	public void setExitPlace(String exitPlace) {
		this.exitPlace = exitPlace;
	}
	public String getPassProvince() {
		return passProvince;
	}
	public void setPassProvince(String passProvince) {
		this.passProvince = passProvince;
	}
	public String getObuno() {
		return obuno;
	}
	public void setObuno(String obuno) {
		this.obuno = obuno;
	}
	public String getEtcnoCpcno() {
		return etcnoCpcno;
	}
	public void setEtcnoCpcno(String etcnoCpcno) {
		this.etcnoCpcno = etcnoCpcno;
	}
	public String getIssuerno() {
		return issuerno;
	}
	public void setIssuerno(String issuerno) {
		this.issuerno = issuerno;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getWorkorderTitle() {
		return workorderTitle;
	}
	public void setWorkorderTitle(String workorderTitle) {
		this.workorderTitle = workorderTitle;
	}
	public String getWorkorderTime() {
		return workorderTime;
	}
	public void setWorkorderTime(String workorderTime) {
		this.workorderTime = workorderTime;
	}
	public String getInvestigateProgress() {
		return investigateProgress;
	}
	public void setInvestigateProgress(String investigateProgress) {
		this.investigateProgress = investigateProgress;
	}
	public String getDataProgress() {
		return dataProgress;
	}
	public void setDataProgress(String dataProgress) {
		this.dataProgress = dataProgress;
	}
	public String getInitiator() {
		return initiator;
	}
	public void setInitiator(String initiator) {
		this.initiator = initiator;
	}
	public String getUpdatetime() {
		return updatetime;
	}
	public void setUpdatetime(String updatetime) {
		this.updatetime = updatetime;
	}
	public Integer getVehColor() {
		return vehColor;
	}
	public void setVehColor(Integer vehColor) {
		this.vehColor = vehColor;
	}
	public Integer getCarColor() {
		return carColor;
	}
	public void setCarColor(Integer carColor) {
		this.carColor = carColor;
	}
	public Integer getVehType() {
		return vehType;
	}
	public void setVehType(Integer vehType) {
		this.vehType = vehType;
	}
	public Integer getVehClass() {
		return vehClass;
	}
	public void setVehClass(Integer vehClass) {
		this.vehClass = vehClass;
	}
	public Integer getWhetherAudit() {
		return whetherAudit;
	}
	public void setWhetherAudit(Integer whetherAudit) {
		this.whetherAudit = whetherAudit;
	}
	public Integer getPassProvinceTotal() {
		return passProvinceTotal;
	}
	public void setPassProvinceTotal(Integer passProvinceTotal) {
		this.passProvinceTotal = passProvinceTotal;
	}
	public Integer getPassRoadTotal() {
		return passRoadTotal;
	}
	public void setPassRoadTotal(Integer passRoadTotal) {
		this.passRoadTotal = passRoadTotal;
	}
	public Integer getPassMediaType() {
		return passMediaType;
	}
	public void setPassMediaType(Integer passMediaType) {
		this.passMediaType = passMediaType;
	}
	public Integer getSpecialType() {
		return specialType;
	}
	public void setSpecialType(Integer specialType) {
		this.specialType = specialType;
	}
	public Integer getEtctype() {
		return etctype;
	}
	public void setEtctype(Integer etctype) {
		this.etctype = etctype;
	}
	public Integer getWorkorderUnit() {
		return workorderUnit;
	}
	public void setWorkorderUnit(Integer workorderUnit) {
		this.workorderUnit = workorderUnit;
	}
	public Integer getWorkorderStatus() {
		return workorderStatus;
	}
	public void setWorkorderStatus(Integer workorderStatus) {
		this.workorderStatus = workorderStatus;
	}
	public Integer getWorkoderCount() {
		return workoderCount;
	}
	public void setWorkoderCount(Integer workoderCount) {
		this.workoderCount = workoderCount;
	}
	public Double getRansTotal() {
		return ransTotal;
	}
	public void setRansTotal(Double ransTotal) {
		this.ransTotal = ransTotal;
	}

	
}
