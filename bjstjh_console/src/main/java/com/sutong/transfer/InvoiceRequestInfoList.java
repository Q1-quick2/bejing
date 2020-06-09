package com.sutong.transfer;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.List;

/**
 * @Description:开票接口用——发票请求信息列表
 * @ClassName: InvoiceRequestInfoListModel
 * @author： liyan
 * @date: 2019/12/28 16:16
 * @Version： 1.0
 */

public class InvoiceRequestInfoList implements Serializable {
    /** 可抵扣标志--必填*/
    @JsonProperty( "DeductionFlag")
    private int DeductionFlag;
    /** 发票抬头ID*/
    @JsonProperty( "BuyerId")
    private String  BuyerId;
    /** 购买方名称--必填*/
    @JsonProperty( "BuyerName")
    private String BuyerName;
    /** 纳税人识别号*/
    @JsonProperty( "BuyerTaxCode")
    private String BuyerTaxCode;
    /** 电话*/
    @JsonProperty( "BuyerTelephoneNo")
    private String BuyerTelephoneNo;
    /** *地址*/
    @JsonProperty( "BuyerAddress")
    private String BuyerAddress;
    /** 开户银行*/
    @JsonProperty( "BuyerBankName")
    private String BuyerBankName;
    /** 银行账号*/
    @JsonProperty( "BuyerBankAccountNo")
    private String BuyerBankAccountNo;
    /** 票面备注*/
    @JsonProperty( "InvoiceRemark")
    private String InvoiceRemark;
    /** 车牌号*/
    @JsonProperty( "VehPlateNo")
    private String VehPlateNo;
    /** 车牌颜色*/
    @JsonProperty( "VehPlateColor")
    private int VehPlateColor;
    /** 开票人*/
    @JsonProperty( "TransList")
    private List<TransList> TransList;

    @JsonIgnore
    public int getDeductionFlag() {
        return DeductionFlag;
    }

    public void setDeductionFlag(int deductionFlag) {
        DeductionFlag = deductionFlag;
    }

    @JsonIgnore
    public String getBuyerId() {
        return BuyerId;
    }

    public void setBuyerId(String buyerId) {
        BuyerId = buyerId;
    }
    @JsonIgnore
    public String getBuyerName() {
        return BuyerName;
    }

    public void setBuyerName(String buyerName) {
        BuyerName = buyerName;
    }
    @JsonIgnore
    public String getBuyerTaxCode() {
        return BuyerTaxCode;
    }

    public void setBuyerTaxCode(String buyerTaxCode) {
        BuyerTaxCode = buyerTaxCode;
    }

    @JsonIgnore
    public String getBuyerTelephoneNo() {
        return BuyerTelephoneNo;
    }

    public void setBuyerTelephoneNo(String buyerTelephoneNo) {
        BuyerTelephoneNo = buyerTelephoneNo;
    }

    @JsonIgnore
    public String getBuyerAddress() {
        return BuyerAddress;
    }

    public void setBuyerAddress(String buyerAddress) {
        BuyerAddress = buyerAddress;
    }

    @JsonIgnore
    public String getBuyerBankName() {
        return BuyerBankName;
    }

    public void setBuyerBankName(String buyerBankName) {
        BuyerBankName = buyerBankName;
    }

    @JsonIgnore
    public String getBuyerBankAccountNo() {
        return BuyerBankAccountNo;
    }

    public void setBuyerBankAccountNo(String buyerBankAccountNo) {
        BuyerBankAccountNo = buyerBankAccountNo;
    }

    @JsonIgnore
    public String getInvoiceRemark() {
        return InvoiceRemark;
    }

    public void setInvoiceRemark(String invoiceRemark) {
        InvoiceRemark = invoiceRemark;
    }

    @JsonIgnore
    public String getVehPlateNo() {
        return VehPlateNo;
    }

    public void setVehPlateNo(String vehPlateNo) {
        VehPlateNo = vehPlateNo;
    }

    @JsonIgnore
    public int getVehPlateColor() {
        return VehPlateColor;
    }

    public void setVehPlateColor(int vehPlateColor) {
        VehPlateColor = vehPlateColor;
    }

    @JsonIgnore
    public List<com.sutong.transfer.TransList> getTransList() {
        return TransList;
    }

    public void setTransList(List<com.sutong.transfer.TransList> transList) {
        TransList = transList;
    }
}