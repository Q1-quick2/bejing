package com.sutong.invoice.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;
import com.sutong.bjstjh.result.Result;
import com.sutong.invoice.model.HistoryInvoiceModel;
import com.sutong.invoice.model.InvoiceModel;
import com.sutong.invoice.service.InvoiceExportService;
import org.apache.poi.xssf.usermodel.*;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;

/**
 * @Description:
 * @ClassName: InvoiceExportController
 * @author： liyan
 * @date: 2019/12/25 10:48
 * @Version： 1.0
 */
@RestController
@CrossOrigin
public class InvoiceExportController {
    @Reference
    InvoiceExportService invoiceExportService;
    /**
     * @description: 历史发票列表查询
     * @auther: liyan
     * @date: 2019/12/26 12:02
     * @param: [historyInvoiceModel, pageNum, pageSize]
     * @return: com.sutong.bjstjh.result.Result
     **/
    @RequestMapping(value = "/findHistoryInvoice")
    public Result FindHistoryInvoice (@ModelAttribute("historyInvoiceModel")HistoryInvoiceModel historyInvoiceModel,
                                      @RequestParam(value = "pageNum",defaultValue = "1")int pageNum,
                                      @RequestParam(value = "pageSize",defaultValue = "10")int pageSize){
        PageInfo<HistoryInvoiceModel> pageInfo = invoiceExportService.doFindHistoryInvoiceList(pageNum,pageSize,historyInvoiceModel);
        return Result.ok().data("pageInfo",pageInfo);

    }
    /**
     * @description: 发票列表查询
     * @auther: liyan
     * @date: 2019/12/26 12:02
     * @param: [invoiceModel, pageNum, pageSize]
     * @return: com.sutong.bjstjh.result.Result
     **/
    @RequestMapping(value = "/findInvoice")
    public Result FindInvoice (@ModelAttribute("invoiceModel")InvoiceModel invoiceModel,
                                      @RequestParam(value = "pageNum",defaultValue = "1")int pageNum,
                                      @RequestParam(value = "pageSize",defaultValue = "10")int pageSize){
//        //截取车牌号
//        invoiceModel.setCarNum(invoiceModel.getInvoiceVehicleId().substring(0, invoiceModel.getInvoiceVehicleId().indexOf("_")));
//        //截取车牌颜色
//        invoiceModel.setCarNumColor(invoiceModel.getInvoiceVehicleId().
//                substring(invoiceModel.getCarNum().length()+1,invoiceModel.getInvoiceVehicleId().length()));
        PageInfo<InvoiceModel> pageInfo = invoiceExportService.doFindInvoiceList(pageNum,pageSize,invoiceModel);
        return Result.ok().data("pageInfo",pageInfo);

    }

