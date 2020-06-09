package com.sutong.workorder.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sutong.bjstjh.entity.AuditVehicleTable;
import com.sutong.bjstjh.exception.BreakRulesException;
import com.sutong.bjstjh.result.Result;
import com.sutong.bjstjh.result.ResultCode;
import com.sutong.workorder.entity.*;
import com.sutong.workorder.mapper.*;
import com.sutong.workorder.model.*;
import com.sutong.workorder.service.IAuditConclusionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;


/**
 * @ClassName: AuditConclusionServiceImpl
 * @Description: 稽核结论接口实现
 * @author: lichengquan
 * @date: 2020年01月08日 15:04
 * @Version: 1.0
 */
@Service
public class AuditConclusionServiceImpl implements IAuditConclusionService {

    @Autowired
    private AuditCheckCommentsTableEntityMapper auditCheckCommentsTableEntityMapper;
    @Autowired
    private AuditWorkOrderTableEntityMapper auditWorkOrderTableEntityMapper;
    @Autowired
    private AuditChargePathInfoEntityMapper auditChargePathInfoEntityMapper;
    @Autowired
    private AuditExitEntrTableEntityMapper auditExitEntrTableEntityMapper;
    @Autowired
    private AuditTransTableEntityMapper auditTransTableEntityMapper;

    @Autowired
    private AuditUnitTableEntityMapper auditUnitTableEntityMapper;

    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

    /**
     * 稽核结论列表查询
     *
     * @param auditCheckCommentsQuery
     * @return
     */
    @Override

    public PageInfo<AuditCheckCommentsDTO> doFindConclusionList(AuditCheckCommentsQuery auditCheckCommentsQuery) {
        PageHelper.startPage(auditCheckCommentsQuery.getPageIndex(), auditCheckCommentsQuery.getPageSize());
        List<AuditCheckCommentsDTO> auditCheckCommentsList = auditCheckCommentsTableEntityMapper.selectByQuery(auditCheckCommentsQuery);
        PageInfo<AuditCheckCommentsDTO> pageInfo = new PageInfo<>(auditCheckCommentsList);
        return pageInfo;
    }

    /**
     * 稽核结论展示单查询
     *
     * @param workOrderId
     * @return
     */
    @Override
    public AuditConclusionDTO getAuditConclusion(String workOrderId) {
        //外部稽核数据清单
        AuditWorkOrderTableEntity auditWorkOrderTableEntity = auditWorkOrderTableEntityMapper.selectOneById(workOrderId);
        //稽核结论
        List<AuditCheckCommentsTableEntity> auditCheckCommentsTableEntitys = auditCheckCommentsTableEntityMapper.selectOneByOrderId(workOrderId, 3);
        //获取最新的外部稽核工单
        AuditCheckCommentsTableEntity auditCheckCommentsTableEntity = auditCheckCommentsTableEntitys.stream().filter(Objects::isNull).max(Comparator.comparing(AuditCheckCommentsTableEntity::getUpdateTime)).orElse(new AuditCheckCommentsTableEntity());
        //收费路径信息
        List<AuditChargePathInfoEntity> auditChargePathInfoEntities = auditChargePathInfoEntityMapper.selectListByOrderId(workOrderId);
        AuditConclusionDTO auditConclusionDto = new AuditConclusionDTO();
        auditConclusionDto.setVehicleNo(auditWorkOrderTableEntity.getVehicleNo());
        auditConclusionDto.setVehColor(auditWorkOrderTableEntity.getVehColor());
        auditConclusionDto.setVehType(auditWorkOrderTableEntity.getVehType());
        auditConclusionDto.setVehClass(auditWorkOrderTableEntity.getVehClass());
        auditConclusionDto.setAuditTime(auditCheckCommentsTableEntity.getCreateTime());
        auditConclusionDto.setProcesser(auditCheckCommentsTableEntity.getProcesser());
        auditConclusionDto.setEscapeType(auditWorkOrderTableEntity.getEscapeTypeOne() + "," + auditWorkOrderTableEntity.getEscapeTypeTwo());
        if (StringUtils.isEmpty(auditCheckCommentsTableEntity.getEscapeType())) {
            auditConclusionDto.setWhetherEscape("否");
        } else {
            auditConclusionDto.setWhetherEscape("是");
        }
        auditConclusionDto.setProof("证据");
        List<AuditChargePathInfoDTO> auditChargePathInfos = new ArrayList<>();
        for (AuditChargePathInfoEntity auditChargePathInfoEntity : auditChargePathInfoEntities) {
            AuditChargePathInfoDTO auditChargePathInfoDto = new AuditChargePathInfoDTO();
            auditChargePathInfoDto.setKeyId(auditChargePathInfoEntity.getKeyId());
            auditChargePathInfoDto.setWorkOrderId(auditChargePathInfoEntity.getWorkOrderId());
            auditChargePathInfoDto.setChargeUnitNumber(auditChargePathInfoEntity.getChargeUnitNumber());
            auditChargePathInfoDto.setChargeUnitName(auditChargePathInfoEntity.getChargeUnitName());
            auditChargePathInfoDto.setPassageTime(auditChargePathInfoEntity.getPassageTime());
            auditChargePathInfoDto.setTollReceivable(auditChargePathInfoEntity.getTollReceivable());
            auditChargePathInfoDto.setFittingFlag(auditChargePathInfoEntity.getFittingFlag());
            auditChargePathInfoDto.setChargeAmount(auditChargePathInfoEntity.getChargeAmount());
            auditChargePathInfos.add(auditChargePathInfoDto);
        }
        auditConclusionDto.setAuditChargePathInfos(auditChargePathInfos);
        //应收金额
        int chargeAmountSum = 0;
        for (AuditChargePathInfoEntity auditChargePathInfoEntity : auditChargePathInfoEntities) {
            chargeAmountSum += auditChargePathInfoEntity.getChargeAmount();
        }
        auditConclusionDto.setAmountReceivable(chargeAmountSum);
        //实收金额
        auditConclusionDto.setAmountReceived(auditCheckCommentsTableEntity.getAdvanceAmount());
        //欠费金额
        auditConclusionDto.setAmountOfArrears(auditCheckCommentsTableEntity.getFee());
        //其他金额=应收-实收-欠费
        auditConclusionDto.setOtherAmount(chargeAmountSum - auditCheckCommentsTableEntity.getAdvanceAmount() - auditCheckCommentsTableEntity.getFee());
        return auditConclusionDto;
    }

