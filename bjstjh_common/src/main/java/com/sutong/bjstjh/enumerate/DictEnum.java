package com.sutong.bjstjh.enumerate;

/**
 * @ClassName: DictEnum
 * @Description: 字典类型枚举
 * @author: pengwz
 * @date: 2019年12月20日 下午4:09:43
 */
public enum DictEnum {

    DICT_DUTY_BELONG            ("责任归属字典(历史工单表)"),
    DICT_VEH_BODY_COLOR         ("车身颜色"),
    DICT_VEH_TYPE                ("车型"),
    DICT_VEH_CLASS               ("车种"),
    VEHPLATE_COLORCODE           ("车牌颜色"),
    EVENT_TYPECODE               ("事件类别");

    /**
     * 字典类型备注
     */
    private String remark;


    private DictEnum(String remark) {
        this.remark = remark;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

}
