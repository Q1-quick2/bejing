package com.sutong.pay.controller.app.confiletool;

import com.alibaba.fastjson.JSONObject;
import com.sutong.bjstjh.entity.AppAuditInvoiceInformationModel;
import com.sutong.bjstjh.entity.AppAuditPastOrderModel;
import com.sutong.bjstjh.entity.AuditPaybackFeeFlowModel;
import com.sutong.transfer.InvoiceRequestInfoList;
import com.sutong.transfer.TransList;
import com.sutong.transfer.TransferInvoice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by nt on 2020/1/6.
 */
@Component
public class InvoiceMouthPieceController {


    @Autowired
    private TransferInvoice invoice;
//    @Autowired
//    private InvoiceLogUtil log;

    /**
     * 调用稽查补缴交易同步接口（B20100173）
     * @param model 查询出的非历史补费流水实体类
     * @return
     */
    public Map<String,Object> getInvoiceSynchronized(AuditPaybackFeeFlowModel model) throws Exception{

        //乐速通用户编号-必填
        String appUserNo = model.getUid();
        //交易编号 -必填 (最大长度19且数字类型)
        String recordNo = model.getPaybackFeeId();
        //车牌号-必填
        String vehplateno = model.getVehicleNumber();
        //车牌颜色编码-必填 (数字)
        String vehplatenoColorCode = model.getVehicleColour();
        //收款时间-必填 (格式必须："yyyy-MM-dd HH:mm:ss")
        String receiveTime = getDateStringfomat(model.getEndPaymenttime());
        //收款金额-必填(单位：分)
        String receivableAmount = model.getEndActpayamt().toString();
        //支付单位-必填
        String payOrganizationName = "元";//Test
        //记录时间（创建时间）-必填 (格式必须："yyyy-MM-dd HH:mm:ss")
        String recordTime = getDateStringfomat(model.getFlowCreateTime());
        // ETC卡号-必填(16位 接口方校验通过的："1901230000001001"/"0808220000098210")
        String cardNo = model.getEtcCardId();
        //固定值
        String operationTypecode = "10000";
        //固定值
        String channel = "10";

        Map<String,Object> map =
                invoice.transactionRecordSync(appUserNo, recordNo,vehplateno, vehplatenoColorCode,
                        receiveTime, receivableAmount,payOrganizationName, recordTime,cardNo,operationTypecode,
                        channel);
//        log.insertLog("173","AppUserNo,Channel,RecordNo,OperationTypecode,Vehplateno,VehplatenoColorCode,ReceiveTime,ReceivableAmount,PayOrganizationName,RecordTime,CardNo,OrderSerialNo","ReturnCode,ReturnMsg");
        return map;
    }
    /**
     * 调用稽查补缴交易同步接口（B20100173）
     * @param model
     * @return
     */
    public Map<String,Object> getInvoiceSynchronized(AppAuditPastOrderModel model) throws Exception{

        //乐速通用户编号-必填
        String appUserNo = model.getUserUid();
        //交易编号 -必填 (最大长度19且数字类型)
        String recordNo = model.getPayBackFeeIdPast();
        //车牌号-必填
        String vehplateno = model.getTransVehicleId();
        //车牌颜色编码-必填 (数字)
        String vehplatenoColorCode = model.getVehicleColour();
        //收款时间-必填 (格式必须："yyyy-MM-dd HH:mm:ss")
        String receiveTime = getDateStringfomat(model.getEndPaymenttime());
        //收款金额-必填 (单位：分)
        String receivableAmount = model.getEndActpayamt().toString();
        //支付单位-必填
        String payOrganizationName = "元";
        //记录时间（创建时间）-必填 (格式必须："yyyy-MM-dd HH:mm:ss")
        String recordTime = getDateStringfomat(model.getFlowCreateTime());
        // ETC卡号-必填 (16位 接口方校验通过的："1901230000001001"/"0808220000098210");
        String cardNo = model.getTransCardId();
        //固定值
        String operationTypecode = "10000";
        //固定值
        String channel = "10";

        Map<String,Object> map =
                invoice.transactionRecordSync(appUserNo, recordNo,vehplateno, vehplatenoColorCode,
                        receiveTime, receivableAmount,payOrganizationName, recordTime,cardNo,operationTypecode,
                        channel);
//        log.insertLog("173","AppUserNo,Channel,RecordNo,OperationTypecode,Vehplateno,VehplatenoColorCode,ReceiveTime,ReceivableAmount,PayOrganizationName,RecordTime,CardNo,OrderSerialNo","ReturnCode,ReturnMsg");
        return map;
    }
    /**
     * 调用根据交易编号查询交易信息（B20100174）接口
     * @Param uid 用户号
     * @Param feeId
     * @return
     */
    public Map<String,Object> getInvoiceFindTrans(String uid,String feeId){
        //APP用户号 可为空
        String appUserNo = uid;
        //渠道 必填 (固定值)
        String channel  ="10";
        //记录编号 173接口recordno 必填
        String recordNo = feeId;
        //10000 (固定值)
        String operationTypecode = "10000";

        Map<String,Object> map =
                invoice.transactionFindTrans(appUserNo, channel,recordNo, operationTypecode);
//        log.insertLog("174","AppUserNo,Channel,RecordNo,OperationTypecode",
//                "ReturnCode,ReturnMsg,TransId,RecordNo,ReceiveTime,ReceivableAmount,PayOrganization,RecordTime,Vehplateno,VehplateColorcode,CardNo,Remarks,DealStatusCode,SurplusAmount,InvoiceFinishFlag,SendFlag,CanSendFlag,OperationTypecode,Accountno,IssueUserno,TransSubTypecode,TransSourceTypecode");
        return map;
    }

