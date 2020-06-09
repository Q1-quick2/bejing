package com.sutong.pay.controller.app;

import com.alibaba.dubbo.config.annotation.Reference;
import com.sutong.bjstjh.entity.*;
import com.sutong.bjstjh.result.Result;
import com.sutong.bjstjh.util.NotNullUtil;
import com.sutong.bjstjh.util.NumberToCN;
import com.sutong.common.model.ConfigModel;
import com.sutong.common.service.ConfigService;
import com.sutong.pay.service.AppPayResultService;
import com.sutong.pay.service.AppRepairfeeService;
import com.sutong.transfer.TransferIssue;
import com.sutong.transfer.TransferSendSMS;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

//import com.murong.merchant.atc.RSASignUtil;

/**
 * Created by main on 2019/12/14.
 */
@RestController
@CrossOrigin
@Api(value = "回调接口")
public class AppRepairfeeController {

    Logger log = LoggerFactory.getLogger(AppRepairfeeController.class);

    @Reference
    private AppRepairfeeService repairfeeService;
    @Reference
    private AppPayResultService appPayResultService;
    @Autowired
    private TransferSendSMS transferSendSMS;
    //@Autowired
    //private ThreadPoolInterface threadPoolInterface;
    @Autowired
    private TransferIssue transferIssue;
    @Reference
    private ConfigService configService;

