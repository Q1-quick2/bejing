package com.sutong.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.sutong.bjstjh.entity.AuditStatusInfoTable;
import com.sutong.mapper.StatusTypeMapper;
import com.sutong.service.IStatusTypeService;
import com.sutong.workorder.model.AuditStatusDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName: StatusTypeServiceImpl
 * @Description: TODO
 * @author: lichengquan
 * @date: 2019年12月27日 17:36
 * @Version: 1.0
 */
@Service
public class StatusTypeServiceImpl implements IStatusTypeService {

    @Autowired
    private StatusTypeMapper statusTypeMapper;

    @Override
    public List<AuditStatusInfoTable> dofindAllStatus(String statusType) {
        return statusTypeMapper.selectByStatusType(statusType);
    }

    @Override
    public List<AuditStatusDTO> dofindStatusByType(String statusType) {
        List<AuditStatusDTO> auditStatuS=new ArrayList<>();
        List<AuditStatusInfoTable> auditStatusInfoTables = statusTypeMapper.selectByStatusType(statusType);
        if(!CollectionUtils.isEmpty(auditStatusInfoTables)){
            for(AuditStatusInfoTable auditStatusInfoTable:auditStatusInfoTables){
                AuditStatusDTO auditStatus=new AuditStatusDTO();
                auditStatus.setTypeNumber(auditStatusInfoTable.getTypeFatherNumber()+"-"+auditStatusInfoTable.getTypeSerialNumber());
                auditStatus.setTypeName(auditStatusInfoTable.getTypeName());
                auditStatuS.add(auditStatus);
            }
        }
        return auditStatuS;
    }
}
