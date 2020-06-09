package com.sutong.dodgingtoll.mapper;


import com.sutong.dodgingtoll.model.AuditPayConfirmation;


public interface AuditPayConfirmationMapper {

    AuditPayConfirmation getConfirmationByObu(String obu);
    int getCountNum(String obu);
}