    /**
     * 调用发票申请接口（B20100023）
     * @param model 查询出的非历史补费流水实体类
     * @param requestmap 请求参数集合
     * @param jsonObjec 查询交易接口的交易信息
     * @return
     */
    public Map<String,Object> getInvoiceParametrar(AuditPaybackFeeFlowModel model, Map<String,String> requestmap, JSONObject jsonObjec){

         /*
          * 根据查询的信息生成发票请求信息列表
          */
        List<InvoiceRequestInfoList> invoList = new ArrayList<>();//发票请求信息列表
        for (int i = 0; i < 1; i++) {

            InvoiceRequestInfoList invo = new InvoiceRequestInfoList();
            // 可抵扣标志--必填*/
            invo.setDeductionFlag(1);
            // 发票抬头ID*/
//            invo.setBuyerId("");
            // 购买方名称--必填*///"test1"
            invo.setBuyerName(requestmap.get("buyerName"));
            // 纳税人识别号*/
            invo.setBuyerTaxCode(requestmap.get("buyerRatepayingNumber"));
            // 电话*/
            invo.setBuyerTelephoneNo(requestmap.get("buyerPhone"));
            // *地址*/
            invo.setBuyerAddress(requestmap.get("purchasersaddress"));
            // 开户银行*/
            invo.setBuyerBankName(requestmap.get("buyerBankNumber"));
            // 银行账号*/
//            invo.setBuyerBankAccountNo(requestmap.get("buyerBankNumber"));
            // 票面备注*/
            invo.setInvoiceRemark("此发票是当前补费交易发票!");
            // 车牌号
            invo.setVehPlateNo(model.getVehicleNumber());
            // 车牌颜色 (数字)
            invo.setVehPlateColor(Integer.valueOf(model.getVehicleColour()));

            List<TransList> transList = new ArrayList<>();
            for (int j = 0; j < 1; j++) {
               /*
                * 生成多个交易列表信息
                */
                TransList trans = new TransList();
                if (jsonObjec.get("TransId") != null && !"".equals(jsonObjec.get("TransId"))){
                    /** 交易唯一标识--必填*/
                    trans.setTransNo(jsonObjec.get("TransId").toString());
                }else{/** 交易唯一标识--必填*/
                    trans.setTransNo("");
                }
                // 交易源类型编码 (固定值)
                trans.setTransSourceTypeCode(9);
                // 开票金额 --必填(单位：分)
                trans.setInvoiceAmount(model.getEndPayorderno());
                // 交易金额--必填(单位：分)
                trans.setTransAmount(model.getEndPayorderno());
                transList.add(trans);
            }
            invo.setTransList(transList);
            invoList.add(invo);
        }

        Map<String,Object> map =
                invoice.transactionMakeInvoice(model.getUid(),//APP用户号"123456111"
                        "","",//公众号中用户ID//公众号ID
                        3,//渠道-必填
                        requestmap.get("personalMailBox"),//推送的邮箱地址
                        requestmap.get("personalPhone"),//短信通知手机号
                        invoList
                );
//        log.insertLog("23","AppUserNo,OpenId,AppId,Channel,EmailAddr,TelephoneNo,InvoiceRequestInfoList[{-DeductionFlag-BuyerId-BuyerName-BuyerTaxCode-BuyerTelephoneNo-BuyerAddress-BuyerBankName-BuyerBankAccountNo-InvoiceRemark-VehPlateNo-VehPlateColor-TransList[--TransNo--TransSourceTypeCode --InvoiceAmount--TransAmount]}]",
//                "ReturnCode,ReturnMsg,OrderNo");
        return map;
    }

