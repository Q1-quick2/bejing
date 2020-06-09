package com.sutong.workorder.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.sutong.bjstjh.util.ExcelFormatUtil;
import com.sutong.workorder.model.AuditHistoryExcelDTO;
import com.sutong.workorder.model.MinistryLevelExcelDTO;
import com.sutong.workorder.model.ProvincialLevelExcelDTO;
import com.sutong.workorder.service.IExcelExportSerice;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.io.IOUtils;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.xssf.streaming.SXSSFCell;
import org.apache.poi.xssf.streaming.SXSSFRow;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName: AduitExportExcelController
 * @Description: 历史工单导出接口
 * @author: lichengquan
 * @date: 2019年12月25日 11:32
 * @Version: 1.0
 */
@EnableSwagger2
@CrossOrigin
@RestController
@RequestMapping("/workorder")
@Api(value = "AuditExportExcelController", tags = "历史工单导出接口")
public class AuditExportExcelController {

    private static final Logger log = LoggerFactory.getLogger(AuditExportExcelController.class);

    @Reference(timeout = 300000)
    private IExcelExportSerice iExcelExportSerice;

    @ApiOperation("部中心导出Excel")
    @GetMapping("/ministryLevelExcel")
    public ResponseEntity<byte[]> ministryLevelExcel(HttpServletRequest request, HttpServletResponse response, AuditHistoryExcelDTO auditHistoryExcelDto) {
        List<MinistryLevelExcelDTO> ministryLevelExcelList = iExcelExportSerice.buildMinistryLevelExcel(auditHistoryExcelDto);
        log.info("开始部级工单导出");
        try {
            return buildResponseEntity(ministryLevelExport(ministryLevelExcelList), "部中心导出.xlsx");
        } catch (Exception e) {
            e.printStackTrace();
            log.error("导出excel异常，异常原因：" + e.getMessage());
        }
        return null;
    }