    /**
     * @description: 补费结果
     * @auther: zhaodengzhuang
     * @date: 2019/12/14
     * @return 订单号
     **/
    @ApiOperation("补费结果")
    @SuppressWarnings("all")
    @RequestMapping(value = "/AppDoFindRepairfeeEnd")
    public Result AppDoFindRepairfeeEnd(@RequestParam("paySerialNo") String paySerialNo){
        log.info("===== 获取补费结果");
        PayEndInformModel repairfee = repairfeeService.doFindRepairfeeEnd(paySerialNo);
        if(repairfee != null && !NotNullUtil.isEmpty(repairfee)){
            return Result.ok().data("repairfee",repairfee);
        }else{
            log.info("<<<<< 数据库没有 << 用第三方接口  获取数据");
                // 没有保存到数据库数据    调用第三方接口
                try {
                    PayEndInformModel payEndInform = appPayResultService.doCallAppPayResult(paySerialNo);
                    // 判断非空
                    if (payEndInform != null && !NotNullUtil.isEmpty(payEndInform)) { // 第三方调用成功
                        // 保存到数据库
                        PayEndInformModel payEnd = repairfeeService.AppPayEndInform(payEndInform);
                        PayEndInformModel repairfe = repairfeeService.doFindRepairfeeEnd(paySerialNo);

                        // 保存数据库成功   调用短信接口发送短信
                        if (payEnd != null && !NotNullUtil.isEmpty(payEnd)) {
                            UserModel userModel = repairfeeService.doFindPhone(payEndInform.getPaySerialNo());
                            // 判断能否获取到手机号
                            if (userModel != null && !NotNullUtil.isEmpty(userModel)) {
                                String mobilNo = userModel.getPhone();// 手机号
                                String content = "尊敬的车主您好,您在乐速通缴费成功,感谢您的支持!";  // 短信内容
                                Map<String, Object> map = transferSendSMS.sendSMS(mobilNo, content);
                                Boolean success = (Boolean) map.get("success");
                                if (success) {
                                    log.info("<<<<短信发送成功!");
                                } else {
                                    log.error("<<<<短信发送失败!");
                                }
                                NotificationModel notificationModel = new NotificationModel();
                                //发送短信之后将结果保存数据库
                                if (success) {
                                    notificationModel.setSendSmsStatus("1");   // 短信调用成功
                                } else {
                                    notificationModel.setSendSmsStatus("2");    // 短信调用成功
                                }
                                notificationModel.setSmsId(UUID.randomUUID().toString());  // 主键ID
                                notificationModel.setSmsType("1");  //发送短信类型 ，1 交易流水通知   2 解禁
                                notificationModel.setPayerName(userModel.getUserName());   // 缴费人姓名
                                notificationModel.setPayerPhone(userModel.getPhone());// 缴费人联系电话
                                notificationModel.setPayAmount(userModel.getMoney());  // 缴费金额
                                notificationModel.setPayRecord(payEndInform.getPaySerialNo());// 缴费流水号
                                notificationModel.setPayTime(payEndInform.getPaymentTime()); // 缴费时间
                                notificationModel.setStatus(payEndInform.getResultCode());  // 状态(缴费成功与否)
                                notificationModel.setPayMode(payEndInform.getPayType()); // 缴费方式
                                repairfeeService.doInsertNotification(notificationModel);
                            } else {
                                log.error("=========从数据库获取手机号失败========");
                            }
                        }
                        return Result.ok().data("repairfe", repairfe);
                    } else {  // 第三方调用失败   重新获取
                        log.error("=========调用第三方获取数据失败,重新获取中========");
                        PayEndInformModel repairfe = new PayEndInformModel();
                        for (int i = 0; i < 3; i++) {
                            try {
                                //睡眠3s
                                Thread.currentThread().sleep(3000);
                            } catch (InterruptedException e) {
                                //  Auto-generated catch block
                                e.printStackTrace();
                            }
                            //try {
                            //  调用第三方接口
                            PayEndInformModel payEndInfor = appPayResultService.doCallAppPayResult(paySerialNo);
                            // 调用成功
                            if (payEndInfor != null && !NotNullUtil.isEmpty(payEndInfor)) {
                                // 保存数据库
                                PayEndInformModel payEnd = repairfeeService.AppPayEndInform(payEndInform);
                                repairfe = repairfeeService.doFindRepairfeeEnd(paySerialNo);
                                if (payEnd != null && !NotNullUtil.isEmpty(payEnd)) {
                                    // 调用短信接口
                                    UserModel userModel = repairfeeService.doFindPhone(payEndInform.getPaySerialNo());
                                    if (userModel != null && !NotNullUtil.isEmpty(userModel)) {
                                        String mobilNo = userModel.getPhone();// 手机号
                                        String content = "尊敬的车主您好,您在乐速通缴费成功,感谢您的支持!";  // 短信内容
                                        Map<String, Object> map = transferSendSMS.sendSMS(mobilNo, content);
                                        Boolean success = (Boolean) map.get("success");
                                        if (success) {
                                            log.info("<<<<   短信发送成功!");
                                        } else {
                                            log.error("<<<<<<   短信发送失败!");
                                        }
                                        NotificationModel notificationModel = new NotificationModel();
                                        //发送短信之后将结果保存数据库
                                        if (success) {
                                            notificationModel.setSendSmsStatus("1");   // 短信调用成功
                                        } else {
                                            notificationModel.setSendSmsStatus("2");    // 短信调用成功
                                        }
                                        notificationModel.setSmsId(UUID.randomUUID().toString());  // 主键ID
                                        notificationModel.setSmsType("1");  //发送短信类型 ，1 交易流水通知   2 解禁
                                        notificationModel.setPayerName(userModel.getUserName());   // 缴费人姓名
                                        notificationModel.setPayerPhone(userModel.getPhone());// 缴费人联系电话
                                        notificationModel.setPayAmount(userModel.getMoney());  // 缴费金额
                                        notificationModel.setPayRecord(payEndInfor.getPaySerialNo());// 缴费流水号
                                        notificationModel.setPayTime(payEndInfor.getPaymentTime()); // 缴费时间
                                        notificationModel.setStatus(payEndInfor.getResultCode());  // 状态(缴费成功与否)
                                        notificationModel.setPayMode(payEndInfor.getPayType()); // 缴费方式
                                        repairfeeService.doInsertNotification(notificationModel);
                                    }
                                }
                                log.info("===== 跳出循环");
                                break;
                            }
                        }
                        return Result.ok().data("repairfe", repairfe);
                    }
                }catch (Exception e){
                    log.info("===== 在循环中调用第三方接口异常");
                }
                return Result.error();
        }
    }


