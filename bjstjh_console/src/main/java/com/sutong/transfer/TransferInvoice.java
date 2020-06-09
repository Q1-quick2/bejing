package com.sutong.transfer;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName: TransferInvoice
 * @author： pengjien
 * @date: 2019/12/27 19:58
 * @Description: 发票相关接口
 */
@Component
public class TransferInvoice {
    Logger log = LoggerFactory.getLogger(TransferInvoice.class);
    @Value("${invoice.url}")
    private String invoiceUrl;

    /**
     * @description:稽查补缴交易同步接口（B20100173）
     * @auther: pengjien
     * @date: 2019/12/27 20:03
     * @Param AppUserNo: 乐速通用户编号-必填
     * @Param RecordNo: 交易编号 -必填
     * @Param Vehplateno: 车牌号-必填
     * @Param VehplatenoColorCode: 车牌颜色编码-必填
     * @Param ReceiveTime: 收款时间-必填
     * @Param ReceivableAmount: 收款金额-必填
     * @Param PayOrganizationName:支付单位-必填
     * @Param RecordTime:记录时间（创建时间）-必填
     * @Param CardNo: ETC卡号-必填
     * @return: java.util.Map<java.lang.String,java.lang.Object>
     **/
        public Map<String,Object> transactionRecordSync(String AppUserNo, String RecordNo,String Vehplateno,
                                                        String VehplatenoColorCode,String ReceiveTime,
                                                        String ReceivableAmount,String PayOrganizationName,
                                                        String RecordTime,String CardNo,String OperationTypecode,
                                                        String Channel){
            Map<String,Object> resultMap = new HashMap<>();
            CloseableHttpClient httpClient = HttpClients.createDefault();
            JSONObject requestJson = new JSONObject();
            requestJson.put("AppUserNo",AppUserNo);
            requestJson.put("RecordNo",RecordNo);
            requestJson.put("Vehplateno",Vehplateno);
            //每次请求必带值
            requestJson.put("VehplatenoColorCode",VehplatenoColorCode);
            requestJson.put("ReceiveTime",ReceiveTime);
            requestJson.put("ReceivableAmount",ReceivableAmount);
            requestJson.put("ReceiveTime",ReceiveTime);
            requestJson.put("PayOrganizationName",PayOrganizationName);
            requestJson.put("RecordTime",RecordTime);
            requestJson.put("CardNo",CardNo);
            requestJson.put("OperationTypecode",OperationTypecode);
            requestJson.put("Channel",Channel);
            String requestURL = invoiceUrl
                    //"http://172.20.53.134:38080/"
                    +"est/est173.xhtml";
//            HttpPost httpPost = new HttpPost(requestURL);
//            RequestConfig requestConfig = RequestConfig.custom().setConnectionRequestTimeout(180 * 1000)
//                    .setConnectTimeout(180 * 1000).setSocketTimeout(180 *1000).setRedirectsEnabled(false).build();
//            httpPost.setConfig(requestConfig);
//            httpPost.setHeader("Content-Type","aplication/json");
//            httpPost.setEntity(new StringEntity(requestJson.toJSONString(), ContentType.create("application/json","GBK")));
            Map<String, Object> stringObjectMap = httpPost(resultMap, httpClient, requestJson,requestURL);
            return stringObjectMap;
        }

    /**
     * @description:根据交易编号查询交易信息（B20100174）
     * @auther: liyan
     * @date: 2019/12/29 16:39
     * @Param AppUserNo: 乐速通用户编号-必填
     * @Param RecordNo: 交易编号 -必填
     * @Param Vehplateno: 车牌号-必填
     * @Param VehplatenoColorCode: 车牌颜色编码-必填
     * @Param ReceiveTime: 收款时间-必填
     * @Param ReceivableAmount: 收款金额-必填
     * @Param PayOrganizationName:支付单位-必填
     * @Param RecordTime:记录时间（创建时间）-必填
     * @Param CardNo: ETC卡号-必填
     * @return: java.util.Map<java.lang.String,java.lang.Object>
     **/
    public Map<String,Object> transactionFindTrans(String AppUserNo, String Channel,
                                                          String RecordNo, String OperationTypecode){
        Map<String,Object> resultMap = new HashMap<>();
        CloseableHttpClient httpClient = HttpClients.createDefault();
        JSONObject requestJson = new JSONObject();
        requestJson.put("AppUserNo",AppUserNo);
        requestJson.put("RecordNo",RecordNo);
        requestJson.put("Channel",Channel);
        requestJson.put("OperationTypecode",OperationTypecode);
        String requestURL = invoiceUrl
                //"http://172.20.53.134:38080/"
                +"est/est174.xhtml";
        Map<String, Object> stringObjectMap = httpPost(resultMap, httpClient, requestJson,requestURL);
        return stringObjectMap;
    }

