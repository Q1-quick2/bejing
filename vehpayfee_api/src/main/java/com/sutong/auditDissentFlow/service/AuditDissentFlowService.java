package com.sutong.auditDissentFlow.service;

import com.github.pagehelper.PageInfo;
import com.sutong.auditDissentFlow.model.AuditDissentFlow;

/**
 * @Description: 异议申请流水管理
 * @author： Mr.Kong
 * @date: 2019/12/19 15:07
 */
public interface AuditDissentFlowService {

    /**
     * @description: 查询异议申请流水分页
     * @auther: Mr.kong
     * @date: 2019/12/19 15:45
     * @Param pageNum: 当前页码
     * @Param pageSize: 分页条数
     * @Param auditDissentFlow: 异议申请流水实体
     * @return: com.github.pagehelper.PageInfo<com.sutong.auditDissentFlow.model.AuditDissentFlow>
     **/
    PageInfo<AuditDissentFlow> doFindAuditDissentFlowPage(int pageNum,int pageSize,AuditDissentFlow auditDissentFlow);

    int deleteByPrimaryKey(String dissentId);

    int insert(AuditDissentFlow record);

    int insertSelective(AuditDissentFlow record);
    /**
     * @description: 查询异议申请流水详情
     * @auther: Mr.kong
     * @date: 2019/12/19 16:33
     * @Param dissentId: 异议流水ID
     * @return: com.sutong.auditDissentFlow.model.AuditDissentFlow
     **/
    AuditDissentFlow selectByPrimaryKey(String dissentId);

    int updateByPrimaryKeySelective(AuditDissentFlow record);

    int updateByPrimaryKey(AuditDissentFlow record);
}
