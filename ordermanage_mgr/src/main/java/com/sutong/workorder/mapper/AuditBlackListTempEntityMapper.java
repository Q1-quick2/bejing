package com.sutong.workorder.mapper;

import com.sutong.workorder.entity.AuditBlackListTempEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @ClassName: AuditBlackListTempEntityMapper
 * @Description: 黑名单表-中间表mapper
 * @author: lichengquan
 * @date: 2020年01月06日 19:10
 * @Version: 1.0
 */
@Repository
@Mapper
public interface AuditBlackListTempEntityMapper {

    List<AuditBlackListTempEntity> doFindAll();
}