    /**
     * @description:开票申请接口（B20100023）
     * @auther: liyan
     * @date: 2019/12/28 15:08
     * @Param AppUserNo: APP用户号
     * @Param OpenId: 公众号中用户ID
     * @Param AppId: 公众号ID
     * @Param Channel: 渠道-必填
     * @Param EmailAddr: 推送的邮箱地址
     * @Param TelephoneNo: 短信通知手机号
     * @Param InvoiceRequestInfoList:发票请求信息列表-必填
     * @return: java.util.Map<java.lang.String,java.lang.Object>
     **/
    public Map<String,Object> transactionMakeInvoice(String AppUserNo, String OpenId, String AppId,
                                                            int Channel, String EmailAddr, String TelephoneNo,
                                                            List<InvoiceRequestInfoList> InvoiceRequestInfoList){
        Map<String,Object> resultMap = new HashMap<>();
        CloseableHttpClient httpClient = HttpClients.createDefault();
        JSONObject requestJson = new JSONObject();
        requestJson.put("AppUserNo",AppUserNo);
        requestJson.put("OpenId",OpenId);
        requestJson.put("AppId",AppId);
        //每次请求必带值
        requestJson.put("Channel",Channel);
        requestJson.put("EmailAddr",EmailAddr);
        requestJson.put("TelephoneNo",TelephoneNo);
        List<JSONObject> InvoiceRequestInfo = new ArrayList<JSONObject>();
        JSONObject json = new JSONObject();
        List<JSONObject> InvoiceRequestInfo1 = new ArrayList<JSONObject>();
        JSONObject json1 = new JSONObject();
        for(InvoiceRequestInfoList InvoiceRequestInfoL:InvoiceRequestInfoList){
            json.put("DeductionFlag",InvoiceRequestInfoL.getDeductionFlag());
            json.put("BuyerId",InvoiceRequestInfoL.getBuyerId());
            json.put("BuyerName",InvoiceRequestInfoL.getBuyerName());
            json.put("BuyerTaxCode",InvoiceRequestInfoL.getBuyerTaxCode());
            json.put("BuyerTelephoneNo",InvoiceRequestInfoL.getBuyerTelephoneNo());
            json.put("BuyerAddress",InvoiceRequestInfoL.getBuyerAddress());
            json.put("BuyerBankName",InvoiceRequestInfoL.getBuyerBankName());
            json.put("BuyerBankAccountNo",InvoiceRequestInfoL.getBuyerBankAccountNo());
            json.put("InvoiceRemark",InvoiceRequestInfoL.getInvoiceRemark());
            json.put("VehPlateNo",InvoiceRequestInfoL.getVehPlateNo());
            json.put("VehPlateColor",InvoiceRequestInfoL.getVehPlateColor());
            for(TransList transList:InvoiceRequestInfoL.getTransList()){
                json1.put("TransNo",transList.getTransNo());
                json1.put("TransSourceTypeCode",transList.getTransSourceTypeCode());
                json1.put("InvoiceAmount",transList.getInvoiceAmount());
                json1.put("TransAmount",transList.getTransAmount());
                InvoiceRequestInfo1.add(json1);
            }
            json.put("TransList",InvoiceRequestInfo1);
            InvoiceRequestInfo.add(json);
        }
        requestJson.put("InvoiceRequestInfoList",InvoiceRequestInfo);


//            String str = "{\n" +
//                    "    \"AppUserNo\": \"123456111\",\n" +
//                    "    \"EmailAddr\": \"907941660@qq.com\",\n" +
////                    "    \"AppId\": \"京A12231\",\n" +
//                    "    \"Channel\": 4,\n" +
//                    "    \"OpenId\": \"435545454351243241\",\n" +
//                    "    \"InvoiceRequestInfoList\": [\n" +
//                    "        {\n" +
//                    "            \"DeductionFlag\": 0,\n" +
////                    "            \"BuyerId\": \"1\",\n" +
//                    "            \"BuyerName\": \"test1\",\n" +
////                    "            \"BuyerTaxCode\": \"12345\",\n" +
////                    "            \"BuyerTelephoneNo\": \"17600001009\",\n" +
////                    "            \"BuyerAddress\": \"北京市丰台区\",\n" +
////                    "            \"BuyerBankName\": \"中国银行\",\n" +
////                    "            \"BuyerBankAccountNo\": \"1243533\",\n" +
////                    "            \"InvoiceRemark\": \"\",\n" +
//                    "            \"VehPlateNo\": \"京A12231\",\n" +
//                    "            \"VehPlateColor\": 0,\n" +
//                    "            \"TransList\": [\n" +
//                    "                {\n" +
//                    "                    \"TransNo\": \"78\",\n" +
//                    "                    \"TransSourceTypeCode\": 9,\n" +
//                    "                    \"InvoiceAmount\": \"200\",\n" +
//                    "                    \"TransAmount\": \"200\"\n" +
//                    "                }\n" +
//                    "            ]\n" +
//                    "        }\n" +
//                    "    ],\n" +
//                    "    \"TelephoneNo\": \"18514358681\"\n" +
//                    "}";
//            JSONObject jsonstr = JSONObject.parseObject(str);


        String requestURL = invoiceUrl
                //"http://172.20.53.134:38080/"
                +"est/est23.xhtml";
        Map<String, Object> stringObjectMap = httpPost(resultMap, httpClient, requestJson,requestURL);
        return stringObjectMap;
    }
    
