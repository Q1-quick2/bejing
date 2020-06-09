package com.sutong.mapper;

import com.sutong.bjstjh.entity.AuditStatusInfoTable;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @ClassName: StatusTypeMapper
 * @Description: 通用状态mapper
 * @author: lichengquan
 * @date: 2019年12月27日 17:38
 * @Version: 1.0
 */
@Repository
@Mapper
@Component
public interface StatusTypeMapper {

    List<AuditStatusInfoTable> selectAll();

    List<AuditStatusInfoTable> selectByStatusType(String statusType);
}
