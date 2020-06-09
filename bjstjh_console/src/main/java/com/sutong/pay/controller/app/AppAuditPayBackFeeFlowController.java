package com.sutong.pay.controller.app;

import com.alibaba.dubbo.common.logger.Logger;
import com.alibaba.dubbo.common.logger.LoggerFactory;
import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sutong.bjstjh.entity.AppAuditInvoiceInformationModel;
import com.sutong.bjstjh.entity.AuditPaybackFeeFlowModel;
import com.sutong.bjstjh.result.ConstClass;
import com.sutong.bjstjh.result.Result;
import com.sutong.bjstjh.result.ResultCode;
import com.sutong.pay.controller.app.confiletool.InvoiceMouthPieceController;
import com.sutong.pay.service.AppAuditInvoiceInformationService;
import com.sutong.pay.service.AuditPaybackFeeFlowService;
import com.sutong.transfer.TransferIssue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;


/**
 * Created by nt on 2019/12/21.
 */
@RestController
@CrossOrigin
@Configuration
public class AppAuditPayBackFeeFlowController {

    private static Logger logger = LoggerFactory.getLogger(AppAuditPayBackFeeFlowController.class);
    /**
     *
     */
    @Reference
    private AuditPaybackFeeFlowService auditPaybackFeeFlowService;
    @Reference
    private AppAuditInvoiceInformationService informationService;

    @Autowired
    TransferIssue transferIssue;

