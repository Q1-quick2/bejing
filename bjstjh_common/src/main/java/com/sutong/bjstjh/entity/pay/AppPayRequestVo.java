/**
 * @Description: 支付预下单请求参数实体类
 * @ClassName: AppPayRequestVo
 * @author： WangLei
 * @date: 2019/12/19 21:33
 * @Version： 1.0
 */
package com.sutong.bjstjh.entity.pay;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by 王磊 on 2019/12/19.
 */
@Data
public class AppPayRequestVo implements Serializable{

    private String uid;//用户id
    private String token;//用户token
    private String userName;//用户名
    private String phone;//用户联系方式

    private String realName;//用户真实姓名
    private String idNumber;//身份证号
    private String vehicleNumber;//车牌号
    private String vehicleColour;//车牌颜色
    private String orderType;//工单类型
    private String obuId;//OBU号
    private String[] orderIds;//工单编号


    private String orderNo;//商户订单号
    private String money;//订单金额（分）


}