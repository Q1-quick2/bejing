package com.sutong.pay.controller.app;

import com.alibaba.dubbo.common.logger.Logger;
import com.alibaba.dubbo.common.logger.LoggerFactory;
import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sutong.bjstjh.entity.AppAuditInvoiceInformationModel;
import com.sutong.bjstjh.entity.AppAuditPastOrderModel;
import com.sutong.bjstjh.result.ConstClass;
import com.sutong.bjstjh.result.Result;
import com.sutong.bjstjh.result.ResultCode;
import com.sutong.pay.controller.app.confiletool.InvoiceMouthPieceController;
import com.sutong.pay.service.AppAuditInvoiceInformationService;
import com.sutong.pay.service.AppAuditPastOrderService;
import com.sutong.transfer.TransferIssue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * Created by main on 2019/12/18.
 */
@RestController
@CrossOrigin
public class AppAuditPastOrderController {

    private static Logger logger = LoggerFactory.getLogger(AppAuditPastOrderController.class);
    /**
     * 引入历史补费service
     */
    @Reference
    private AppAuditPastOrderService appauditpastorderService;
    @Reference
    private AppAuditInvoiceInformationService informationService;

    @Autowired
    TransferIssue transferIssue;
    @Autowired
    private InvoiceMouthPieceController mouthPiece;
    /**
     * 历史补费流水列表controller
     * @return
     */
    @RequestMapping(value = "/app/auditpastordershow",method = RequestMethod.POST)
    public Result getAuditpastorderController(
            @RequestBody Map<String,String> map){

        try{
            String obuId = map.get("obuId");
            String userCard = map.get("userCard");
            String certificateType = map.get("certificateType");
            Map<String, Object> objectMap = transferIssue.transferIssueLogin();
            Boolean sessionKeySucc = (Boolean)objectMap.get("success");
            if(!sessionKeySucc) {
                return Result.error().code(ResultCode.USER_INF_ERROR).message(ConstClass.SESSION_KEY_NULL);
            }
            Map<String, Object> etcInfo = transferIssue.transferQueryETCInfo(null, null, null, obuId, objectMap.get("data").toString());
            // log.info("transferIssue---------------->"+etcInfo);
            Boolean success = (Boolean)etcInfo.get("success");
            String msg = (String) etcInfo.get("msg");
            if(success){
                JSONObject data = (JSONObject)etcInfo.get("data");
                JSONObject userInfo = data.getJSONObject("UserInfo");
                String CertificateTypeCode = userInfo.getString("CertificateTypeCode");
                String certificateNo = userInfo.getString("CertificateNo");
//            log.info("参数值userCard---------------->"+userCard);
//            log.info("参数值certificateType---------------->"+certificateType);
//            log.info("返回值CertificateTypeCode---------------->"+CertificateTypeCode);
//            log.info("返回值certificateNo---------------->"+certificateNo);
                if(certificateType.equals(ConstClass.CARD_TYPE_OTHER)){
                    if(ConstClass.CARD_TYPE_OTHER_ENTER.equals(CertificateTypeCode)){
                        if(!userCard.equals(certificateNo)){
                            return Result.error().code(ResultCode.USER_INF_ERROR).message(ConstClass.USER_INFO_NOT_EXISTENT);
                        }
                    }else if (ConstClass.CARD_TYPE_OTHER_PER.equals(CertificateTypeCode)){
                        if(!userCard.equals(certificateNo)){
                            return Result.error().code(ResultCode.USER_INF_ERROR).message(ConstClass.USER_INFO_NOT_EXISTENT);
                        }
                    }else{
                        return Result.error().code(ResultCode.USER_INF_ERROR).message(ConstClass.USER_INFO_NOT_EXISTENT);
                    }

                }else if(!certificateType.equals(CertificateTypeCode)||!userCard.equals(certificateNo)){

                    return Result.error().code(ResultCode.USER_INF_ERROR).message(ConstClass.USER_INFO_NOT_EXISTENT);
                }


            }else{

                return Result.error().code(ResultCode.USER_INF_ERROR).message(msg);
            }



            AppAuditPastOrderModel model = new AppAuditPastOrderModel();
            model.setObuId(map.get("obuId"));
            model.setBegintime(map.get("begintime"));
            model.setEndtime(map.get("endtime"));
            List<AppAuditPastOrderModel> list = appauditpastorderService.getAppAuditPastOrderResultList(model);
            if (list != null && list.size() > 0){
                model = list.get(0);
                model.setEndPaymenttime(mouthPiece.getDateStringfomat(model.getEndPaymenttime()));
                List<AppAuditPastOrderModel> lists = new ArrayList<>();
                lists.add(model);
                return Result.ok().data("resultdata",lists);
            }else {
                return Result.ok().data("resultdata",null);
            }
        }catch (Exception e){
            e.printStackTrace();
            return Result.error().data("Exception","服务出现错误!");
        }
    }

