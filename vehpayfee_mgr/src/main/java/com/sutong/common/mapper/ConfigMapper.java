package com.sutong.common.mapper;

import com.sutong.common.model.ConfigModel;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface ConfigMapper {
    //根据条查询
   List<ConfigModel> doFindConfigList(ConfigModel config);
    //新增数据
    int doInsertConfig(ConfigModel config);
}
