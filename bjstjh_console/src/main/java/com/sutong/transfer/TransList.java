package com.sutong.transfer;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * @Description:开票接口用--发票请求信息列表--交易列表信息
 * @ClassName: TransListModel
 * @author： liyan
 * @date: 2019/12/28 16:16
 * @Version： 1.0
 */

public class TransList implements Serializable {
    /** 交易唯一标识--必填*/
    @JsonProperty( "TransNo")
    private String TransNo;
    /** 交易源类型编码*/
    @JsonProperty( "TransSourceTypeCode")
    private int TransSourceTypeCode ;
    /** 开票金额 --必填*/
    @JsonProperty( "InvoiceAmount")
    private String InvoiceAmount;
    /** 交易金额--必填*/
    @JsonProperty( "TransAmount")
    private String TransAmount;

    @JsonIgnore
    public String getTransNo() {
        return TransNo;
    }

    public void setTransNo(String transNo) {
        TransNo = transNo;
    }

    @JsonIgnore
    public int getTransSourceTypeCode() {
        return TransSourceTypeCode;
    }

    public void setTransSourceTypeCode(int transSourceTypeCode) {
        TransSourceTypeCode = transSourceTypeCode;
    }

    @JsonIgnore
    public String getInvoiceAmount() {
        return InvoiceAmount;
    }

    public void setInvoiceAmount(String invoiceAmount) {
        InvoiceAmount = invoiceAmount;
    }

    @JsonIgnore
    public String getTransAmount() {
        return TransAmount;
    }

    public void setTransAmount(String transAmount) {
        TransAmount = transAmount;
    }
}