    @Autowired
    private InvoiceMouthPieceController mouthPiece;
    /**
     * 查询补费流水记录列表
     * @param map 接收 Vue axios请求参数(json转化成map Content-Type：application/json;charset=UTF-8)
     *            Content-Type：application/x-www-form-urlencoded
     * @return
     */
    @RequestMapping(value = "/app/auditPaybackFeeFlowList",method = RequestMethod.POST)
    public Result getAuditPaybackFeeFlowResultController(
            @RequestBody Map<String,String> map){

        try{
            String vehicleColour = map.get("vehicleColour");
            String vehicleNumber = map.get("vehicleNumber");
            String userCard = map.get("userCard");
            String certificateType = map.get("certificateType");

            Map<String, Object> objectMap = transferIssue.transferIssueLogin();
            Boolean sessionKeySucc = (Boolean)objectMap.get("success");
            if(!sessionKeySucc) {
                return Result.error().code(ResultCode.USER_INF_ERROR).message(ConstClass.SESSION_KEY_NULL);
            }

            Map<String, Object> etcInfo = transferIssue.transferQueryETCInfo(vehicleNumber,vehicleColour, null, null, objectMap.get("data").toString());
            //log.info("transferIssue---------------->"+etcInfo);
            Boolean success = (Boolean)etcInfo.get("success");
            String msg = (String) etcInfo.get("msg");
            if(success){
                JSONObject data = (JSONObject)etcInfo.get("data");
                JSONObject userInfo = data.getJSONObject("UserInfo");
                String CertificateTypeCode = userInfo.getString("CertificateTypeCode");
                String certificateNo = userInfo.getString("CertificateNo");
//            log.info("参数值vehicleId---------------->"+vehicleId);
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






            AuditPaybackFeeFlowModel model = new AuditPaybackFeeFlowModel();
            model.setVehicleId(map.get("vehicleNumber")+"_"+map.get("vehicleColour"));
            model.setPaybegintime(map.get("paybegintime"));
            model.setPayendtime(map.get("payendtime"));
            List<AuditPaybackFeeFlowModel> list = auditPaybackFeeFlowService.getAuditPaybackFeeFlowResult(model);
            return Result.ok().data("resultValue",list);
        }catch (Exception e){
            e.printStackTrace();
            return Result.ok().data("Exception","服务出现错误,请联系客服或管理员!");
        }

    }

    /**
     * 补费流水详情
     * @param map (json转化成map Content-Type：application/json;charset=UTF-8)
     * @param flowId 补费流水ID (Content-Type：application/x-www-form-urlencoded)
     * @return
     */
    @RequestMapping(value = "/app/auditPaybackFeeFlowShow",method = RequestMethod.POST)
    public Result getAuditPaybackFeeFlowDetailResultController(@RequestBody Map<String,String> map,String flowId){
        try{
            AuditPaybackFeeFlowModel model = new AuditPaybackFeeFlowModel();
            model.setFlowId(map.get("flowId"));
            AuditPaybackFeeFlowModel model1 = auditPaybackFeeFlowService.getAuditPaybackFeeFlowDetailResult(model);
            return Result.ok().data("resultValue",model1);
        }catch (Exception e){
            e.printStackTrace();
            return  Result.error().data("Exception","服务出现错误,请联系客服或管理员!");
        }

    }

    /**
     * 补费流水凭证查询
     * @param map (json转化成map Content-Type：application/json;charset=UTF-8)
     * @param flowId 补费流水ID (Content-Type：application/x-www-form-urlencoded)
     * @return
     */
    @RequestMapping(value = "/app/auditPaybackFeeFlowDetail",method = RequestMethod.POST)
    public Result getAuditPaybackFeeFlowDatilController(@RequestBody Map<String,String> map,String flowId){
        try{
            AuditPaybackFeeFlowModel model = new AuditPaybackFeeFlowModel();
            model.setFlowId(map.get("flowId"));
            AuditPaybackFeeFlowModel list = auditPaybackFeeFlowService.getAuditPaybackFeeFlowDetailResult(model);
            return Result.ok().data("resultValue",list);
        }catch (Exception e){
            e.printStackTrace();
            return  Result.error().data("Exception","服务出现错误,请联系客服或管理员!");
        }

    }
    /**
     * 补费流水发票信息返回
     * @param map (json转化成map Content-Type：application/json;charset=UTF-8)
     * @param flowId 补费流水ID (Content-Type：application/x-www-form-urlencoded)
     * @return
     */
    @RequestMapping(value = "/app/auditPaybackFeeFlowInvoiceshow",method = RequestMethod.POST)
    public Result getAppAuditInvoiceInformationController(@RequestBody Map<String,String> map,String flowId){
        try{
            AuditPaybackFeeFlowModel model = new AuditPaybackFeeFlowModel();
            model.setFlowId(map.get("flowId"));
            AuditPaybackFeeFlowModel feeFlowModel = auditPaybackFeeFlowService.getAuditPaybackFeeFlowDetailResult(model);

            AppAuditInvoiceInformationModel informationModel = new AppAuditInvoiceInformationModel();
            informationModel.setOrderId(feeFlowModel.getOrderId());
            informationModel.setFlowId(feeFlowModel.getFlowId());
            List<AppAuditInvoiceInformationModel> informationResult = informationService.getAppAuditInvoiceInformationListService(informationModel);

            if (informationResult != null && informationResult.size() > 0) {
                return Result.error().data("result",false).data("resultValue", null);//已开发票
            }else {
                return Result.ok().data("result","true").data("resultValue", feeFlowModel);//未开发票
            }
        }catch (Exception e){
            e.printStackTrace();
            return  Result.error().data("Exception","服务出现错误,请联系客服或管理员!");
        }

    }

    /**
     * 接受页面参数，调用接口开发票
     * @param requestmap (json转化成map Content-Type：application/json;charset=UTF-8)
     * @return
     */
    @RequestMapping(value = "/app/appintoinvoiceauditpay",method = RequestMethod.POST)
    public Result depositinAppAuditInvoiceInformationController(@RequestBody Map<String,String> requestmap, HttpServletRequest request){
        logger.info("当前补费交易-开始执行开发票功能!");
        String message = "";
        JSONObject json = new JSONObject();//
        AuditPaybackFeeFlowModel model = null;
        AppAuditInvoiceInformationModel infomodel = null;
        int number = 0;
        try{
            /**
             * 根据流水id查询流水补费信息
             *
             */
            if (requestmap.get("flowId") != null && !"".equals(requestmap.get("flowId"))){
                model = new AuditPaybackFeeFlowModel();
                model.setFlowId(requestmap.get("flowId"));
                model = auditPaybackFeeFlowService.getAuditPaybackFeeFlowDetailResult(model);
            }else{
                return Result.error().data("resultValueMassage","交易流水号为空!");
            }
            logger.info("当前补费交易-查询流水补费信息结束!");

            if (model != null){//补费流水不为null
                /**
                 * 根据条件查询本地发票信息
                 *
                 */
                AppAuditInvoiceInformationModel informationModel = new AppAuditInvoiceInformationModel();
                informationModel.setOrderId(model.getOrderId());
                informationModel.setFlowId(model.getFlowId());
                List<AppAuditInvoiceInformationModel> list = informationService.getAppAuditInvoiceInformationListService(informationModel);
                /**
                 * 判断本地数据库是否有信息
                 */
                if (list != null && list.size() > 0){
                    //获取交易编码
                    String transId = list.get(0).getTransId();
                    number = Integer.valueOf(list.get(0).getChangeNumbers());
                    /**
                     * 调用55接口查询发票信息
                     */
                    Map<String,Object> messagemap55 = mouthPiece.getInvoiceMessage(transId);
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
                    informationService.updateAppAuditInvoiceInformationService(model1);
                    //如果不能开发票，返回信息
                    if ("1".equals(invoiceTypeCode)){//&& "3".equals(invoiceStatusCode)

                        //红冲开发票取出交易编码
                        json.put("TransId",transId);
                    }else{
                        return Result.error().data("resultValueMassage","已开发票,不能开发票!");
                    }


                }
                //直接开发票
                else{
                    /**
                     * 调用稽查补缴交易同步接口（B20100173）
                     */
                    Map<String,Object> synchronizedmap = mouthPiece.getInvoiceSynchronized(model);
                    logger.info("当前补费交易-调用稽查补缴交易同步接口结束! 结果 : " + synchronizedmap);
                    System.out.println(synchronizedmap);
                    /**
                     * 调用根据交易编号查询交易信息接口（B20100174）
                     *
                     */
                    Map<String,Object> transmap = mouthPiece.getInvoiceFindTrans(model.getUid(),model.getPaybackFeeId());
                    logger.info("当前补费交易-调用交易编号查询交易信息接口结束! 结果 : " + transmap);
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
                            return Result.error().data("resultValueMassage","当前补费交易发票开具申请失败!");
                        }else {
                            logger.info("新增稽查补缴交易失败!");
                            return Result.error().data("resultValueMassage","当前补费交易发票开具申请失败!");
                        }
                    }

                }

                /**
                 * 调用开票申请接口（B20100023）
                 *
                 */
                Map<String,Object> maps = mouthPiece.getInvoiceParametrar(model,requestmap,json);
                logger.info("当前补费交易-调用开票申请接口结束! 结果 : " + maps);
                System.out.println(maps);
                /**
                 * 开票成功
                 */
                if ("true".equals(maps.get("success")) || (boolean)maps.get("success") == true){
                    //获取查询交易信息
                    String transid = json.get("TransId").toString();
                    AppAuditInvoiceInformationModel appmodel = mouthPiece.getAppAuditInvoiceInformation(model,null,requestmap,transid,true);
                    /**
                     * 查询55接口信息
                     */
                    //..........

                    //存入数据库开发票记录
                    if (number > 0 ){
                        appmodel.setChangeNumbers(String.valueOf(number));
                    }
                    appmodel.setInvalidEffective("0");
                    int n = informationService.positinAppAuditInvoiceInformationService(appmodel);
                    if (n != 1){
                        logger.info("!");
                        return Result.error().data("resultValueMassage","开具发票成功,存储发票信息出错!");
                    }
                }
                //开发票失败
                else{
                    logger.info(maps.get("msg").toString());
                    return Result.error().data("resultValueMassage",maps.get("msg").toString());
                }

            }else{
                return Result.error().data("resultValueMassage","抱歉,未找到此补费成功信息!");
            }
            return Result.ok().data("resultValueMassage","当前补费交易发票开具成功,请注意查收!");
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
    @RequestMapping(value = "/app/changeinvoiceinfomationfee", method = RequestMethod.POST)
    public Result getChangeInvoiceInfomationFeeController(@RequestBody Map<String, String> requestmap) {
        AuditPaybackFeeFlowModel feemodel = null;
        int changenumber = 0;
        try {
            /**
             * 根据流水id查询流水补费信息
             *
             */
            if (requestmap.get("flowId") != null && !"".equals(requestmap.get("flowId"))) {
                feemodel = new AuditPaybackFeeFlowModel();
                feemodel.setFlowId(requestmap.get("flowId"));
                feemodel = auditPaybackFeeFlowService.getAuditPaybackFeeFlowDetailResult(feemodel);
            } else {
                return Result.error().data("resultValueMassage", "交易流水号为空!");
            }
            logger.info("当前补费交易-查询流水补费信息结束!");

            //查询本地数据库是否开过发票
            AppAuditInvoiceInformationModel model = new AppAuditInvoiceInformationModel();
            model.setFlowId(requestmap.get("flowId"));
            List<AppAuditInvoiceInformationModel> list = informationService.getAppAuditInvoiceInformationListService(model);

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
                if (!"true".equals(messagemap55.get("success")) || (boolean) messagemap55.get("success") == false) {
                    return Result.ok().data("resultValueMassage", messagemap55.get("msg"));
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

                if (!("0".equals(jsono.get("invoiceTypeCode")) && "2".equals(jsono.get("invoiceStatusCode")))) {
                    model1.setInvalidEffective("0");
                    informationService.updateAppAuditInvoiceInformationService(model1);
                    return Result.error().data("resultValueMassage", "此发票不能换票!");
                } else {


                    int num = Integer.valueOf(model1.getChangeNumbers());//换票次数
//                    String ie = model1.getInvalidEffective();//是否生效
                    if (num == 0) {//一次换票
                        model1.setInvalidEffective("0");
                        informationService.updateAppAuditInvoiceInformationService(model1);
                        JSONObject jsonObject = new JSONObject();
                        jsonObject.put("InvoiceCode",model1.getInvoiceCode());
                        jsonObject.put("InvoiceNo",model1.getInvoiceId());
                        Map<String,Object> changemap = mouthPiece.getChangeInvoice(feemodel.getUid(),requestmap,jsonObject);

                        if ("true".equals(changemap.get("success")) || (boolean)changemap.get("success") == true){
                            /**
                             * 查询55接口信息
                             */
                            //.............

                            AppAuditInvoiceInformationModel appmodel = mouthPiece.getAppAuditInvoiceInformation(feemodel,null,requestmap,model1.getTransId(),true);
                            appmodel.setChangeNumbers(String.valueOf(changenumber+1));
                            appmodel.setInvalidEffective("1");
                            int n = informationService.positinAppAuditInvoiceInformationService(appmodel);
                            if (n != 1){
                                return  Result.error().data("resultValueMassage","存储换票记录失败!");
                            }
                        }else {
                            return Result.error().data("resultValueMassage",changemap.get("msg"));
                        }
                    } else if (num == 1) {//二次换票
                        model1.setInvalidEffective("0");
                        informationService.updateAppAuditInvoiceInformationService(model1);
                        JSONObject jsonObject = new JSONObject();
                        jsonObject.put("InvoiceCode",model1.getInvoiceCode());
                        jsonObject.put("InvoiceNo",model1.getInvoiceId());
                        Map<String,Object> changemap = mouthPiece.getChangeInvoice(feemodel.getUid(),requestmap,jsonObject);

                        if ("true".equals(changemap.get("success")) || (boolean)changemap.get("success") == true){
                            /**
                             * 查询55接口信息
                             */
                            //.............

                            AppAuditInvoiceInformationModel appmodel = mouthPiece.getAppAuditInvoiceInformation(feemodel,null,requestmap,model1.getTransId(),true);
                            appmodel.setChangeNumbers(String.valueOf(changenumber+1));
                            appmodel.setInvalidEffective("1");
                            int n = informationService.positinAppAuditInvoiceInformationService(appmodel);
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
     * 同步获取发票信息
     */
    public void getupdateInovice(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        AppAuditInvoiceInformationModel model = new AppAuditInvoiceInformationModel();
//        Date date1 = new Date(date.getTime()-600000);
//        model.setInvoicebegintime(sdf.format(date1));
//        model.setInvoiceendtime(sdf.format(date));
        model.setConditional_query("1");
        List<AppAuditInvoiceInformationModel> list = informationService.getAppAuditInvoiceInformationListService(model);
        List<AppAuditInvoiceInformationModel> listpast = informationService.getAppAuditInvoiceInformationPastListService(model);
        if (list != null && list.size() > 0){
            for (AppAuditInvoiceInformationModel models : list) {
                String transid = models.getTransId();
                /**
                 *查询55接口信息
                 */
                Map<String,Object> messagemap55 = mouthPiece.getInvoiceMessage(transid);
                if ("true".equals(messagemap55.get("success")) || (boolean)messagemap55.get("success") == true){
                    JSONObject jsonString = (JSONObject)messagemap55.get("data");
                    int invoiceInfoListnum = Integer.valueOf(jsonString.get("InvoiceInfoList_NUM").toString());
                    JSONArray jsonArray = (JSONArray)jsonString.get("InvoiceInfoList");
                    JSONObject jsono = (JSONObject)jsonArray.get(invoiceInfoListnum-1);
                    /**
                     * 完善实体类信息
                     */
                    models.setSellerPhone(jsono.get("SellerTelephoneNo").toString());//销售方联系方式
                    models.setSellerRatepayingNumber(jsono.get("SellerTaxCode").toString());//销售方纳税识别号
                    models.setSellerBankNumber(jsono.get("SellerBankName").toString()); //销售方开户行及账号
                    models.setPayee(jsono.get("Payee").toString());//收款人
                    models.setRevier(jsono.get("Reviewer").toString()); //复核
                    models.setDrawer(jsono.get("Invoicer").toString()); //开票人
                    models.setInvoiceCode(jsono.get("InvoiceCode").toString());//发票代码
                    models.setInvoiceNumber(jsono.get("InvoiceNo").toString());//发票号
                    int n = informationService.updateAppAuditInvoiceInformationService(models);
                    if (n != 1){
                        continue;
                    }
                }


            }
        }
        if (listpast != null && listpast.size() > 0){
            for (AppAuditInvoiceInformationModel models : listpast) {
                String transid = models.getTransId();
                /**
                 *查询55接口信息
                 */
                Map<String,Object> messagemap55 = mouthPiece.getInvoiceMessage(transid);
                if ("true".equals(messagemap55.get("success")) || (boolean)messagemap55.get("success") == true){
                    JSONObject jsonString = (JSONObject)messagemap55.get("data");
                    int invoiceInfoListnum = Integer.valueOf(jsonString.get("InvoiceInfoList_NUM").toString());
                    JSONArray jsonArray = (JSONArray)jsonString.get("InvoiceInfoList");
                    JSONObject jsono = (JSONObject)jsonArray.get(invoiceInfoListnum-1);
                    /**
                     * 完善实体类信息
                     */
                    models.setSellerPhone(jsono.get("SellerTelephoneNo").toString());//销售方联系方式
                    models.setSellerRatepayingNumber(jsono.get("SellerTaxCode").toString());//销售方纳税识别号
                    models.setSellerBankNumber(jsono.get("SellerBankName").toString()); //销售方开户行及账号
                    models.setPayee(jsono.get("Payee").toString());//收款人
                    models.setRevier(jsono.get("Reviewer").toString()); //复核
                    models.setDrawer(jsono.get("Invoicer").toString()); //开票人
                    models.setInvoiceCode(jsono.get("InvoiceCode").toString());//发票代码
                    models.setInvoiceNumber(jsono.get("InvoiceNo").toString());//发票号
                    int m = informationService.updateAppAuditInvoiceInformationPastService(models);
                    if (m != 1){
                        continue;
                    }
                }
            }
        }
        System.out.println("更新同步发票信息!");
    }
}
