package com.sutong.pay.service;

import com.sutong.bjstjh.entity.AppAuditInvoiceInformationModel;

import java.util.List;

/**
 * Created by nt on 2020/1/5.
 */
public interface AppAuditInvoiceInformationService {

    // 非历史发票信息

    /**
     * 根据主键查询非历史补费发票信息
     * @param model 发票信息实体类
     * @return
     */
    AppAuditInvoiceInformationModel getAppAuditInvoiceInformationModelService(AppAuditInvoiceInformationModel model);

    /**
     *根据条件查询非历史补费发票信息
     * @param model 发票信息实体类
     * @return
     */
    List<AppAuditInvoiceInformationModel> getAppAuditInvoiceInformationListService(AppAuditInvoiceInformationModel model);

    /**
     * 存入非历史补费发票信息
     * @param model 发票信息实体类
     * @return
     */
    int positinAppAuditInvoiceInformationService(AppAuditInvoiceInformationModel model);

    /**
     * 修改非历史补费发票信息
     * @param model 发票信息实体类
     * @return
     */
    int updateAppAuditInvoiceInformationService(AppAuditInvoiceInformationModel model);

    /**
     * 删除非历史补费发票信息
     * @param model 发票信息实体类
     * @return
     */
    int deteleAppAuditInvoiceInformationService(AppAuditInvoiceInformationModel model);




    //  历史发票信息


    /**
     *根据主键查询历史补费发票信息
     * @param model 发票信息实体类
     * @return
     */
    AppAuditInvoiceInformationModel getAppAuditInvoiceInformationPastModelService(AppAuditInvoiceInformationModel model);

    /**
     *根据条件查询历史补费发票信息
     * @param model
     * @return
     */
    List<AppAuditInvoiceInformationModel> getAppAuditInvoiceInformationPastListService(AppAuditInvoiceInformationModel model);

    /**
     * 存入历史补费发票信息
     * @param model 发票信息实体类
     * @return
     */
    int positinAppAuditInvoiceInformationPastService(AppAuditInvoiceInformationModel model);

    /**
     * 修改历史补费发票信息
     * @param model 发票信息实体类
     * @return
     */
    int updateAppAuditInvoiceInformationPastService(AppAuditInvoiceInformationModel model);

    /**
     * 删除历史补费发票信息
     * @param model 发票信息实体类
     * @return
     */
    int deleteAppAuditInvoiceInformationPastService(AppAuditInvoiceInformationModel model);
}
