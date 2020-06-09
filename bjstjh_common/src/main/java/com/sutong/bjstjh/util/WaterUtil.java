/**
 * @Description: 补费流水工具类
 * @ClassName: WaterUtil
 * @author： WangLei
 * @date: 2019/12/16 15:11
 * @Version： 1.0
 */
package com.sutong.bjstjh.util;

/**
 * Created by 王磊 on 2019/12/16.
 */
public class WaterUtil {

    public static String subStr(String str, int start) {
        if (str == null || str.equals("") || str.length() == 0)
            return "";
        if (start < str.length()) {
            return str.substring(start);
        } else {
            return "";
        }

    }

}
