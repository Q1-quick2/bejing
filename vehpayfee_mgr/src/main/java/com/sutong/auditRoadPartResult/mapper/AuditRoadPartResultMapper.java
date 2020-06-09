package com.sutong.auditRoadPartResult.mapper;

import com.sutong.auditRoadPartResult.model.AuditRoadPartResult;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Description: 路段稽核结论管理
 * @author： Mr.Kong
 * @date: 2019/12/16 16:53
 */
@Repository
@Mapper
@Component
public interface AuditRoadPartResultMapper {

    /**
     * @description: 查询路段稽核结论分页
     * @auther: Mr.kong
     * @date: 2019/12/17 15:27
     * @Param record: 路段稽核结论实体
     * @return: java.util.List<com.sutong.auditRoadPartResult.model.AuditRoadPartResult>
     **/
    List<AuditRoadPartResult> doFindAuditRoadPartResultPage(AuditRoadPartResult record);

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
