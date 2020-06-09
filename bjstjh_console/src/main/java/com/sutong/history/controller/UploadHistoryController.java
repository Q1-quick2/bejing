package com.sutong.history.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;
import com.sutong.bjstjh.result.Result;
import com.sutong.history.model.PaidHistoryModel;
import com.sutong.history.model.PastHistoryModel;
import com.sutong.history.model.PastInfoHistoryModel;
import com.sutong.history.service.UploadHistoryService;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * @ClassName UploadHistoryController
 * @Description 历史数据上传
 * @Author ly
 * @Date 2019/12/13 16:36
 */
@RestController
@CrossOrigin
public class UploadHistoryController {

    @Reference(timeout = 300000)
    private UploadHistoryService uploadHistoryService;

    // 绑定变量名字和属性，参数封装进类
    @InitBinder("payHistory")
    public void initBinderPayHistory(WebDataBinder binder) {
        binder.setFieldDefaultPrefix("payHistory.");
    }

    /**
     * @description: 查询历史补费分页
     * @auther: Mr.kong
     * @date: 2019/12/22 11:18
     * @Param payHistoryModel:历史补费实体
     * @Param pageNum:当前页码
     * @Param pageSize:分页条数
     * @return: com.sutong.bjstjh.result.Result
     **/
    @RequestMapping("/payHistory/page")
    public Result doFindPayHistoryPage(@ModelAttribute("payHistory")PaidHistoryModel payHistoryModel,
                                       @RequestParam(value = "pageNum",defaultValue = "1")int pageNum,
                                       @RequestParam(value = "pageSize",defaultValue = "10")int pageSize){
        PageInfo<PaidHistoryModel> pageInfo = uploadHistoryService.doFindPayHistoryPage(pageNum, pageSize, payHistoryModel);
        return Result.ok().data("pageInfo",pageInfo);
    }


    /**
     * @description: 查询历史补费详情
     * @auther: Mr.kong
     * @date: 2019/12/22 13:58
     * @Param id: 主键id
     * @return: com.sutong.bjstjh.result.Result
     **/
    @RequestMapping("/payHistory/info")
    public Result doFindPayHistoryInfo(@RequestParam(value = "id")Integer id){
        PaidHistoryModel paidHistoryModel = uploadHistoryService.doFindPayHistoryInfoById(id);
        if(paidHistoryModel.getPaidStatus().equals("1")){
            paidHistoryModel.setPaidStatus("已缴费");
        }else{
            paidHistoryModel.setPaidStatus("未缴费");
        }
        return Result.ok().data("paidHistoryModel",paidHistoryModel);
    }

    //补费历史导入
//    @RequestMapping(value = "/paid", method = RequestMethod.POST)
//    public Result Paid(@RequestParam("file") MultipartFile file){//, HttpServletRequest request
//        Result res;
//        try {
//            InputStream is = file.getInputStream();
//            //获取文件名
//            String fileName = file.getOriginalFilename();
//            res = this.paid(fileName, is);//request
//        }catch (Exception e){
//            e.printStackTrace();
//            res = Result.error();
//        }
//        return res;
//    }

    /**
     * @description: 历史逃费记录导入
     * @auther: liyan
     * @date: 2020/1/6 14:16
     * @param: [file 导入excel]
     * @return: com.sutong.bjstjh.result.Result
     **/
    @RequestMapping(value = "/payHistory/past", method = RequestMethod.POST)
    public Result PastOder(@RequestParam("file") MultipartFile file){
        Result res;
        try {
            String fileName = file.getOriginalFilename();
            InputStream is = file.getInputStream();
            res = this.past(fileName, is);
        }catch(Exception e){
            e.printStackTrace();
            res=Result.error();
        }
        return res;
    }