    /**
     * 调用发票申请接口（B20100023）
     * @param requestmap
     * @return
     */
    public Map<String,Object> getInvoiceMake(AppAuditPastOrderModel model, Map<String,String> requestmap, JSONObject jsonObjec){

         /*
          * 根据查询的信息生成发票请求信息列表
          */
        List<InvoiceRequestInfoList> invoList = new ArrayList<>();
        for (int i = 0; i < 1; i++) {

            InvoiceRequestInfoList invo = new InvoiceRequestInfoList();
            // 可抵扣标志--必填
            invo.setDeductionFlag(0);
            // 发票抬头ID
            invo.setBuyerId("");
            // 购买方名称--必填
            invo.setBuyerName(requestmap.get("buyerName"));
            // 纳税人识别号
            invo.setBuyerTaxCode(requestmap.get("buyerRatepayingNumber"));
            // 电话
            invo.setBuyerTelephoneNo(requestmap.get("buyerPhone"));
            // 地址
            invo.setBuyerAddress(requestmap.get("purchasersaddress"));
            // 开户银行
            invo.setBuyerBankName(requestmap.get("buyerBankNumber"));
            // 银行账号
            invo.setBuyerBankAccountNo(requestmap.get("buyerBankNumber"));
            // 票面备注
            invo.setInvoiceRemark("此发票是历史补费交易发票!");
            // 车牌号
            invo.setVehPlateNo(model.getTransVehicleId());
            // 车牌颜色(数字)
            invo.setVehPlateColor(Integer.valueOf(model.getVehicleColour()));

            List<TransList> transList = new ArrayList<>();
            for (int j = 0; j < 1; j++) {
               /*
                * 生成多个交易列表信息
                */
                TransList trans = new TransList();

                if (jsonObjec.get("TransId") != null && !"".equals(jsonObjec.get("TransId"))){
                    /** 交易唯一标识--必填*/
                    trans.setTransNo(jsonObjec.get("TransId").toString());
                }else{/** 交易唯一标识--必填*/
                    trans.setTransNo("");
                }
                // 交易源类型编码 (固定值)
                trans.setTransSourceTypeCode(9) ;
                // 开票金额 --必填(单位：分)
                trans.setInvoiceAmount(model.getEndActpayamt().toString());
                // 交易金额--必填 (单位：分)
                trans.setTransAmount(model.getEndActpayamt().toString());
                transList.add(trans);
            }
            invo.setTransList(transList);
            invoList.add(invo);
        }

        Map<String,Object> map =
                invoice.transactionMakeInvoice(model.getUserUid(),//APP用户号
                        "","",//公众号中用户ID//公众号IDd
                        3,//渠道-必填
                        requestmap.get("personalMailBox"),//推送的邮箱地址
                        requestmap.get("personalPhone"),//短信通知手机号
                        invoList
                );
//        log.insertLog("23","AppUserNo,OpenId,AppId,Channel,EmailAddr,TelephoneNo,InvoiceRequestInfoList[{-DeductionFlag-BuyerId-BuyerName-BuyerTaxCode-BuyerTelephoneNo-BuyerAddress-BuyerBankName-BuyerBankAccountNo-InvoiceRemark-VehPlateNo-VehPlateColor-TransList[--TransNo--TransSourceTypeCode --InvoiceAmount--TransAmount]}]",
//                "ReturnCode,ReturnMsg,OrderNo");
        return map;
    }

