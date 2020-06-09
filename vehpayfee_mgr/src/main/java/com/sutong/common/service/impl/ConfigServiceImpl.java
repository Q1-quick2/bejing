package com.sutong.common.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.sutong.common.mapper.ConfigMapper;
import com.sutong.common.model.ConfigModel;
import com.sutong.common.service.ConfigService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @ClassName: ConfigServiceImpl
 * @author： pengjien
 * @date: 2019/12/23 14:06
 * @Description:配置实现类
 */
@Service
public class ConfigServiceImpl implements ConfigService {
    @Autowired
    private ConfigMapper configMapper;
    @Override
    public List<ConfigModel> doFindConfigList(ConfigModel config) {
        List<ConfigModel> configModelList = configMapper.doFindConfigList(config);
        return configModelList;
    }

    @Override
    public int doInsertConfig(ConfigModel config) {
        int i = configMapper.doInsertConfig(config);
        return i;
    }
}
