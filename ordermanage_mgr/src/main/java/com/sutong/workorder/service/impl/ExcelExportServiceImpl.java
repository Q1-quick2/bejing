package com.sutong.workorder.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.sutong.bjstjh.entity.AuditDictionaryInfoTable;
import com.sutong.bjstjh.entity.AuditWorkOrderHistoryTable;
import com.sutong.historyOrder.mapper.HistoryOrderMapper;
import com.sutong.mapper.DictMapper;
import com.sutong.workorder.model.AuditHistoryExcelDTO;
import com.sutong.workorder.model.MinistryLevelExcelDTO;
import com.sutong.workorder.model.ProvincialLevelExcelDTO;
import com.sutong.workorder.service.IExcelExportSerice;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @ClassName: ExcelExportServiceImpl
 * @Description: excel导出
 * @author: lichengquan
 * @date: 2019年12月24日 20:42
 * @Version: 1.0
 */
@Service
public class ExcelExportServiceImpl implements IExcelExportSerice {

    private static final Logger log = LoggerFactory.getLogger(ExcelExportServiceImpl.class);

    @Autowired
    private HistoryOrderMapper historyOrderMapper;
    @Autowired
    private DictMapper dictMapper;

    @Override
    public List<MinistryLevelExcelDTO> buildMinistryLevelExcel(AuditHistoryExcelDTO auditHistoryExcelDTO) {
        List<MinistryLevelExcelDTO> ministryLevelExcelList = new ArrayList<>();
        List<AuditWorkOrderHistoryTable> allList = historyOrderMapper.doFindHistoryExcel(auditHistoryExcelDTO);
        //是否通知(字典)
        List<AuditDictionaryInfoTable> noticeList = dictMapper.queryDictByDictType("INFORM_CUSTOMERS_FLAG");
        Map<Integer, String> noticeMap = noticeList.stream().collect(Collectors.toMap(AuditDictionaryInfoTable::getDictNumber, AuditDictionaryInfoTable::getDictName));
        //是否已下发(字典)
        List<AuditDictionaryInfoTable> hasBeenIssuedList = dictMapper.queryDictByDictType("HAS_BEEN_ISSUED");
        Map<Integer, String> hasBeenIssuedMap = hasBeenIssuedList.stream().collect(Collectors.toMap(AuditDictionaryInfoTable::getDictNumber, AuditDictionaryInfoTable::getDictName));
        for (int i = 0; i < allList.size(); i++) {
            AuditWorkOrderHistoryTable auditWorkOrderHistoryTable = allList.get(i);
            MinistryLevelExcelDTO ministryLevelExcelDto = new MinistryLevelExcelDTO();
            ministryLevelExcelDto.setSerialNumber(String.valueOf(i + 1));
            ministryLevelExcelDto.setVerificationParty("核查方");
            ministryLevelExcelDto.setIssuingPeople("发行方");
            ministryLevelExcelDto.setIssuingType("发行方式");
            if (StringUtils.isEmpty(auditWorkOrderHistoryTable.getIssuingAgent())) {
                auditWorkOrderHistoryTable.setIssuingAgent("");
            }
            ministryLevelExcelDto.setIssuingAgent(auditWorkOrderHistoryTable.getIssuingAgent());
            if (StringUtils.isEmpty(auditWorkOrderHistoryTable.getObuno())) {
                auditWorkOrderHistoryTable.setObuno("");
            }
            ministryLevelExcelDto.setObuno(auditWorkOrderHistoryTable.getObuno());
            if (StringUtils.isEmpty(auditWorkOrderHistoryTable.getEtcno())) {
                auditWorkOrderHistoryTable.setObuno("");
            }
            ministryLevelExcelDto.setEtcno(auditWorkOrderHistoryTable.getEtcno());
            if (StringUtils.isEmpty(auditWorkOrderHistoryTable.getVehplateNo())) {
                auditWorkOrderHistoryTable.setVehplateNo("");
            }
            ministryLevelExcelDto.setVehplateNo(auditWorkOrderHistoryTable.getVehplateNo());
            ministryLevelExcelDto.setWhetherEvasion("是");
            ministryLevelExcelDto.setEvasionReason("无");
            ministryLevelExcelDto.setInformCustomersFlag(noticeMap.get(auditWorkOrderHistoryTable.getInformCustomersFlag()));
            ministryLevelExcelDto.setNotificationBookFlag(hasBeenIssuedMap.get(auditWorkOrderHistoryTable.getNotificationBookFlag()));
            ministryLevelExcelDto.setEvasionType("逃费类型");
            ministryLevelExcelDto.setEctCarType("etc卡内车型");
            ministryLevelExcelDto.setCarActualType("实际车型");
            ministryLevelExcelDto.setEvasionCurrentNumber("1");
            ministryLevelExcelDto.setAmountOfArrears(12.5);
            ministryLevelExcelDto.setOtherAmountOfArrears(13.5);
            ministryLevelExcelDto.setAlreadySumPay(11.5);
            ministryLevelExcelDto.setReadySumPay(11.5);
            ministryLevelExcelDto.setProvincesInvolved(1);
            ministryLevelExcelDto.setSpecificProvincesInvolved("具体通行省份");
            ministryLevelExcelDto.setAlreadySumPays(111.5);
            ministryLevelExcelDto.setReadySumPays(112.5);
            ministryLevelExcelDto.setProvincesInvolveds(12);
            ministryLevelExcelDto.setSpecificProvincesInvolveds("涉及通行具体省份");
            ministryLevelExcelDto.setProvincesInvolveds(5);
            if (StringUtils.isEmpty(auditWorkOrderHistoryTable.getRemarkStatus())) {
                auditWorkOrderHistoryTable.setRemarkStatus("");
            }
            ministryLevelExcelDto.setRemarks(auditWorkOrderHistoryTable.getRemarkStatus());
            ministryLevelExcelList.add(ministryLevelExcelDto);
        }
        return ministryLevelExcelList;
    }

