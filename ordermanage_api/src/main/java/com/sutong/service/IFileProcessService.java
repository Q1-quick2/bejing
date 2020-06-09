package com.sutong.service;

import java.util.List;
import java.util.Map;

/**   
 * @ClassName:  IFileProcessService   
 * @Description:  处理文件上传后的入库操作
 * @author: pengwz
 * @date:   2019年12月23日 上午10:36:39      
 */
public interface IFileProcessService {

	/**
	 * 
	 * @Description 文件路径入库操作
	 * @param map 
	 * @return 
	 * @author pengwz
	 * @date 2019年12月23日 上午11:46:06
	 */
	Integer saveFilepath(Map<String,String> map);
	
	/**
	 * 
	 * @Description 根据传入的表名和id获取文件路径(不区分该文件属于哪个字段)
	 * @param tableName
	 * @param tableID
	 * @return 
	 * @author pengwz
	 * @date 2019年12月23日 下午1:34:55
	 */
	List<String> queryFilepath(Object tableName,String tableID);
	
	/**
	 * 
	 * @Description 根据表名，表id，表字段获取文件路径
	 * @param tableName
	 * @param tableID
	 * @param tableColumn
	 * @return 
	 * @author pengwz
	 * @date 2019年12月23日 下午3:41:09
	 */
	List<String> queryFilepathByColumn(Object tableName,String tableID,Object tableColumn);
}