    /**
     * 外部稽核-稽核数据清单查询
     *
     * @param auditDataListQuery
     * @return
     */
    @Override
    public PageInfo<AuditDataListDTO> doFindAuditDataList(AuditDataListQuery auditDataListQuery) {
        PageHelper.startPage(auditDataListQuery.getPageIndex(), auditDataListQuery.getPageSize());
        List<AuditDataListDTO> auditDataListDtos = new ArrayList<>();
        List<AuditWorkOrderTableEntity> auditWorkOrderTableEntities = auditWorkOrderTableEntityMapper.selectByQueryOne(auditDataListQuery);
        for (AuditWorkOrderTableEntity auditWorkOrderTableEntity : auditWorkOrderTableEntities) {
            AuditDataListDTO auditDataListDto = new AuditDataListDTO();
            auditDataListDto.setWorkOrderId(auditWorkOrderTableEntity.getWorkOrderId());
            auditDataListDto.setVehicleNo(auditWorkOrderTableEntity.getVehicleNo());
            auditDataListDto.setVehColor(auditWorkOrderTableEntity.getVehColor());
            auditDataListDto.setEntryTime(auditWorkOrderTableEntity.getEntryTime());
            auditDataListDto.setEntryPlace(auditWorkOrderTableEntity.getEntryPlace());
            auditDataListDto.setExitTime(auditWorkOrderTableEntity.getExitTime());
            auditDataListDto.setExitPlace(auditWorkOrderTableEntity.getExitPlace());
            auditDataListDto.setPassProvince(auditWorkOrderTableEntity.getPassProvince());
            auditDataListDto.setPassProvinceTotal(auditWorkOrderTableEntity.getPassProvinceTotal());
            auditDataListDto.setPassRoadTotal(auditWorkOrderTableEntity.getPassRoadTotal());
            auditDataListDto.setPassMediaType(auditWorkOrderTableEntity.getPassMediaType());
            auditDataListDto.setObuNo(auditWorkOrderTableEntity.getObuNo());
            auditDataListDto.setEtcNoCpcNo(auditWorkOrderTableEntity.getEtcNoCpcNo());
            auditDataListDto.setSpecialType(auditWorkOrderTableEntity.getSpecialType());
            auditDataListDto.setRansTotal(auditWorkOrderTableEntity.getRansTotal());
            auditDataListDto.setWhetherAudit(auditWorkOrderTableEntity.getWhetherAudit());
            auditDataListDtos.add(auditDataListDto);
        }
        PageInfo<AuditWorkOrderTableEntity> entityPageInfo = new PageInfo<>(auditWorkOrderTableEntities);
        PageInfo<AuditDataListDTO> pageInfo = new PageInfo<>(auditDataListDtos);
        pageInfo.setTotal(entityPageInfo.getTotal());
        pageInfo.setPageNum(entityPageInfo.getPageNum());
        pageInfo.setPageSize(entityPageInfo.getPageSize());
        pageInfo.setSize(entityPageInfo.getSize());
        return pageInfo;
    }

