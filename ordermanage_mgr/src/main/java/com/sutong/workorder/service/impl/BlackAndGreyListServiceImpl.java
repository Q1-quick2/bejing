package com.sutong.workorder.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sutong.bjstjh.result.Result;
import com.sutong.workorder.entity.AuditBlackListTableEntity;
import com.sutong.workorder.entity.AuditGrayListTableEntity;
import com.sutong.workorder.mapper.AuditBlackListTableEntityMapper;
import com.sutong.workorder.mapper.AuditGrayListTableEntityMapper;
import com.sutong.workorder.model.AuditBlackListSelectDTO;
import com.sutong.workorder.model.AuditBlackListSelectQuery;
import com.sutong.workorder.model.AuditGreyListSelectDTO;
import com.sutong.workorder.model.AuditGreyListSelectQuery;
import com.sutong.workorder.service.IBlackAndGreyListService;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * @ClassName: BlackAndGreyListServiceImpl
 * @Description: 黑名单和灰名单实现
 * @author: lichengquan
 * @date: 2019年12月19日 11:09
 * @Version: 1.0
 */
@Service
public class BlackAndGreyListServiceImpl implements IBlackAndGreyListService {

    @Autowired
    private AuditBlackListTableEntityMapper auditBlackListTableEntityMapper;

    @Autowired
    private AuditGrayListTableEntityMapper auditGrayListTableEntityMapper;

    /**
     * 查询黑名单
     *
     * @param auditBlackListSelectQuery
     * @return
     */
    @Override
    public PageInfo<AuditBlackListSelectDTO> doFindBlackList(AuditBlackListSelectQuery auditBlackListSelectQuery) {
        PageHelper.startPage(auditBlackListSelectQuery.getPageIndex(), auditBlackListSelectQuery.getPageSize());
        List<AuditBlackListSelectDTO> auditBlacklistDtos = new ArrayList<>();
        List<AuditBlackListTableEntity> auditBlackListTableEntities = auditBlackListTableEntityMapper.selectByQuery(auditBlackListSelectQuery);
        for (AuditBlackListTableEntity auditBlackListTableEntity : auditBlackListTableEntities) {
            AuditBlackListSelectDTO auditBlacklistDto = new AuditBlackListSelectDTO();
            auditBlacklistDto.setVehicleId(auditBlackListTableEntity.getVehPlateNo());
            auditBlacklistDto.setVehicleColor(auditBlackListTableEntity.getVehColor());
            auditBlacklistDto.setType(auditBlackListTableEntity.getType());
            auditBlacklistDto.setStatus(auditBlackListTableEntity.getStatus());
            auditBlacklistDto.setReason(auditBlackListTableEntity.getReason());
            auditBlacklistDto.setOweFee(Integer.parseInt(auditBlackListTableEntity.getOweFee()));
            auditBlacklistDto.setEvasioCount(auditBlackListTableEntity.getEvasioCount());
            auditBlacklistDto.setCreationTime(auditBlackListTableEntity.getCreationTime());
            auditBlacklistDto.setVersion(auditBlackListTableEntity.getVersion());
            auditBlacklistDtos.add(auditBlacklistDto);
        }
        PageInfo<AuditBlackListTableEntity> entityPageInfo = new PageInfo<>(auditBlackListTableEntities);
        PageInfo<AuditBlackListSelectDTO> pageInfo = new PageInfo<>(auditBlacklistDtos);
        pageInfo.setTotal(entityPageInfo.getTotal());
        pageInfo.setPageNum(entityPageInfo.getPageNum());
        pageInfo.setPageSize(entityPageInfo.getPageSize());
        pageInfo.setSize(entityPageInfo.getSize());
        return pageInfo;
    }

    /**
     * 保存黑名单
     *
     * @param auditBlackListTableEntity
     * @return
     */
    public Result doInsertBlackList(AuditBlackListTableEntity auditBlackListTableEntity) {
        Result error = Result.error();
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        String format = dateFormat.format(date);
        auditBlackListTableEntity.setBlackListId(String.valueOf(UUID.randomUUID()).replace("-","").substring(0, 24));
        auditBlackListTableEntity.setCreationTime(format);
        auditBlackListTableEntity.setProof("证据链");
        int insert = auditBlackListTableEntityMapper.insert(auditBlackListTableEntity);
        if (insert == 1) {
            return Result.ok();
        } else {
            error.setMessage("插入表失败");
        }
        return error;
    }

    /**
     * 查询灰名单
     *
     * @param auditGreyListSelectQuery
     * @return
     */
    @Override
    public PageInfo<AuditGreyListSelectDTO> doFindGreyList(AuditGreyListSelectQuery auditGreyListSelectQuery) {
        PageHelper.startPage(auditGreyListSelectQuery.getPageIndex(), auditGreyListSelectQuery.getPageSize());
        List<AuditGreyListSelectDTO> auditGreyListSelectDtos = new ArrayList<>();
        List<AuditGrayListTableEntity> auditGrayListTableEntities = auditGrayListTableEntityMapper.selectByQuery(auditGreyListSelectQuery);
        for (AuditGrayListTableEntity auditGrayListTableEntity : auditGrayListTableEntities) {
            AuditGreyListSelectDTO auditGreyListSelectDto = new AuditGreyListSelectDTO();
            auditGreyListSelectDto.setEnteredProvince(auditGrayListTableEntity.getEnteredProvince());
            auditGreyListSelectDto.setVehPlateNo(auditGrayListTableEntity.getVehPlateNo());
            auditGreyListSelectDto.setVehColor(auditGrayListTableEntity.getVehColor());
            auditGreyListSelectDto.setStatus(auditGrayListTableEntity.getStatus());
            auditGreyListSelectDto.setReason(auditGrayListTableEntity.getReason());
            auditGreyListSelectDto.setCreationTime(auditGrayListTableEntity.getCreationTime());
            auditGreyListSelectDto.setVersion(auditGrayListTableEntity.getVersion());
            auditGreyListSelectDto.setChainOfEvidence(auditGrayListTableEntity.getProof());
            auditGreyListSelectDtos.add(auditGreyListSelectDto);
        }
        PageInfo<AuditGrayListTableEntity> entityPageInfo = new PageInfo<>(auditGrayListTableEntities);
        PageInfo<AuditGreyListSelectDTO> pageInfo = new PageInfo<>(auditGreyListSelectDtos);
        pageInfo.setTotal(entityPageInfo.getTotal());
        pageInfo.setPageNum(entityPageInfo.getPageNum());
        pageInfo.setPageSize(entityPageInfo.getPageSize());
        pageInfo.setSize(entityPageInfo.getSize());
        return pageInfo;
    }

    /**
     * 插入灰名单
     *
     * @param auditGrayListTableEntity
     * @return
     */
    public Result doInsertGrayList(AuditGrayListTableEntity auditGrayListTableEntity) {
        Result error = Result.error();
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        String format = dateFormat.format(date);
        auditGrayListTableEntity.setProof("证据链");
        auditGrayListTableEntity.setGrayListId(String.valueOf(UUID.randomUUID()).replace("-","").substring(0, 24));
        auditGrayListTableEntity.setCreationTime(format);
        int insert = auditGrayListTableEntityMapper.insert(auditGrayListTableEntity);
        if (insert == 1) {
            return Result.ok();
        } else {
            error.setMessage("插入表失败");
        }
        return error;
    }
}
