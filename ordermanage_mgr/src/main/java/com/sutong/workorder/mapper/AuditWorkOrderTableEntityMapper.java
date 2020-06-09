package com.sutong.workorder.mapper;

import com.sutong.bjstjh.entity.AuditVehicleTable;
import com.sutong.workorder.entity.AuditWorkOrderTableEntity;
import com.sutong.workorder.model.AuditDataListQuery;
import com.sutong.workorder.model.AuditPendDisposalQuery;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @ClassName: AuditWorkOrderTableEntityMapper
 * @Description: 省级稽核---外部稽核数据清单mapper
 * @author: lichengquan
 * @date: 2020年01月09日 9:28
 * @Version: 1.0
 */
@Repository
@Mapper
public interface AuditWorkOrderTableEntityMapper {

    AuditWorkOrderTableEntity selectOneById(@Param("workOrderId") String workOrderId);

    List<AuditWorkOrderTableEntity> selectByQueryOne(AuditDataListQuery auditDataListQuery);

    List<AuditWorkOrderTableEntity> selectByQueryTwo(AuditPendDisposalQuery auditPendDisposalQuery);

    Integer insert(AuditWorkOrderTableEntity auditWorkOrderTableEntity);

    Integer deleteById(@Param("workOrderId") String workOrderId);

    Integer updateById(AuditWorkOrderTableEntity auditWorkOrderTableEntity);

    /**
     * 	根据条件去查询外部工单的数据
     *
     * @param map
     * @return
     * @author pengwz
     * @date 2020年1月15日 下午4:48:15
     */
    List<AuditWorkOrderTableEntity> doFindWorkorder(Map<String, Object> map);

    /**
     * 	根据实体类条件去查询外部工单
     * @param table
     * @return 
     * @author pengwz
     * @date 2020年1月17日 下午4:23:10
     */
    List<AuditWorkOrderTableEntity> doFindWorkorderByVO(AuditWorkOrderTableEntity table);

	/**
	 * 	根据工单编号查询车俩信息表
	 * @param vehicleTable
	 * @return 
	 * @author pengwz
	 * @date 2020年1月17日 下午4:37:20
	 */
	List<AuditVehicleTable> doFindAuditVehicleTable(@Param("data_number")String data_number);
}