    /**
     * @description: 客户补费凭证
     * @auther: zhaodengzhuang
     * @date: 2019/12/14
     **/
    @ApiOperation("客户补费凭证")
    @RequestMapping("/AppDoRepairfeeVoucher")
    public Result AppDoRepairfeeVoucher(@RequestParam("orderId") String orderId){
        log.info("==========客户补费凭证============");
        VoucherModel voucherModel = repairfeeService.doRepairfeeVoucher(orderId);
        return Result.ok().data("voucherModel",voucherModel);
    }


    /**
     * @description: 支付结果通知
     * @auther: zhaodengzhuang
     * @date:   2019/12/15
     **/
    @ApiOperation("支付结果通知")
    @RequestMapping(value = "/AppPayEndInform")
    public PayEndInformModel AppPayEndInform(PayEndInformModel payEndInformModel) throws Exception {
       /* log.info("进入支付回调方法");

        String res = payEndInformModel.toString();

        res = res.substring(res.indexOf("(")+1);
        res = res.substring(0,res.indexOf(")"));
        res = res.replace(", ","&");
        log.info(" <<<<<  支付结果通知 <<<<<<  参数为 : "+res);

        String merchantId="888022044110010";//商户号
        String merCert = merchantId + ".p12";//商户证书名字
        File directory = new File("");
        //String merchantCertPath = directory.getCanonicalPath() + "\\" + merCert;//拼接商户证书路径
        String merchantCertPath = "/usr/local/java/cert/" + merCert;//拼接商户证书路径
        String rootCertPath = "/usr/local/java/cert/" + "rootca.cer";//拼接根证书路径
        log.info("商户证书路径：" + merchantCertPath);
        String merchantCertPass = "591760";//商户证书密码
        //String rootCertPath = directory.getCanonicalPath() + "\\" + "rootca.cer";//拼接根证书路径
        log.info("根证书路径：" + rootCertPath);

        RSASignUtil util = new RSASignUtil( merchantCertPath, merchantCertPass);
        //获取帮付宝平台返回的签名消息摘要，用来验签
        Map<String,String> retMap = new LinkedHashMap<String,String>();
        retMap.put("charset",(String)util.getValue(res,"charset"));
        retMap.put("version",(String)util.getValue(res,"version"));
        retMap.put("serverCert",(String)util.getValue(res,"serverCert"));
        retMap.put("serverSign",(String)util.getValue(res,"serverSign"));
        retMap.put("signType",(String)util.getValue(res,"signType"));
        retMap.put("service",(String)util.getValue(res,"service"));
        retMap.put("merchantId",(String)util.getValue(res,"merchantId"));
        retMap.put("resultCode",(String)util.getValue(res,"resultCode"));
        retMap.put("resultMsg",(String)util.getValue(res,"resultMsg"));
        retMap.put("paySerialNo",(String)util.getValue(res,"paySerialNo"));
        retMap.put("txnAmt",(String)util.getValue(res,"txnAmt"));
        retMap.put("payOrderNo",(String)util.getValue(res,"payOrderNo"));
        retMap.put("paymentTime",(String)util.getValue(res,"paymentTime"));
        retMap.put("payType",(String)util.getValue(res,"payType"));
        retMap.put("rmk",(String)util.getValue(res,"rmk"));
        retMap.put("actPayAmt",(String)util.getValue(res,"actPayAmt"));
        retMap.put("discountsAmt",(String)util.getValue(res,"discountsAmt"));
        retMap.put("discountsInfoListNum",(String)util.getValue(res,"discountsInfoListNum"));
        retMap.put("discountsInfoList",(String)util.getValue(res,"discountsInfoList"));

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
        log.info("<<<<<<验签结果为 : "+ res);

        flag = rsautil.verify(sf,util.getValue(res,"serverSign"),util.getValue(res,"serverCert"),"GBK");
        if (!flag) {
            log.error("错误信息：验签错误");
            return null;
        }

        log.info("===== 支付结果通知===========");
        PayEndInformModel payEnd = repairfeeService.AppPayEndInform(payEndInformModel);
        log.info(""+payEndInformModel);
        // 发送短信通知用户缴费成功
        if(payEnd != null && !NotNullUtil.isEmpty(payEnd) ){  // 请求成功  调用短信接口 发送短信
            UserModel userModel = repairfeeService.doFindPhone(payEndInformModel.getPaySerialNo());
            String mobilNo =  userModel.getPhone();// 手机号
            String content = "尊敬的车主您好,您在乐速通缴费成功,感谢您的支持!";  // 短信内容
            Map<String,Object> map = transferSendSMS.sendSMS(mobilNo,content);
            Boolean success = (Boolean) map.get("success");
            if(success){
            log.info("短信发送成功!");
            }else{log.error("短信发送失败!");}
            NotificationModel notificationModel = new NotificationModel();
            //发送短信之后将结果保存数据库
            if(success){
                notificationModel.setSendSmsStatus("1");   // 短信调用成功
            }else{
                notificationModel.setSendSmsStatus("2");    // 短信调用成功
            }
            notificationModel.setSmsId(UUID.randomUUID().toString());  // 主键ID
            notificationModel.setSmsType("1");  //发送短信类型 ，1 交易流水通知   2 解禁
            notificationModel.setPayerName(userModel.getUserName());   // 缴费人姓名
            notificationModel.setPayerPhone(userModel.getPhone());// 缴费人联系电话
            notificationModel.setPayAmount(userModel.getMoney());  // 缴费金额
            notificationModel.setPayRecord(payEndInformModel.getPaySerialNo());// 缴费流水号
            notificationModel.setPayTime(payEndInformModel.getPaymentTime()); // 缴费时间
            notificationModel.setStatus(payEndInformModel.getResultCode());  // 状态(缴费成功与否)
            notificationModel.setPayMode(payEndInformModel.getPayType()); // 缴费方式
            try {
                repairfeeService.doInsertNotification(notificationModel);
            }catch (Exception e){
                log.info("==========支付结果通知 !!!  短信表保存失败============");
            }
            }

        // 签名
        Map<String,Object> dataMap = new LinkedHashMap<String,Object>();
        dataMap.put("charset",payEnd.getCharset());
        dataMap.put("version",payEnd.getVersion());
        dataMap.put("rmk",payEnd.getRmk());
        dataMap.put("signType",payEnd.getSignType());
        dataMap.put("service",payEnd.getService());
        dataMap.put("requestTime",payEnd.getRequestTime());

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
        String reqData=util.coverMap2String(requestMap);
        String merchantSign = util.sign(reqData,"GBK");
        String merchantCert = util.getCertInfo();
        payEnd.setMerchantSign(merchantSign);
        payEnd.setMerchantCert(merchantCert);
        return payEnd;*/
       return null;
    }

