/**
 * @Description: 支付平台APP支付预下单返回结果实体类
 * @ClassName: AppPayPreOrderResponseEntity
 * @author： WangLei
 * @date: 2019/12/14 17:27
 * @Version： 1.0
 */
package com.sutong.bjstjh.entity.pay;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by 王磊 on 2019/12/14.
 */
@Data
public class AppPayPreOrderResponseEntity implements Serializable{

    private String charset;//字符集
    private String version;//版本号
    //private String serverCert; 服务器证书
    //private String serverSign; 服务器签名
    private String signType;//签名方式
    private String service;//接口类型
    private String merchantId;//商户编号
    private String returnCode;//返回码
    private String returnMessage;//返回码描述信息

    private String resultCode;//支付结果状态
    private String resultMsg;//支付结果描述
    private String orderNo;//商户订单号
    private String createTime;//订单创建时间

}