    @Override
    public List<ProvincialLevelExcelDTO> buildProvincialLevelExcel(AuditHistoryExcelDTO auditHistoryExcelDTO) {
        List<ProvincialLevelExcelDTO> provincialLevelExcels = new ArrayList<>();
        List<AuditWorkOrderHistoryTable> allList = historyOrderMapper.doFindHistoryExcel(auditHistoryExcelDTO);
        //是否通知(字典)
        List<AuditDictionaryInfoTable> noticeList = dictMapper.queryDictByDictType("INFORM_CUSTOMERS_FLAG");
        Map<Integer, String> noticeMap = noticeList.stream().collect(Collectors.toMap(AuditDictionaryInfoTable::getDictNumber, AuditDictionaryInfoTable::getDictName));
        //是否有客户资料
        List<AuditDictionaryInfoTable> customerInfoList = dictMapper.queryDictByDictType("CUSTOMER_INFO_FLAG");
        Map<Integer, String> customerInfoMap = customerInfoList.stream().collect(Collectors.toMap(AuditDictionaryInfoTable::getDictNumber, AuditDictionaryInfoTable::getDictName));
        //责任
        List<AuditDictionaryInfoTable> responsibilityList = dictMapper.queryDictByDictType("DICT_DUTY_BELONG");
        Map<Integer, String> responsibilityMap = responsibilityList.stream().collect(Collectors.toMap(AuditDictionaryInfoTable::getDictNumber, AuditDictionaryInfoTable::getDictName));
        //OBU标签状态
        List<AuditDictionaryInfoTable> obuAccountStatusList = dictMapper.queryDictByDictType("OBU_ACCOUNT_STATUS");
        Map<Integer, String> obuAccountStatusMap = obuAccountStatusList.stream().collect(Collectors.toMap(AuditDictionaryInfoTable::getDictNumber, AuditDictionaryInfoTable::getDictName));
        //ETC卡状态
        List<AuditDictionaryInfoTable> etcStatusList = dictMapper.queryDictByDictType("ETC_STATUS_LIST");
        Map<Integer, String> etcStatusMap = etcStatusList.stream().collect(Collectors.toMap(AuditDictionaryInfoTable::getDictNumber, AuditDictionaryInfoTable::getDictName));
        //车牌状态
        List<AuditDictionaryInfoTable> vehStatusList = dictMapper.queryDictByDictType("VEH_STATUS_LIST");
        Map<Integer, String> vehStatusMap = vehStatusList.stream().collect(Collectors.toMap(AuditDictionaryInfoTable::getDictNumber, AuditDictionaryInfoTable::getDictName));
        //工单状态
        List<AuditDictionaryInfoTable> orderStatusList = dictMapper.queryDictByDictType("ORDER_STATUS");
        Map<Integer, String> orderStatusMap = orderStatusList.stream().collect(Collectors.toMap(AuditDictionaryInfoTable::getDictNumber, AuditDictionaryInfoTable::getDictName));
        for (int i = 0; i < allList.size(); i++) {
            AuditWorkOrderHistoryTable auditWorkOrderHistoryTable = allList.get(i);
            ProvincialLevelExcelDTO provincialLevelExceldto = new ProvincialLevelExcelDTO();
            provincialLevelExceldto.setSerialNumber(String.valueOf(i + 1));
            if (StringUtils.isEmpty(auditWorkOrderHistoryTable.getExternalOrderNo())) {
                auditWorkOrderHistoryTable.setExternalOrderNo("");
            }
            provincialLevelExceldto.setExternalOrderNo(auditWorkOrderHistoryTable.getExternalOrderNo());
            provincialLevelExceldto.setVerificationParty("核查方");
            if (StringUtils.isEmpty(auditWorkOrderHistoryTable.getIssuingAgent())) {
                auditWorkOrderHistoryTable.setIssuingAgent("");
            }
            provincialLevelExceldto.setIssuingAgent(auditWorkOrderHistoryTable.getIssuingAgent());
            if (StringUtils.isEmpty(auditWorkOrderHistoryTable.getSignDate())) {
                auditWorkOrderHistoryTable.setSignDate("");
            }
            if (StringUtils.isEmpty(auditWorkOrderHistoryTable.getSignExpireDate())) {
                auditWorkOrderHistoryTable.setSignExpireDate("");
            }
            provincialLevelExceldto.setSignDate(auditWorkOrderHistoryTable.getSignDate() + "-" + auditWorkOrderHistoryTable.getSignExpireDate());
            if (StringUtils.isEmpty(auditWorkOrderHistoryTable.getIssueStartTime())) {
                auditWorkOrderHistoryTable.setIssueStartTime("");
            }
            if (StringUtils.isEmpty(auditWorkOrderHistoryTable.getIssueEndTime())) {
                auditWorkOrderHistoryTable.setIssueEndTime("");
            }
            provincialLevelExceldto.setIssueTime(auditWorkOrderHistoryTable.getIssueStartTime() + "-" + auditWorkOrderHistoryTable.getIssueEndTime());
            if (StringUtils.isEmpty(auditWorkOrderHistoryTable.getCustomerNo())) {
                auditWorkOrderHistoryTable.setCustomerNo("");
            }
            provincialLevelExceldto.setCustomerNo(auditWorkOrderHistoryTable.getCustomerNo());
            if (StringUtils.isEmpty(auditWorkOrderHistoryTable.getCustomerName())) {
                auditWorkOrderHistoryTable.setCustomerName("");
            }
            provincialLevelExceldto.setCustomerName(auditWorkOrderHistoryTable.getCustomerName());
            if (StringUtils.isEmpty(auditWorkOrderHistoryTable.getVehOwner())) {
                auditWorkOrderHistoryTable.setVehOwner("");
            }
            provincialLevelExceldto.setVehicleOwner(auditWorkOrderHistoryTable.getVehOwner());
            if (StringUtils.isEmpty(auditWorkOrderHistoryTable.getProxymercName())) {
                auditWorkOrderHistoryTable.setProxymercName("");
            }
            provincialLevelExceldto.setProxymercName(auditWorkOrderHistoryTable.getProxymercName());
            if (StringUtils.isEmpty(auditWorkOrderHistoryTable.getCertificateNo())) {
                auditWorkOrderHistoryTable.setCertificateNo("");
            }
            provincialLevelExceldto.setCertificateNumber(auditWorkOrderHistoryTable.getCertificateNo());
            if (StringUtils.isEmpty(auditWorkOrderHistoryTable.getContactTelePhoneNo())) {
                auditWorkOrderHistoryTable.setContactTelePhoneNo("");
            }
            provincialLevelExceldto.setContactTelePhoneNo(auditWorkOrderHistoryTable.getContactTelePhoneNo());
            if (StringUtils.isEmpty(auditWorkOrderHistoryTable.getDrivingLicAddress())) {
                auditWorkOrderHistoryTable.setDrivingLicAddress("");
            }
            provincialLevelExceldto.setDrivingLicAddress(auditWorkOrderHistoryTable.getDrivingLicAddress());
            if (StringUtils.isEmpty(auditWorkOrderHistoryTable.getDrivingLicSystemAddress())) {
                auditWorkOrderHistoryTable.setDrivingLicSystemAddress("");
            }
            provincialLevelExceldto.setDrivingLicSystemAddress(auditWorkOrderHistoryTable.getDrivingLicSystemAddress());
            if (StringUtils.isEmpty(auditWorkOrderHistoryTable.getObuno())) {
                auditWorkOrderHistoryTable.setObuno("");
            }
            provincialLevelExceldto.setObuno(auditWorkOrderHistoryTable.getObuno());
            if (StringUtils.isEmpty(auditWorkOrderHistoryTable.getObuno())) {
                auditWorkOrderHistoryTable.setObuno("");
            }
            provincialLevelExceldto.setEtcno(auditWorkOrderHistoryTable.getEtcno());
            if (StringUtils.isEmpty(auditWorkOrderHistoryTable.getVehplateNo())) {
                auditWorkOrderHistoryTable.setVehplateNo("");
            }
            provincialLevelExceldto.setVehplateNo(auditWorkOrderHistoryTable.getVehplateNo());
            provincialLevelExceldto.setWhetherEvasion("是");
            provincialLevelExceldto.setEvasionReason("未确认逃费原因");
            provincialLevelExceldto.setInformCustomersFlag(noticeMap.get(auditWorkOrderHistoryTable.getInformCustomersFlag()));
            provincialLevelExceldto.setEvasionType("逃费类型");
            provincialLevelExceldto.setEtcCarType("ETC卡内类型");
            provincialLevelExceldto.setEtcTruckColor("ETC卡内车牌颜色");
            provincialLevelExceldto.setCarActualType("实际车型");
            provincialLevelExceldto.setCustomerInfoFlag(customerInfoMap.get(auditWorkOrderHistoryTable.getCustomerInfoFlag()));
            if (StringUtils.isEmpty(auditWorkOrderHistoryTable.getProof())) {
                auditWorkOrderHistoryTable.setProof("");
            }
            provincialLevelExceldto.setProof(auditWorkOrderHistoryTable.getProof());
            provincialLevelExceldto.setDutyBelong(responsibilityMap.get(auditWorkOrderHistoryTable.getDutyBelong()));
            provincialLevelExceldto.setEvasionCurrentNumber("1");
            provincialLevelExceldto.setAmountOfArrears(22.5);
            provincialLevelExceldto.setOtherAmountOfArrears(15.5);
            provincialLevelExceldto.setAlreadySumPay(11.5);
            provincialLevelExceldto.setReadySumPay(11.5);
            provincialLevelExceldto.setAlreadySumPays(14.5);
            provincialLevelExceldto.setReadySumPays(15.5);
            if (StringUtils.isEmpty(auditWorkOrderHistoryTable.getFirstPassTime())) {
                auditWorkOrderHistoryTable.setFirstPassTime("");
            }
            provincialLevelExceldto.setFirstPassTime(auditWorkOrderHistoryTable.getFirstPassTime());
            if (StringUtils.isEmpty(auditWorkOrderHistoryTable.getLastPassTime())) {
                auditWorkOrderHistoryTable.setLastPassTime("");
            }
            provincialLevelExceldto.setLastPassTime(auditWorkOrderHistoryTable.getLastPassTime());
            if (StringUtils.isEmpty(auditWorkOrderHistoryTable.getSumTime())) {
                auditWorkOrderHistoryTable.setSumTime("");
            }
            provincialLevelExceldto.setSumTime(auditWorkOrderHistoryTable.getSumTime());
            if (StringUtils.isEmpty(auditWorkOrderHistoryTable.getObuDisableDate())) {
                auditWorkOrderHistoryTable.setObuDisableDate("");
            }
            provincialLevelExceldto.setObuDisableDate(auditWorkOrderHistoryTable.getObuDisableDate());
            if (StringUtils.isEmpty(auditWorkOrderHistoryTable.getEtcDisableDate())) {
                auditWorkOrderHistoryTable.setEtcDisableDate("");
            }
            provincialLevelExceldto.setEtcDisableDate(auditWorkOrderHistoryTable.getEtcDisableDate());
            provincialLevelExceldto.setObuLiftBanTime("OBU解禁时间");
            provincialLevelExceldto.setEtcLiftBanTime("ETC解禁时间");
            provincialLevelExceldto.setObuAccountStatus(obuAccountStatusMap.get(auditWorkOrderHistoryTable.getObuAccountStatus()));
            provincialLevelExceldto.setEtcStatusList(etcStatusMap.get(auditWorkOrderHistoryTable.getEtcStatusList()));
            provincialLevelExceldto.setVehStatusList(vehStatusMap.get(auditWorkOrderHistoryTable.getVehStatusList()));
            provincialLevelExceldto.setStatus(orderStatusMap.get(auditWorkOrderHistoryTable.getStatus()));
            provincialLevelExceldto.setResponsibilitys("责任");
            provincialLevelExceldto.setRemark("备注");
            provincialLevelExcels.add(provincialLevelExceldto);
        }
        return provincialLevelExcels;
    }


}