    /**
     * 历史补费详情controller
     * @return
     */
    @RequestMapping(value = "/app/auditpastorderInfoshow",method = RequestMethod.POST)
    public Result getAuditpastorderInfoController(@RequestBody Map<String,String> map, String infoid,String flowId){
        try{
            AppAuditPastOrderModel model = new AppAuditPastOrderModel();
            model.setId(map.get("infoid"));
            model.setFlowId(map.get("flowId"));
            AppAuditPastOrderModel  lists = appauditpastorderService.getAppAuditPastOrderInfoResult(model);
            return Result.ok().data("resultdata",lists);
        }catch (Exception e){
            e.printStackTrace();
            return Result.error().data("Exception","服务出现错误!");
        }

    }
    /**
     * 历史补费凭证
     * @return
     */
    @RequestMapping(value = "/app/auditpastorderInfoSsupplementVoucher",method = RequestMethod.POST)
    public Result getAuditpastorderInfoPastController(@RequestBody Map<String,String> map,String infoid,String flowId){
        try{
            AppAuditPastOrderModel model = new AppAuditPastOrderModel();
            model.setObuId(map.get("obuId"));
            model.setBegintime(map.get("begintime"));
            model.setEndtime(map.get("endtime"));
            List<AppAuditPastOrderModel> list = appauditpastorderService.getAppAuditPastOrderResultList(model);
            if (list != null && list.size() > 0){
                model = list.get(0);
                model.setEndPaymenttime(mouthPiece.getDateStringfomat(model.getEndPaymenttime()).replace("-","/"));
            }
            return Result.ok().data("resultdata",model);
        }catch (Exception e){
            e.printStackTrace();
            return Result.error().data("Exception","服务出现错误!");
        }

    }