    /**
     * @description:根据交易查询发票信息（B20100055）
     * @auther: liyan
     * @date: 2020/1/2 14:03
     * @param: [TransId 交易记录编号, TransSourceTypecode交易源类型编码]
     * @return: java.util.Map<java.lang.String,java.lang.Object>
     **/
    public Map<String,Object> transactionInvoiceMessage(String TransId, String TransSourceTypecode){
        Map<String,Object> resultMap = new HashMap<>();
        CloseableHttpClient httpClient = HttpClients.createDefault();
        JSONObject requestJson = new JSONObject();
        requestJson.put("TransId",TransId);
        requestJson.put("TransSourceTypecode",TransSourceTypecode);
        String requestURL = invoiceUrl
//                 "http://172.20.53.134:38080/"
                +"est/est55.xhtml";
        Map<String, Object> stringObjectMap = httpPost(resultMap, httpClient, requestJson,requestURL);
        return stringObjectMap;
    }
    
    /**
     * @description: 发票换开手动调用接口（B20100065）
     * @auther: liyan
     * @date: 2020/1/2 14:11
     * @param: [LstUserNo 乐速通用户号, Channel 渠道号, AppId 公众号ID, OpenId 公众号中用户ID, InvoiceCode 发票代码, InvoiceNo 发票号码,
     * ApplyTime 申请时间, Email 消费者邮箱, ChangeReason 换开原因, BuyerId 购买方Id, BuyerName 购买方名称, PayerTaxCode 购买方纳税人识别号,
     * BuyerAddress 购买方地址, BuyerBankName 开户银行, BuyerBankAccountNo 购买方银行账号, TelephoneNo 电话, BuyerTypeCode 购买方类型编码,
     * DefaultFlag 默认标识]
     * @return: java.util.Map<java.lang.String,java.lang.Object>
     **/
    public Map<String,Object> transactionChangeInvoice(String LstUserNo, String Channel, String AppId, String OpenId, String InvoiceCode,
                                                              String InvoiceNo, String ApplyTime, String Email, String ChangeReason, String BuyerId,
                                                              String BuyerName, String PayerTaxCode, String BuyerAddress, String BuyerBankName,
                                                              String BuyerBankAccountNo, String TelephoneNo, int BuyerTypeCode, int DefaultFlag){
        Map<String,Object> resultMap = new HashMap<>();
        CloseableHttpClient httpClient = HttpClients.createDefault();
        JSONObject requestJson = new JSONObject();
        requestJson.put("LstUserNo",LstUserNo);
        requestJson.put("Channel",Channel);
        requestJson.put("AppId",AppId);
        requestJson.put("OpenId",OpenId);
        requestJson.put("InvoiceCode",InvoiceCode);
        requestJson.put("InvoiceNo",InvoiceNo);
        requestJson.put("ApplyTime",ApplyTime);
        requestJson.put("Email",Email);
        requestJson.put("ChangeReason",ChangeReason);
        requestJson.put("BuyerId",BuyerId);
        requestJson.put("BuyerName",BuyerName);
        requestJson.put("PayerTaxCode",PayerTaxCode);
        requestJson.put("BuyerAddress",BuyerAddress);
        requestJson.put("BuyerBankName",BuyerBankName);
        requestJson.put("BuyerBankAccountNo",BuyerBankAccountNo);
        requestJson.put("TelephoneNo",TelephoneNo);
        requestJson.put("BuyerTypeCode",BuyerTypeCode);
        requestJson.put("DefaultFlag",DefaultFlag);
        String requestURL = invoiceUrl
//                "http://172.20.53.134:38080/"
                +"est/est65.xhtml";
        Map<String, Object> stringObjectMap = httpPost(resultMap, httpClient, requestJson,requestURL);
        return stringObjectMap;
    }
    
