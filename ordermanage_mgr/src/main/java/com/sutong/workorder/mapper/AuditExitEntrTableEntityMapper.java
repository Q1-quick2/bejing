package com.sutong.workorder.mapper;

import com.sutong.workorder.entity.AuditExitEntrTableEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @ClassName: AuditExitEntrTableEntityMapper
 * @Description: 省级稽核---出入口数据表Mapper
 * @author: lichengquan
 * @date: 2020年01月13日 17:28
 * @Version: 1.0
 */
@Repository
@Mapper
public interface AuditExitEntrTableEntityMapper {

    List<AuditExitEntrTableEntity> doFindExitEntr(@Param("workOrderId") String workOrderId, @Param("entryTime") List<String> entryTimes);
}