    /**
     * @description: 高速公路通行费补费确认单    获取
     * @auther: zhaodengzhuang
     * @date: 2019/12/18
     */
    @ApiOperation("确认单")
    @RequestMapping("/doConfirmationInfo")
    public Result doConfirmationInfo(@RequestParam("obu") String obu){
        log.info("===== 调用确认单 ====== ");
        //ConfirmationEntity confirmationEntity = repairfeeService.doConfirmationInfo(obu);
        //if(NotNullUtil.isEmpty(confirmationEntity)){
            ConfirmationEntity confirmation = new ConfirmationEntity();
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            String disposeTime = formatter.format(new Date());
            //  编号 ID
            try {
                ConfirmationEntity c = repairfeeService.getConfirId(obu);
                confirmation.setPayId(c.getPayId());
                //  □  改变车型逃费 □ 改变缴费路经逃费 □ 利用优免政策逃费□其他情况
                confirmation.setPayInfo(c.getPayInfo());
            }catch (Exception e){
                log.error("<<<<<<  获取编号 ID失败");
            }
            confirmation.setPayDisposeTime(disposeTime);    //  处理时间 (当前系统时间)
            confirmation.setPayDisposeSite("乐速通线上补费平台");  //  处理地点
            AuditPastOrderEntity auditPastOrder = repairfeeService.doFindListQuery(obu);
            ConfirmationEntity con = repairfeeService.doFindVehicle(obu);
            /**
             * @Param con.getDoVehicleId(): 车牌号
             * @Param VehColorTypeCode: 车牌颜色类型
             * @Param ObuNo:OBU卡号
             * @Param sessionKey:会话秘钥
             *  调用第三方接口 获取数据
             */
            ConfigModel queryConfigModel = new ConfigModel();
            queryConfigModel.setType(1);
            List<ConfigModel> configModelList = configService.doFindConfigList(queryConfigModel);

            String VehColorTypeCode= "";
            Map<String, Object> map = transferIssue.transferQueryETCCarInfo(con.getDoVehicleId(),VehColorTypeCode,obu,configModelList.get(0).getConfigValue());
            Map<String, Object> map2 =(Map<String, Object>) map.get("data");
            String respCode =(String) map2.get("RespCode"); // 返回码
            String respMsg =(String) map2.get("RespMsg"); // 返回信息说明
            String vehPlateNo =(String) map2.get("VehPlateNo"); // 车牌号码
            String vehColorTypeCode =(String) map2.get("VehColorTypeCode"); // 车牌颜色
            String vehOwnerName =(String) map2.get("VehOwnerName"); // 车辆所有人姓名
            String engineNo =(String) map2.get("EngineNo"); // 发动机号
            String vehClassCode =(String) map2.get("VehClassCode"); // 车辆类别
            String vehTypeCode =(String) map2.get("VehTypeCode"); // 车型
            String truckTypeCode =(String) map2.get("TruckTypeCode"); // 车种
            String cardNo =(String) map2.get("CardNo"); // ETC卡编号
            String userTypeCode =(String) map2.get("UserTypeCode"); // 用户类型
            String certificateNoTrprCode =(String) map2.get("CertificateNoTrprCode"); // 证件类型
            String certificateNo =(String) map2.get("CertificateNo"); // 证件号码

            if(con != null && !NotNullUtil.isEmpty(con)){
                confirmation.setPayPlateNumber(con.getDoVehicleId());  //  车牌号码
                confirmation.setPayCarType(con.getDoVehicleType());      //  车型（轴）
                confirmation.setPayCardNumber(con.getPayCardNumber());     //  非现金卡号/通行卡卡号   (ETC)
                confirmation.setPaySite(con.getPaySite());          //  涉及路段与站点
                //  相关事实：___
                confirmation.setPayTime(con.getPayTime());  //  年  月  日
                confirmation.setPayEndTime(con.getPayEndTime());  //  至  年  月  日期间
            }

            confirmation.setPayCarCategory(vehClassCode);  //  车辆类别        □客 车	□货 车      □专项作业车
            confirmation.setPayPlateNumberColor(vehColorTypeCode);   //  车牌颜色
            confirmation.setPayVehicleOwner(vehOwnerName);   //  车辆所有人
            confirmation.setPayDriverName("");     // auditPastOrder.getCustomerName() 驾驶员姓名  (用户输入)
            confirmation.setPayDriverNumber("");   // certificateNo驾驶员身份证号  (用户输入)
            confirmation.setPayRfid(obu);          //  电子标签（表面号） (obu)
            confirmation.setPayEngineNumber(engineNo);  //  发动机号
            confirmation.setPayCount(auditPastOrder.getTransNum());  //  共计__次
            // 处理依据：
            confirmation.setPayDate(disposeTime);   //   年 	月 	日

            String price = auditPastOrder.getOweFee();// 获取数据库金额  (分)
            confirmation.setPayPike(price);               // 通行费
            Double money = Double.parseDouble(price)/100;// 转换成元

            BigDecimal numberOfMoney = new BigDecimal(money);
            String s = NumberToCN.number2CNMontrayUnit(numberOfMoney);
            confirmation.setPayTotal(s);           //  合计：（大写）
            confirmation.setPayConductor("收费公路联网结算管理中心");       //    处理人：
            confirmation.setPayProcessingUnit("北京速通科技有限公司");    //   处理单位：

            log.info("===== 调用确认单 !!! 第三方接口====== ");
            return Result.ok().data("confirmationEntity",confirmation);
        //}else{
          //  log.info("===== 调用确认单 !!! 数据库====== ");
          //  confirmationEntity.setPayDriverName("");     // auditPastOrder.getCustomerName() 驾驶员姓名  (用户输入)
          //  confirmationEntity.setPayDriverNumber("");   // certificateNo驾驶员身份证号  (用户输入)
          //  return Result.ok().data("confirmationEntity",confirmationEntity);
        //}
    }



