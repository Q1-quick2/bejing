package com.sutong.dodgingtoll.model.vo;

import com.sutong.dodgingtoll.model.AuditPastOrder;
import com.sutong.dodgingtoll.model.AuditPastOrderInfo;
import lombok.Data;
import lombok.ToString;

import java.util.List;

/**
 * @Description:
 * @ClassName: AuditPastOrderVo
 * @author： lzq
 * @date: 2019/12/18 9:33
 * @Version： 1.0
 */
@Data
@ToString
public class AuditPastOrderVo extends AuditPastOrder{


    private List<AuditPastOrderInfo> auditPastOrderInfos;

}
