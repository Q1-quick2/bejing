package com.sutong.pay.mapper;

import com.sutong.bjstjh.entity.AppAuditInvoiceInformationModel;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by nt on 2019/12/24.
 */
@Repository
@Mapper
@Component
public interface AppAuditInvoiceInformationMapper {

    // 非历史发票信息

    /**
     * 根据主键查询非历史补费发票信息
     * @param model 发票信息实体类
     * @return
     */
    AppAuditInvoiceInformationModel getAppAuditInvoiceInformationModel(AppAuditInvoiceInformationModel model);

    /**
     *根据条件查询非历史补费发票信息
     * @param model 发票信息实体类
     * @return
     */
    List<AppAuditInvoiceInformationModel> getAppAuditInvoiceInformationList(AppAuditInvoiceInformationModel model);

    /**
     * 存入非历史补费发票信息
     * @param model 发票信息实体类
     * @return
     */
    int insertAppAuditInvoiceInformation(AppAuditInvoiceInformationModel model);

    /**
     * 修改非历史补费发票信息
     * @param model 发票信息实体类
     * @return
     */
    int updateAppAuditInvoiceInformation(AppAuditInvoiceInformationModel model);

    /**
     * 删除非历史补费发票信息
     * @param model 发票信息实体类
     * @return
     */
    int deteleAppAuditInvoiceInformation(AppAuditInvoiceInformationModel model);




    //  历史发票信息


    /**
     *根据主键查询历史补费发票信息
     * @param model 发票信息实体类
     * @return
     */
    AppAuditInvoiceInformationModel getAppAuditInvoiceInformationPastModel(AppAuditInvoiceInformationModel model);

    /**
     *根据条件查询历史补费发票信息
     * @param model
     * @return
     */
    List<AppAuditInvoiceInformationModel> getAppAuditInvoiceInformationPastList(AppAuditInvoiceInformationModel model);

    /**
     * 存入历史补费发票信息
     * @param model 发票信息实体类
     * @return
     */
    int insertAppAuditInvoiceInformationPast(AppAuditInvoiceInformationModel model);

    /**
     * 修改历史补费发票信息
     * @param model 发票信息实体类
     * @return
     */
    int updateAppAuditInvoiceInformationPast(AppAuditInvoiceInformationModel model);

    /**
     * 删除历史补费发票信息
     * @param model 发票信息实体类
     * @return
     */
    int deleteAppAuditInvoiceInformationPast(AppAuditInvoiceInformationModel model);


}