    @RequestMapping("a")
    public void a(){
        Map<String, Object> map = transferIssue.transferQueryETCCarInfo("","","1101060600000206","10e3f039506daac220c0ad8647830b61");
        Map<String, Object> map2 =(Map<String, Object>) map.get("data");
        String respCode =(String) map2.get("RespCode"); // 返回码
        String respMsg =(String) map2.get("RespMsg"); // 返回信息说明
        String vehPlateNo =(String) map2.get("VehPlateNo"); // 车牌号码
        String vehColorTypeCode =(String) map2.get("VehColorTypeCode"); // 车牌颜色
        String vehOwnerName =(String) map2.get("VehOwnerName"); // 车辆所有人姓名
        String engineNo =(String) map2.get("EngineNo"); // 发动机号
        String vehClassCode =(String) map2.get("VehClassCode"); // 车辆类别
        String vehTypeCode =(String) map2.get("VehTypeCode"); // 车型
        String truckTypeCode =(String) map2.get("TruckTypeCode"); // 车种
        String cardNo =(String) map2.get("CardNo"); // ETC卡编号
        String userTypeCode =(String) map2.get("UserTypeCode"); // 用户类型
        String certificateNoTrprCode =(String) map2.get("CertificateNoTrprCode"); // 证件类型
        String certificateNo =(String) map2.get("CertificateNo"); // 证件号码
        System.out.println(map2);
        System.out.println(engineNo);
    }



