package com.sutong.workorder.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.sutong.bjstjh.entity.AuditDictionaryInfoTable;
import com.sutong.bjstjh.entity.AuditWorkOrderHistoryTable;
import com.sutong.historyOrder.mapper.HistoryOrderMapper;
import com.sutong.mapper.DictMapper;
import com.sutong.workorder.entity.AuditBlackListTableEntity;
import com.sutong.workorder.entity.AuditBlackListTempEntity;
import com.sutong.workorder.entity.AuditGrayListTableEntity;
import com.sutong.workorder.entity.AuditGreyListTempEntity;
import com.sutong.workorder.mapper.AuditBlackListTableEntityMapper;
import com.sutong.workorder.mapper.AuditBlackListTempEntityMapper;
import com.sutong.workorder.mapper.AuditGrayListTableEntityMapper;
import com.sutong.workorder.mapper.AuditGreyListTempEntityMapper;
import com.sutong.workorder.model.AuditBlacklistDTO;
import com.sutong.workorder.model.AuditGreyListDTO;
import com.sutong.workorder.service.IScheduleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @ClassName: IScheduleServiceImpl
 * @Description: 工单定时任务实现
 * @author: lichengquan
 * @date: 2019年12月23日 15:13
 * @Version: 1.0
 */
@Service
public class ScheduleServiceImpl implements IScheduleService {

    private static final Logger log = LoggerFactory.getLogger(ScheduleServiceImpl.class);

    @Autowired
    private AuditBlackListTableEntityMapper auditBlackListTableEntityMapper;

    @Autowired
    private AuditGrayListTableEntityMapper auditGrayListTableEntityMapper;

    @Autowired
    private HistoryOrderMapper historyOrderMapper;

    @Autowired
    private DictMapper dictMapper;

    @Autowired
    private AuditBlackListTempEntityMapper auditBlackListTempEntityMapper;

    @Autowired
    private AuditGreyListTempEntityMapper auditGreyListTempEntityMapper;

    /**
     * 稽核黑名单全量下载-保存定时任务
     */
    @Override
    public void blackListScheduleTask() {
        List<AuditBlacklistDTO> auditBlacklistDTOS = newAuditBlackDto();
        List<AuditBlackListTableEntity> auditBlackListTableEntities = new ArrayList<>();
        for (AuditBlacklistDTO auditBlacklistDTO : auditBlacklistDTOS) {
            Date date = new Date();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            String format = dateFormat.format(date);
            AuditBlackListTableEntity auditBlackListTableEntity = new AuditBlackListTableEntity();
            String[] split = auditBlacklistDTO.getVehicleId().split("_");
            auditBlackListTableEntity.setBlackListId(String.valueOf(UUID.randomUUID()).replace("-", "").substring(0, 24));
            auditBlackListTableEntity.setVehPlateNo(split[0]);
            auditBlackListTableEntity.setVehColor(Integer.parseInt(split[1]));
            auditBlackListTableEntity.setReason(auditBlacklistDTO.getReason());
            auditBlackListTableEntity.setStatus(auditBlacklistDTO.getStatus());
            auditBlackListTableEntity.setType(auditBlacklistDTO.getType());
            auditBlackListTableEntity.setVersion(auditBlacklistDTO.getVersion());
            auditBlackListTableEntity.setCreationTime(auditBlacklistDTO.getCreationTime());
            //TODO 生效时间 暂时不用，先用生成时间
            auditBlackListTableEntity.setEffectiveTime(format);
            auditBlackListTableEntity.setOweFee(String.valueOf(auditBlacklistDTO.getOweFee()));
            auditBlackListTableEntity.setEvasioCount(auditBlacklistDTO.getEvasionCount());
            auditBlackListTableEntity.setVehicleFeatureVersion(auditBlacklistDTO.getVehicleFeatureVersion());
            auditBlackListTableEntity.setVehicleFeatureCode(auditBlacklistDTO.getVehicleFeatureCode());
            auditBlackListTableEntities.add(auditBlackListTableEntity);
        }
        if (auditBlackListTableEntities.size() > 0) {
            Integer integer = auditBlackListTableEntityMapper.batchInsertBlackList(auditBlackListTableEntities);
            if (integer == auditBlacklistDTOS.size()) {
                log.info("成功");
            }
        }
    }

