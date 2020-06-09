package com.sutong.common.model;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @ClassName: ConfigModel
 * @author： pengjien
 * @date: 2019/12/23 14:02
 * @Description:配置实体类
 */
@Data
public class ConfigModel implements Serializable {
    private static final long serialVersionUID = 1L;
    /**主键ID*/
    private Integer id;
    /**配置value值*/
    private String configValue;
    /**配置类型（目前只存发行sessionKey，type类型为1，以后增加类型，依次往上增加）*/
    private Integer type;
    /**创建时间*/
    private Date createTime;
}
