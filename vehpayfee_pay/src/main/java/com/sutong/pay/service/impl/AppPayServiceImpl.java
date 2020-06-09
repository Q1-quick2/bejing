/**
 * @Description: APP支付Service实现类
 * @ClassName: AppPayServiceImpl
 * @author： WangLei
 * @date: 2019/12/15 15:57
 * @Version： 1.0
 */
package com.sutong.pay.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.murong.merchant.atc.MerchantUtil;
import com.murong.merchant.atc.RSASignUtil;
import com.sutong.bjstjh.entity.AuditPastOrderEntity;
import com.sutong.bjstjh.entity.pay.*;
import com.sutong.bjstjh.util.NotNullUtil;
import com.sutong.bjstjh.util.SnowflakeIdWorker;
import com.sutong.pay.mapper.AppPayMapper;
import com.sutong.pay.service.AppPayService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by 王磊 on 2019/12/15.
 */
@Component
@Service(timeout = 1200000)
public class AppPayServiceImpl implements AppPayService{

    Logger log = LoggerFactory.getLogger(AppPayServiceImpl.class);

    @Autowired
    private AppPayMapper appPayMapper;


    //判断非历史逃费补费状态
    @Override
    public String doFindOrderStatus(String orderId) {
        return appPayMapper.doFindOrderStatus(orderId);
    }

    //判断历史逃费补费状态
    @Override
    public String doFindOrderStatusPast(String obuId) {
        return appPayMapper.doFindOrderStatusPast(obuId);
    }

