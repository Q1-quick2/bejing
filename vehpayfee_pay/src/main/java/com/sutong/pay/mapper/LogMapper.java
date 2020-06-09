package com.sutong.pay.mapper;

import com.sutong.bjstjh.entity.pay.LogModel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * Created by main on 2019/12/24.
 */
@Mapper
@Repository
public interface LogMapper {
    // Aop切面记录日志
    void doAddAspectj(LogModel logModel);

    void doDelete(@Param("date") String date);

}
