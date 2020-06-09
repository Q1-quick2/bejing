package com.sutong.workorder.mapper;

import com.sutong.workorder.entity.AuditChargePathInfoEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @ClassName: AuditChargePathInfoEntityMapper
 * @Description: 收费路径信息表mapper
 * @author: lichengquan
 * @date: 2020年01月08日 17:44
 * @Version: 1.0
 */
@Repository
@Mapper
public interface AuditChargePathInfoEntityMapper {

    List<AuditChargePathInfoEntity> selectListByOrderId(@Param("workOrderId")String workOrderId);

}
