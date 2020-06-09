package com.sutong.auditDissentEvidence.mapper;

import com.sutong.auditDissentEvidence.model.AuditDissentEvidence;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Description: 异议申请流水证据管理
 * @author： Mr.Kong
 * @date: 2019/12/19 15:02
 */
@Repository
@Component
@Mapper
public interface AuditDissentEvidenceMapper {
    /**
     * @description: 查询异议申请流水证据集合
     * @auther: Mr.kong
     * @date: 2019/12/19 16:34
     * @Param dissentId: 异议编码
     * @return: java.util.List<com.sutong.auditDissentEvidence.model.AuditDissentEvidence>
     **/
    List<AuditDissentEvidence> doFindAuditDissentEvidenceList(String dissentId);

    int deleteByPrimaryKey(String dissentId);

    int insert(AuditDissentEvidence record);

    int insertSelective(AuditDissentEvidence record);

    AuditDissentEvidence selectByPrimaryKey(String dissentId);

    int updateByPrimaryKeySelective(AuditDissentEvidence record);

    int updateByPrimaryKey(AuditDissentEvidence record);
}
