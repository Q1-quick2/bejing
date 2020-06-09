package com.sutong.auditRoadPartResult.service;

import com.github.pagehelper.PageInfo;
import com.sutong.auditRoadPartResult.model.AuditRoadPartResult;

/**
 * @Description: 路段稽核结论管理
 * @author： Mr.Kong
 * @date: 2019/12/16 16:54
 */
public interface AuditRoadPartResultService {

    /**
     * @description: 查询路段稽核结论分页
     * @auther: Mr.kong
     * @date: 2019/12/17 15:26
     * @Param pageNum: 当前页
     * @Param pageSize: 分页条数
     * @Param record: 路段稽核结论实体
     * @return: com.github.pagehelper.PageInfo<com.sutong.auditRoadPartResult.model.AuditRoadPartResult>
     **/
    PageInfo<AuditRoadPartResult> doFindAuditRoadPartResultPage(int pageNum, int pageSize, AuditRoadPartResult record);

    int deleteByPrimaryKey(String orderId);

    int insert(AuditRoadPartResult record);

    int insertSelective(AuditRoadPartResult record);

    /**
     * @description: 查询路段稽核结论详情
     * @auther: Mr.kong
     * @date: 2019/12/17 21:49
     * @Param orderId: 工单编号
     * @return: com.sutong.auditRoadPartResult.model.AuditRoadPartResult
     **/
    AuditRoadPartResult doFindByOrderId(String orderId);

    int updateByPrimaryKeySelective(AuditRoadPartResult record);

    int updateByPrimaryKey(AuditRoadPartResult record);
}
