package com.sutong.auditDissentFlow.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sutong.auditDissentFlow.mapper.AuditDissentFlowMapper;
import com.sutong.auditDissentFlow.model.AuditDissentFlow;
import com.sutong.auditDissentFlow.service.AuditDissentFlowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Description: 异议申请流水管理
 * @author： Mr.Kong
 * @date: 2019/12/19 15:08
 */
@Component
@Service
public class AuditDissentFlowServiceImpl implements AuditDissentFlowService {

    @Autowired
    private AuditDissentFlowMapper auditDissentFlowMapper;

    /**
     * @description: 查询异议申请流水分页
     * @auther: Mr.kong
     * @date: 2019/12/19 15:45
     * @Param pageNum: 当前页码
     * @Param pageSize: 分页条数
     * @Param auditDissentFlow: 异议申请流水实体
     * @return: com.github.pagehelper.PageInfo<com.sutong.auditDissentFlow.model.AuditDissentFlow>
     **/
    @Override
    public PageInfo<AuditDissentFlow> doFindAuditDissentFlowPage(int pageNum, int pageSize, AuditDissentFlow auditDissentFlow) {
        PageHelper.startPage(pageNum,pageSize);
        List<AuditDissentFlow> auditDissentFlows = auditDissentFlowMapper.doFindAuditDissentFlowPage(auditDissentFlow);
        PageInfo<AuditDissentFlow> pageInfo=new PageInfo<>(auditDissentFlows);
        return pageInfo;
    }

    @Override
    public int deleteByPrimaryKey(String dissentId) {
        return auditDissentFlowMapper.deleteByPrimaryKey(dissentId);
    }

    @Override
    public int insert(AuditDissentFlow record) {
        return auditDissentFlowMapper.insert(record);
    }

    @Override
    public int insertSelective(AuditDissentFlow record) {
        return auditDissentFlowMapper.insertSelective(record);
    }
    /**
     * @description: 查询异议申请流水详情
     * @auther: Mr.kong
     * @date: 2019/12/19 16:33
     * @Param dissentId: 异议流水ID
     * @return: com.sutong.auditDissentFlow.model.AuditDissentFlow
     **/
    @Override
    public AuditDissentFlow selectByPrimaryKey(String dissentId) {
        return auditDissentFlowMapper.selectByPrimaryKey(dissentId);
    }

    @Override
    public int updateByPrimaryKeySelective(AuditDissentFlow record) {
        return auditDissentFlowMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(AuditDissentFlow record) {
        return auditDissentFlowMapper.updateByPrimaryKey(record);
    }
}
