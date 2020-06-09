package com.sutong.transflow.model;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * null
 * 
 * @author wcyong
 * 
 * @date 2019-12-22
 */
@Data
@ToString
public class AuditPayEnd implements Serializable{
    private String endCharset;

    private String endVersion;

    private String endServercert;

    private String endServersign;

    private String endSigntype;

    private String endService;

    private String endMerchantid;

    private String endResultcode;

    private String endResultmsg;

    private String endPayserialno;

    private Long endTxnamt;

    private String endPayorderno;

    private String endPaymenttime;

    private String endPaytype;

    private String endRmk;

    private Long endActpayamt;

    private Long endDiscountsamt;

    private String endRequesttime;

    private String endDiscountsinfolistnum;

    private String endDiscountsinfolist;

    private String payHistory;


}