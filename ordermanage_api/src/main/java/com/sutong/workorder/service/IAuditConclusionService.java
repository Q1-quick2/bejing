package com.sutong.workorder.service;

import com.github.pagehelper.PageInfo;
import com.sutong.bjstjh.exception.BreakRulesException;
import com.sutong.bjstjh.result.Result;
import com.sutong.workorder.entity.AuditWorkOrderTableEntity;
import com.sutong.workorder.model.*;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @ClassName: IAuditConclusionService
 * @Description: 稽核结论查询
 * @author: lichengquan
 * @date: 2020年01月08日 14:59
 * @Version: 1.0
 */
public interface IAuditConclusionService {

    /**
     * 稽核结论列表查询
     *
     * @param auditCheckCommentsQuery
     * @return
     */
    PageInfo<AuditCheckCommentsDTO> doFindConclusionList(AuditCheckCommentsQuery auditCheckCommentsQuery);

    /**
     * 稽核结论展示单查询
     *
     * @param workOrderId
     * @return
     */
    AuditConclusionDTO getAuditConclusion(String workOrderId);

    /**
     * 外部稽核-稽核数据清单查询
     *
     * @param auditDataListQuery
     * @return
     */
    PageInfo<AuditDataListDTO> doFindAuditDataList(AuditDataListQuery auditDataListQuery);

    /**
     * 外部稽核-创建稽核工单查询
     *
     * @param workOrderId
     * @return
     */
    AuditAuditorListDTO createWorkOrderQuery(String workOrderId);

    /**
     * 外部稽核-待处理查询
     *
     * @param auditPendDisposalQuery
     * @return
     */
    PageInfo<AuditPendDisposalDTO> doPendingDisposalList(AuditPendDisposalQuery auditPendDisposalQuery);

    /**
     * 外部稽核-详情查看-数据概况
     *
     * @param workOrderId
     * @return
     */
    AuditDataSurveyDTO doFindDataSurvey(String workOrderId);

    /**
     * 外部稽核-稽核数据清单-出入口数据
     *
     * @param workOrderId
     * @return
     */
    List<AuditExitEntrDTO> doFindEntryAndExitList(String workOrderId);

    /**
     * 外部稽核-稽核数据清单-门架数据
     *
     * @param auditTransQuery
     * @return
     */
    PageInfo<AuditTransDTO> doFindAuditTrans(AuditTransQuery auditTransQuery);

    /**
     * 外部稽核-稽核数据清单-发行方稽核结论查询
     *
     * @param workOrderId
     * @return
     */
    AuditIssuerConclusionDTO doFindIssuerAuditConclusion(String workOrderId);

    /**
     * 外部稽核-稽核数据清单-路方稽核结论查询
     *
     * @param auditRoadConclusionQuery
     * @return
     */
    List<AuditRoadConclusionDTO> doFindRoadAuditConclusion(AuditRoadConclusionQuery auditRoadConclusionQuery);

    /**
     * 外部稽核-稽核数据清单-稽核结论-协查方
     *
     * @param checkCommentsId
     * @return
     */
    AuditUnitDTO doFindAuditUnitById(String checkCommentsId);

    /**
     * 外部稽核-创建稽查工单-提交
     *
     * @param auditAuditorListDto
     * @return
     */
    Result doCreateWorkOrder(AuditAuditorListDTO auditAuditorListDto);

	/**
	 * 	根据条件去查询外部工单
	 * @param table
	 * @param startTime 开始时间(作为查询'发起时间'的开始时间)
	 * @param endTime	结束时间(作为查询'发起时间'的结束时间)
	 * @param pageIndex 当前页码
	 * @param pageSize	当前页码显示条数
	 * @throws ParseException 当开始和结束时间无法正常转换时，抛出此异常
	 * @return 工单集合
	 * @author pengwz
	 * @date 2020年1月15日 下午4:38:02
	 */
	List<AuditWorkOrderTableEntity> doFindWorkorder(AuditWorkOrderTableEntity table, Date startTime,
			Date endTime, Integer pageIndex,Integer pageSize) throws BreakRulesException,ParseException;

	/**
	 * 	根据工单编号查询具体的省中心信息
	 * 	<p>
	 * 	目前省中心数据包含 1 工单概况  2 车辆信息 3 稽核数据
	 * @param <T>	封装返回的具体对象
	 * @param dataNumber
	 * @return
	 * @author pengwz
	 * @date 2020年1月17日 下午4:00:57
	 */
	<T extends Object> Map<String, List<T>> doFindProvincialCenter(String dataNumber);


}
