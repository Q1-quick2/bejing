package com.sutong.workorder.service;

import com.github.pagehelper.PageInfo;
import com.sutong.workorder.model.AuditBlackListSelectDTO;
import com.sutong.workorder.model.AuditBlackListSelectQuery;
import com.sutong.workorder.model.AuditGreyListSelectDTO;
import com.sutong.workorder.model.AuditGreyListSelectQuery;

/**
 * @ClassName: IBlackAndGreyListService
 * @Description: 黑名单和灰名单相关接口
 * @author: lichengquan
 * @date: 2019年12月19日 11:06
 * @Version: 1.0
 */
public interface IBlackAndGreyListService {

    /**
     * 查询黑名单
     *
     * @param auditBlackListSelectQuery
     * @return
     */
    PageInfo<AuditBlackListSelectDTO> doFindBlackList(AuditBlackListSelectQuery auditBlackListSelectQuery);

    /**
     * 查询灰名单
     *
     * @param auditGreyListSelectQuery
     * @return
     */
    PageInfo<AuditGreyListSelectDTO> doFindGreyList(AuditGreyListSelectQuery auditGreyListSelectQuery);

}