    /**
     * @description: 高速公路通行费补费确认单  保存
     * @auther: zhaodengzhuang
     * @date: 2019/12/19
     */
    @ApiOperation("确认单 回调")
    @PostMapping("/doUpdateConfirmationInfo")
    public Result doUpdateConfirmationInfo(@RequestBody ConfirmationEntity confirmationEntity){
        log.info("保存确认单的信息 : " + confirmationEntity);
        confirmationEntity.setPayDate(confirmationEntity.getPayDate().substring(0,10));
        confirmationEntity.setPayDisposeTime(confirmationEntity.getPayDisposeTime().substring(0,10));
        Boolean result = repairfeeService.doUpdateConfirmationInfo(confirmationEntity);
        if(result){
            log.info("===== 确认单 回调  成功====== ");
            return Result.ok().data("result",result);
        }else{
            log.info("===== 确认单 回调  失败====== ");
            return Result.error();
        }
    }

    /**
     * 测试多线程
     */
    /*@RequestMapping("test")
    public synchronized void test(){
        //循环遍历300次，创建300个线程
        for (int i = 0; i < 300; i++) {
            threadPoolInterface.test();
        }
    }*/


    /**
     * 用户ID存入Session
     * @param userId
     * @param request
     */
    @RequestMapping("setSession")
    public Result setSession(String userId, HttpServletRequest request){
        if((String)request.getSession().getAttribute("userId") == null){
            request.getSession().setAttribute("userId",userId);
            System.out.println("<<<<<<<<<    Session保存存的ID为 : "+ userId);
            log.info("<<<<<<<<<    Session保存存的ID为 : "+ userId);
            return Result.ok();
        }else{
            System.out.println("<<<<<<<<<    Session中已有编号为:"+userId+"的用户ID");
            log.info("<<<<<<<<<    Session中已有编号为:"+userId+"的用户ID");
            return Result.ok();
        }
    }




}
