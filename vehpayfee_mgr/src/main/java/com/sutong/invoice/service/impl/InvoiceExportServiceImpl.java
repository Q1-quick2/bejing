package com.sutong.invoice.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sutong.invoice.mapper.InvoiceExportMapper;
import com.sutong.invoice.model.HistoryInvoiceModel;
import com.sutong.invoice.model.InvoiceModel;
import com.sutong.invoice.service.InvoiceExportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description:
 * @ClassName: InvoiceExportServiceImpl
 * @author： liyan
 * @date: 2019/12/25 15:31
 * @Version： 1.0
 */
@Component
@Service
public class InvoiceExportServiceImpl implements InvoiceExportService {
    @Autowired
    InvoiceExportMapper invoiceExportMapper;
    /**
     * @description: 历史发票列表查询
     * @auther: liyan
     * @date: 2019/12/26 11:59
     * @param: [pageNum, pageSize, historyInvoiceModel]
     * @return: com.github.pagehelper.PageInfo<com.sutong.invoice.model.HistoryInvoiceModel>
     **/
    @Override
    public PageInfo<HistoryInvoiceModel> doFindHistoryInvoiceList(int pageNum, int pageSize,HistoryInvoiceModel historyInvoiceModel){
        PageHelper.startPage(pageNum, pageSize);
        List<HistoryInvoiceModel> historyInvoice = invoiceExportMapper.doFindHistoryInvoiceList(historyInvoiceModel);
        PageInfo<HistoryInvoiceModel> pageInfo=new PageInfo<>(historyInvoice);
        return pageInfo;
    }
    /**
     * @description: 发票列表查询
     * @auther: liyan
     * @date: 2019/12/26 11:59
     * @param: [pageNum, pageSize, invoice]
     * @return: com.github.pagehelper.PageInfo<com.sutong.invoice.model.InvoiceModel>
     **/
    @Override
    public PageInfo<InvoiceModel> doFindInvoiceList(int pageNum, int pageSize,InvoiceModel invoice){
        PageHelper.startPage(pageNum, pageSize);
        List<InvoiceModel> invoiceModelList = invoiceExportMapper.doFindInvoiceList(invoice);
        List<InvoiceModel> invoiceList = new ArrayList<>();
        for(InvoiceModel invoiceModel : invoiceModelList){
            if(invoiceModel.getInvoiceVehicleId() != null) {
                //截取车牌号
                invoiceModel.setCarNum(invoiceModel.getInvoiceVehicleId().substring(0, invoiceModel.getInvoiceVehicleId().indexOf("_")));
                //截取车牌颜色
                invoiceModel.setCarNumColor(invoiceModel.getInvoiceVehicleId().
                        substring(invoiceModel.getCarNum().length() + 1, invoiceModel.getInvoiceVehicleId().length()));
            }
            invoiceList.add(invoiceModel);
        }
        PageInfo<InvoiceModel> pageInfo=new PageInfo<>(invoiceList);
        return pageInfo;
    }
    /**
     * @description: 历史发票导出
     * @auther: liyan
     * @date: 2019/12/25 18:18
     * @param: [obuId]
     * @return: java.util.List<com.sutong.invoice.model.HistoryInvoiceModel>
     **/
    @Override
    public List<HistoryInvoiceModel> doFindHistoryInvoice(String[] obuId){
        return invoiceExportMapper.doFindHistoryInvoice(obuId);
    }

    /**
     * @description: 发票导出
     * @auther: liyan
     * @date: 2019/12/25 18:19
     * @param: [orderId]
     * @return: java.util.List<com.sutong.invoice.model.HistoryInvoiceModel>
     **/
    @Override
    public List<InvoiceModel> doFindInvoice(String[] orderId){
        return invoiceExportMapper.doFindInvoice(orderId);
    }

}