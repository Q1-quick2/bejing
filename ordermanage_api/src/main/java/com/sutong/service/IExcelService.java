package com.sutong.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

/**
 *
 * @ClassName:  IExcelService
 * @Description:
 * @author: pengwz
 * @date:   2019年12月28日 下午5:40:13
 * @param <T>
 */
public interface IExcelService {
	/**
	 * 	设定严格状态; 即: 当Excel表列和实体类字段无法完全匹配时,拒绝将excel数据赋值给实体类
	 * 	并抛出异常
	 */
	public static final int StrictState = 0;
	/**
	 * 	设定宽松状态; 即: 当Excel表列和实体类字段无法完全匹配时,仍然尝试将相同的数据赋值给实体类
	 */
	public static final int Relaxed 	= 1;

	/**
	 *  	将传入的excel数据绑定为实体类,返回实体类的集合
	 * @param <T>
	 * @param obj 具体的对象
	 * @param map excel数据
	 * @param status  两个状态: StrictState 和 Relaxed
	 * @return
	 * @throws Exception
	 * @author pengwz
	 * @date 2019年12月28日 下午5:37:23
	 */
	public <T extends Object> List<T> setFileds(T t,Map<String,List<Object>> map,int status) throws Exception;

	/**
	 * 	Excel导入操作,目前只支持列头位于第一行的标准Excel文件
	 * 	<p>
	 * 	该方法将excel文件解析完毕,会将对应的值赋值给实体类,返回List对象的集合
	 * 	<p>
	 * 	如果对应的实体类的属性标识为字典,程序会尝试将对应的Excel数据转换为对应的字典值,赋值给实体类
	 * @param <T>
	 * @param excelFile 需要导入的Excel文件
	 * @param t	对应的实体类对象
	 * @param status 两个状态: StrictState 和 Relaxed
	 * @return
	 * @throws Exception
	 * @author pengwz
	 * @date 2020年1月14日 下午5:40:21
	 */
	public <T extends Object> List<T> importExcel(MultipartFile excelFile,T t,int status) throws Exception;

	/**
	 * 	Excel导出
	 * @param <T>
	 * @param t
	 * @param fileName
	 * @param sheetName
	 * @return
	 * @throws Exception
	 * @author pengwz
	 * @date 2020年1月14日 下午5:29:01
	 */
	public <T extends Object> Void exportExcel(T t,String fileName,String sheetName) throws Exception;
}
