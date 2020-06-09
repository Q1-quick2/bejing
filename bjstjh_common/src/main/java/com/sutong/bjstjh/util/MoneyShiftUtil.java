/**
 * @Description: 金额转换工具类
 * @ClassName: MoneyShiftUtil
 * @author： WangLei
 * @date: 2019/12/23 11:28
 * @Version： 1.0
 */
package com.sutong.bjstjh.util;

import java.text.NumberFormat;
import java.text.ParseException;

/**
 * Created by 王磊 on 2019/12/23.
 */
public class MoneyShiftUtil {

    //分转元
    public static String fenToYuan(String amount) {
        NumberFormat format = NumberFormat.getInstance();
        try {
            Number number = format.parse(amount);
            double temp = number.doubleValue() / 100.0;
            format.setGroupingUsed(false);
            // 设置返回的小数部分所允许的最大位数
            format.setMaximumFractionDigits(2);
            amount = format.format(temp);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return amount;
    }


    //元转分
    public static String yuanToFen(String amount) {
        NumberFormat format = NumberFormat.getInstance();
        try {
            Number number = format.parse(amount);
            double temp = number.doubleValue() * 100.0;
            format.setGroupingUsed(false);
            // 设置返回数的小数部分所允许的最大位数
            format.setMaximumFractionDigits(0);
            amount = format.format(temp);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return amount;
    }



}