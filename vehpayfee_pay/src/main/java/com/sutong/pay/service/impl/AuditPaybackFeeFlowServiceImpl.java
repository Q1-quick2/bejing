package com.sutong.pay.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.sutong.bjstjh.entity.AppAuditInvoiceInformationModel;
import com.sutong.bjstjh.entity.AuditPaybackFeeFlowModel;
import com.sutong.pay.mapper.AppAuditInvoiceInformationMapper;
import com.sutong.pay.mapper.AuditPaybackFeeFlowMapper;
import com.sutong.pay.service.AuditPaybackFeeFlowService;
import com.sutong.pay.service.impl.filetool.GenerateJSONCompressedFile;
import com.sutong.pay.service.impl.filetool.QRcodeGenerationAndAnalysis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;


@Component
@Service
public class AuditPaybackFeeFlowServiceImpl implements AuditPaybackFeeFlowService {
    /**
     * 引入非历史补费流水mapper
     */
    @Autowired
    private AuditPaybackFeeFlowMapper flowMapper;
    /**
     * 引入非历史补费发票mapper
     */
    @Autowired
    private AppAuditInvoiceInformationMapper appinformationMapper;
    /**
     *引入二维码方法类
     */
    @Autowired
    private QRcodeGenerationAndAnalysis qrcodeGenerationAndAnalysis;
    /**
    *引入json字符串压缩成文件类
     */
    @Autowired
    private GenerateJSONCompressedFile generateJSONCompressedFile;

    /**
     * 根据条件查询非历史补费流水
     * @param auditPaybackFeeFlowModel
     * @return
     */
    @Override
    public List<AuditPaybackFeeFlowModel> getAuditPaybackFeeFlowResult(AuditPaybackFeeFlowModel auditPaybackFeeFlowModel) {
        List<AuditPaybackFeeFlowModel> list = flowMapper.paybackfeeFlow(auditPaybackFeeFlowModel);
        for (int i = 0 ,length = list.size(); i < length; i++) {
            list.set(i,getChangeAuditPaybackFeeFlowModel(list.get(i)));
        }
        return list;
    }

    /**
     * 根据主键查询非历史补费流水
     * @return
     */
    @Override
    public AuditPaybackFeeFlowModel getAuditPaybackFeeFlowDetailResult(AuditPaybackFeeFlowModel auditPaybackFeeFlowModel) {

        return getChangeAuditPaybackFeeFlowModel(flowMapper.paybackfeeFlowDetail(auditPaybackFeeFlowModel));
    }