    /**
     * 外部稽核-创建稽核工单查询
     *
     * @param workOrderId
     * @return
     */
    @Override
    public AuditAuditorListDTO createWorkOrderQuery(String workOrderId) {
        AuditWorkOrderTableEntity auditWorkOrderTableEntity = auditWorkOrderTableEntityMapper.selectOneById(workOrderId);
        AuditAuditorListDTO auditAuditorListDto = new AuditAuditorListDTO();
        auditAuditorListDto.setWorkOrderId(auditWorkOrderTableEntity.getWorkOrderId());
        auditAuditorListDto.setDataNumber(auditWorkOrderTableEntity.getDataNumber());
        auditAuditorListDto.setVehicleNo(auditWorkOrderTableEntity.getVehicleNo());
        auditAuditorListDto.setVehColor(auditWorkOrderTableEntity.getVehColor());
        auditAuditorListDto.setVehType(auditWorkOrderTableEntity.getVehType());
        auditAuditorListDto.setVehClass(auditWorkOrderTableEntity.getVehClass());
        auditAuditorListDto.setPassProvince(auditWorkOrderTableEntity.getPassProvince());
        auditAuditorListDto.setEscape(auditWorkOrderTableEntity.getEscape());
        return auditAuditorListDto;
    }

    /**
     * 外部稽核-待处理查询
     *
     * @param auditPendDisposalQuery
     * @return
     */
    @Override
    public PageInfo<AuditPendDisposalDTO> doPendingDisposalList(AuditPendDisposalQuery auditPendDisposalQuery) {
        PageHelper.startPage(auditPendDisposalQuery.getPageIndex(), auditPendDisposalQuery.getPageSize());
        List<AuditPendDisposalDTO> auditPendDisposals = new ArrayList<>();
        List<AuditWorkOrderTableEntity> auditWorkOrderTableEntities = auditWorkOrderTableEntityMapper.selectByQueryTwo(auditPendDisposalQuery);
        for (AuditWorkOrderTableEntity auditWorkOrderTableEntity : auditWorkOrderTableEntities) {
            AuditPendDisposalDTO auditPendDisposalDto = new AuditPendDisposalDTO();
            auditPendDisposalDto.setWorkOrderId(auditWorkOrderTableEntity.getWorkOrderId());
            auditPendDisposalDto.setWorkOrderTitle(auditWorkOrderTableEntity.getWorkOrderTitle());
            auditPendDisposalDto.setVehicleNo(auditWorkOrderTableEntity.getVehicleNo());
            auditPendDisposalDto.setVehColor(auditWorkOrderTableEntity.getVehColor());
            auditPendDisposalDto.setVehType(auditWorkOrderTableEntity.getVehType());
            auditPendDisposalDto.setWorkOrderUnit(auditWorkOrderTableEntity.getWorkOrderUnit());
            auditPendDisposalDto.setWorkOrderTime(auditWorkOrderTableEntity.getWorkOrderTime());
            auditPendDisposalDto.setInvestigateProgress(auditWorkOrderTableEntity.getInvestigateProgress());
            auditPendDisposalDto.setDataProgress(auditWorkOrderTableEntity.getDataProgress());
            auditPendDisposals.add(auditPendDisposalDto);
        }
        PageInfo<AuditWorkOrderTableEntity> entityPageInfo = new PageInfo<>(auditWorkOrderTableEntities);
        PageInfo<AuditPendDisposalDTO> pageInfo = new PageInfo<>(auditPendDisposals);
        pageInfo.setTotal(entityPageInfo.getTotal());
        pageInfo.setPageNum(entityPageInfo.getPageNum());
        pageInfo.setPageSize(entityPageInfo.getPageSize());
        pageInfo.setSize(entityPageInfo.getSize());
        return pageInfo;
    }

