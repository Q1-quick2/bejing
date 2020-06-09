package com.sutong.bjstjh.entity;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * @Description: 补费结果查询model类
 * @ClassName: AuditFeeResultModel
 * @author： FengJingBo
 * @date: 2019/12/14 16:27
 * @Version： 1.0
 */
@Data
@ToString
public class AuditFeeResultModel implements Serializable{

    //车牌号
    private String vehicle_id;
    //车牌颜色
    private String plate_color;
    //用户id
    private String user_id;
    //补费人姓名
    private String payback_name;
    //补费人联系方式
    private String phone_num;
    //是否补费成功
    private Integer confirmed_payback_fee;
    //补费金额
    private Integer payback_fee;
    //是否是历史补费


    public String getVehicle_id() {
        return vehicle_id;
    }

    public void setVehicle_id(String vehicle_id) {
        this.vehicle_id = vehicle_id;
    }

    public String getPlate_color() {
        return plate_color;
    }

    public void setPlate_color(String plate_color) {
        this.plate_color = plate_color;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getPayback_name() {
        return payback_name;
    }

    public void setPayback_name(String payback_name) {
        this.payback_name = payback_name;
    }

    public String getPhone_num() {
        return phone_num;
    }

    public void setPhone_num(String phone_num) {
        this.phone_num = phone_num;
    }

    public Integer getConfirmed_payback_fee() {
        return confirmed_payback_fee;
    }

    public void setConfirmed_payback_fee(Integer confirmed_payback_fee) {
        this.confirmed_payback_fee = confirmed_payback_fee;
    }

    public Integer getPayback_fee() {
        return payback_fee;
    }

    public void setPayback_fee(Integer payback_fee) {
        this.payback_fee = payback_fee;
    }
}
