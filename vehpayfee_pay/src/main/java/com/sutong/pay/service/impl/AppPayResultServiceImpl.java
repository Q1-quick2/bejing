/**
 * @Description: APP支付结果查询Service实现类
 * @ClassName: AppPayResultServiceImpl
 * @author： WangLei
 * @date: 2019/12/21 10:31
 * @Version： 1.0
 */
package com.sutong.pay.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.murong.merchant.atc.MerchantUtil;
import com.murong.merchant.atc.RSASignUtil;
import com.sutong.bjstjh.entity.PayEndInformModel;
import com.sutong.bjstjh.util.NotNullUtil;
import com.sutong.pay.service.AppPayResultService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.File;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by 王磊 on 2019/12/21.
 */
@Component
@Service(timeout = 1200000)
public class AppPayResultServiceImpl implements AppPayResultService{

    Logger log = LoggerFactory.getLogger(AppPayResultServiceImpl.class);

    //调用支付平台接口查询支付结果
    @Override
    public PayEndInformModel doCallAppPayResult(String orderNo) throws Exception{
        log.info("开始调用支付平台查询支付结果接口");

        String charset = "00";//字符集（唯一值）
        String version = "1.0";//接口版本（固定值）
        String signType = "RSA";// 签名方式（固定值）
        String service = "OrderPaySearch";//服务类型（固定值）
        String channelType = "02";//交易渠道（APP客户端）
        String txnType = "02";//交易类型（消费）
        String busType = "0105";//业务类型（快捷消费）
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String requestTime = formatter.format(date);//请求时间
        String merchantId = "888022044110010";//商户编号
        String rmk = "查询支付结果";//备注

        String requestUrl = "http://172.20.53.138:38080/mrpos/stmpay";//请求地址（内网）
        //String requestUrl = "http://202.106.235.34:10027/mrpos/stmpay";//请求地址（外网）

        String merCert = merchantId + ".p12";//商户证书名字
        File directory = new File("");
        String merchantCertPath = directory.getCanonicalPath() + "\\" + merCert;//拼接证书路径
        log.info("商户证书路径：" + merchantCertPath);
        String merchantCertPass = "591760";//商户证书密码

        String rootCertPath = directory.getCanonicalPath() + "\\" + "rootca.cer";//拼接根证书路径
        log.info("根证书路径：" + rootCertPath);


        // 签名
        Map<String, Object> dataMap = new LinkedHashMap<String, Object>();
        dataMap.put("charset", charset);
        dataMap.put("version", version);
        dataMap.put("signType", signType);
        dataMap.put("service", service);
        dataMap.put("channelType", channelType);
        dataMap.put("txnType", txnType);
        dataMap.put("busType", busType);
        dataMap.put("requestTime", requestTime);
        dataMap.put("merchantId", merchantId);
        dataMap.put("orderNo", orderNo);
        dataMap.put("rmk", rmk);

        //去掉map中的空值
        Map requestMap = new HashMap();
        requestMap.putAll(dataMap);
        Set set = dataMap.keySet();
        Iterator iterator = set.iterator();
        while (iterator.hasNext()) {
            String key = (String) iterator.next();
            if ((dataMap.get(key) == null) || (dataMap.get(key) == "")) {
                requestMap.remove(key);
            }
        }

        log.info("开始生成商户证书和商户签名");
        RSASignUtil util = new RSASignUtil(merchantCertPath, merchantCertPass);
        String reqData = util.coverMap2String(requestMap);
        String merchantSign = util.sign(reqData, "GBK");
        String merchantCert = util.getCertInfo();
        log.info("商户证书是否生成成功：" + !NotNullUtil.isEmpty(merchantCert));
        log.info("商户签名是否生成成功：" + !NotNullUtil.isEmpty(merchantSign));


        //请求报文
        String buf = reqData + "&merchantSign=" + merchantSign + "&merchantCert=" + merchantCert;
        //发起http请求，并获取响应报文
        log.info("开始向支付平台发送HttpClient请求");
        String res = MerchantUtil.sendAndRecv(requestUrl, buf, charset);
        log.info("支付平台是否返回结果：" + !NotNullUtil.isEmpty(res));
        log.info("res值：" + res);


        //获取帮付宝平台返回的签名消息摘要，用来验签
        Map<String, String> retMap = new LinkedHashMap<String, String>();
        retMap.put("charset", (String) util.getValue(res, "charset"));
        retMap.put("version", (String) util.getValue(res, "version"));
        retMap.put("serverCert", (String) util.getValue(res, "serverCert"));
        retMap.put("serverSign", (String) util.getValue(res, "serverSign"));
        retMap.put("signType", (String) util.getValue(res, "signType"));
        retMap.put("service", (String) util.getValue(res, "service"));
        retMap.put("merchantId", (String) util.getValue(res, "merchantId"));
        retMap.put("returnCode", (String) util.getValue(res, "returnCode"));
        retMap.put("returnMessage", (String) util.getValue(res, "returnMessage"));

        retMap.put("resultCode", (String) util.getValue(res, "resultCode"));
        retMap.put("resultMsg", (String) util.getValue(res, "resultMsg"));
        retMap.put("orderNo", (String) util.getValue(res, "orderNo"));
        retMap.put("payType", (String) util.getValue(res, "payType"));
        retMap.put("txnAmt", (String) util.getValue(res, "txnAmt"));
        retMap.put("payOrderNo", (String) util.getValue(res, "payOrderNo"));
        retMap.put("paymentTime", (String) util.getValue(res, "paymentTime"));
        retMap.put("stlDate", (String) util.getValue(res, "stlDate"));
        retMap.put("rmk", (String) util.getValue(res, "rmk"));
        retMap.put("actPayAmt", (String) util.getValue(res, "actPayAmt"));
        retMap.put("discountsAmt", (String) util.getValue(res, "discountsAmt"));
        retMap.put("discountsInfoListNum", (String) util.getValue(res, "discountsInfoListNum"));
        retMap.put("discountsInfoList", (String) util.getValue(res, "discountsInfoList"));
        retMap.put("payCapMod", (String) util.getValue(res, "payCapMod")); //支付方式


        Map responseMap = new HashMap();
        responseMap.putAll(retMap);
        Set set1 = retMap.keySet();
        Iterator iterator1 = set1.iterator();
        while (iterator1.hasNext()) {
            String key0 = (String) iterator1.next();
            String tmp = retMap.get(key0);
            if (StringUtils.equals(tmp, "null") || StringUtils.isBlank(tmp)) {
                responseMap.remove(key0);
            }
        }
        String sf = util.coverMap2String(responseMap);


        // 验证签名
        log.info("开始验签");
        boolean flag = false;
        RSASignUtil rsautil = new RSASignUtil(rootCertPath);
        flag = rsautil.verify(sf,util.getValue(res,"serverSign"),util.getValue(res,"serverCert"),"GBK");
        if (!flag) {
            //System.out.println("返回验签报文：" + res);
            log.error("错误信息：验签错误");
            return null;
        }
        log.info("验签结束");
        String code = (String) retMap.get("returnCode");
        if (!code.equals("000000")) {
            log.error("响应码：" + code);
            log.error("响应信息：" + URLDecoder.decode((String)retMap.get("returnMessage"),"UTF-8"));
            return null;
        }

        //map集合转换为实体类对象
        PayEndInformModel payEndVo = new PayEndInformModel();
        payEndVo.setCharset(retMap.get("charset"));
        payEndVo.setVersion(retMap.get("version"));
        payEndVo.setServerCert(retMap.get("serverCert"));
        payEndVo.setServerSign(retMap.get("serverSign"));
        payEndVo.setSignType(retMap.get("signType"));
        payEndVo.setService(retMap.get("service"));
        payEndVo.setMerchantId(retMap.get("merchantId"));

        payEndVo.setResultCode(retMap.get("resultCode"));
        payEndVo.setResultMsg(retMap.get("resultMsg"));
        payEndVo.setPaySerialNo(retMap.get("orderNo"));
        payEndVo.setTxnAmt(Integer.parseInt(retMap.get("txnAmt")));
        payEndVo.setPayOrderNo(retMap.get("payOrderNo"));
        payEndVo.setPaymentTime(retMap.get("paymentTime"));
        payEndVo.setPayType(retMap.get("payType"));
        payEndVo.setRmk(retMap.get("rmk"));
        payEndVo.setActPayAmt(Integer.parseInt(retMap.get("actPayAmt")));
        payEndVo.setDiscountsAmt(Integer.parseInt(retMap.get("discountsAmt")));
        payEndVo.setDiscountsInfoListNum(retMap.get("discountsInfoListNum"));
        payEndVo.setDiscountsInfoList(retMap.get("discountsInfoList"));

        log.info("调用支付平台查询支付结果接口结束");
        log.info("支付结果状态：" + retMap.get("resultCode"));
        log.info("支付状态描述：" + retMap.get("resultMsg"));
        return payEndVo;

    }


}