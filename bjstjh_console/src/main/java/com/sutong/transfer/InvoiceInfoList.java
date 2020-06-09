package com.sutong.transfer;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @Description:申请红冲--发票信息列表
 * @ClassName: InvoiceInfoList
 * @author： liyan
 * @date: 2020/1/3 15:27
 * @Version： 1.0
 */
public class InvoiceInfoList {
    /**发票开具流水号*/
    @JsonProperty( "InvoiceSerialNo")
    private String InvoiceSerialNo;
    /**红冲原因*/
    @JsonProperty( "RedReason")
    private String RedReason;
    /**发票关联交易标志*/
    @JsonProperty( "RelatedTransFlag")
    private int RelatedTransFlag;
    /**开票金额*/
    @JsonProperty( "InvoiceAmount")
    private String InvoiceAmount;
    @JsonIgnore
    public String getInvoiceSerialNo() {
        return InvoiceSerialNo;
    }

    public void setInvoiceSerialNo(String invoiceSerialNo) {
        InvoiceSerialNo = invoiceSerialNo;
    }
    @JsonIgnore
    public String getRedReason() {
        return RedReason;
    }

    public void setRedReason(String redReason) {
        RedReason = redReason;
    }
    @JsonIgnore
    public int getRelatedTransFlag() {
        return RelatedTransFlag;
    }

    public void setRelatedTransFlag(int relatedTransFlag) {
        RelatedTransFlag = relatedTransFlag;
    }
    @JsonIgnore
    public String getInvoiceAmount() {
        return InvoiceAmount;
    }

    public void setInvoiceAmount(String invoiceAmount) {
        InvoiceAmount = invoiceAmount;
    }
}