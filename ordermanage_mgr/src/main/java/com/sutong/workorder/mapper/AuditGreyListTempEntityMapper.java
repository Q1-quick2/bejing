package com.sutong.workorder.mapper;

import com.sutong.workorder.entity.AuditGreyListTempEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @ClassName: AuditGreyListTempEntityMapper
 * @Description: 灰名单表-中间表mapper
 * @author: lichengquan
 * @date: 2020年01月06日 18:04
 * @Version: 1.0
 */
@Repository
@Mapper
public interface AuditGreyListTempEntityMapper {

    List<AuditGreyListTempEntity> dofindAll();
}
