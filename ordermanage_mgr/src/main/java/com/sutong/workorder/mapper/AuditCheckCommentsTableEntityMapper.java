package com.sutong.workorder.mapper;

import com.sutong.workorder.entity.AuditCheckCommentsTableEntity;
import com.sutong.workorder.model.AuditCheckCommentsDTO;
import com.sutong.workorder.model.AuditCheckCommentsQuery;
import com.sutong.workorder.model.AuditRoadConclusionQuery;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @ClassName: AuditCheckCommentsTableEntityMapper
 * @Description: 稽核结论（稽核数据）mapper
 * @author: lichengquan
 * @date: 2020年01月08日 10:45
 * @Version: 1.0
 */
@Repository
@Mapper
public interface AuditCheckCommentsTableEntityMapper {

    /**
     * 稽核结论列表查询
     *
     * @param auditCheckCommentsQuery
     * @return
     */
    List<AuditCheckCommentsDTO> selectByQuery(AuditCheckCommentsQuery auditCheckCommentsQuery);

    /**
     * 依据清单id查询稽核结论
     *
     * @param workOrderId 清单id
     * @param adscription 结论归属方
     * @return
     */
    List<AuditCheckCommentsTableEntity> selectOneByOrderId(@Param("workOrderId") String workOrderId, @Param("adscription") Integer adscription);

    /**
     * 路方稽核结论查询
     *
     * @param auditRoadConclusionQuery
     * @return
     */
    List<AuditCheckCommentsTableEntity> selectRoadByQuery(AuditRoadConclusionQuery auditRoadConclusionQuery);
    /**
     * 	根据工单编号查询稽核数据
     * @param table
     * @return 
     * @author pengwz
     * @date 2020年1月17日 下午5:29:45
     */
    List<AuditCheckCommentsTableEntity>doFindAuditCheckcommentsTable(@Param("data_number")String data_number);
}