    /**
     * @description: 模板下载
     * @auther: liyan
     * @date: 2019/12/23 11:15
     * @param: [type]
     * @return: void
     **/
    @RequestMapping(value = "/payHistory/download")//, method = RequestMethod.POST)
    public void  DownloadTemplate(//@RequestParam("type") String type,
                                  HttpServletResponse response) throws IOException{

        String fileName = "历史逃费导入.xlsx";//"历史逃费导入.xlsx";
//        String downloadFilePath = this.getClass().getResource("/").getPath();
        String downloadFilePath = this.getClass().getResource("/").getPath()+"/fileTemplate/"+fileName;//被下载的文件在服务器中的路径

        File file = new File(downloadFilePath);
        if (file.exists()) {
            response.setContentType("application/force-download");// 设置强制下载不打开            
            response.addHeader("Content-Disposition", "attachment;fileName=" + URLEncoder.encode(fileName, "UTF-8"));
            byte[] buffer = new byte[1024];
            FileInputStream fis = null;
            BufferedInputStream bis = null;
            try {
                fis = new FileInputStream(file);
                bis = new BufferedInputStream(fis);
                OutputStream outputStream = response.getOutputStream();
                int i = bis.read(buffer);
                while (i != -1) {
                    outputStream.write(buffer, 0, i);
                    i = bis.read(buffer);
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (bis != null) {
                    try {
                        bis.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (fis != null) {
                    try {
                        fis.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

    }

    /**
     * @description: 补费历史导入
     * @auther: liyan
     * @date: 2019/12/15 11:04
     * @param: [fileName, is]
     * @return: com.sutong.bjstjh.result.Result
     **/
//    public Result paid(String fileName, InputStream is){//,HttpServletRequest request
//        String reMes = null;
//        boolean notNull = false;
//        try {
//            if (!fileName.matches("^.+\\.(?i)(xlsx)$")) {
//                return Result.error().message("请使用与下载模板一样的xlsx文件类型！");
//            }
//            Workbook wb = new XSSFWorkbook(is);//null
//            Sheet sheet = wb.getSheetAt(0);
//            if (sheet != null) {
//                notNull = true;
//            }
//            if(notNull){
//                String result = this.paidExcel(wb);
//                if(!result.equals("成功！")){
//                    return Result.error().message(result);
//                }
//            }
//        }catch (Exception e){
//            e.printStackTrace();
//            return Result.error();//.message("数据解析失败，请检查数据后重新上传！");
//        }
//        return Result.ok();
//    }

    /**
     * @description: 历史逃费主表导入解析
     * @auther: liyan
     * @date: 2019/12/19 11:19
     * @Param MultipartFile: Excel
     * @return: String
     **/
    public Result past(String fileName, InputStream is){
        boolean notNull = false;
        try {
            if (!fileName.matches("^.+\\.(?i)(xls)$") && !fileName.matches("^.+\\.(?i)(xlsx)$")) {
                return Result.error().message("请使用与下载模板一样的xlsx文件类型！");
            }
            Workbook wb = null;
            if (fileName.endsWith("xls")) {
                wb = new HSSFWorkbook(is);
            } else if (fileName.endsWith("xlsx")) {
                wb = new XSSFWorkbook(is);
            }
            Sheet sheet = wb.getSheetAt(0);
            if (sheet != null) {
                notNull = true;
            }
            if(notNull){
               String result = this.pastExcel(wb);
                if(!result.equals("成功！")){
                    return Result.error().message(result);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            return Result.error().message("导入文件解析失败，请检查后重新上传！");//.message("文件格式错误，请检查后重新上传！");
        }
        return Result.ok();
    }


    /**
     * @description: 解析excel给实体类赋值
     * @auther: liyan
     * @date: 2019/12/11 11:19
     * @Param PaidHistoryModel: 补费历史导入
     * @return: List
     **/
//    private String paidExcel(Workbook workbook) throws IOException {
//
//        List<PaidHistoryModel> paidHistoryModel = new ArrayList();
//
//        //获取一共有多少sheet,遍历
//        int numberOfSheets = workbook.getNumberOfSheets();
//        Cell cell = null;
//        for (int i=0; i<numberOfSheets; i++) {
//            Sheet sheet = workbook.getSheetAt(i);
//            //获取sheet中有多少行，遍历每一行
//            int physicalNumberOfRows = sheet.getPhysicalNumberOfRows();
//            if(physicalNumberOfRows>1001){
//                return "每次上传数据请少于1000条!";
//            }
//            for (int j=0;j<physicalNumberOfRows;j++) {
//                if (j == 0) {
//                    continue;//标题行
//                }
//                PaidHistoryModel paidHistory = new PaidHistoryModel();
//                Row row = sheet.getRow(j);//获得当前行数据
//                row.getCell(0).setCellType(XSSFCell.CELL_TYPE_STRING);
//                row.getCell(1).setCellType(XSSFCell.CELL_TYPE_STRING);
//                row.getCell(2).setCellType(XSSFCell.CELL_TYPE_STRING);
//                row.getCell(3).setCellType(XSSFCell.CELL_TYPE_STRING);
//                row.getCell(4).setCellType(XSSFCell.CELL_TYPE_STRING);
//                row.getCell(5).setCellType(XSSFCell.CELL_TYPE_STRING);
//                row.getCell(6).setCellType(XSSFCell.CELL_TYPE_STRING);
//                row.getCell(7).setCellType(XSSFCell.CELL_TYPE_STRING);
//                row.getCell(8).setCellType(XSSFCell.CELL_TYPE_STRING);
//                //完整的循环一次 就组成了一个对象
//                if (!row.getCell(0).getStringCellValue().equals("") && !row.getCell(1).getStringCellValue().equals("")
//                        && !row.getCell(2).getStringCellValue().equals("") && !row.getCell(3).getStringCellValue().equals("")
//                        && !row.getCell(4).getStringCellValue().equals("") && !row.getCell(5).getStringCellValue().equals("")
//                        && !row.getCell(6).getStringCellValue().equals("") && !row.getCell(7).getStringCellValue().equals("")
//                        && !row.getCell(8).getStringCellValue().equals("")) {
//                    paidHistory.setCarNum(row.getCell(0).getStringCellValue());
//                    paidHistory.setLicensePlateColor(row.getCell(1).getStringCellValue());
//                    paidHistory.setToBePaidAmount(row.getCell(2).getStringCellValue());
//                    paidHistory.setCustomerName(row.getCell(3).getStringCellValue());
//                    paidHistory.setPaidTime(row.getCell(4).getStringCellValue());
//                    paidHistory.setPaidAmount(row.getCell(5).getStringCellValue());
//                    paidHistory.setPaymentFormation(row.getCell(6).getStringCellValue());
//                    paidHistory.setPaidStatus(row.getCell(7).getStringCellValue());
//                    paidHistory.setInvoiceNum(row.getCell(8).getStringCellValue());
//                    paidHistory.setSendSMSStatus("1");
//
//                    paidHistoryModel.add(paidHistory);
//                }else {
//                    return "表格数据不能有空值！";
//                }
//            }
//        }
//        uploadHistoryService.paid(paidHistoryModel);
//        return "成功！";
//    }

    /**
     * @description: 解析excel给实体类赋值
     * @auther: liyan
     * @date: 2019/12/11 11:19
     * @Param PaidHistoryModel: 历史逃费详情和主表导入
     * @return: List
     **/
    private String pastExcel(Workbook workbook) {

        List<PastHistoryModel> pastHistoryModel = new ArrayList();
        List<PastInfoHistoryModel> pastInfoHistorymodel = new ArrayList();
        try {
            //获取一共有多少sheet
            int numberOfSheets = workbook.getNumberOfSheets();
            Cell cell = null;
            for (int i = 0; i < numberOfSheets; i++) {
                Sheet sheet = workbook.getSheetAt(i);
                //获取sheet中有多少行，遍历每一行
                int rowNum = sheet.getPhysicalNumberOfRows();//getLastRowNum();
                if (rowNum > 1003) {
                    return "每次上传数据请少于1000条!";
                }
                for (int j = 0; j < rowNum; j++) {
                    if (j < 3) {
                        continue;//标题行
                    }
                    if (i == 0) {
                        PastInfoHistoryModel pastInfoHistory = new PastInfoHistoryModel();
                        Row row = sheet.getRow(j);//获得当前行数据
                        if (row == null) continue;
                        //该行为空，则跳出本次循环
                        if (row.getCell(0).getCellType() == Cell.CELL_TYPE_BLANK && row.getCell(1).getCellType() == Cell.CELL_TYPE_BLANK
                                && row.getCell(2).getCellType() == Cell.CELL_TYPE_BLANK && row.getCell(3).getCellType() == Cell.CELL_TYPE_BLANK
                                && row.getCell(4).getCellType() == Cell.CELL_TYPE_BLANK && row.getCell(5).getCellType() == Cell.CELL_TYPE_BLANK
                                && row.getCell(6).getCellType() == Cell.CELL_TYPE_BLANK && row.getCell(7).getCellType() == Cell.CELL_TYPE_BLANK
                                && row.getCell(8).getCellType() == Cell.CELL_TYPE_BLANK && row.getCell(9).getCellType() == Cell.CELL_TYPE_BLANK
                                && row.getCell(10).getCellType() == Cell.CELL_TYPE_BLANK && row.getCell(11).getCellType() == Cell.CELL_TYPE_BLANK
                                && row.getCell(12).getCellType() == Cell.CELL_TYPE_BLANK) {
                            continue;
                        }
//                    if(row.getCell(0).equals(" ") || row.getCell(1).equals(" ") || row.getCell(2).equals(" ") || row.getCell(3).equals(" ")
//                            || row.getCell(4).equals(" ") || row.getCell(5).equals(" ") || row.getCell(6).equals(" ") || row.getCell(7).equals(" ")
//                            || row.getCell(8).equals(" ") || row.getCell(9).equals(" ") || row.getCell(10).equals(" ") || row.getCell(11).equals(" ")
//                            || row.getCell(12).equals(" ")){
//                        return "表格数据不能有空值！";
//                    }
                        //判断单元格是否为空
//                        if (row.getCell(0).getCellType() == Cell.CELL_TYPE_BLANK || row.getCell(1).getCellType() == Cell.CELL_TYPE_BLANK
//                                || row.getCell(2).getCellType() == Cell.CELL_TYPE_BLANK || row.getCell(3).getCellType() == Cell.CELL_TYPE_BLANK
//                                || row.getCell(4).getCellType() == Cell.CELL_TYPE_BLANK || row.getCell(5).getCellType() == Cell.CELL_TYPE_BLANK
//                                || row.getCell(6).getCellType() == Cell.CELL_TYPE_BLANK || row.getCell(7).getCellType() == Cell.CELL_TYPE_BLANK
//                                || row.getCell(8).getCellType() == Cell.CELL_TYPE_BLANK || row.getCell(9).getCellType() == Cell.CELL_TYPE_BLANK
//                                || row.getCell(10).getCellType() == Cell.CELL_TYPE_BLANK || row.getCell(11).getCellType() == Cell.CELL_TYPE_BLANK
//                                || row.getCell(12).getCellType() == Cell.CELL_TYPE_BLANK) {
//                            return "汇总单表格数据不能有空值！";
//                        }
                        //金额不能<0
                        if(row.getCell(7).getNumericCellValue()<0 || row.getCell(8).getNumericCellValue()<0
                                || row.getCell(9).getNumericCellValue()<0){
                            return "汇总单OBU号："+StringTrim(getCellValue(row.getCell(11)))+"的金额不能为负数！";
                        }
                        pastInfoHistory.setEnstation(StringTrim(getCellValue(row.getCell(0))));
                        pastInfoHistory.setExstation(StringTrim(getCellValue(row.getCell(1))));
                        pastInfoHistory.setTransVehicleType(StringTrim(getCellValue(row.getCell(2))));
                        pastInfoHistory.setDoVehicleType(StringTrim(getCellValue(row.getCell(3))));
                        pastInfoHistory.setVehicleType(StringTrim(getCellValue(row.getCell(4))));
                        pastInfoHistory.setTransVehicleId(StringTrim(getCellValue(row.getCell(5))));
                        pastInfoHistory.setDoVehicleId(StringTrim(getCellValue(row.getCell(6))));
                        int transSubtrFee = ((int) (row.getCell(7).getNumericCellValue() * 100));//元转分
                        pastInfoHistory.setTransSubtrFee(String.valueOf(transSubtrFee));
                        int owFee = ((int) (row.getCell(8).getNumericCellValue() * 100));//元转分
                        pastInfoHistory.setOweFee(String.valueOf(owFee));
                        int subtrFee = ((int) (row.getCell(9).getNumericCellValue() * 100));//元转分
                        pastInfoHistory.setSubtrFee(String.valueOf(subtrFee));
                        //如果日期是/格式，转换成-存到数据库中
                        if(getCellValue(row.getCell(10)).substring(4,5).equals("/")){
                            pastInfoHistory.setTransTime(getCellValue(row.getCell(10)).replace("/","-"));
                        }else{
                            pastInfoHistory.setTransTime(getCellValue(row.getCell(10)));
                        }

                        pastInfoHistory.setTransObuId(StringTrim(getCellValue(row.getCell(11))));
                        pastInfoHistory.setTransCardId(StringTrim(getCellValue(row.getCell(12))));
                        pastInfoHistory.setOrderStatus("2");
                        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
                        pastInfoHistory.setId(uuid);
                        //判断字段是否有空值
//                        if (pastInfoHistory.getEnstation().equals("") || pastInfoHistory.getExstation().equals("")
//                                || pastInfoHistory.getTransVehicleType().equals("") || pastInfoHistory.getDoVehicleType().equals("")
//                                || pastInfoHistory.getDoVehicleType().equals("") || pastInfoHistory.getTransVehicleId().equals("")
//                                || pastInfoHistory.getDoVehicleId().equals("") || pastInfoHistory.getTransSubtrFee().equals("")
//                                || pastInfoHistory.getOweFee().equals("") || pastInfoHistory.getSubtrFee().equals("")
//                                || pastInfoHistory.getTransTime().equals("") || pastInfoHistory.getTransObuId().equals("")
//                                || pastInfoHistory.getTransCardId().equals("")) {
//                            return "汇总单表格数据不能有空值！";
//                        }
                        pastInfoHistorymodel.add(pastInfoHistory);

                    }
                    if (i == 1) {
                        PastHistoryModel pastHistory = new PastHistoryModel();
                        Row row = sheet.getRow(j);//获得当前行数据
                        if (row == null) continue;
                        //若本行为空则跳过本行的循环
                        if (row.getCell(0).getCellType() == Cell.CELL_TYPE_BLANK && row.getCell(1).getCellType() == Cell.CELL_TYPE_BLANK
                                && row.getCell(2).getCellType() == Cell.CELL_TYPE_BLANK && row.getCell(3).getCellType() == Cell.CELL_TYPE_BLANK
                                && row.getCell(4).getCellType() == Cell.CELL_TYPE_BLANK && row.getCell(5).getCellType() == Cell.CELL_TYPE_BLANK
                                && row.getCell(6).getCellType() == Cell.CELL_TYPE_BLANK) {
                            continue;
                        }
                        //判断单元格是否为空
//                        if (row.getCell(1).getCellType() == Cell.CELL_TYPE_BLANK || row.getCell(2).getCellType() == Cell.CELL_TYPE_BLANK
//                                || row.getCell(3).getCellType() == Cell.CELL_TYPE_BLANK || row.getCell(4).getCellType() == Cell.CELL_TYPE_BLANK
//                                || row.getCell(5).getCellType() == Cell.CELL_TYPE_BLANK || row.getCell(6).getCellType() == Cell.CELL_TYPE_BLANK) {
//                            return "明细单表格数据不能有空值！";
//                        }
                        //金额不能<0
                        if(row.getCell(5).getNumericCellValue()<0 || row.getCell(6).getNumericCellValue()<0){
                            return "明细单OBU号："+StringTrim(getCellValue(row.getCell(3)))+"的金额不能为负数！";
                        }
                        pastHistory.setCustomerName(StringTrim(getCellValue(row.getCell(1))));
                        pastHistory.setCustomerPhone(StringTrim(getCellValue(row.getCell(2))));
                        pastHistory.setObuId(StringTrim(getCellValue(row.getCell(3))));
                        pastHistory.setTransNum(StringTrim(getCellValue(row.getCell(4))));
                        int transAllMoney = ((int) (row.getCell(5).getNumericCellValue() * 100));//元转分
                        pastHistory.setTransAllMoney(String.valueOf(transAllMoney));
                        int owFee = ((int) (row.getCell(6).getNumericCellValue() * 100));//元转分
                        pastHistory.setOwFee(String.valueOf(owFee));
                        pastHistory.setOrderStatus("2");
                        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
                        pastHistory.setPastOrderId(uuid);
                        //判断字段是否为空
//                        if (pastHistory.getCustomerName().equals("") || pastHistory.getCustomerPhone().equals("")
//                                || pastHistory.getObuId().equals("") || pastHistory.getTransNum().equals("")) {
//                            return "明细单表格数据不能有空值！";
//                        }
                        pastHistoryModel.add(pastHistory);
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            return "表格数据格式错误，请检查表格后重新上传！";
        }
        String msg = "";
        try {
            msg = uploadHistoryService.past(pastHistoryModel, pastInfoHistorymodel);
            if(!msg.equals("success")){
                return msg;
            }
        }catch (Exception e){
            e.printStackTrace();
            return "失败！";
        }
        return "成功！";
    }

    /**
     * @description: 日期格式处理
     * @auther: liyan
     * @date: 2020/1/6 16:03
     * @param: [cell]
     * @return: java.lang.String
     **/
    public static String stringDateProcess(Cell cell) {
        String result = new String();
        if (HSSFDateUtil.isCellDateFormatted(cell)) {// 处理日期格式、时间格式
            SimpleDateFormat sdf = null;
            if (cell.getCellStyle().getDataFormat() == HSSFDataFormat.getBuiltinFormat("H:mm")) {
                sdf = new SimpleDateFormat("HH:mm");
            } else {// 日期
                sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            }
            Date date = cell.getDateCellValue();
            result = sdf.format(date);
        } else if (cell.getCellStyle().getDataFormat() == 58) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            double value = cell.getNumericCellValue();
            Date date = org.apache.poi.ss.usermodel.DateUtil
                    .getJavaDate(value);
            result = sdf.format(date);
        } else {
            double value = cell.getNumericCellValue();
            CellStyle style = cell.getCellStyle();
            DecimalFormat format = new DecimalFormat();
            String temp = style.getDataFormatString();
            // 单元格设置成常规
            if (temp.equals("General")) {
                format.applyPattern("#");
            }
            result = format.format(value);
        }

        return result;
    }

    /**
     * @description: Excel单元格数据格式判断
     * @auther: liyan
     * @date: 2020/1/6 16:03
     * @param: [cell]
     * @return: java.lang.String
     **/
    public static String getCellValue(Cell cell) {
        String cellValue = "";
        if (cell == null) {
            return cellValue;
        }
        //判断数据的类型
        switch (cell.getCellType()) {
            case Cell.CELL_TYPE_NUMERIC: //数字
                cellValue = stringDateProcess(cell);
                break;
            case Cell.CELL_TYPE_STRING: //字符串
                cellValue = String.valueOf(cell.getStringCellValue());
                break;
            case Cell.CELL_TYPE_BOOLEAN: //Boolean
                cellValue = String.valueOf(cell.getBooleanCellValue());
                break;
            case Cell.CELL_TYPE_FORMULA: //公式
                cellValue = String.valueOf(cell.getCellFormula());
                break;
            case Cell.CELL_TYPE_BLANK: //空值
                cellValue = "";
                break;
            case Cell.CELL_TYPE_ERROR: //故障
                cellValue = "非法字符";
                break;
            default:
                cellValue = "未知类型";
                break;
        }
        return cellValue;
    }

    /**
     * @description: 去掉单元格中值的空格
     * @auther: liyan
     * @date: 2020/1/11 14:12
     * @param: [str]
     * @return: java.lang.String
     **/
    public static String StringTrim(String str){
        return str.replaceAll("[\\s\\u00A0]+","").trim();
    }
}
