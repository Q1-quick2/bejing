package com.sutong.workorder.mapper;

import com.sutong.workorder.entity.AuditTransTableEntity;
import com.sutong.workorder.model.AuditTransQuery;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @ClassName: auditTransTableEntityMapper
 * @Description: 收费门架数据mapper
 * @author: lichengquan
 * @date: 2020年01月14日 14:27
 * @Version: 1.0
 */
@Repository
@Mapper
public interface AuditTransTableEntityMapper {

    List<AuditTransTableEntity> selectByQuery(AuditTransQuery auditTransQuery);
}