    /**
     * 稽核灰名单全量下载-保存定时任务
     */
    @Override
    public void greyListScheduleTask() {
        List<AuditGreyListDTO> auditGreyListDTOS = newAuditGreyListDto();
        List<AuditGrayListTableEntity> auditGrayListTableEntities = new ArrayList<>();
        List<AuditDictionaryInfoTable> auditDictionaryInfoTables = dictMapper.queryDictByDictType("DICT_ENTERED_PROVINCE");
        Map<Integer, String> stringMap = auditDictionaryInfoTables.stream().collect(Collectors.toMap(AuditDictionaryInfoTable::getDictNumber, AuditDictionaryInfoTable::getDictName));
        for (AuditGreyListDTO auditGreyListDTO : auditGreyListDTOS) {
            AuditGrayListTableEntity auditGrayListTableEntity = new AuditGrayListTableEntity();
            String[] split = auditGreyListDTO.getVehicleId().split("_");
            auditGrayListTableEntity.setGrayListId(String.valueOf(UUID.randomUUID()).replace("-", "").substring(0, 24));
            auditGrayListTableEntity.setVehPlateNo(split[0]);
            auditGrayListTableEntity.setVehColor(Integer.parseInt(split[1]));
            auditGrayListTableEntity.setReason(auditGreyListDTO.getReason());
            auditGrayListTableEntity.setStatus(auditGreyListDTO.getStatus());
            auditGrayListTableEntity.setCreationTime(auditGreyListDTO.getCreationTime());
            auditGrayListTableEntity.setEnteredProvinceId(Integer.valueOf(auditGreyListDTO.getTollProvinceId()));
            auditGrayListTableEntity.setEnteredProvince(stringMap.get(Integer.valueOf(auditGreyListDTO.getTollProvinceId())));
            auditGrayListTableEntity.setVersion(Integer.valueOf(auditGreyListDTO.getVersion()));
            auditGrayListTableEntities.add(auditGrayListTableEntity);
        }
        if (auditGrayListTableEntities.size() > 0) {
            Integer integer = auditGrayListTableEntityMapper.batchInsertGrayList(auditGrayListTableEntities);
            if (integer == auditGreyListDTOS.size()) {
                log.info("成功");
            }
        }
    }

    /**
     * 取得历史工单表中未通知客户的工单
     *
     * @return
     */
    @Override
    public List<AuditWorkOrderHistoryTable> getAuditWorkOrderHistory() {
        return historyOrderMapper.selectByInFormCustomersFlag();
    }

    /**
     * 更新通知状态为已通知
     *
     * @param keyIds
     * @return
     */
    @Override
    public Integer updateInFormCustomersFlag(List<String> keyIds) {
        return historyOrderMapper.updateInFormCustomersFlagById(keyIds);
    }

    /**
     * 获得未解禁的信息
     *
     * @return
     */
    @Override
    public List<AuditWorkOrderHistoryTable> getCardLiftingWorkOrder() {
        return historyOrderMapper.getCardLiftingWorkOrder();
    }

    /**
     * 将状态变更为解禁
     *
     * @param keyIds
     * @return
     */
    @Override
    public Integer updateEtcStatus(List<String> keyIds) {
        return historyOrderMapper.updateEtcStatus(keyIds);
    }


    private List<AuditBlacklistDTO> newAuditBlackDto() {
        List<AuditBlackListTempEntity> auditBlackListTempEntityList = auditBlackListTempEntityMapper.doFindAll();
        List<AuditBlacklistDTO> auditBlacklistDtos = new ArrayList<>();
        for (AuditBlackListTempEntity auditBlackListTempEntity : auditBlackListTempEntityList) {
            AuditBlacklistDTO auditBlacklistDto = new AuditBlacklistDTO();
            auditBlacklistDto.setVehicleId(auditBlackListTempEntity.getVehicleId());
            auditBlacklistDto.setStatus(auditBlackListTempEntity.getStatus());
            auditBlacklistDto.setType(auditBlackListTempEntity.getType());
            auditBlacklistDto.setVehicleFeatureVersion(auditBlackListTempEntity.getVehicleFeatureVersion());
            auditBlacklistDto.setVehicleFeatureCode(auditBlackListTempEntity.getVehicleFeatureCode());
            auditBlacklistDto.setReason(auditBlackListTempEntity.getReason());
            auditBlacklistDto.setOweFee(auditBlackListTempEntity.getOweFee());
            auditBlacklistDto.setEvasionCount(auditBlackListTempEntity.getEvasionCount());
            auditBlacklistDto.setCreationTime(auditBlackListTempEntity.getCreationTime());
            auditBlacklistDto.setVersion(auditBlackListTempEntity.getVersion());
            auditBlacklistDtos.add(auditBlacklistDto);
        }
        return auditBlacklistDtos;
    }


    private List<AuditGreyListDTO> newAuditGreyListDto() {
        List<AuditGreyListTempEntity> auditGreyListTempEntityList = auditGreyListTempEntityMapper.dofindAll();
        List<AuditGreyListDTO> auditGreyListDtos = new ArrayList<>();
        for (AuditGreyListTempEntity auditGreyListTempEntity : auditGreyListTempEntityList) {
            AuditGreyListDTO auditGreyListDTO = new AuditGreyListDTO();
            auditGreyListDTO.setVehicleId(auditGreyListTempEntity.getVehicleId());
            auditGreyListDTO.setStatus(auditGreyListTempEntity.getStatus());
            auditGreyListDTO.setReason(auditGreyListTempEntity.getReason());
            auditGreyListDTO.setCreationTime(auditGreyListTempEntity.getCreationTime());
            auditGreyListDTO.setTollProvinceId("1");
            auditGreyListDTO.setProvince("北京市");
            auditGreyListDTO.setVersion(auditGreyListTempEntity.getVersion());
            auditGreyListDtos.add(auditGreyListDTO);
        }
        return auditGreyListDtos;
    }


}