    /**
     * 根据交易查询发票信息（B20100055）
     * @param transIds 174接口信息交易编码
     * @return
     */
    public Map<String,Object> getInvoiceMessage(String transIds){
        //交易唯一标识
        String transId = transIds;
        //交易源类型编码 (固定值)
        String transSourceTypecode = "9";
        Map<String,Object> map =
                invoice.transactionInvoiceMessage(transId, transSourceTypecode);
//        log.insertLog("55","TransId,TransSourceTypecode",
//                "ReturnCode,ReturnMsg,InvoiceInfoList-BuyerAddress-BuyerBankAccountNo-BuyerBankName-BuyerTelephoneNo-Deduction-Email-FailReason-InvoiceApplyNo-InvoiceExampleTypecode-InvoiceSerialNo-Invoicer-MachineNo-PassFeeFlag-Payee-RegisterTime-RelatedTransFlag-Remarks-Reviewer-SellerAddress-SellerBankAccountNo-SellerBankName-SellerId-SellerTelephoneNo-SendCount-TaxMode-UserNo-InvoiceCode-InvoiceNo-InvoiceValidateCode-InvoiceTypeCode-InvoiceStatusCode-InvoiceChangeFlag-InvoiceTime-TotalTax-TotalAmount-InvoiceAmount-BuyerName-BuyerTaxCode-SellerName-SellerTaxCode-DownloadUrl-DeductFlag,-InvoiceItemList--FreeTaxFlag--ItemId--ItemName--ItemType--InvoiceSerialNo--MetricUnit--Specification--UnitPrice--Quantity--Amount--TaxRate--TaxAmount--GoodsId--GoodsCode--PreferentialFlag--PassEndDate--PassStartDate--VadTaxSpecial--VehTypeCode--VehplateColor--VehplateNo");
        return map;
    }

    /**
     * 发票换开手动调用接口（B20100065）
     * @param userNo 用户号
     * @param requestmap 请求集合map
     * @param jsonObject 55接口查询内容
     * @return
     */
    public Map<String,Object> getChangeInvoice(
            String userNo,Map<String,String> requestmap,JSONObject jsonObject){
        // 乐速通用户号(Uid)
        String lstUserNo = userNo;
        //渠道号(固定值) --必填
        String channel = "10";
        String appId = "";//公众号ID
        String openId = "";//公众号中用户ID
        //发票代码 --必填
        String invoiceCode = jsonObject.get("InvoiceCode").toString();
        //发票号码 --必填
        String invoiceNo = jsonObject.get("InvoiceNo").toString();
        //申请时间 --必填
        String applyTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        //消费者邮箱 --必填
        String email = requestmap.get("personalMailBox");
        //换开原因
        String changeReason = requestmap.get("changeReason");
        //购买方Id
        String buyerId = requestmap.get("");
        //购买方名称 --必填
        String buyerName = requestmap.get("buyerName");
        //购买方纳税人识别号(企业类型时为必输)
        String payerTaxCode = requestmap.get("buyerRatepayingNumber");
        //购买方地址
        String buyerAddress = requestmap.get("purchasersAddress");
        //开户银行
        String buyerBankName = requestmap.get("buyerBankNumber");
        //购买方银行账号
        String buyerBankAccountNo = requestmap.get("");
        //电话
        String telephoneNo = requestmap.get("buyerPhone");
        //购买方类型编码 --必填
        int buyerTypeCode = Integer.valueOf(requestmap.get("certificateType"));
        //默认标识(默认1，非默认0) --必填
        int defaultFlag = 1;

        Map<String,Object> map =
                invoice.transactionChangeInvoice(lstUserNo, channel, appId, openId, invoiceCode,
                        invoiceNo, applyTime, email, changeReason, buyerId,
                        buyerName, payerTaxCode, buyerAddress, buyerBankName,
                        buyerBankAccountNo, telephoneNo, buyerTypeCode, defaultFlag);
//        log.insertLog("65","LstUserNo,Channel,AppId,OpenId,InvoiceCode,InvoiceNo,ApplyTime,Email,ChangeReason,BuyerId,BuyerName,PayerTaxCode,BuyerAddress,BuyerBankName,BuyerBankAccountNo,TelephoneNo,BuyerTypeCode,DefaultFlag",
//                "ReturnCode,ReturnMsg,OrderNo");
        return map;
    }