    /**
     * 历史补费能否开发票controller
     * @return
     */
    @RequestMapping(value = "/app/auditpastorderInfoInvoiceshowPast",method = RequestMethod.POST)
    public Result getAppAuditInvoiceInformationPastController(@RequestBody Map<String,String> map){
        try{
            AppAuditPastOrderModel model = new AppAuditPastOrderModel();
            model.setObuId(map.get("obuId"));

            model = appauditpastorderService.getAppAuditPastOrderResultList(model).get(0);

            AppAuditInvoiceInformationModel informationModel = new AppAuditInvoiceInformationModel();
            informationModel.setObuId(map.get("obuId"));
            informationModel.setFlowId(model.getFlowId());
            List<AppAuditInvoiceInformationModel> information =
                    informationService.getAppAuditInvoiceInformationPastListService(informationModel);
            System.out.println(information);
            if (information != null && information.size() >0) {
                return Result.ok().data("result",false).data("resultValue",null);
            }else {
                return Result.ok().data("result",true).data("resultValue",model);
            }
        }catch (Exception e){
            e.printStackTrace();
            return Result.error().data("Exception","服务出现错误!");
        }

    }
    /**
     * 执行调用开发票
     * @return
     */
    @RequestMapping(value = "/app/auditpastorderInfoInvoicesInto",method = RequestMethod.POST)
    public Result depositinAppAuditInvoiceInformationPastController(@RequestBody Map<String,String> requestmap){
        logger.info("历史补费交易-开始执行开发票!");
        String message = "";
        JSONObject json = new JSONObject();
        AppAuditPastOrderModel model = null;
        AppAuditInvoiceInformationModel infomodel = null;
        try{
            /**
             * 根据obuid查询信息
             *
             */
            if (requestmap.get("obuId") != null && !"".equals(requestmap.get("obuId"))){
                model = new AppAuditPastOrderModel();
                model.setObuId(requestmap.get("obuId"));
                List<AppAuditPastOrderModel> list = appauditpastorderService.getAppAuditPastOrderResultList(model);
                if (list != null && list.size() > 0){
                    model = list.get(0);
                }
            }else{
                return Result.error().data("resultValueMassage","OBU号为空!");
            }
            logger.info("历史补费交易-查询流水补费信息结束!");
            //补费流水不为null
            if (model != null){
                /**
                 * 根据条件查询本地发票信息
                 *
                 */
                AppAuditInvoiceInformationModel informationModel = new AppAuditInvoiceInformationModel();
                informationModel.setObuId(requestmap.get("obuId"));
//                informationModel.setFlowId(model.getFlowId());
                List<AppAuditInvoiceInformationModel> list = informationService.getAppAuditInvoiceInformationPastListService(informationModel);
                /**
                 * 判断本地数据库是否有信息
                 */
                if (list != null && list.size() > 0){
                    //获取交易编码
                    String transId = list.get(0).getTransId();
                    /**
                     * 调用55接口查询发票信息
                     */
                    Map<String,Object> messagemap55 = mouthPiece.getInvoiceMessage(transId);
                    System.out.println(messagemap55);
                    if ("false".equals(messagemap55.get("success")) || (boolean)messagemap55.get("success") == false){
                        return  Result.error().data("resultValueMassage",messagemap55.get("msg"));
                    }
                    JSONObject jsonString = (JSONObject)messagemap55.get("data");
                    int invoiceInfoListnum = Integer.valueOf(jsonString.get("InvoiceInfoList_NUM").toString());
                    JSONArray jsonArray = (JSONArray)jsonString.get("InvoiceInfoList");
                    JSONObject jsono = (JSONObject)jsonArray.get(invoiceInfoListnum-1);
                    String invoiceTypeCode = jsono.get("InvoiceTypeCode").toString();
                    String invoiceStatusCode = jsono.get("InvoiceStatusCode").toString();
                    /**
                     * 根据发票信息更改本地数据库信息，获取最新发票信息
                     */
                    AppAuditInvoiceInformationModel model1 = list.get(list.size()-1);
                    model1.setSellerPhone(jsono.get("SellerTelephoneNo").toString());//销售方联系方式
                    model1.setSellerRatepayingNumber(jsono.get("SellerTaxCode").toString());//销售方纳税识别号
                    model1.setSellerBankNumber(jsono.get("SellerBankName").toString()); //销售方开户行及账号
                    model1.setPayee(jsono.get("Payee").toString());//收款人
                    model1.setRevier(jsono.get("Reviewer").toString()); //复核
                    model1.setDrawer(jsono.get("Invoicer").toString()); //开票人
                    model1.setInvoiceCode(jsono.get("InvoiceCode").toString());//发票代码
                    model1.setInvoiceNumber(jsono.get("InvoiceNo").toString());//发票号
                    model1.setInvalidEffective("-1");
                    informationService.updateAppAuditInvoiceInformationPastService(model1);
                    //如果不能开发票，返回信息
//                    if (!("1".equals(invoiceTypeCode)) ){//&& "3".equals(invoiceStatusCode)
//                        return Result.error().data("resultValueMassage","已开发票,不能开发票!");
//                    }

                    //红冲开发票取出交易编码
                    json.put("TransId",model1.getTransId());
                }
                //直接开发票
                else{
                    /**
                     * 调用稽查补缴交易同步接口（B20100173）
                     */
                    Map<String,Object> synchronizedmap = mouthPiece.getInvoiceSynchronized(model);
                    logger.info("历史补费交易-调用稽查补缴交易同步接口结束! 结果 : " + synchronizedmap);
                    System.out.println(synchronizedmap);
                    /**
                     * 调用根据交易编号查询交易信息接口（B20100174）
                     *
                     */
                    Map<String,Object> transmap = mouthPiece.getInvoiceFindTrans(model.getUserUid(),model.getPayBackFeeIdPast());
                    logger.info("历史补费交易-调用交易编号查询交易信息接口结束! 结果 : " + transmap);
                    System.out.println(transmap);
                    //稽查同步交易信息返回
                    boolean synchronize = "true".equals(synchronizedmap.get("success")) || (boolean)synchronizedmap.get("success") == true;
                    //查询交易信息返回
                    boolean tran = "true".equals(transmap.get("success")) || ((boolean)transmap.get("success")) == true;

                    if (tran){
                        //获取查询交易信息
                        String string = transmap.get("data").toString();
                        json = JSON.parseObject(string);

                    }else{
                        if (synchronize){
                            logger.info("交易编号查询交易信息失败!新增稽查补缴交易失败!");
                            return Result.error().data("resultValueMassage","历史补费交易发票开具申请失败!");
                        }else {
                            logger.info("新增稽查补缴交易失败!");
                            return Result.error().data("resultValueMassage","历史补费交易发票开具申请失败!");
                        }
                    }

                }

                /**
                 * 调用开票申请接口（B20100023）
                 *
                 */
                Map<String,Object> maps = mouthPiece.getInvoiceMake(model,requestmap,json);
                logger.info("历史补费交易-调用开票申请接口结果 : " + maps);
                System.out.println(maps);
                if ("true".equals(maps.get("success")) || (boolean)maps.get("success") == true){//开票成功
                    //获取查询交易信息
                    String transid = json.get("TransId").toString();
                    AppAuditInvoiceInformationModel appmodel = mouthPiece.getAppAuditInvoiceInformation(null,model,requestmap,transid,false);
//                    /**
//                     * 查询55接口信息
//                     */
//                    Map<String,Object> messagemap55 = mouthPiece.getInvoiceMessage(transid);
//                    if (!"true".equals(messagemap55.get("success")) || (boolean)messagemap55.get("success") == false){
//                        return  Result.ok().data("resultValueMassage",messagemap55.get("msg"));
//                    }
//                    JSONObject jsonString = (JSONObject)messagemap55.get("data");
//                    int invoiceInfoListnum = Integer.valueOf(jsonString.get("InvoiceInfoList_NUM").toString());
//                    JSONArray jsonArray = (JSONArray)jsonString.get("InvoiceInfoList");
//                    JSONObject jsono = (JSONObject)jsonArray.get(invoiceInfoListnum-1);
//                    /**
//                     * 完善实体类信息
//                     */
//                    appmodel.setSellerPhone(jsono.get("SellerTelephoneNo").toString());//销售方联系方式
//                    appmodel.setSellerRatepayingNumber(jsono.get("SellerTaxCode").toString());//销售方纳税识别号
//                    appmodel.setSellerBankNumber(jsono.get("SellerBankName").toString()); //销售方开户行及账号
//                    appmodel.setPayee(jsono.get("Payee").toString());//收款人
//                    appmodel.setRevier(jsono.get("Reviewer").toString()); //复核
//                    appmodel.setDrawer(jsono.get("Invoicer").toString()); //开票人
//                    appmodel.setInvoiceCode(jsono.get("InvoiceCode").toString());//发票代码
//                    appmodel.setInvoiceNumber(jsono.get("InvoiceNo").toString());//发票号
                    //存入数据库开发票记录
                    appmodel.setInvalidEffective("0");
                    int n = informationService.positinAppAuditInvoiceInformationPastService(appmodel);
                    if (n != 1){
                        return Result.error().data("resultValueMassage","开具发票成功,存储发票信息出错!");
                    }

                }else{//开票失败
                    logger.info(maps.get("msg").toString());
                    return Result.error().data("resultValueMassage","历史补费交易发票开具申请失败!");
                }

            }else{
                logger.info("抱歉,未找到此历史补费成功信息!");
                return Result.error().data("resultValueMassage","抱歉,未找到此历史补费成功信息!");
            }

            return Result.ok().data("resultValueMassage","历史补费交易发票开具申请成功!");
        }catch (Exception e){
            logger.info(e.getMessage());
            e.printStackTrace();
            return  Result.error().data("Exception","服务出现错误!");
        }

    }

