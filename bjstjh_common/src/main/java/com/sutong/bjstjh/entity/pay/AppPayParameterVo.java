/**
 * @Description: APP支付参数实体类
 * @ClassName: AppPayParameterVo
 * @author： WangLei
 * @date: 2019/12/15 20:10
 * @Version： 1.0
 */
package com.sutong.bjstjh.entity.pay;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by 王磊 on 2019/12/15.
 */
@Data
public class AppPayParameterVo implements Serializable{

    private String order;//订单号
    private String merId;//商户号
    private String money;//支付金额

}