    /**
     * 外部稽核-详情查看-数据概况
     *
     * @param workOrderId
     * @return
     */
    @Override
    public AuditDataSurveyDTO doFindDataSurvey(String workOrderId) {
        AuditWorkOrderTableEntity auditWorkOrderTableEntity = auditWorkOrderTableEntityMapper.selectOneById(workOrderId);
        AuditDataSurveyDTO auditDataSurveyDto = new AuditDataSurveyDTO();
        auditDataSurveyDto.setDataNumber(auditWorkOrderTableEntity.getDataNumber());
        auditDataSurveyDto.setVehicleNo(auditWorkOrderTableEntity.getVehicleNo());
        auditDataSurveyDto.setVehColor(auditWorkOrderTableEntity.getVehColor());
        auditDataSurveyDto.setVehType(auditWorkOrderTableEntity.getVehType());
        auditDataSurveyDto.setVehClass(auditWorkOrderTableEntity.getVehClass());
        auditDataSurveyDto.setEscape(auditWorkOrderTableEntity.getEscape());
        auditDataSurveyDto.setPassMediaType(auditWorkOrderTableEntity.getPassMediaType());
        auditDataSurveyDto.setIssuerNo(auditWorkOrderTableEntity.getIssuerNo());
        auditDataSurveyDto.setObuNo(auditWorkOrderTableEntity.getObuNo());
        auditDataSurveyDto.setEtcType(auditWorkOrderTableEntity.getEtcType());
        auditDataSurveyDto.setEtcNoCpcNo(auditWorkOrderTableEntity.getEtcNoCpcNo());
        auditDataSurveyDto.setEntryTime(auditWorkOrderTableEntity.getEntryTime());
        auditDataSurveyDto.setEntryPlace(auditWorkOrderTableEntity.getEntryPlace());
        auditDataSurveyDto.setExitTime(auditWorkOrderTableEntity.getExitTime());
        auditDataSurveyDto.setExitPlace(auditWorkOrderTableEntity.getExitPlace());
        auditDataSurveyDto.setPassProvince(auditWorkOrderTableEntity.getPassProvince());
        return auditDataSurveyDto;
    }

    /**
     * 外部稽核-稽核数据清单-出入口数据
     *
     * @param workOrderId
     * @return
     */
    @Override
    public List<AuditExitEntrDTO> doFindEntryAndExitList(String workOrderId) {
        AuditWorkOrderTableEntity auditWorkOrderTableEntity = auditWorkOrderTableEntityMapper.selectOneById(workOrderId);
        List<String> entryTimes = new ArrayList<>();
        entryTimes.add(auditWorkOrderTableEntity.getEntryTime());
        entryTimes.add(auditWorkOrderTableEntity.getExitTime());
        List<AuditExitEntrTableEntity> auditExitEntrTableEntities = auditExitEntrTableEntityMapper.doFindExitEntr(auditWorkOrderTableEntity.getWorkOrderId(), entryTimes);
        List<AuditExitEntrDTO> auditExitEntrDtoList = new ArrayList<>();
        for (AuditExitEntrTableEntity auditExitEntrTableEntity : auditExitEntrTableEntities) {
            AuditExitEntrDTO auditExitEntrDto = new AuditExitEntrDTO();
            auditExitEntrDto.setExitEntrId(auditExitEntrTableEntity.getExitEntrId());
            auditExitEntrDto.setWorkOrderId(auditExitEntrTableEntity.getWorkOrderId());
            auditExitEntrDto.setEntrance(auditExitEntrTableEntity.getEntrance());
            auditExitEntrDto.setEntranceCode(auditExitEntrTableEntity.getEntranceCode());
            if (0 == auditExitEntrTableEntity.getEntrance()) {
                auditExitEntrDto.setEntranceName(auditWorkOrderTableEntity.getExitPlace());
            } else if (1 == auditExitEntrTableEntity.getEntrance()) {
                auditExitEntrDto.setEntranceName(auditWorkOrderTableEntity.getEntryPlace());
            }
            auditExitEntrDto.setPassMediaType(auditExitEntrTableEntity.getPassMediaType());
            auditExitEntrDto.setObuSign(auditExitEntrTableEntity.getObuSign());
            auditExitEntrDto.setTerminalTransNo(auditExitEntrTableEntity.getTerminalTransNo());
            auditExitEntrDto.setObuNo(auditExitEntrTableEntity.getObuNo());
            auditExitEntrDto.setEtcNoCpcNo(auditExitEntrTableEntity.getEtcNoCpcNo());
            auditExitEntrDto.setEntryTime(auditExitEntrTableEntity.getEntryTime());
            auditExitEntrDto.setRealVehplateNo(auditExitEntrTableEntity.getRealVehplateNo());
            auditExitEntrDto.setRealVehColorCode(auditExitEntrTableEntity.getRealVehColorCode());
            auditExitEntrDto.setEnRealVehplateNo(auditExitEntrTableEntity.getEnRealVehplateNo());
            auditExitEntrDto.setEnRealVehColorCode(auditExitEntrTableEntity.getEnRealVehColorCode());
            auditExitEntrDto.setCollectFeeVehType(auditExitEntrTableEntity.getCollectFeeVehType());
            auditExitEntrDto.setVehClassTypeCode(auditExitEntrTableEntity.getVehClassTypeCode());
            auditExitEntrDto.setTacNo(auditExitEntrTableEntity.getTacNo());
            auditExitEntrDto.setTransType(auditExitEntrTableEntity.getTransType());
            auditExitEntrDto.setTerminalNo(auditExitEntrTableEntity.getTerminalNo());
            auditExitEntrDto.setEnWeight(auditExitEntrTableEntity.getEnWeight());
            auditExitEntrDto.setEnAxleCount(auditExitEntrTableEntity.getEnAxleCount());
            auditExitEntrDto.setPercentage(auditExitEntrTableEntity.getPercentage());
            auditExitEntrDto.setLabelStatus(auditExitEntrTableEntity.getLabelStatus());
            auditExitEntrDto.setDescription(auditExitEntrTableEntity.getDescription());
            auditExitEntrDtoList.add(auditExitEntrDto);
        }
        return auditExitEntrDtoList;
    }

