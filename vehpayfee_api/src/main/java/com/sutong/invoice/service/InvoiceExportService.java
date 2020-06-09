package com.sutong.invoice.service;

import com.github.pagehelper.PageInfo;
import com.sutong.invoice.model.HistoryInvoiceModel;
import com.sutong.invoice.model.InvoiceModel;

import java.util.List;

public interface InvoiceExportService {

    /**
     * @description: 历史发票列表查询
     * @auther: liyan
     * @date: 2019/12/26 12:00
     * @param: [pageNum, pageSize, historyInvoiceModel]
     * @return: com.github.pagehelper.PageInfo<com.sutong.invoice.model.HistoryInvoiceModel>
     **/
    PageInfo<HistoryInvoiceModel> doFindHistoryInvoiceList(int pageNum, int pageSize, HistoryInvoiceModel historyInvoiceModel);
    /**
     * @description: 发票列表查询
     * @auther: liyan
     * @date: 2019/12/26 12:00
     * @param: [pageNum, pageSize, invoiceModel]
     * @return: com.github.pagehelper.PageInfo<com.sutong.invoice.model.InvoiceModel>
     **/
    PageInfo<InvoiceModel> doFindInvoiceList(int pageNum, int pageSize,InvoiceModel invoiceModel);
    /**
     * @description: 历史发票导出
     * @auther: liyan
     * @date: 2019/12/25 18:17
     * @param: [obuId]
     * @return: java.util.List<com.sutong.invoice.model.HistoryInvoiceModel>
     **/
    List<HistoryInvoiceModel> doFindHistoryInvoice(String[] obuId);
    /**
     * @description: 发票导出
     * @auther: liyan
     * @date: 2019/12/25 18:18
     * @param: [orderId]
     * @return: java.util.List<com.sutong.invoice.model.HistoryInvoiceModel>
     **/
    List<InvoiceModel> doFindInvoice(String[] orderId);
}