    /**
     * @description: 发票手动红冲申请（B20100066）--测试用
     * @auther: liyan
     * @date: 2020/1/3 16:32
     * @param: [AppUserNo APP用户号, Channel 渠道, InvoiceInfoList 发票信息列表]
     * @return: java.util.Map<java.lang.String,java.lang.Object>
     **/
//    public static Map<String,Object> transactionInvoiceHC(String AppUserNo, String Channel, List<InvoiceInfoList> InvoiceInfoList){
//        Map<String,Object> resultMap = new HashMap<>();
//        CloseableHttpClient httpClient = HttpClients.createDefault();
//        JSONObject requestJson = new JSONObject();
//        requestJson.put("LstUserNo",AppUserNo);
//        requestJson.put("Channel",Channel);
//        List<JSONObject> InvoiceList = new ArrayList<JSONObject>();
//        JSONObject json = new JSONObject();
//        for(InvoiceInfoList invoiceInfo:InvoiceInfoList) {
//            json.put("InvoiceSerialNo",invoiceInfo.getInvoiceSerialNo());
//            json.put("RedReason",invoiceInfo.getRedReason());
//            json.put("RelatedTransFlag",invoiceInfo.getRelatedTransFlag());
//            json.put("InvoiceAmount",invoiceInfo.getInvoiceAmount());
//            InvoiceList.add(json);
//        }
//        requestJson.put("InvoiceInfoList",InvoiceList);
//
//        String requestURL = invoiceUrl
//                //"http://172.20.53.134:38080/"
//                        +"est/est66.xhtml";
//        Map<String, Object> stringObjectMap = httpPost(resultMap, httpClient, requestJson,requestURL);
//        return stringObjectMap;
//    }

    /**
     * @description: 换票授权接口(B20100075)--测试用
     * @auther: liyan
     * @date: 2020/1/3 18:55
     * @param: [InvoiceCode 发票代码, InvoiceNo 发票号码]
     * @return: java.util.Map<java.lang.String,java.lang.Object>
     **/
//    public static Map<String,Object> transactionInvoicetimes(String InvoiceCode, String InvoiceNo){
//        Map<String,Object> resultMap = new HashMap<>();
//        CloseableHttpClient httpClient = HttpClients.createDefault();
//        JSONObject requestJson = new JSONObject();
//        requestJson.put("InvoiceCode",InvoiceCode);
//        requestJson.put("InvoiceNo",InvoiceNo);
//        String requestURL = //invoiceUrl
//                "http://172.20.53.134:38080/"
//                        +"est/est75.xhtml";
//        Map<String, Object> stringObjectMap = httpPost(resultMap, httpClient, requestJson,requestURL);
//        return stringObjectMap;
//    }




