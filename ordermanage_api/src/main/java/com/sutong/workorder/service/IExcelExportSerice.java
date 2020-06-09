package com.sutong.workorder.service;

import com.sutong.workorder.model.AuditHistoryExcelDTO;
import com.sutong.workorder.model.MinistryLevelExcelDTO;
import com.sutong.workorder.model.ProvincialLevelExcelDTO;

import java.util.List;

/**
 * @ClassName: IExcelExportSerice
 * @Description: excel导出接口
 * @author: lichengquan
 * @date: 2019年12月24日 20:39
 * @Version: 1.0
 */
public interface IExcelExportSerice {

    /**
     * 部级导出
     * @param auditHistoryExcelDTO
     * @return
     */
    List<MinistryLevelExcelDTO> buildMinistryLevelExcel(AuditHistoryExcelDTO auditHistoryExcelDTO);

    /**
     * 省级导出
     * @param auditHistoryExcelDTO
     * @return
     */
    List<ProvincialLevelExcelDTO> buildProvincialLevelExcel(AuditHistoryExcelDTO auditHistoryExcelDTO);

}