    /**
     * 换发票
     *
     * @param requestmap
     * @return
     */
    @RequestMapping(value = "/app/changeinvoiceinfomationorder", method = RequestMethod.POST)
    public Result getChangeInvoiceInfomationOrderController(@RequestBody Map<String, String> requestmap) {
        AppAuditPastOrderModel ordmodel = null;
        int changenumber = 0;
        try {
            /**
             * 根据obuid查询信息
             *
             */
            if (requestmap.get("obuId") != null && !"".equals(requestmap.get("obuId"))){
                ordmodel = new AppAuditPastOrderModel();
                ordmodel.setObuId(requestmap.get("obuId"));
                List<AppAuditPastOrderModel> list = appauditpastorderService.getAppAuditPastOrderResultList(ordmodel);
                if (list != null && list.size() > 0){
                    ordmodel = list.get(0);
                }
            }else{
                return Result.error().data("resultValueMassage","OBU号为空!");
            }
            logger.info("历史补费交易-查询流水补费信息结束!");

            //查询本地数据库是否开过发票
            AppAuditInvoiceInformationModel model = new AppAuditInvoiceInformationModel();
            model.setObuId(ordmodel.getTransObuId());
            List<AppAuditInvoiceInformationModel> list = informationService.getAppAuditInvoiceInformationPastListService(model);

            if (list != null && list.size() > 0) {
                //获取最新发票信息
                AppAuditInvoiceInformationModel model1 = list.get(list.size() - 1);
                String transid = model1.getTransId();
                changenumber = Integer.valueOf(model1.getChangeNumbers());
                //
                /**
                 * 查询55接口信息
                 */
                Map<String, Object> messagemap55 = mouthPiece.getInvoiceMessage(transid);
                if ("false".equals(messagemap55.get("success")) || (boolean) messagemap55.get("success") == false) {
                    return Result.error().data("resultValueMassage", messagemap55.get("msg"));
                }
                JSONObject jsonString = (JSONObject) messagemap55.get("data");
                int invoiceInfoListnum = Integer.valueOf(jsonString.get("InvoiceInfoList_NUM").toString());
                JSONArray jsonArray = (JSONArray) jsonString.get("InvoiceInfoList");
                JSONObject jsono = (JSONObject) jsonArray.get(invoiceInfoListnum - 1);

                model1.setSellerPhone(jsono.get("SellerTelephoneNo").toString());//销售方联系方式
                model1.setSellerRatepayingNumber(jsono.get("SellerTaxCode").toString());//销售方纳税识别号
                model1.setSellerBankNumber(jsono.get("SellerBankName").toString()); //销售方开户行及账号
                model1.setPayee(jsono.get("Payee").toString());//收款人
                model1.setRevier(jsono.get("Reviewer").toString()); //复核
                model1.setDrawer(jsono.get("Invoicer").toString()); //开票人
                model1.setInvoiceCode(jsono.get("InvoiceCode").toString());//发票代码
                model1.setInvoiceNumber(jsono.get("InvoiceNo").toString());//发票号

                String invoiceTypeCode = jsono.get("InvoiceTypeCode").toString();
                String invoiceStatusCode = jsono.get("InvoiceStatusCode").toString();
                if ("0".equals(invoiceTypeCode) && "2".equals(invoiceStatusCode)) {

                    int num = Integer.valueOf(model1.getChangeNumbers());//换票次数
//                    String ie = model1.getInvalidEffective();//是否生效
                    if (num == 0) {//一次换票
                        model1.setInvalidEffective("-1");
                        informationService.updateAppAuditInvoiceInformationPastService(model1);
                        JSONObject jsonObject = new JSONObject();
                        jsonObject.put("InvoiceCode",model1.getInvoiceCode());
                        jsonObject.put("InvoiceNo",model1.getInvoiceId());
                        Map<String,Object> changemap = mouthPiece.getChangeInvoice(ordmodel.getUserUid(),requestmap,jsonObject);
                        System.out.println(changemap);
                        if ("true".equals(changemap.get("success")) || (boolean)changemap.get("success") == true){
                            /**
                             * 查询55接口信息
                             */
                            //.............

                            AppAuditInvoiceInformationModel appmodel = mouthPiece.getAppAuditInvoiceInformation(null,ordmodel,requestmap,model1.getTransId(),false);
                            appmodel.setChangeNumbers(String.valueOf(changenumber+1));
                            appmodel.setInvalidEffective("1");
                            int n = informationService.positinAppAuditInvoiceInformationPastService(appmodel);
                            if (n != 1){
                                return  Result.error().data("resultValueMassage","存储换票记录失败!");
                            }
                        }else {
                            return Result.error().data("resultValueMassage",changemap.get("msg"));
                        }
                    } else if (num == 1) {//二次换票
                        model1.setInvalidEffective("-1");
                        informationService.updateAppAuditInvoiceInformationPastService(model1);
                        JSONObject jsonObject = new JSONObject();
                        jsonObject.put("InvoiceCode",model1.getInvoiceCode());
                        jsonObject.put("InvoiceNo",model1.getInvoiceId());
                        Map<String,Object> changemap = mouthPiece.getChangeInvoice(ordmodel.getUserUid(),requestmap,jsonObject);

                        if ("true".equals(changemap.get("success")) || (boolean)changemap.get("success") == true){
                            /**
                             * 查询55接口信息
                             */
                            //.............

                            AppAuditInvoiceInformationModel appmodel = mouthPiece.getAppAuditInvoiceInformation(null,ordmodel,requestmap,model1.getTransId(),false);
                            appmodel.setChangeNumbers(String.valueOf(changenumber+1));
                            appmodel.setInvalidEffective("1");
                            int n = informationService.positinAppAuditInvoiceInformationPastService(appmodel);
                            if (n != 1){
                                return  Result.error().data("resultValueMassage","存储换票记录失败!");
                            }
                        }else {
                            return Result.error().data("resultValueMassage",changemap.get("msg"));
                        }
                    } else {
                        Result.error().data("resultValueMassage", "不支持多次换票!");
                    }

                }
                else{
//                    model1.setInvalidEffective("1");
                    informationService.updateAppAuditInvoiceInformationPastService(model1);
                    return Result.error().data("resultValueMassage", "此发票不能换票!");
                }

            }
            //没有开票信息
            else {
                return Result.error().data("resultValueMassage", "没有开票信息请先开票!");
            }

            return Result.ok().data("resultValueMassage","换票成功，请注意查收!");
        } catch (Exception e) {
            return Result.error().data("resultValueMassage", "服务出现错误!");
        }
    }