    //调用“支付平台”APP支付预下单接口
    @Override
    @Transactional(rollbackFor = Exception.class)
    public AppPayParameterVo doCallAppPayPreOrder(AppPayRequestVo requestVo) throws Exception{

        log.info("开始调用支付平台APP支付预下单接口");

        //雪花算法
        SnowflakeIdWorker idWorker = new SnowflakeIdWorker(0,0);
        long orderNoBack = idWorker.nextId();

        String orderNoLast = null;
        String money = null;
        String goodsDescTemp = null;
        if(!NotNullUtil.isEmpty(requestVo) && "1".equals(requestVo.getOrderType())){ //非历史
            //获取商户订单号
            orderNoLast = "1" + orderNoBack;
            //获取补费总金额
            Integer moneyLast = 0;
            String[] orderIds = requestVo.getOrderIds();
            for(String orderId : orderIds){
                Integer tempMoney = appPayMapper.doFindArrearageMoney(orderId);
                moneyLast = moneyLast + tempMoney;
                money = moneyLast.toString();
            }
            //商品描述赋值
            goodsDescTemp = "测试稽核补费";

        } else if(!NotNullUtil.isEmpty(requestVo) && "2".equals(requestVo.getOrderType())){ //历史
            //获取商户订单号
            orderNoLast = "2" + orderNoBack;
            //获取补费总金额
            money = appPayMapper.doFindPastArrearageMoney(requestVo.getObuId());
            //商品描述赋值
            goodsDescTemp = "测试稽核历史补费";

        } else {
            log.error("获取补费总金额和商户订单号时失败！");
            return null;
        }

        //判断商户订单号长度是否符合要求
        if(!NotNullUtil.isEmpty(orderNoLast) && orderNoLast.length() != 19){
            log.error("商户订单号长度不符合要求！长度为：" + orderNoLast.length());
            return null;
        }


        String merchantId="888022044110010";//商户号
        String service = "UOrder";//服务类型（固定值）

        String requestUrl = "http://172.20.53.138:38080/mrpos/stmpay";//请求地址（内网）
        //String requestUrl = "http://202.106.235.34:10027/mrpos/stmpay";//请求地址（外网）
        String charset = "00";//字符集（唯一值）
        String version = "1.0";//接口版本（固定值）
        String signType = "RSA";//签名方式（固定值）

        String merCert = merchantId + ".p12";//商户证书名字
        File directory = new File("");
        String merchantCertPath = directory.getCanonicalPath() + "\\" + merCert;//拼接商户证书路径
        //String merchantCertPath = path + merCert;//拼接证书路径
        log.info("商户证书路径：" + merchantCertPath);
        String merchantCertPass = "591760";//商户证书密码

        String rootCertPath = directory.getCanonicalPath() + "\\" + "rootca.cer";//拼接根证书路径
        log.info("根证书路径：" + rootCertPath);
        //String requestId = String.valueOf(System.currentTimeMillis());//获取时间戳
        //String requestUrl1 = (String) request.getSession().getAttribute("requestUrl1");

        //编码设置
        /*if ("00".equals(charset)) {
            request.setCharacterEncoding("GBK");
            encoding="GBK";
        } else if ("01".equals(charset)) {
            request.setCharacterEncoding("GB2312");
            encoding="GB2312";
        } else if ("02".equals(charset)) {
            request.setCharacterEncoding("UTF-8");
            encoding="UTF-8";
        } else {
            request.setCharacterEncoding("GBK");
            encoding="GBK";
        }*/


        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String requestTime = formatter.format(date);//请求时间
        String channelType = "02";//交易渠道（APP客户端）
        String txnType = "02";//交易类型（消费）
        String busType = "0105";//业务类型（快捷消费）
        String bussInitiator = "10001";//业务发起方（无特殊要求为商户号）
        String orderNo = orderNoLast;//商户订单号
        String goodsDesc = goodsDescTemp;//商品描述
        String orderAmt = money;//订单金额（分）
        String ccy = "CNY";//交易币种
        String notifyUrl = "http://172.20.53.245:8080/AppPayEndInform";//信息回调地址
        String rmk = "";//备注


        // 签名
        Map<String,Object> dataMap = new LinkedHashMap<String,Object>();
        dataMap.put("charset",charset);
        dataMap.put("version",version);
        dataMap.put("signType",signType);
        dataMap.put("service",service);
        dataMap.put("channelType",channelType);
        dataMap.put("txnType",txnType);
        dataMap.put("busType",busType);
        dataMap.put("requestTime",requestTime);
        dataMap.put("merchantId",merchantId);
        dataMap.put("bussInitiator",bussInitiator);
        dataMap.put("orderNo",orderNo);
        dataMap.put("goodsDesc",goodsDesc);
        dataMap.put("orderAmt",orderAmt);
        dataMap.put("ccy",ccy);
        dataMap.put("notifyUrl",notifyUrl);
        dataMap.put("rmk",rmk);

        //去掉map中的空值
        Map requestMap = new HashMap();
        requestMap.putAll(dataMap);
        Set set = dataMap.keySet();
        Iterator iterator = set.iterator();
        while (iterator.hasNext()) {
            String key = (String)iterator.next();
            if ((dataMap.get(key) == null) || (dataMap.get(key) == "")) {
                requestMap.remove(key);
            }
        }

        log.info("开始生成商户证书和商户签名");
        RSASignUtil util = new RSASignUtil( merchantCertPath, merchantCertPass);
        String reqData=util.coverMap2String(requestMap);
        String merchantSign = util.sign(reqData,"GBK");
        String merchantCert = util.getCertInfo();
        log.info("商户证书是否生成成功：" + !NotNullUtil.isEmpty(merchantCert));
        log.info("商户签名是否生成成功：" + !NotNullUtil.isEmpty(merchantSign));


        //请求报文
        String buf = reqData + "&merchantSign=" + merchantSign + "&merchantCert=" + merchantCert;
        //发起http请求，并获取响应报文
        log.info("开始向支付平台发送HttpClient请求");
        String res = MerchantUtil.sendAndRecv(requestUrl, buf, charset);
        log.info("支付平台是否返回结果：" + !NotNullUtil.isEmpty(res));
        log.info("res：" + res);


        //获取帮付宝平台返回的签名消息摘要，用来验签
        Map<String,String> retMap = new LinkedHashMap<String,String>();
        retMap.put("charset",(String)util.getValue(res,"charset"));
        retMap.put("version",(String)util.getValue(res,"version"));
        retMap.put("signType",(String)util.getValue(res,"signType"));
        retMap.put("service",(String)util.getValue(res,"service"));
        retMap.put("returnCode",(String)util.getValue(res,"returnCode"));
        retMap.put("returnMessage",(String)util.getValue(res,"returnMessage"));
        retMap.put("orderNo",(String)util.getValue(res,"orderNo"));
        retMap.put("createTime",(String)util.getValue(res,"createTime"));
        retMap.put("merchantId",(String)util.getValue(res,"merchantId"));
        retMap.put("resultCode",(String)util.getValue(res,"resultCode"));
        retMap.put("resultMsg",(String)util.getValue(res,"resultMsg"));


        Map responseMap = new HashMap();
        responseMap.putAll(retMap);
        Set set1 = retMap.keySet();
        Iterator iterator1 = set1.iterator();
        while (iterator1.hasNext()) {
            String key0 = (String)iterator1.next();
            String tmp = retMap.get(key0);
            if (StringUtils.equals(tmp, "null")||StringUtils.isBlank(tmp)) {
                responseMap.remove(key0);
            }
        }
        String sf=	util.coverMap2String(responseMap);


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
        String code = (String)retMap.get("returnCode");
        if (!code.equals("000000")) {
            log.error("响应码：" + code);
            log.error("响应信息：" + URLDecoder.decode((String)retMap.get("returnMessage"),"UTF-8"));
            return null;
        }


        //map集合转换为实体类对象
        AppPayPreOrderResponseEntity responseVo = new AppPayPreOrderResponseEntity();
        responseVo.setCharset(retMap.get("charset"));
        responseVo.setVersion(retMap.get("version"));
        responseVo.setSignType(retMap.get("signType"));
        responseVo.setService(retMap.get("service"));
        responseVo.setMerchantId(retMap.get("merchantId"));
        responseVo.setReturnCode(retMap.get("returnCode"));
        responseVo.setReturnMessage(retMap.get("returnMessage"));
        responseVo.setResultCode(retMap.get("resultCode"));
        responseVo.setResultMsg(retMap.get("resultMsg"));
        responseVo.setOrderNo(retMap.get("orderNo"));
        responseVo.setCreateTime(retMap.get("createTime"));
        //保存支付平台APP支付预下单返回结果
        appPayMapper.doInsertAppPayPreOrderResult(responseVo);


        //生成补费流水
        if(!NotNullUtil.isEmpty(requestVo) && "1".equals(requestVo.getOrderType())){ //非历史
            log.info("开始生成非历史逃费补费流水");

            //补费流水生成时间
            Date date2 = new Date();
            SimpleDateFormat formatter2 = new SimpleDateFormat("yyyyMMddHHmmss");
            String flowCreateTime = formatter2.format(date2);
            //单独保存一份流水号，用来防止流水号重复
            appPayMapper.doInsertPayBackFeeId(orderNo,flowCreateTime);
            log.info("生成的流水号可以正常使用");

            for(String orderId : requestVo.getOrderIds()){
                PaybackFeeFlowEntity flowEntity = new PaybackFeeFlowEntity();
                PaybackFeeFlowVo flowVo = new PaybackFeeFlowVo();

                String idStart = UUID.randomUUID().toString().replace("-","");
                flowEntity.setFlowId(idStart + flowCreateTime);//主键ID
                flowEntity.setPayBackFeeId(orderNo);//补费流水号
                flowEntity.setOrderId(orderId);//工单编号
                flowEntity.setOrderType(requestVo.getOrderType());//工单类型
                flowEntity.setVehicleNumber(requestVo.getVehicleNumber());//车牌号码
                flowEntity.setVehicleColour(requestVo.getVehicleColour());//车牌颜色
                flowEntity.setPayBackSum(Integer.valueOf(money));//补费总金额
                flowEntity.setUid(requestVo.getUid());//用户id
                flowEntity.setToken(requestVo.getToken());//用户Token
                flowEntity.setUserName(requestVo.getUserName());//用户名
                flowEntity.setIdNumber(requestVo.getIdNumber());//用户身份证号
                flowEntity.setVehicleId(requestVo.getVehicleNumber() + "_" + requestVo.getVehicleColour());//车辆编号
                flowEntity.setPayBackUser(requestVo.getRealName());//补费人姓名
                flowEntity.setPayBackUserPhone(requestVo.getPhone());//补费人联系方式
                flowEntity.setTransactionType(1);//补费类型（ETC交易）
                flowEntity.setPayBackType(2);//补费渠道（省中心）
                flowEntity.setOperator(null);//经办人姓名
                flowEntity.setOperateOrg(null);//经办单位
                flowEntity.setOperateRoad(null);//经办路段
                flowEntity.setOperateStation(null);//经办站
                flowEntity.setFlowCreateTime(flowCreateTime);//补费流水生成时间

                flowVo = appPayMapper.doFindDisposeAndPassInfo(orderId);
                flowEntity.setTollProvinceId(flowVo.getTollProvinceId());//处理方省中心id
                flowEntity.setMessageId(flowVo.getMessageId());//处理方省中心生成的文件id
                flowEntity.setPassId(flowVo.getPassId());//通行标识
                flowEntity.setEtcCardId(flowVo.getObuId());//用户卡编号
                flowEntity.setOweFee(flowVo.getOwefee());//应缴金额
                flowEntity.setPayBackFee(flowVo.getOwefee());//此次补费金额

                appPayMapper.doInsertRepairFeeWater(flowEntity);
                log.info("非历史逃费补费流水生成完毕");
            }
        } else if(!NotNullUtil.isEmpty(requestVo) && "2".equals(requestVo.getOrderType())){ //历史
            log.info("开始生成历史逃费补费流水");

            //补费流水生成时间
            Date date2 = new Date();
            SimpleDateFormat formatter2 = new SimpleDateFormat("yyyyMMddHHmmss");
            String flowCreateTime = formatter2.format(date2);

            PaybackFeeFlowPastEntity flowPastEntity = new PaybackFeeFlowPastEntity();
            String idStart = UUID.randomUUID().toString().replace("-","");
            flowPastEntity.setFlowId(idStart + flowCreateTime);//主键ID
            flowPastEntity.setPayBackFeeId(orderNo);//补费流水号
            flowPastEntity.setOrderType(requestVo.getOrderType());//工单类型
            flowPastEntity.setPayBackSum(Integer.valueOf(money));//补费总金额
            flowPastEntity.setUid(requestVo.getUid());//用户id
            flowPastEntity.setToken(requestVo.getToken());//用户Token
            flowPastEntity.setUserName(requestVo.getUserName());//用户名
            flowPastEntity.setIdNumber(requestVo.getIdNumber());//身份证号
            flowPastEntity.setPayBackUser(requestVo.getRealName());//补费人姓名
            flowPastEntity.setPayBackUserPhone(requestVo.getPhone());//补费人联系方式
            flowPastEntity.setTransactionType(1);//补费类型（ETC交易）
            flowPastEntity.setPayBackType(2);//补费渠道（省中心）
            flowPastEntity.setFlowCreateTime(flowCreateTime);//补费流水生成时间
            flowPastEntity.setObuId(requestVo.getObuId());//OBU号
            flowPastEntity.setOweFee(Integer.valueOf(money));//应缴总金额（分）

            AuditPastOrderEntity vo = appPayMapper.doFindPastOrder(requestVo.getObuId());
            flowPastEntity.setCustomerName(vo.getCustomerName());//客户名称
            flowPastEntity.setCustomerPhone(vo.getCustomerPhone());//联系电话
            flowPastEntity.setTransNum(vo.getTransNum());//交易次数
            flowPastEntity.setTransAllMoney(Integer.valueOf(vo.getTransAllMoney()));//交易总金额（分）

            appPayMapper.doInsertPastPayBackFeeWater(flowPastEntity);
            log.info("历史逃费补费流水生成完毕");
        } else {
            log.error("生成补费流水时出错！");
            return null;
        }

        AppPayParameterVo parameterVo = new AppPayParameterVo();
        parameterVo.setOrder(orderNo);//订单号
        parameterVo.setMerId(merchantId);//商户号
        parameterVo.setMoney(orderAmt);//支付金额（分）
        log.info("调用支付平台APP支付预下单接口结束");
        log.info("返回给H5的参数为：" + parameterVo);
        return parameterVo;

    }



}