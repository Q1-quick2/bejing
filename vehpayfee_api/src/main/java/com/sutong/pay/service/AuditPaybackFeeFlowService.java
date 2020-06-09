package com.sutong.pay.service;

import com.sutong.bjstjh.entity.AppAuditInvoiceInformationModel;
import com.sutong.bjstjh.entity.AuditPaybackFeeFlowModel;

import java.util.List;
import java.util.Map;

public interface AuditPaybackFeeFlowService {
    /**
     * 根据条件查询非历史补费流水
     * @param auditPaybackFeeFlow
     * @return
     */
    List<AuditPaybackFeeFlowModel> getAuditPaybackFeeFlowResult(AuditPaybackFeeFlowModel auditPaybackFeeFlow);
    /**
     * 根据主键查询非历史补费流水
     * @param auditPaybackFeeFlow
     * @return
     */
    AuditPaybackFeeFlowModel getAuditPaybackFeeFlowDetailResult(AuditPaybackFeeFlowModel auditPaybackFeeFlow);
    /**
     * 二维码生成
     * @param text 存放在二维码中的内容
     * @param imgPath 嵌入二维码的图片路径(可为空)
     * @param destPath 生成的二维码的路径及名称
     */
    void getQRcodeGenerationAndAnalysis(String text, String imgPath, String destPath);
    /**
     * 将json字符串生成json文件到指定路径并压缩到指定的路径下
     * @param jsonString json字符串
     * @param filepath 生成的json文件目的路径
     * @param fileName json文件和zip文件名
     * @param zipFilePath 生成的zip文件目的路径
     */
    void getJsonCompressedFild(String jsonString, String filepath, String fileName, String zipFilePath);

    /**
     * 插入非历史补费流水发票信息
     * @param map 非历史补费流水主键 ID
     * @return
     */
    int positinAppAuditInvoiceInformation(Map<String,String> map);

    /**
     * 查询发票信息
     * @param orderId
     * @return
     */
    AppAuditInvoiceInformationModel getAppAuditInvoiceInformationResult(String orderId);
}
