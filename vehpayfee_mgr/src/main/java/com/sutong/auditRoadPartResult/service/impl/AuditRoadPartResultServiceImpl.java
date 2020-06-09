package com.sutong.auditRoadPartResult.service.impl;


import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sutong.auditEvidenceResult.model.AuditEvidenceResult;
import com.sutong.auditEvidenceResult.service.AuditEvidenceResultService;
import com.sutong.auditRoadPartResult.mapper.AuditRoadPartResultMapper;
import com.sutong.auditRoadPartResult.model.AuditRoadPartResult;
import com.sutong.auditRoadPartResult.service.AuditRoadPartResultService;
import com.sutong.bjstjh.util.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Description: 路段稽核结论管理
 * @author： Mr.Kong
 * @date: 2019/12/16 16:54
 */
@Component
@Service
public class AuditRoadPartResultServiceImpl implements AuditRoadPartResultService {

    @Autowired
    private AuditRoadPartResultMapper auditRoadPartResultMapper;
    @Autowired
    private AuditEvidenceResultService auditEvidenceResultService;

    /**
     * @description: 查询路段稽核结论分页
     * @auther: Mr.kong
     * @date: 2019/12/17 15:26
     * @Param pageNum: 当前页
     * @Param pageSize: 分页条数
     * @Param record: 路段稽核结论实体
     * @return: com.github.pagehelper.PageInfo<com.sutong.auditRoadPartResult.model.AuditRoadPartResult>
     **/
    @Override
    public PageInfo<AuditRoadPartResult> doFindAuditRoadPartResultPage(int pageNum, int pageSize, AuditRoadPartResult record) {
        //将参数传给这个方法就可以实现物理分页了，非常简单。
        PageHelper.startPage(pageNum, pageSize);
        List<AuditRoadPartResult> roadPartResults = auditRoadPartResultMapper.doFindAuditRoadPartResultPage(record);
        //设置证据数据量、收费单元数量
        if (ObjectUtils.isNotNull(roadPartResults)){
            for (AuditRoadPartResult auditRoadPartResult:roadPartResults){
                //证据数据量
                AuditEvidenceResult auditEvidenceResult=new AuditEvidenceResult();
                auditEvidenceResult.setOrderId(auditRoadPartResult.getOrderId());
                auditEvidenceResult.setAudType("2");
                List<AuditEvidenceResult> auditEvidenceResults = auditEvidenceResultService.doFindAuditEvidenceResultList(auditEvidenceResult);
                auditRoadPartResult.setEvidenceTotal(auditEvidenceResults.size());
                //收费单元数量
                auditRoadPartResult.setChargeUnitTotal(3);
            }
        }
        PageInfo<AuditRoadPartResult> pageInfo=new PageInfo<>(roadPartResults);
        return pageInfo;
    }

    @Override
    public int deleteByPrimaryKey(String orderId) {
        return auditRoadPartResultMapper.deleteByPrimaryKey(orderId);
    }

    @Override
    public int insert(AuditRoadPartResult record) {
        return auditRoadPartResultMapper.insert(record);
    }

    @Override
    public int insertSelective(AuditRoadPartResult record) {
        return auditRoadPartResultMapper.insertSelective(record);
    }
    /**
     * @description: 查询路段稽核结论详情
     * @auther: Mr.kong
     * @date: 2019/12/17 21:49
     * @Param orderId: 工单编号
     * @return: com.sutong.auditRoadPartResult.model.AuditRoadPartResult
     **/
    @Override
    public AuditRoadPartResult doFindByOrderId(String orderId) {
        return auditRoadPartResultMapper.doFindByOrderId(orderId);
    }

    @Override
    public int updateByPrimaryKeySelective(AuditRoadPartResult record) {
        return auditRoadPartResultMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(AuditRoadPartResult record) {
        return auditRoadPartResultMapper.updateByPrimaryKey(record);
    }
}
