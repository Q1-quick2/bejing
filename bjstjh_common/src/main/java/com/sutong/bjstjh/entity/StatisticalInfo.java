package com.sutong.bjstjh.entity;

import java.io.Serializable;

/*
 * 统计信息实体类
 * */
public class StatisticalInfo implements Serializable {
    private static final long serialVersionUID = 4780177053465402830L;
    private Integer dictNumber;
    private Integer countnum;
    public Integer getDictNumber() {
        return dictNumber;
    }
    public void setDictNumber(Integer dictNumber) {
        this.dictNumber = dictNumber;
    }
    public Integer getCountnum() {
        return countnum;
    }
    public void setCountnum(Integer countnum) {
        this.countnum = countnum;
    }
    @Override
    public String toString() {
        return "StatisticalInfo [dictNumber=" + dictNumber + ", countnum=" + countnum + "]";
    }
}
