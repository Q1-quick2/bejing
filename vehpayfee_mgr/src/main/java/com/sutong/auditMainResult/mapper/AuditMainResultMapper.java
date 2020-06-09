package com.sutong.auditMainResult.mapper;

import com.sutong.auditMainResult.model.AuditMainResult;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Description: 稽核结论信息管理
 * @author： Mr.Kong
 * @date: 2019/12/15 15:09
 */
@Repository
@Mapper
@Component
public interface AuditMainResultMapper {

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
     * @date: 2019/12/15 15:46
     * @Param record: 稽核结论信息实体
     * @return: java.util.List<com.sutong.auditMainResult.model.AuditMainResult>
     **/
    List<AuditMainResult> doFindAuditMainResultPage(AuditMainResult record);

    int deleteByPrimaryKey(String auditResultId);

    int insert(AuditMainResult record);

    int insertSelective(AuditMainResult record);

    AuditMainResult selectByPrimaryKey(String auditResultId);

    int updateByPrimaryKeySelective(AuditMainResult record);

    int updateByPrimaryKey(AuditMainResult record);

}