    /**
     * 外部稽核-稽核数据清单-门架数据
     *
     * @param auditTransQuery
     * @return
     */
    @Override
    public PageInfo<AuditTransDTO> doFindAuditTrans(AuditTransQuery auditTransQuery) {
        PageHelper.startPage(auditTransQuery.getPageIndex(), auditTransQuery.getPageSize());
        List<AuditTransDTO> auditTransDtos = new ArrayList<>();
        List<AuditTransTableEntity> auditTransTableEntities = auditTransTableEntityMapper.selectByQuery(auditTransQuery);
        for (AuditTransTableEntity auditTransTableEntity : auditTransTableEntities) {
            AuditTransDTO auditTransDto = new AuditTransDTO();
            auditTransDto.setTransId(auditTransTableEntity.getTransId());
            auditTransDto.setTransCode(auditTransTableEntity.getTransCode());
            auditTransDto.setRoadName(auditTransTableEntity.getRoadName());
            auditTransDto.setCollectFeeModuelCode(auditTransTableEntity.getCollectFeeModuelCode());
            auditTransDto.setCollectFeeModuelName(auditTransTableEntity.getCollectFeeModuelName());
            auditTransDto.setRealVehplateNo(auditTransTableEntity.getRealVehplateNo());
            auditTransDto.setRealVehColor(auditTransTableEntity.getRealVehColor());
            auditTransDto.setIdentVehplateNo(auditTransTableEntity.getIdentVehplateNo());
            auditTransDto.setIdentVehColor(auditTransTableEntity.getIdentVehColor());
            auditTransDto.setTransTime(auditTransTableEntity.getTransTime());
            auditTransDto.setVehicleType(auditTransTableEntity.getVehicleType());
            auditTransDto.setIdentVehType(auditTransTableEntity.getIdentVehType());
            auditTransDto.setPassMediaType(auditTransTableEntity.getPassMediaType());
            auditTransDto.setPassMediaCode(auditTransTableEntity.getPassMediaCode());
            auditTransDto.setObuNo(auditTransTableEntity.getObuNo());
            auditTransDto.setEtcNoCpcNo(auditTransTableEntity.getEtcNoCpcNo());
            auditTransDto.setVehDentificationCode(auditTransTableEntity.getVehDentificationCode());
            auditTransDto.setImgAddress(auditTransTableEntity.getImgAddress());
            auditTransDtos.add(auditTransDto);
        }
        PageInfo<AuditTransTableEntity> entityPageInfo = new PageInfo<>(auditTransTableEntities);
        PageInfo<AuditTransDTO> pageInfo = new PageInfo<>(auditTransDtos);
        pageInfo.setTotal(entityPageInfo.getTotal());
        pageInfo.setPageNum(entityPageInfo.getPageNum());
        pageInfo.setPageSize(entityPageInfo.getPageSize());
        pageInfo.setSize(entityPageInfo.getSize());
        return pageInfo;
    }

