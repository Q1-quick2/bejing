package com.sutong.auditPastOrderInfo.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sutong.auditPastOrderInfo.mapper.AuditPastOrderInfoMapper;
import com.sutong.auditPastOrderInfo.model.AuditPastOrderInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Description: 通行费差额明细单管理
 * @author： Mr.Kong
 * @date: 2019/12/18 10:03
 */
@Component
@Service
public class AuditPastOrderInfoServiceImpl implements AuditPastOrderInfoService{

    @Autowired
    private AuditPastOrderInfoMapper auditPastOrderInfoMapper;
    /**
     * @description: 查询通行费差额明细单列表
     * @auther: Mr.kong
     * @date: 2019/12/18 16:19
     * @Param transObuId: 交易时OBU号
     * @return: java.util.List<com.sutong.auditPastOrderInfo.model.AuditPastOrderInfo>
     **/
    @Override
    public PageInfo<AuditPastOrderInfo> doFindAuditPastOrderInfoPage(AuditPastOrderInfo auditPastOrderInfo, int pageNun, int pageSize) {
        PageHelper.startPage(pageNun,pageSize);
        List<AuditPastOrderInfo> auditPastOrderInfos = auditPastOrderInfoMapper.doFindAuditPastOrderInfoPage(auditPastOrderInfo);
        PageInfo<AuditPastOrderInfo> pageInfo=new PageInfo<>(auditPastOrderInfos);
        return pageInfo;
    }

    @Override
    public int deleteByPrimaryKey(String id) {
        return auditPastOrderInfoMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(AuditPastOrderInfo record) {
        return auditPastOrderInfoMapper.insert(record);
    }

    @Override
    public int insertSelective(AuditPastOrderInfo record) {
        return auditPastOrderInfoMapper.insertSelective(record);
    }

    @Override
    public AuditPastOrderInfo selectByPrimaryKey(String id) {
        return auditPastOrderInfoMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(AuditPastOrderInfo record) {
        return auditPastOrderInfoMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(AuditPastOrderInfo record) {
        return auditPastOrderInfoMapper.updateByPrimaryKey(record);
    }
}
