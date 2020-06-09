package com.sutong.common.service;

import com.sutong.common.model.ConfigModel;

import java.util.List;

public interface ConfigService {
    //查询配置信息
     List<ConfigModel> doFindConfigList(ConfigModel config);
    //新增配置信息
     int doInsertConfig(ConfigModel config);
}
