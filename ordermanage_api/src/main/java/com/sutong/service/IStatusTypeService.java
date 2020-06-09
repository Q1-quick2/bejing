package com.sutong.service;

import com.sutong.bjstjh.entity.AuditStatusInfoTable;
import com.sutong.workorder.model.AuditStatusDTO;

import java.util.List;

/**
 * @ClassName: IStatusTypeService
 * @Description: 通用状态service接口
 * @author: lichengquan
 * @date: 2019年12月27日 17:34
 * @Version: 1.0
 */
public interface IStatusTypeService {

    List<AuditStatusInfoTable> dofindAllStatus(String statusType);

    List<AuditStatusDTO> dofindStatusByType(String statusType);
}
