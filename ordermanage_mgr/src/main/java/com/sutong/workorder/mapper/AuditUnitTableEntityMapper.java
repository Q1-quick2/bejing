package com.sutong.workorder.mapper;

import com.sutong.workorder.entity.AuditUnitTableEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @ClassName: AuditUnitTableEntityMapper
 * @Description: 协查方实体mapper
 * @author: lichengquan
 * @date: 2020年01月16日 10:01
 * @Version: 1.0
 */
@Repository
@Mapper
public interface AuditUnitTableEntityMapper {

    List<AuditUnitTableEntity> doFindListByCheckId(@Param("checkCommentsId") String checkCommentsId);
}