    /**
     * 根据请求和查询实体类生成发票信息类
     * @param flowmodel 当前补费流水实体类
     * @param orderModel 历史补费实体类
     * @param requestmap 请求参数map
     * @param transId 同步接口交易编码
     * @param flowororder 是当前还是历史补费
     * @return
     */
    public AppAuditInvoiceInformationModel getAppAuditInvoiceInformation(AuditPaybackFeeFlowModel flowmodel, AppAuditPastOrderModel orderModel,
                                                                         Map<String,String> requestmap, String transId, boolean flowororder){
        AppAuditInvoiceInformationModel model = new AppAuditInvoiceInformationModel();
        model.setTransId(transId);
        model.setChangeNumbers("0");
        if (flowororder){
            if (flowmodel != null) {
                model.setFlowId(flowmodel.getFlowId());
                model.setOrderId(flowmodel.getOrderId());//工单编号
                model.setOrderNumber(flowmodel.getPaybackFeeId()); //订单号
                model.setCommodityOrderNo(flowmodel.getEndPayorderno()); //第三方支付订单号
                model.setFeeTransTime(flowmodel.getEndPaymenttime());//补费时间
                model.setInvoiceVehicleId(flowmodel.getVehicleId());//车辆车牌号+颜色
                if (flowmodel.getEndActpayamt() != null && !"".equals(flowmodel.getEndActpayamt())){
                    model.setBuyerPaybackTotal(
                            (flowmodel.getEndActpayamt().multiply(new BigDecimal("0.01"))).toString());//购买方补费合计 单位:元
                }else{
                    model.setBuyerPaybackTotal("0");
                }
            }
        }else{
            if (orderModel != null) {
                model.setObuId(orderModel.getTransObuId());//OBU编号
                model.setFlowId(orderModel.getFlowId());
                model.setOrderNumber(orderModel.getPayBackFeeIdPast()); //订单号
                model.setCommodityOrderNo(orderModel.getEndPayorderno()); //第三方支付订单号
                model.setFeeTransTime(orderModel.getEndPaymenttime());//补费时间
                model.setInvoiceVehicleId(orderModel.getTransVehicleId() + "_" + orderModel.getVehicleColour());//车辆车牌号+颜色
                if (orderModel.getEndActpayamt() != null && !"".equals(orderModel.getEndActpayamt())) {
                    model.setBuyerPaybackTotal(
                            (orderModel.getEndActpayamt().multiply(new BigDecimal("0.01"))).toString());//购买方补费合计 单位:元
                }else {
                    model.setBuyerPaybackTotal("0");
                }

            }
        }



        model.setBuyerName(requestmap.get("buyerName"));//购买方名称
        model.setBuyerRatepayingNumber(requestmap.get("buyerRatepayingNumber"));//购买方纳税识别号
        model.setBuyerPhone(requestmap.get("buyerPhone"));//购买方电话
        model.setPurchasersAddress(requestmap.get("purchasersaddress"));////购买方地址
        model.setBuyerBankNumber(requestmap.get("buyerBankNumber")); //购买方开户行及账号
        model.setInvoiceType(requestmap.get("invoiceType"));//发票类型
        model.setPersonalMailBox(requestmap.get("personalMailBox")); //收件人邮箱
        model.setPersonalPhone(requestmap.get("personalPhone"));//收件人电话
        model.setPersonalName(requestmap.get("personalName"));//收件人姓名
        model.setPersonalDetailedAdress(requestmap.get("personalDetailedAdress"));//收件人地址


        model.setSellerPhone("###");//销售方联系方式
        model.setSellerRatepayingNumber("###");//销售方纳税识别号
        model.setSellerBankNumber("###"); //销售方开户行及账号
        model.setPayee("###");//收款人
        model.setRevier("###"); //复核
        model.setDrawer("###"); //开票人
        model.setOpenInvoiceTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));//开票日期

        return  model;
    }


    /**
     * 将时间字符串转换成固定格式(yyyy-MM-dd HH:mm:ss)的字符串
     * @param dateString
     */
    public String getDateStringfomat(String dateString){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        StringBuffer dates = new StringBuffer();
        if (dateString != null && !"".equals(dateString)){
            dateString = dateString.replace("/","").replace("-","").replace(" ","");
            int length = dateString.length();
            if (length > 14){
                dateString = dateString.substring(0,14);
            }

            for (int i = 0; i < 14; i++) {
                if (i==4 || i == 6){
                    dates.append("-");
                }
                if (length < 14 && i == 8){
                    dates.append(" 00:00:00");
                    break;
                }else{
                    if (i == 8){
                        dates.append(" ");
                    }
                    if (i== 10 || i == 12){
                        dates.append(":");
                    }
                }

                dates.append(dateString.charAt(i));
            }
        }else {
            dates.append(sdf.format(new Date()).toString());
        }

        return dates.toString();
    }
}
