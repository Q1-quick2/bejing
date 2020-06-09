package com.sutong.historyEvidenceOrder.mapper;

import com.sutong.historyEvidenceOrder.model.AuditFile;

import java.util.List;

public interface HistoryEvidenceOrderMapper {

    List<AuditFile> doFindEvidenceResultList(AuditFile historyEvidenceOrder);


}
