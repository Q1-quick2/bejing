package com.sutong.bjstjh.annotation;

import java.lang.annotation.*;

/**
 *
 * @ClassName:  Dictionary
 * @Description:  当实体类的属性为字典时,添加此注解,value指向该字典所属的字典类型
 * @author: pengwz
 * @date:   2019年12月30日 上午8:55:39
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Dictionary {
	/**
	 * 	字典类型
	 * @return
	 * @author pengwz
	 * @date 2019年12月29日 下午3:53:55
	 */
	String value() default "";

}