    /**
     * 外部稽核-稽核数据清单-发行方稽核结论查询
     *
     * @param workOrderId
     * @return
     */
    @Override
    public AuditIssuerConclusionDTO doFindIssuerAuditConclusion(String workOrderId) {
        List<AuditCheckCommentsTableEntity> auditCheckCommentsTableEntities = auditCheckCommentsTableEntityMapper.selectOneByOrderId(workOrderId, 1);
        //获取最新的外部稽核工单
        AuditCheckCommentsTableEntity auditCheckCommentsTableEntity = auditCheckCommentsTableEntities.stream().filter(Objects::isNull).max(Comparator.comparing(AuditCheckCommentsTableEntity::getUpdateTime)).orElse(new AuditCheckCommentsTableEntity());
        AuditIssuerConclusionDTO auditIssuerConclusionDto = new AuditIssuerConclusionDTO();
        auditIssuerConclusionDto.setCheckCommentsId(auditCheckCommentsTableEntity.getCheckCommentsId());
        auditIssuerConclusionDto.setWorkOrderId(auditCheckCommentsTableEntity.getWorkOrderId());
        auditIssuerConclusionDto.setIssuer(auditCheckCommentsTableEntity.getIssuer());
        auditIssuerConclusionDto.setType(auditCheckCommentsTableEntity.getType());
        auditIssuerConclusionDto.setCode(auditCheckCommentsTableEntity.getCode());
        auditIssuerConclusionDto.setEscapeType(auditCheckCommentsTableEntity.getEscapeType());
        auditIssuerConclusionDto.setReSponsor(auditCheckCommentsTableEntity.getReSponsor());
        auditIssuerConclusionDto.setAdequacy(auditCheckCommentsTableEntity.getAdequacy());
        auditIssuerConclusionDto.setAdvanceAmount(auditCheckCommentsTableEntity.getAdvanceAmount());
        auditIssuerConclusionDto.setAmount(auditCheckCommentsTableEntity.getAmount());
        auditIssuerConclusionDto.setUpdateTime(auditCheckCommentsTableEntity.getUpdateTime());
        return auditIssuerConclusionDto;
    }

    /**
     * 外部稽核-稽核数据清单-路段稽核结论查询
     *
     * @param auditRoadConclusionQuery
     * @return
     */
    @Override
    public List<AuditRoadConclusionDTO> doFindRoadAuditConclusion(AuditRoadConclusionQuery auditRoadConclusionQuery) {
        auditRoadConclusionQuery.setAdscription(2);
        List<AuditCheckCommentsTableEntity> auditCheckCommentsTableEntities = auditCheckCommentsTableEntityMapper.selectRoadByQuery(auditRoadConclusionQuery);
        List<AuditRoadConclusionDTO> auditRoadConclusionList = new ArrayList<>();
        for (AuditCheckCommentsTableEntity auditCheckCommentsTableEntity : auditCheckCommentsTableEntities) {
            AuditRoadConclusionDTO auditRoadConclusionDto = new AuditRoadConclusionDTO();
            auditRoadConclusionDto.setCheckCommentsId(auditCheckCommentsTableEntity.getCheckCommentsId());
            auditRoadConclusionDto.setWorkOrderId(auditCheckCommentsTableEntity.getWorkOrderId());
            auditRoadConclusionDto.setRoadId(auditCheckCommentsTableEntity.getRoadId());
            auditRoadConclusionDto.setUnitName(auditCheckCommentsTableEntity.getUnitName());
            auditRoadConclusionDto.setEscapeType(auditCheckCommentsTableEntity.getEscapeType());
            auditRoadConclusionDto.setIsLessFee(auditCheckCommentsTableEntity.getIsLessFee());
            auditRoadConclusionDto.setReSponsor(auditCheckCommentsTableEntity.getReSponsor());
            auditRoadConclusionDto.setAdequacy(auditCheckCommentsTableEntity.getAdequacy());
            auditRoadConclusionDto.setChargeUnitNumber(auditCheckCommentsTableEntity.getChargeUnitNumber());
            auditRoadConclusionDto.setFee(auditCheckCommentsTableEntity.getFee());
            auditRoadConclusionDto.setAmount(auditCheckCommentsTableEntity.getAmount());
            auditRoadConclusionDto.setUpdateTime(auditCheckCommentsTableEntity.getUpdateTime());
            auditRoadConclusionList.add(auditRoadConclusionDto);
        }
        //以更新时间降序
        List<AuditRoadConclusionDTO> collect = auditRoadConclusionList.stream().sorted(Comparator.comparing(AuditRoadConclusionDTO::getUpdateTime, Comparator.reverseOrder())).collect(Collectors.toList());
        return collect;
    }

