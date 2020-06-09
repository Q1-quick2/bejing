package com.sutong.auditDissentFlow.mapper;

import com.sutong.auditDissentFlow.model.AuditDissentFlow;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Description: 异议申请流水管理
 * @author： Mr.Kong
 * @date: 2019/12/19 15:02
 */
@Repository
@Component
@Mapper
public interface AuditDissentFlowMapper {

    /**
     * @description: 查询异议申请流水分页
     * @auther: Mr.kong
     * @date: 2019/12/19 15:46
     * @Param auditDissentFlow:异议申请流水实体
     * @return: java.util.List<com.sutong.auditDissentFlow.model.AuditDissentFlow>
     **/
    List<AuditDissentFlow> doFindAuditDissentFlowPage(AuditDissentFlow auditDissentFlow);

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
