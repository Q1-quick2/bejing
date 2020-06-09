package com.sutong.pay.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.sutong.bjstjh.entity.AppAuditInvoiceInformationModel;
import com.sutong.pay.mapper.AppAuditInvoiceInformationMapper;
import com.sutong.pay.service.AppAuditInvoiceInformationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

/**
 * Created by nt on 2020/1/5.
 */
@Service
@Component
public class AppAuditInvoiceInformationServiceImpl implements AppAuditInvoiceInformationService {

    @Autowired
    private AppAuditInvoiceInformationMapper mapper;
    // 非历史发票信息

    /**
     * 根据主键查询非历史补费发票信息
     * @param model 发票信息实体类
     * @return
     */
    @Override
    public AppAuditInvoiceInformationModel getAppAuditInvoiceInformationModelService(AppAuditInvoiceInformationModel model){
        return mapper.getAppAuditInvoiceInformationModel(model);
    };

    /**
     *根据条件查询非历史补费发票信息
     * @param model 发票信息实体类
     * @return
     */
    @Override
    public List<AppAuditInvoiceInformationModel> getAppAuditInvoiceInformationListService(AppAuditInvoiceInformationModel model){
       return mapper.getAppAuditInvoiceInformationList(model);
    };

    /**
     * 存入非历史补费发票信息
     * @param model 发票信息实体类
     * @return
     */
    @Override
    public int positinAppAuditInvoiceInformationService(AppAuditInvoiceInformationModel model){
        model.setInvoiceId(UUID.randomUUID().toString().replace("-","") + "" + System.currentTimeMillis());
        return mapper.insertAppAuditInvoiceInformation(model);
    };

    /**
     * 修改非历史补费发票信息
     * @param model 发票信息实体类
     * @return
     */
    @Override
    public int updateAppAuditInvoiceInformationService(AppAuditInvoiceInformationModel model){

        return mapper.updateAppAuditInvoiceInformation(model);
    };

    /**
     * 删除非历史补费发票信息
     * @param model 发票信息实体类
     * @return
     */
    @Override
    public int deteleAppAuditInvoiceInformationService(AppAuditInvoiceInformationModel model){

        return mapper.deteleAppAuditInvoiceInformation(model);
    };




    //  历史发票信息


    /**
     *根据主键查询历史补费发票信息
     * @param model 发票信息实体类
     * @return
     */
    @Override
    public AppAuditInvoiceInformationModel getAppAuditInvoiceInformationPastModelService(AppAuditInvoiceInformationModel model){

        return mapper.getAppAuditInvoiceInformationPastModel(model);
    };

    /**
     *根据条件查询历史补费发票信息
     * @param model
     * @return
     */
    @Override
    public List<AppAuditInvoiceInformationModel> getAppAuditInvoiceInformationPastListService(AppAuditInvoiceInformationModel model){

        return mapper.getAppAuditInvoiceInformationPastList(model);
    };

    /**
     * 存入历史补费发票信息
     * @param model 发票信息实体类
     * @return
     */
    @Override
    public int positinAppAuditInvoiceInformationPastService(AppAuditInvoiceInformationModel model){
        model.setInvoicePastId(UUID.randomUUID().toString().replace("-","") + "" + System.currentTimeMillis());
        return mapper.insertAppAuditInvoiceInformationPast(model);
    };

    /**
     * 修改历史补费发票信息
     * @param model 发票信息实体类
     * @return
     */
    @Override
    public int updateAppAuditInvoiceInformationPastService(AppAuditInvoiceInformationModel model){

        return mapper.updateAppAuditInvoiceInformationPast(model);
    };

    /**
     * 删除历史补费发票信息
     * @param model 发票信息实体类
     * @return
     */
    @Override
    public int deleteAppAuditInvoiceInformationPastService(AppAuditInvoiceInformationModel model){
        return mapper.deleteAppAuditInvoiceInformationPast(model);
    };
}