    /**
     * @description: 历史发票导出
     * @auther: liyan
     * @date: 2019/12/25 18:29
     * @param: [obuId, response]
     * @return: void
     **/
    @RequestMapping(value = "/historyInvoice")
    public void InvoiceHistoryDownload(@RequestParam("obuId") String obuId, HttpServletResponse response) throws IOException {
        //表头数据
        String[] header = {"OBU号", "购买方名称", "购买方纳税识别号", "购买方电话", "购买方开户行及账号","购买方补费合计","销售方联系方式",
                "销售方纳税识别号","销售方开户行及账号","收款人","复核人","开票人","开票日期","发票类型","订单号","第三方支付订单号","补费时间",
                "收件人邮箱","收件人电话","收件人姓名","收件人详细地址"};

        String[] obu = obuId.split(",");
        List<HistoryInvoiceModel> historyInvoiceModel = invoiceExportService.doFindHistoryInvoice(obu);

        //声明一个工作簿
        XSSFWorkbook workbook = new XSSFWorkbook();

        //生成一个表格，设置表格名称为"学生表"
        XSSFSheet sheet = workbook.createSheet("发票");

        //设置表格列宽度为10个字节
        sheet.setDefaultColumnWidth(20);

        //创建第一行表头
        XSSFRow headrow = sheet.createRow(0);

        //遍历添加表头
        for (int i = 0; i < header.length; i++) {
            //创建一个单元格
            XSSFCell cell = headrow.createCell(i);

            //创建一个内容对象
            XSSFRichTextString text = new XSSFRichTextString(header[i]);

            //将内容对象的文字内容写入到单元格中
            cell.setCellValue(text);
        }

        //模拟遍历结果集，把内容加入表格
        int rowNum=1;
        for(HistoryInvoiceModel historyInvoice:historyInvoiceModel){
            XSSFRow row = sheet.createRow(rowNum);
            row.createCell(0).setCellValue(historyInvoice.getObuId());
            row.createCell(1).setCellValue(historyInvoice.getBuyerName());
            row.createCell(2).setCellValue(historyInvoice.getBuyerRatepayingNumber());
            row.createCell(3).setCellValue(historyInvoice.getBuyerPhone());
            row.createCell(4).setCellValue(historyInvoice.getBuyerBankNumber());
            row.createCell(5).setCellValue(historyInvoice.getBuyerPaybackTotal());
            row.createCell(6).setCellValue(historyInvoice.getSellerPhone());
            row.createCell(7).setCellValue(historyInvoice.getSellerRatepayingNumber());
            row.createCell(8).setCellValue(historyInvoice.getSellerBankNumber());
            row.createCell(9).setCellValue(historyInvoice.getPayee());
            row.createCell(10).setCellValue(historyInvoice.getRevier());
            row.createCell(11).setCellValue(historyInvoice.getDrawer());
            row.createCell(12).setCellValue(historyInvoice.getOpenInvoiceTime());
            row.createCell(13).setCellValue(historyInvoice.getInvoiceType());
            row.createCell(14).setCellValue(historyInvoice.getOrderNumber());
            row.createCell(15).setCellValue(historyInvoice.getCommodityOrderNo());
            row.createCell(16).setCellValue(historyInvoice.getFeeTransTime());
            row.createCell(17).setCellValue(historyInvoice.getPersonalMailBox());
            row.createCell(18).setCellValue(historyInvoice.getPersonalPhone());
            row.createCell(19).setCellValue(historyInvoice.getPersonalName());
            row.createCell(20).setCellValue(historyInvoice.getPersonalDetailedAdress());
            rowNum++;

        }

        //准备将Excel的输出流通过response输出到页面下载
        //八进制输出流
        response.setContentType("application/octet-stream");

        //这后面可以设置导出Excel的名称
        String fileName="发票.xls";
        response.setHeader("Content-disposition", "attachment;filename="+ URLEncoder.encode(fileName,"UTF-8"));

        //刷新缓冲
        response.flushBuffer();

        //workbook将Excel写入到response的输出流中，供页面下载
        workbook.write(response.getOutputStream());
    }

