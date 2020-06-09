/**
 * @Description:
 * @ClassName: MapUtil
 * @author： Mr.Kong
 * @date: 2019/12/17 10:49
 * @Version： 1.0
 */
package com.sutong.bjstjh.util;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
* @description: map工具类
* @auther: lzq
* @date: 2019/12/17 10:50
* @Param null:
* @return: null
**/
public class MapUtil {
    /**
    * @description: 实体转map
    * @auther: Mr.Wind
    * @date: 2019/12/17 10:51
    * @Param object:
    * @return: java.util.Map<java.lang.String,java.lang.Object>
    **/
    public static Map<String, Object> beanToMap(Object object) {
        Map<String, Object> map = new HashMap();
        for (Field field : object.getClass().getDeclaredFields()){
            try {
                boolean flag = field.isAccessible();
                field.setAccessible(true);
                Object o = field.get(object);
                map.put(field.getName(), o);
                field.setAccessible(flag);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return map;
    }


    /**
     * Map转成实体对象
     *
     * @param map   map实体对象包含属性
     * @param clazz 实体对象类型
     * @return
     */
    public static <T> T map2Object(Map<String, Object> map, Class<T> clazz) {

        if (map == null) {
            return null;
        }

            T t = null;
            try {
                t = clazz.newInstance();
                for(Field field : clazz.getDeclaredFields()) {
                    if (map.containsKey(field.getName())) {
                        boolean flag = field.isAccessible();
                        field.setAccessible(true);
                        Object object = map.get(field.getName());
                        if (object!= null && field.getType().isAssignableFrom(object.getClass())) {
                            field.set(t, object);
                        }
                        field.setAccessible(flag);
                    }
                }
                return t;
            } catch (InstantiationException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return t;
        }




}