    /**
     * 下载文件
     *
     * @param is   文件流
     * @param name 文件的名称
     * @return
     * @throws Exception
     */
    public ResponseEntity<byte[]> buildResponseEntity(InputStream is, String name) throws Exception {
        if (log.isDebugEnabled()) {
            log.debug("download: " + name);
        }
        HttpHeaders header = new HttpHeaders();
        String fileSuffix = name.substring(name.lastIndexOf('.') + 1);
        fileSuffix = fileSuffix.toLowerCase();

        Map<String, String> arguments = new HashMap<String, String>();
        arguments.put("xlsx", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        arguments.put("xls", "application/vnd.ms-excel");

        String contentType = arguments.get(fileSuffix);
        header.add("Content-Type", (StringUtils.hasText(contentType) ? contentType : "application/x-download"));
        if (is != null && is.available() != 0) {
            header.add("Content-Length", String.valueOf(is.available()));
            header.add("Content-Disposition", "attachment;filename*=utf-8'zh_cn'" + URLEncoder.encode(name, "UTF-8"));
            byte[] bs = IOUtils.toByteArray(is);
            log.info(">>>>>>>>>>>>>>>>>>>>结束下载文件-有记录>>>>>>>>>>");
            log.info(">>>>>>>>>>结束导出excel>>>>>>>>>>");
            return new ResponseEntity<>(bs, header, HttpStatus.OK);
        } else {
            String string = "数据为空";
            header.add("Content-Length", "0");
            header.add("Content-Disposition", "attachment;filename*=utf-8'zh_cn'" + URLEncoder.encode(name, "UTF-8"));
            log.info(">>>>>>>>>>>>>>>>>>>>结束下载文件-无记录>>>>>>>>>>");
            log.info(">>>>>>>>>>结束导出excel>>>>>>>>>>");
            return new ResponseEntity<>(string.getBytes(), header, HttpStatus.OK);
        }
    }

    private InputStream ministryLevelExport(List<MinistryLevelExcelDTO> list) {
        log.info(">>>>>>>>>>>>>>>>>>>>开始进入导出方法>>>>>>>>>>");
        ByteArrayOutputStream output = null;
        InputStream inputStream = null;
        log.info("listSize:{}", list.size());
        SXSSFWorkbook wb = new SXSSFWorkbook(1 + list.size());
        // 保留1000条数据在内存中
        SXSSFSheet sheet = wb.createSheet();
        // 设置报表头样式
        CellStyle header = ExcelFormatUtil.headSytle(wb);
        // cell样式
        CellStyle content = ExcelFormatUtil.contentStyle(wb);
        // 报表体样式
        // 每一列字段名
        String[] strs = new String[]{"序号", "核查方", "发行方", "发行方式", "发行机构", "OBU号",
                "ETC卡号", "车号", "是否逃费", "未确认逃费原因", "是否通知客户补缴", "是否下发状态名单", "逃费类型",
                "ETC卡内车型", "实际车型", "逃费通行次数", "核查省欠费金额（元）", "其他省欠费金额（元）",
                "已追缴金额", "未追缴金额", "涉及通行省份（个）", "涉及通行具体省份", "已追缴金额（元）",
                "未追缴金额（元）", "涉及通行省份（个）", "涉及通行具体省份", "备注"};
        // 字段名所在表格的宽度
        int[] ints = new int[]{5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000,
                5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000,
                5000, 5000};
        // 设置表头样式
        ExcelFormatUtil.initTitleEX(sheet, header, strs, ints);
        log.info(">>>>>>>>>>>>>>>>>>>>表头样式设置完成>>>>>>>>>>");
        if (!CollectionUtils.isEmpty(list)) {
            log.info(">>>>>>>>>>>>>>>>>>>>开始遍历数据组装单元格内容>>>>>>>>>>");
            for (int i = 0; i < list.size(); i++) {
                MinistryLevelExcelDTO ministryLevelExcelDTO = list.get(i);
                SXSSFRow row = sheet.createRow(i + 1);
                int j = 0;
                SXSSFCell cell = row.createCell(j++);
                //序号
                cell.setCellValue(ministryLevelExcelDTO.getSerialNumber());
                cell.setCellStyle(content);
                //核查方
                cell = row.createCell(j++);
                cell.setCellValue(ministryLevelExcelDTO.getVerificationParty());
                cell.setCellStyle(content);
                //发行方
                cell = row.createCell(j++);
                cell.setCellValue(ministryLevelExcelDTO.getIssuingPeople());
                cell.setCellStyle(content);
                //发行方式
                cell = row.createCell(j++);
                cell.setCellValue(ministryLevelExcelDTO.getIssuingType());
                cell.setCellStyle(content);
                //发行机构
                cell = row.createCell(j++);
                cell.setCellValue(ministryLevelExcelDTO.getIssuingType());
                cell.setCellStyle(content);
                //OBU号
                cell = row.createCell(j++);
                cell.setCellValue(ministryLevelExcelDTO.getObuno());
                cell.setCellStyle(content);
                //ETC卡号
                cell = row.createCell(j++);
                cell.setCellValue(ministryLevelExcelDTO.getEtcno());
                cell.setCellStyle(content);
                //车号
                cell = row.createCell(j++);
                cell.setCellValue(ministryLevelExcelDTO.getVehplateNo());
                cell.setCellStyle(content);
                //是否逃费
                cell = row.createCell(j++);
                cell.setCellValue(ministryLevelExcelDTO.getWhetherEvasion());
                cell.setCellStyle(content);
                //未确认逃费原因
                cell = row.createCell(j++);
                cell.setCellValue(ministryLevelExcelDTO.getEvasionReason());
                cell.setCellStyle(content);
                //是否通知客户补缴
                cell = row.createCell(j++);
                cell.setCellValue(ministryLevelExcelDTO.getInformCustomersFlag());
                cell.setCellStyle(content);
                //是否下发状态名单
                cell = row.createCell(j++);
                cell.setCellValue(ministryLevelExcelDTO.getNotificationBookFlag());
                cell.setCellStyle(content);
                //逃费类型
                cell = row.createCell(j++);
                cell.setCellValue(ministryLevelExcelDTO.getEvasionType());
                cell.setCellStyle(content);
                //ETC卡内车型
                cell = row.createCell(j++);
                cell.setCellValue(ministryLevelExcelDTO.getEctCarType());
                cell.setCellStyle(content);
                //实际车型
                cell = row.createCell(j++);
                cell.setCellValue(ministryLevelExcelDTO.getCarActualType());
                cell.setCellStyle(content);
                //逃费通行次数
                cell = row.createCell(j++);
                cell.setCellValue(ministryLevelExcelDTO.getEvasionCurrentNumber());
                cell.setCellStyle(content);
                //核查省欠费金额（元）
                cell = row.createCell(j++);
                cell.setCellValue(ministryLevelExcelDTO.getAmountOfArrears());
                cell.setCellStyle(content);
                //其他省欠费金额（元）
                cell = row.createCell(j++);
                cell.setCellValue(ministryLevelExcelDTO.getOtherAmountOfArrears());
                cell.setCellStyle(content);
                //其他省欠费金额（元）
                cell = row.createCell(j++);
                cell.setCellValue(ministryLevelExcelDTO.getAlreadySumPay());
                cell.setCellStyle(content);
                //未追缴金额
                cell = row.createCell(j++);
                cell.setCellValue(ministryLevelExcelDTO.getReadySumPay());
                cell.setCellStyle(content);
                //涉及通行省份（个）
                cell = row.createCell(j++);
                cell.setCellValue(ministryLevelExcelDTO.getProvincesInvolved());
                cell.setCellStyle(content);
                //涉及通行具体省份
                cell = row.createCell(j++);
                cell.setCellValue(ministryLevelExcelDTO.getSpecificProvincesInvolved());
                cell.setCellStyle(content);
                //已追缴金额（元）
                cell = row.createCell(j++);
                cell.setCellValue(ministryLevelExcelDTO.getAlreadySumPays());
                cell.setCellStyle(content);
                //未追缴金额（元）
                cell = row.createCell(j++);
                cell.setCellValue(ministryLevelExcelDTO.getReadySumPays());
                cell.setCellStyle(content);
                //涉及通行省份（个）
                cell = row.createCell(j++);
                cell.setCellValue(ministryLevelExcelDTO.getProvincesInvolveds());
                cell.setCellStyle(content);
                //涉及通行具体省份
                cell = row.createCell(j++);
                cell.setCellValue(ministryLevelExcelDTO.getSpecificProvincesInvolveds());
                cell.setCellStyle(content);
                //备注
                cell = row.createCell(j++);
                cell.setCellValue(ministryLevelExcelDTO.getRemarks());
                cell.setCellStyle(content);
            }
            log.info(">>>>>>>>>>>>>>>>>>>>结束遍历数据组装单元格内容>>>>>>>>>>");
        }
        try {
            output = new ByteArrayOutputStream();
            wb.write(output);
            inputStream = new ByteArrayInputStream(output.toByteArray());
            output.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (output != null) {
                    output.close();
                    if (inputStream != null) {
                        inputStream.close();
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return inputStream;
    }

    @ApiOperation("省中心导出Excel")
    @GetMapping("/provinceLevelExcel")
    public ResponseEntity<byte[]> provinceLevelExcel(HttpServletRequest request, HttpServletResponse response, AuditHistoryExcelDTO auditHistoryExcelDTO) {
        List<ProvincialLevelExcelDTO> provincialLevelExcelList = iExcelExportSerice.buildProvincialLevelExcel(auditHistoryExcelDTO);
        log.info("开始省级工单导出");
        try {
            return buildResponseEntity(provinceLevelExport(provincialLevelExcelList), "省中心导出.xlsx");
        } catch (Exception e) {
            e.printStackTrace();
            log.error("导出excel异常，异常原因" + e.getMessage());
        }
        return null;
    }

    private InputStream provinceLevelExport(List<ProvincialLevelExcelDTO> list) {
        log.info(">>>>>>>>>>>>>>>>>>>>开始进入导出方法>>>>>>>>>>");
        ByteArrayOutputStream output = null;
        InputStream inputStream = null;
        log.info("listSize:{}", list.size());
        SXSSFWorkbook wb = new SXSSFWorkbook(1 + list.size());
        // 保留1000条数据在内存中
        SXSSFSheet sheet = wb.createSheet();
        // 设置报表头样式
        CellStyle header = ExcelFormatUtil.headSytle(wb);
        // cell样式
        CellStyle content = ExcelFormatUtil.contentStyle(wb);
        // 报表体样式
        // 每一列字段名
        String[] strs = new String[]{"序号", "外查单号", "核查方", "发行营业厅", "签约时间", "发行时间", "客编", "客户名称",
                "车辆所有人", "代办人", "证件号码", "联系电话", "行驶证地址", "发行系统地址", "OBU号", "ETC卡号", "车号", "是否逃费",
                "未确认逃费原因", "是否通知客户补缴", "逃费类型", "ETC卡内车型", "ETC卡内车牌颜色", "实际车型", "是否有客户资料",
                "分公司提交的证据（违规事件认定单和车道截图/车道视频）", "责任", "逃费通行次数", "核查省欠费金额（元）",
                "其他省欠费金额（元）", "已追缴金额", "未追缴金额", "已追缴金额（元）", "未追缴金额（元）", "初次通行时间", "最后通行时间",
                "补缴时间", "OBU禁用时间", "ETC卡禁用时间", "OBU解禁时间", "ETC解禁时间", "OBU状态",
                "ETC卡状态", "车牌黑名单状态", "状态", "责任", "备注"};
        // 字段名所在表格的宽度
        int[] ints = new int[]{5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000,
                5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000,
                5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000,
                5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000,
                5000, 5000, 5000, 5000, 5000, 5000, 5000};
        // 设置表头样式
        ExcelFormatUtil.initTitleEX(sheet, header, strs, ints);
        log.info(">>>>>>>>>>>>>>>>>>>>表头样式设置完成>>>>>>>>>>");
        if (!CollectionUtils.isEmpty(list)) {
            log.info(">>>>>>>>>>>>>>>>>>>>开始遍历数据组装单元格内容>>>>>>>>>>");
            for (int i = 0; i < list.size(); i++) {
                ProvincialLevelExcelDTO provincialLevelExcelDTO = list.get(i);
                SXSSFRow row = sheet.createRow(i + 1);
                int j = 0;
                SXSSFCell cell = row.createCell(j++);
                //序号
                cell.setCellValue(provincialLevelExcelDTO.getSerialNumber());
                cell.setCellStyle(content);
                //外查单号
                cell = row.createCell(j++);
                cell.setCellValue(provincialLevelExcelDTO.getExternalOrderNo());
                cell.setCellStyle(content);
                //核查方
                cell = row.createCell(j++);
                cell.setCellValue(provincialLevelExcelDTO.getVerificationParty());
                cell.setCellStyle(content);
                //发行营业厅
                cell = row.createCell(j++);
                cell.setCellValue(provincialLevelExcelDTO.getIssuingAgent());
                cell.setCellStyle(content);
                //签约时间
                cell = row.createCell(j++);
                cell.setCellValue(provincialLevelExcelDTO.getSignDate());
                cell.setCellStyle(content);
                //发行时间
                cell = row.createCell(j++);
                cell.setCellValue(provincialLevelExcelDTO.getIssueTime());
                cell.setCellStyle(content);
                //客编
                cell = row.createCell(j++);
                cell.setCellValue(provincialLevelExcelDTO.getCustomerNo());
                cell.setCellStyle(content);
                //客户名称
                cell = row.createCell(j++);
                cell.setCellValue(provincialLevelExcelDTO.getCustomerName());
                cell.setCellStyle(content);
                //车辆所有人
                cell = row.createCell(j++);
                cell.setCellValue(provincialLevelExcelDTO.getVehicleOwner());
                cell.setCellStyle(content);
                //代办人
                cell = row.createCell(j++);
                cell.setCellValue(provincialLevelExcelDTO.getProxymercName());
                cell.setCellStyle(content);
                //证件号码
                cell = row.createCell(j++);
                cell.setCellValue(provincialLevelExcelDTO.getCertificateNumber());
                cell.setCellStyle(content);
                //联系电话
                cell = row.createCell(j++);
                cell.setCellValue(provincialLevelExcelDTO.getContactTelePhoneNo());
                cell.setCellStyle(content);
                //行驶证地址
                cell = row.createCell(j++);
                cell.setCellValue(provincialLevelExcelDTO.getDrivingLicAddress());
                cell.setCellStyle(content);
                //发行系统地址
                cell = row.createCell(j++);
                cell.setCellValue(provincialLevelExcelDTO.getDrivingLicSystemAddress());
                cell.setCellStyle(content);
                //OBU号
                cell = row.createCell(j++);
                cell.setCellValue(provincialLevelExcelDTO.getObuno());
                cell.setCellStyle(content);
                //ETC卡号
                cell = row.createCell(j++);
                cell.setCellValue(provincialLevelExcelDTO.getEtcno());
                cell.setCellStyle(content);
                //车号
                cell = row.createCell(j++);
                cell.setCellValue(provincialLevelExcelDTO.getVehplateNo());
                cell.setCellStyle(content);
                //是否逃费
                cell = row.createCell(j++);
                cell.setCellValue(provincialLevelExcelDTO.getWhetherEvasion());
                cell.setCellStyle(content);
                //未确认逃费原因
                cell = row.createCell(j++);
                cell.setCellValue(provincialLevelExcelDTO.getEvasionReason());
                cell.setCellStyle(content);
                //是否通知客户补缴
                cell = row.createCell(j++);
                cell.setCellValue(provincialLevelExcelDTO.getInformCustomersFlag());
                cell.setCellStyle(content);
                //逃费类型
                cell = row.createCell(j++);
                cell.setCellValue(provincialLevelExcelDTO.getEvasionType());
                cell.setCellStyle(content);
                //ETC卡内车型
                cell = row.createCell(j++);
                cell.setCellValue(provincialLevelExcelDTO.getEtcCarType());
                cell.setCellStyle(content);
                //ETC卡内车牌颜色
                cell = row.createCell(j++);
                cell.setCellValue(provincialLevelExcelDTO.getEtcTruckColor());
                cell.setCellStyle(content);
                //实际车型
                cell = row.createCell(j++);
                cell.setCellValue(provincialLevelExcelDTO.getCarActualType());
                cell.setCellStyle(content);
                //是否有客户资料
                cell = row.createCell(j++);
                cell.setCellValue(provincialLevelExcelDTO.getCustomerInfoFlag());
                cell.setCellStyle(content);
                //分公司提交的证据（违规事件认定单和车道截图/车道视频）
                cell = row.createCell(j++);
                cell.setCellValue(provincialLevelExcelDTO.getProof());
                cell.setCellStyle(content);
                //责任
                cell = row.createCell(j++);
                cell.setCellValue(provincialLevelExcelDTO.getDutyBelong());
                cell.setCellStyle(content);
                //逃费通行次数
                cell = row.createCell(j++);
                cell.setCellValue(provincialLevelExcelDTO.getEvasionCurrentNumber());
                cell.setCellStyle(content);
                //核查省欠费金额（元）
                cell = row.createCell(j++);
                cell.setCellValue(provincialLevelExcelDTO.getAmountOfArrears());
                cell.setCellStyle(content);
                //其他省欠费金额（元）
                cell = row.createCell(j++);
                cell.setCellValue(provincialLevelExcelDTO.getOtherAmountOfArrears());
                cell.setCellStyle(content);
                //已追缴金额
                cell = row.createCell(j++);
                cell.setCellValue(provincialLevelExcelDTO.getAlreadySumPay());
                cell.setCellStyle(content);
                //未追缴金额
                cell = row.createCell(j++);
                cell.setCellValue(provincialLevelExcelDTO.getReadySumPay());
                cell.setCellStyle(content);
                //已追缴金额（元）
                cell = row.createCell(j++);
                cell.setCellValue(provincialLevelExcelDTO.getAlreadySumPays());
                cell.setCellStyle(content);
                //未追缴金额（元）
                cell = row.createCell(j++);
                cell.setCellValue(provincialLevelExcelDTO.getReadySumPays());
                cell.setCellStyle(content);
                //初次通行时间
                cell = row.createCell(j++);
                cell.setCellValue(provincialLevelExcelDTO.getFirstPassTime());
                cell.setCellStyle(content);
                //最后通行时间
                cell = row.createCell(j++);
                cell.setCellValue(provincialLevelExcelDTO.getLastPassTime());
                cell.setCellStyle(content);
                //补缴时间
                cell = row.createCell(j++);
                cell.setCellValue(provincialLevelExcelDTO.getSumTime());
                cell.setCellStyle(content);
                //OBU禁用时间
                cell = row.createCell(j++);
                cell.setCellValue(provincialLevelExcelDTO.getObuDisableDate());
                cell.setCellStyle(content);
                //ETC卡禁用时间
                cell = row.createCell(j++);
                cell.setCellValue(provincialLevelExcelDTO.getEtcDisableDate());
                cell.setCellStyle(content);
                //OBU解禁时间
                cell = row.createCell(j++);
                cell.setCellValue(provincialLevelExcelDTO.getObuLiftBanTime());
                cell.setCellStyle(content);
                //ETC解禁时间
                cell = row.createCell(j++);
                cell.setCellValue(provincialLevelExcelDTO.getEtcLiftBanTime());
                cell.setCellStyle(content);
                //OBU状态
                cell = row.createCell(j++);
                cell.setCellValue(provincialLevelExcelDTO.getObuAccountStatus());
                cell.setCellStyle(content);
                //ETC卡状态
                cell = row.createCell(j++);
                cell.setCellValue(provincialLevelExcelDTO.getEtcStatusList());
                cell.setCellStyle(content);
                //车牌黑名单状态
                cell = row.createCell(j++);
                cell.setCellValue(provincialLevelExcelDTO.getVehStatusList());
                cell.setCellStyle(content);
                //状态
                cell = row.createCell(j++);
                cell.setCellValue(provincialLevelExcelDTO.getStatus());
                cell.setCellStyle(content);
                //责任
                cell = row.createCell(j++);
                cell.setCellValue(provincialLevelExcelDTO.getResponsibilitys());
                cell.setCellStyle(content);
                //备注
                cell = row.createCell(j++);
                cell.setCellValue(provincialLevelExcelDTO.getRemark());
                cell.setCellStyle(content);
            }
            log.info(">>>>>>>>>>>>>>>>>>>>结束遍历数据组装单元格内容>>>>>>>>>>");
        }
        try {
            output = new ByteArrayOutputStream();
            wb.write(output);
            inputStream = new ByteArrayInputStream(output.toByteArray());
            output.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (output != null) {
                    output.close();
                    if (inputStream != null) {
                        inputStream.close();
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return inputStream;
    }
}