    /**
     * 更新55接口与本地数据库的发票信息
     * @param transid 交易编号
     * @param model new的发票信息类
     * @param list 数据库查询的发票信息集合
     */
    private void getInvoiceInformation55toDB(String transid,AppAuditInvoiceInformationModel model,List<AppAuditInvoiceInformationModel> list){
        AppAuditInvoiceInformationModel appinfomodel = new AppAuditInvoiceInformationModel();
        int number = list.size();
        /**
         * 查询55接口信息
         */
        Map<String, Object> messagemap55 = mouthPiece.getInvoiceMessage(transid);
        if ("false".equals(messagemap55.get("success")) || (boolean) messagemap55.get("success") == false) {
//            return Result.error().data("resultValueMassage", messagemap55.get("msg"));
        }
        JSONObject jsonString = (JSONObject) messagemap55.get("data");
        int invoiceInfoListnum = Integer.valueOf(jsonString.get("InvoiceInfoList_NUM").toString());
        JSONArray jsonArray = (JSONArray) jsonString.get("InvoiceInfoList");
        if (invoiceInfoListnum >= number){
            for (int i = number-1; i < invoiceInfoListnum; i++) {
                JSONObject jsono = (JSONObject) jsonArray.get(i);
                String invoiceTypeCode = jsono.get("InvoiceTypeCode").toString();
                String invoiceStatusCode = jsono.get("InvoiceStatusCode").toString();
                /**
                 * 根据发票信息更改本地数据库信息，获取最新发票信息
                 */
                if (invoiceInfoListnum > number){
                    appinfomodel = model;
                }else{
                    appinfomodel = list.get(list.size()-1);
                }
                appinfomodel.setSellerPhone(jsono.get("SellerTelephoneNo").toString());//销售方联系方式
                appinfomodel.setSellerRatepayingNumber(jsono.get("SellerTaxCode").toString());//销售方纳税识别号
                appinfomodel.setSellerBankNumber(jsono.get("SellerBankName").toString()); //销售方开户行及账号
                appinfomodel.setPayee(jsono.get("Payee").toString());//收款人
                appinfomodel.setRevier(jsono.get("Reviewer").toString()); //复核
                appinfomodel.setDrawer(jsono.get("Invoicer").toString()); //开票人
                appinfomodel.setInvoiceCode(jsono.get("InvoiceCode").toString());//发票代码
                appinfomodel.setInvoiceNumber(jsono.get("InvoiceNo").toString());//发票号
                if ("1".equals(invoiceTypeCode)){
                    appinfomodel.setInvalidEffective("-1");
                }
                if ("0".equals(invoiceTypeCode)){
                    appinfomodel.setInvalidEffective("0");
                }
                if (invoiceInfoListnum > number){
                    informationService.positinAppAuditInvoiceInformationPastService(appinfomodel);
                }else{
                    informationService.updateAppAuditInvoiceInformationPastService(appinfomodel);
                }

            }

        }


    }

}