    @Override
    public AuditUnitDTO doFindAuditUnitById(String checkCommentsId) {
        List<AuditUnitTableEntity> auditUnitTableEntities = auditUnitTableEntityMapper.doFindListByCheckId(checkCommentsId);
        AuditUnitTableEntity auditUnitTableEntity = auditUnitTableEntities.stream().filter(Objects::isNull).max(Comparator.comparing(AuditUnitTableEntity::getProcessTime)).orElse(new AuditUnitTableEntity());
        AuditUnitDTO auditUnitDto = new AuditUnitDTO();
        auditUnitDto.setProcessTime(auditUnitTableEntity.getProcessTime());
        auditUnitDto.setProcesser(auditUnitTableEntity.getProcesser());
        auditUnitDto.setVehplateNo(auditUnitTableEntity.getVehplateNo());
        auditUnitDto.setVehicleColor(auditUnitTableEntity.getVehicleColor());
        auditUnitDto.setConFirmedeScape(auditUnitTableEntity.getConFirmedeScape());
        auditUnitDto.setWhetherFee(auditUnitTableEntity.getWhetherFee());
        auditUnitDto.setChainOfEvidence(auditUnitTableEntity.getChainOfEvidence());
        auditUnitDto.setTollPath(auditUnitTableEntity.getTollPath());
        auditUnitDto.setPathwayUnit(auditUnitTableEntity.getPathwayUnit());
        auditUnitDto.setPayFee(auditUnitTableEntity.getPayFee());
        auditUnitDto.setTollFee(auditUnitTableEntity.getTollFee());
        auditUnitDto.setFee(auditUnitTableEntity.getFee());
        auditUnitDto.setOtherFee(auditUnitTableEntity.getOtherFee());
        auditUnitDto.setRemark(auditUnitTableEntity.getRemark());
        return auditUnitDto;
    }

    /**
     * 外部稽核-创建稽查工单-提交
     *
     * @param auditAuditorListDto
     * @return
     */
    @Override
    public Result doCreateWorkOrder(AuditAuditorListDTO auditAuditorListDto) {
        //移除
        if (1 == auditAuditorListDto.getRmFlag()) {
            Integer deleteNum = auditWorkOrderTableEntityMapper.deleteById(auditAuditorListDto.getWorkOrderId());
            if (deleteNum > 0) {
                return Result.ok();
            } else {
                return Result.error().message("移除工单失败");
            }
        }
        //新增
        if (StringUtils.isEmpty(auditAuditorListDto.getWorkOrderId())) {
            AuditWorkOrderTableEntity auditWorkOrderTableEntity = new AuditWorkOrderTableEntity();
            auditWorkOrderTableEntity.setWorkOrderId(String.valueOf(UUID.randomUUID()).replace("-", "").substring(0, 24));
            auditWorkOrderTableEntity = buildAuditWorkOrderTableEntity(auditWorkOrderTableEntity, auditAuditorListDto);
            Integer insertNum = auditWorkOrderTableEntityMapper.insert(auditWorkOrderTableEntity);
            if (insertNum > 0) {
                return Result.ok();
            } else {
                return Result.error().message("新增工单失败");
            }
        }
        //更新
        if (!StringUtils.isEmpty(auditAuditorListDto.getWorkOrderId()) && 0 == auditAuditorListDto.getRmFlag()) {
            AuditWorkOrderTableEntity auditWorkOrderTableEntity = new AuditWorkOrderTableEntity();
            auditWorkOrderTableEntity.setWorkOrderId(auditAuditorListDto.getWorkOrderId());
            auditWorkOrderTableEntity = buildAuditWorkOrderTableEntity(auditWorkOrderTableEntity, auditAuditorListDto);
            Integer updateNum = auditWorkOrderTableEntityMapper.updateById(auditWorkOrderTableEntity);
            if (updateNum > 0) {
                return Result.ok();
            } else {
                return Result.error().message("更新工单失败");
            }
        }
        return null;
    }