    private static Map<String, Object> httpPost(Map<String, Object> resultMap,CloseableHttpClient httpClient ,
                                                JSONObject requestJson, String requestURL) {
        try {
            HttpPost httpPost = new HttpPost(requestURL);
            RequestConfig requestConfig = RequestConfig.custom().setConnectionRequestTimeout(180 * 1000)
                    .setConnectTimeout(180 * 1000).setSocketTimeout(180 *1000).setRedirectsEnabled(false).build();
            httpPost.setConfig(requestConfig);
            httpPost.setHeader("Content-Type","aplication/json");
            httpPost.setEntity(new StringEntity(requestJson.toJSONString(), ContentType.create("application/json","GBK")));
            CloseableHttpResponse response = httpClient.execute(httpPost);
            if (response != null && response.getStatusLine().getStatusCode() == 200) {
                String result = EntityUtils.toString(response.getEntity());
                JSONObject resultJson = JSONObject.parseObject(result);
                //respCode等于0
                if(0 == Integer.parseInt(resultJson.get("ReturnCode")+"")){
                    resultMap.put("success",true);
                    resultMap.put("msg","调用接口成功");
                    resultMap.put("data",resultJson);
                }else{
                    resultMap.put("success",false);
                    resultMap.put("msg",resultJson.get("ReturnMsg")+"");
                }
            }else{
                resultMap.put("success",false);
                resultMap.put("msg","调用接口失败");
            }
        }catch (Exception e){
            e.printStackTrace();
            resultMap.put("success",false);
            resultMap.put("msg","调用接口异常");
            return resultMap;
        }
        return resultMap;
    }


//    public static void main(String[] args) {
//       //173
//        Map<String, Object> stringObjectMap = transactionRecordSync(
//                "123456000","435545454351236668","京A12231","0",
//                "2019-12-01 17:14:12","200","test1",
//                "2019-12-27 17:14:12","0808220000098210","9","10");
//        System.out.println(stringObjectMap);

//              //174
//            Map<String, Object> stringObjectMap = transactionFindTrans("123456111",
//                "10","435545454351236668",
//                "10000");
//        System.out.println(stringObjectMap);

    //23
//        List<InvoiceRequestInfoList> list = new ArrayList<>();
//            InvoiceRequestInfoList invoice = new InvoiceRequestInfoList();
//
//            invoice.setDeductionFlag(0);
////        invoice.setBuyerId("");
//                invoice.setBuyerName("test1");
////        invoice.setBuyerTaxCode("");
////                invoice.setBuyerTelephoneNo("");
////        invoice.setBuyerAddress("");
////                invoice.setBuyerBankName("");
////        invoice.setBuyerBankAccountNo("");
////                invoice.setInvoiceRemark("");
//        invoice.setVehPlateNo("京A12231");
//                invoice.setVehPlateColor(0);
//
//        List<TransList> l=new ArrayList<>();
//        TransList trans = new TransList();
//        trans.setTransNo("145");
//        trans.setTransSourceTypeCode(9);
//        trans.setTransAmount("200");
//        trans.setInvoiceAmount("200");
//        l.add(trans);
//
//        invoice.setTransList(l);
//        list.add(invoice);
//        Map<String, Object> stringObjectMap = transactionMakeInvoice("123456111","","",
//                3, "907941660@qq.com","18514358681",
//                list);
//            System.out.println(stringObjectMap);

    //55
//        Map<String, Object> stringObjectMap = transactionInvoiceMessage(
//                "145","9");
//        System.out.println(stringObjectMap);

    //65
//        Map<String, Object> stringObjectMap = transactionChangeInvoice(
//                "","10","","","999222367973","44183650",
//                "2020-01-02 00:00:00","907941660@qq.com","","","ss1",
//                "","","","","",0,
//                1);
//        System.out.println(stringObjectMap);

    //66
//        List<InvoiceInfoList> list = new ArrayList<>();
//        InvoiceInfoList invoice = new InvoiceInfoList();
//
//        invoice.setInvoiceSerialNo("6000000000037354");
//        invoice.setRedReason("无");
//        invoice.setRelatedTransFlag(1);
//        invoice.setInvoiceAmount("200");
//        list.add(invoice);
//        Map<String, Object> stringObjectMap = transactionInvoiceHC(
//                "","5",list);
//        System.out.println(stringObjectMap);

        //75
//        Map<String, Object> stringObjectMap = transactionInvoicetimes("999222347262","50423511");
//        System.out.println(stringObjectMap);
//    }


}