    /**
     * 根据主键查询非历史补费流水
     * @param orderId 工单编号
     * @return
     */
    @Override
    public AppAuditInvoiceInformationModel getAppAuditInvoiceInformationResult(String orderId) {
        AppAuditInvoiceInformationModel model = new AppAuditInvoiceInformationModel();
        model.setOrderId(orderId);
        return appinformationMapper.getAppAuditInvoiceInformationModel(model);
    }
    /**
     * 插入非历史补费流水发票信息
     * @param map 参数集合map
     * @return
     */
    @Override
    public int positinAppAuditInvoiceInformation(Map<String,String> map){
        int m = 0;
        /*
         * 查询非历史补费流水信息
         */
        AuditPaybackFeeFlowModel model = new AuditPaybackFeeFlowModel();
        model.setFlowId(map.get("flowId"));
        model = flowMapper.paybackfeeFlowDetail(model);

        /*
         *生成非历史补费流水发票信息
         */
        AppAuditInvoiceInformationModel appinformationModel = new AppAuditInvoiceInformationModel();
        if (model != null) {
            appinformationModel.setOrderId(model.getOrderId());//工单编号
            appinformationModel.setOrderNumber(model.getPaybackFeeId()); //订单号
            appinformationModel.setCommodityOrderNo(model.getEndPayorderno()); //第三方支付订单号
            appinformationModel.setFeeTransTime(model.getEndPaymenttime());//补费时间
            appinformationModel.setInvoiceVehicleId(model.getVehicleId());//车辆车牌号+颜色
            if (model.getEndActpayamt() != null && !"".equals(model.getEndActpayamt())){
                appinformationModel.setBuyerPaybackTotal(
                        (model.getEndActpayamt().multiply(new BigDecimal("0.01"))).toString());//购买方补费合计 单位:元
            }else{
                appinformationModel.setBuyerPaybackTotal("###");
            }
        }

        appinformationModel.setBuyerName(map.get("buyerName"));//购买方名称
        appinformationModel.setBuyerRatepayingNumber(map.get("buyerRatepayingNumber"));//购买方纳税识别号
        appinformationModel.setBuyerPhone(map.get("buyerPhone"));//购买方电话
        appinformationModel.setPurchasersAddress(map.get("purchasersaddress"));////购买方地址
        appinformationModel.setBuyerBankNumber(map.get("buyerBankNumber")); //购买方开户行及账号
        appinformationModel.setInvoiceType(map.get("invoiceType"));//发票类型
        appinformationModel.setPersonalMailBox(map.get("personalMailBox")); //收件人邮箱
        appinformationModel.setPersonalPhone(map.get("personalPhone"));//收件人电话
        appinformationModel.setPersonalName(map.get("personalName"));//收件人姓名
        appinformationModel.setPersonalDetailedAdress(map.get("personalDetailedAdress"));//收件人地址


        appinformationModel.setSellerPhone("###");//销售方联系方式
        appinformationModel.setSellerRatepayingNumber("###");//销售方纳税识别号
        appinformationModel.setSellerBankNumber("###"); //销售方开户行及账号
        appinformationModel.setPayee("###");//收款人
        appinformationModel.setRevier("###"); //复核
        appinformationModel.setDrawer("###"); //开票人
        appinformationModel.setOpenInvoiceTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));//开票日期

        if (model != null && model.getOrderId() != null && !"".equals(model.getOrderId())){
            m = appinformationMapper.insertAppAuditInvoiceInformation(appinformationModel);
        }
        return m;
    }

    /**
     * 二维码生成
     * @param text 存放在二维码中的内容
     * @param imgPath 嵌入二维码的图片路径(可为空)
     * @param destPath 生成的二维码的路径及名称
     */
    @Override
    public void getQRcodeGenerationAndAnalysis(String text, String imgPath, String destPath){
        try{
            qrcodeGenerationAndAnalysis.encode(text, imgPath, destPath, true);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    /**
     * 将json字符串生成json文件到指定路径并压缩到指定的路径下
     * @param jsonString json字符串
     * @param filepath 生成的json文件目的路径
     * @param fileName json文件和zip文件名
     * @param zipFilePath 生成的zip文件目的路径
     */
    @Override
    public void getJsonCompressedFild(String jsonString,String filepath,String fileName,String zipFilePath){
        generateJSONCompressedFile.jsonCompressedFild(jsonString,filepath,fileName,zipFilePath);
    }

    /**
     * 将查询出的码表转换
     * @param model
     * @return
     */
    private AuditPaybackFeeFlowModel getChangeAuditPaybackFeeFlowModel(AuditPaybackFeeFlowModel model){
        /**
         * 转换补费方式码表
         */
        if (model != null){
            if (model.getEndPaytype() != null && !"".equals(model.getEndPaytype())){
                String value = model.getEndPaytype();
                switch (value){
                    case "WXPAY":
                        model.setEndPaytype("微信支付");
                        break;
                    case "ALIPAY":
                        model.setEndPaytype("支付宝支付");
                        break;
                    case "BDWPAY":
                        model.setEndPaytype("百度钱包支付");
                        break;
                    case "ETCPAY":
                        model.setEndPaytype("ETC卡支付");
                        break;
                    case "UPOPPAY":
                        model.setEndPaytype("银联支付");
                        break;
                    default:
                        break;
                }
            }
//            model.setPayBackSum(model.getPayBackSum().multiply(new BigDecimal("0.01")));
//            model.setOweFee(model.getOweFee().multiply(new BigDecimal("0.01")));
//            model.setPayBackFee(model.getPayBackFee().multiply(new BigDecimal("0.01")));
//            model.setEndActpayamt(model.getEndActpayamt().multiply(new BigDecimal("0.01")));
        }

        return model;
    }
}