    /**
     * @description: 发票导出
     * @auther: liyan
     * @date: 2019/12/25 18:29
     * @param: [orderId, response]
     * @return: void
     **/
    @RequestMapping(value = "/invoice")
    public void InvoiceDownload(@RequestParam("orderId") String orderId, HttpServletResponse response) throws IOException {
        //表头数据
        String[] header = {"工单编号","车牌号","车牌颜色","购买方名称", "购买方纳税识别号", "购买方电话", "购买方开户行及账号","购买方补费合计","销售方联系方式",
                "销售方纳税识别号","销售方开户行及账号","收款人","复核人","开票人","开票日期","发票类型","订单号","第三方支付订单号","补费时间",
                "收件人邮箱","收件人电话","收件人姓名","收件人详细地址"};

        String[] order = orderId.split(",");
        List<InvoiceModel> InvoiceModel = invoiceExportService.doFindInvoice(order);

        //声明一个工作簿
        XSSFWorkbook workbook = new XSSFWorkbook();

        //生成一个表格，设置表格名称为"学生表"
        XSSFSheet sheet = workbook.createSheet("发票");

        //设置表格列宽度为10个字节
        sheet.setDefaultColumnWidth(20);

        //创建第一行表头
        XSSFRow headrow = sheet.createRow(0);

        //遍历添加表头
        for (int i = 0; i < header.length; i++) {
            //创建一个单元格
            XSSFCell cell = headrow.createCell(i);

            //创建一个内容对象
            XSSFRichTextString text = new XSSFRichTextString(header[i]);

            //将内容对象的文字内容写入到单元格中
            cell.setCellValue(text);
        }

        //模拟遍历结果集，把内容加入表格
        int rowNum=1;
        for(InvoiceModel Invoice:InvoiceModel){
            XSSFRow row = sheet.createRow(rowNum);
            row.createCell(0).setCellValue(Invoice.getOrderId());
            if(Invoice.getInvoiceVehicleId() != null) {
                //截取车牌号
                Invoice.setCarNum(Invoice.getInvoiceVehicleId().substring(0, Invoice.getInvoiceVehicleId().indexOf("_")));
                //截取车牌颜色
                String color = Invoice.getInvoiceVehicleId().
                        substring(Invoice.getCarNum().length() + 1, Invoice.getInvoiceVehicleId().length());
                if(color.equals("0")){
                    Invoice.setCarNumColor("蓝");
                }
                if(color.equals("1")){
                    Invoice.setCarNumColor("黄");
                }
                if(color.equals("2")){
                    Invoice.setCarNumColor("黑");
                }
                if(color.equals("3")){
                    Invoice.setCarNumColor("白");
                }
                if(color.equals("4")){
                    Invoice.setCarNumColor("绿白");
                }
                if(color.equals("5")){
                    Invoice.setCarNumColor("绿黄");
                }
                if(color.equals("6")){
                    Invoice.setCarNumColor("绿");
                }
            }
            row.createCell(1).setCellValue(Invoice.getCarNum());
            row.createCell(2).setCellValue(Invoice.getCarNumColor());
            row.createCell(3).setCellValue(Invoice.getBuyerName());
            row.createCell(4).setCellValue(Invoice.getBuyerRatepayingNumber());
            row.createCell(5).setCellValue(Invoice.getBuyerPhone());
            row.createCell(6).setCellValue(Invoice.getBuyerBankNumber());
            row.createCell(7).setCellValue(Invoice.getBuyerPaybackTotal());
            row.createCell(8).setCellValue(Invoice.getSellerPhone());
            row.createCell(9).setCellValue(Invoice.getSellerRatepayingNumber());
            row.createCell(10).setCellValue(Invoice.getSellerBankNumber());
            row.createCell(11).setCellValue(Invoice.getPayee());
            row.createCell(12).setCellValue(Invoice.getRevier());
            row.createCell(13).setCellValue(Invoice.getDrawer());
            row.createCell(14).setCellValue(Invoice.getOpenInvoiceTime());
            row.createCell(15).setCellValue(Invoice.getInvoiceType());
            row.createCell(16).setCellValue(Invoice.getOrderNumber());
            row.createCell(17).setCellValue(Invoice.getCommodityOrderNo());
            row.createCell(18).setCellValue(Invoice.getFeeTransTime());
            row.createCell(19).setCellValue(Invoice.getPersonalMailBox());
            row.createCell(20).setCellValue(Invoice.getPersonalPhone());
            row.createCell(21).setCellValue(Invoice.getPersonalName());
            row.createCell(22).setCellValue(Invoice.getPersonalDetailedAdress());
            rowNum++;
        }

        //准备将Excel的输出流通过response输出到页面下载
        //八进制输出流
        response.setContentType("application/octet-stream");

        //这后面可以设置导出Excel的名称
        String fileName="发票.xls";
        response.setHeader("Content-disposition", "attachment;filename="+ URLEncoder.encode(fileName,"UTF-8"));

        //刷新缓冲
        response.flushBuffer();

        //workbook将Excel写入到response的输出流中，供页面下载
        workbook.write(response.getOutputStream());
    }
}