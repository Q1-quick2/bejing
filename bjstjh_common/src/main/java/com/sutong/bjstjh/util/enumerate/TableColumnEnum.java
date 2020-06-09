package com.sutong.bjstjh.util.enumerate;

/**   
 * @ClassName:  TableColumnEnum   
 * @Description: 列出用于文件的表字段
 * @author: pengwz
 * @date:   2019年12月23日 下午3:27:53      
 */
public enum TableColumnEnum {
	
	VEH_IMG_ADDRESS	("发行车辆信息表 AUDIT_ISSUE_VEH_TABLE  车辆图片"),
	
	DRIV_IMG_ADDRESS("发行车辆信息表AUDIT_ISSUE_VEH_TABLE  行驶证图片"),
	
	IMG_ADDRESS		("稽核结果对比结论表 AUDIT_CHECK_RESULTS 证据图片地址"),
	
	PROOF			("历史工单表/车牌不符表 AUDIT_WORKORDER_HISTORY_TABLE 证据字段");

	/**字段备注*/
	private String remark;

	private TableColumnEnum(String name) {
		this.remark =name;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
}
