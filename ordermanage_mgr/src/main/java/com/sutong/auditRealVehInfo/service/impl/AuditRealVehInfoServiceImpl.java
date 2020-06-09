package com.sutong.auditRealVehInfo.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.sutong.auditRealVehInfo.mapper.AuditRealVehInfoMapper;
import com.sutong.auditRealVehInfo.service.AuditRealVehInfoService;
import com.sutong.bjstjh.entity.AuditRealVehInfo;
import com.sutong.bjstjh.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class AuditRealVehInfoServiceImpl implements AuditRealVehInfoService {

    @Autowired
    private AuditRealVehInfoMapper auditRealVehInfoMapper;
    @Override
    public Integer doInsertAuditRealVehInfo(AuditRealVehInfo auditRealVehInfo) {
//        调用插入方法之前先执行删除，还保留原来的外键
        auditRealVehInfoMapper.doDeleteAuditRealVehInfoById(auditRealVehInfo.getMinistryWorkOrderForeignId());
//        生成24位UUID大写主键
        String s = StringUtils.generateUUID();
        auditRealVehInfo.setRealVehInfoId(s);
        Integer integer = auditRealVehInfoMapper.doInsertAuditRealVehInfo(auditRealVehInfo);
        return integer;
    }
//  实际车辆信息查询
    @Override
    public AuditRealVehInfo doFindAuditRealVehInfo(String ministryWorkOrderForeignId) {
        return auditRealVehInfoMapper.doFindAuditRealVehInfo(ministryWorkOrderForeignId);
    }
}
