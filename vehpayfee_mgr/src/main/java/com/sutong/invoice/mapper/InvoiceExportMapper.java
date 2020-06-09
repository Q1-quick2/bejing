package com.sutong.invoice.mapper;

import com.sutong.invoice.model.HistoryInvoiceModel;
import com.sutong.invoice.model.InvoiceModel;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
@Component
public interface InvoiceExportMapper {
    /**
     * @description: 历史发票列表查询
     * @auther: liyan
     * @date: 2019/12/26 12:01
     * @param: [historyInvoiceModel]
     * @return: java.util.List<com.sutong.invoice.model.HistoryInvoiceModel>
     **/
    List<HistoryInvoiceModel> doFindHistoryInvoiceList(HistoryInvoiceModel historyInvoiceModel);
    /**
     * @description: 发票列表查询
     * @auther: liyan
     * @date: 2019/12/26 12:01
     * @param: [invoiceModel]
     * @return: java.util.List<com.sutong.invoice.model.InvoiceModel>
     **/
    List<InvoiceModel> doFindInvoiceList(InvoiceModel invoiceModel);
    /**
     * @description: 历史发票导出查询
     * @auther: liyan
     * @date: 2019/12/25 18:20
     * @param: [obuId]
     * @return: java.util.List<com.sutong.invoice.model.HistoryInvoiceModel>
     **/
    List<HistoryInvoiceModel> doFindHistoryInvoice(String[] obuId);

    /**
     * @description: 发票导出查询
     * @auther: liyan
     * @date: 2019/12/25 18:20
     * @param: [orderId]
     * @return: java.util.List<com.sutong.invoice.model.HistoryInvoiceModel>
     **/
    List<InvoiceModel> doFindInvoice(String[] orderId);
}
