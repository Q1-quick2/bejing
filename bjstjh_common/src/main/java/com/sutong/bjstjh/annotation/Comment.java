package com.sutong.bjstjh.annotation;

import java.lang.annotation.*;

/**
 *
 * @ClassName:  Comment
 * @Description:  当实体类有Excel导入导出需求时,使用此注解,该注解加在和Excel共用的属性上,
 * 				并指定与之对应的Excel列名,如果注释内容和Excel列头内容不一致,可能会无法进行
 * 				属性赋值或者抛出异常
 * @author: pengwz
 * @date:   2019年12月28日 上午11:26:25
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
/* @Tables */
public @interface Comment {

	/**
	 * 	注释内容,与Excel列头保持一致
	 * @return
	 * @author pengwz
	 * @date 2019年12月28日 上午11:30:21
	 */
	String value() default "";

	/**
	 * 	表头排序,用于Excel导出,只能输入除0外的自然数
	 * 	<p>
	 * 	当值为1时,表示该属性下的字段在excel的位置为第一列,以此类推
	 * 	<p>
	 * 	程序对于不连贯值暂未特殊处理,采用整列为空处理
	 * 	<p>
	 * 	<red>该属性暂未使用</red>
	 * @return
	 * @author pengwz
	 * @date 2020年1月14日 下午5:31:49
	 */
	int sort() default -1;
}