    private AuditWorkOrderTableEntity buildAuditWorkOrderTableEntity(AuditWorkOrderTableEntity auditWorkOrderTableEntity, AuditAuditorListDTO auditAuditorListDto) {
        auditWorkOrderTableEntity.setVehicleNo(auditAuditorListDto.getVehicleNo());
        auditWorkOrderTableEntity.setVehColor(auditAuditorListDto.getVehColor());
        auditWorkOrderTableEntity.setVehType(auditAuditorListDto.getVehType());
        auditWorkOrderTableEntity.setVehClass(auditAuditorListDto.getVehClass());
        auditWorkOrderTableEntity.setPassProvince(auditAuditorListDto.getPassProvince());
        auditWorkOrderTableEntity.setEscape(auditAuditorListDto.getEscape());
        auditWorkOrderTableEntity.setDataNumber(auditAuditorListDto.getDataNumber());
        auditWorkOrderTableEntity.setWorkOrderTitle(auditAuditorListDto.getWorkOrderTitle());
        auditWorkOrderTableEntity.setRemark(auditAuditorListDto.getRemark());
        auditWorkOrderTableEntity.setInitiator("省中心");
        return auditWorkOrderTableEntity;
    }

    @Override
    public List<AuditWorkOrderTableEntity> doFindWorkorder(AuditWorkOrderTableEntity table,
    		Date startTime,Date endTime, Integer pageIndex, Integer pageSize) throws ParseException {
        if (startTime != null && endTime == null) {
            throw new BreakRulesException(ResultCode.ERROR, "当选择开始时间后，结束时间必须选择");
        }
        if (startTime == null && endTime != null) {
            throw new BreakRulesException(ResultCode.ERROR, "当选择结束时间后，开始时间必须选择");
        }
        if (startTime != null && endTime != null) {
            if (startTime.after(endTime)) {
                throw new BreakRulesException(ResultCode.ERROR, "开始时间不允许在结束时间之后");
            }
            //如果结束时间传入的是yyyy-MM-dd
            String string = sdf.format(startTime).toString();
            String[] split = string.split(" ");
            if (split[1].equals("00:00:00")) {
                string = split[0] + " " + "23:59:59";
                endTime = sdf.parse(string);
            }
        }
        if (pageIndex == null) {
            pageIndex = 1;
        }
        if (pageSize == null) {
            pageSize = 10;
        }
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("startTime", startTime);
        map.put("endTime", endTime);
        map.put("AuditWorkOrderTableEntity", table);
        PageHelper.startPage(pageIndex, pageSize);
        List<AuditWorkOrderTableEntity> tableList = auditWorkOrderTableEntityMapper.doFindWorkorder(map);
        PageInfo<AuditWorkOrderTableEntity> pageInfo = new PageInfo<>(tableList);
        return pageInfo.getList();
    }

	@Override
	@SuppressWarnings("unchecked")
	public <T> Map<String, List<T>> doFindProvincialCenter(String dataNumber) {
		Map<String, List<T>> resultMap = new HashMap<String, List<T>>();
		if(dataNumber == null || dataNumber.trim().equals("")) {
			return resultMap;
		}
		AuditWorkOrderTableEntity tableEntity = new AuditWorkOrderTableEntity();
		tableEntity.setDataNumber(dataNumber);
		List<AuditWorkOrderTableEntity> tableList = auditWorkOrderTableEntityMapper.doFindWorkorderByVO(tableEntity);
		// 工单
		resultMap.put("AuditWorkOrderTableEntity", (List<T>) tableList);
		List<AuditVehicleTable> vehicleTableList = auditWorkOrderTableEntityMapper.doFindAuditVehicleTable(dataNumber);
		// 车辆信息表
		resultMap.put("AuditVehicleTable", (List<T>) vehicleTableList);
		// 稽核数据
		List<AuditCheckCommentsTableEntity> checkcommentsTable =
				auditCheckCommentsTableEntityMapper.doFindAuditCheckcommentsTable(dataNumber);
		resultMap.put("AuditCheckCommentsTableEntity", (List<T>) checkcommentsTable);
		return resultMap;
	}



}









