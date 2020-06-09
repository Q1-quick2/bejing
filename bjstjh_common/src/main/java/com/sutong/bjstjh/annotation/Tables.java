package com.sutong.bjstjh.annotation;

import java.lang.annotation.*;

/**
 *
 * @ClassName:  Tables
 * @Description:
 * @author: pengwz
 * @date:   2019年12月28日 上午11:25:02
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface Tables {
	/*** 物理表表名   该属性暂未使用
	 * @return
	 * @author pengwz
	 * @date 2019年12月28日 上午3:14:15
	 */
	public String name() default "";
	/**
	 * 	物理表主键对应的实体类属性
	 * @return
	 * @author pengwz
	 * @date 2019年12月28日 上午3:14:43
	 */
	public String vopri() default "";
	/**
	 * 	物理表主键  该属性暂未使用
	 * @return
	 * @author pengwz
	 * @date 2019年12月28日 上午3:15:07
	 */
	public String pri() default "";
}
