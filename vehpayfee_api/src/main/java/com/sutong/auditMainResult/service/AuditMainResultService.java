package com.sutong.auditMainResult.service;

import com.github.pagehelper.PageInfo;
import com.sutong.auditMainResult.model.AuditMainResult;

import java.util.List;

/**
 * @Description: 稽核结论信息管理
 * @author： Mr.Kong
 * @date: 2019/12/15 15:00
 */
public interface AuditMainResultService {
    /**
     * @description: 查询稽核结论信息列表
     * @auther: Mr.kong
     * @date: 2019/12/15 17:17
     * @Param record: 稽核结论信息实体
     * @return: java.util.List<com.sutong.auditMainResult.model.AuditMainResult>
     **/
    List<AuditMainResult> doFindAuditMainResultList(AuditMainResult record);

    /**
     * @description: 查询稽核结论信息分页
     * @auther: Mr.kong
     * @date: 2019/12/15 15:43
     * @Param pageNum: 当前页码
     * @Param pageSize: 分页条数
     * @Param record: 稽核结论信息实体
     * @return: com.github.pagehelper.PageInfo<com.sutong.auditMainResult.model.AuditMainResult>
     **/
    PageInfo<AuditMainResult> doFindAuditMainResultPage(int pageNum, int pageSize, AuditMainResult record);

    int deleteByPrimaryKey(String auditResultId);

    int insert(AuditMainResult record);

    int insertSelective(AuditMainResult record);

    AuditMainResult selectByPrimaryKey(String auditResultId);

    int updateByPrimaryKeySelective(AuditMainResult record);

    int updateByPrimaryKey(AuditMainResult record);
}
