package com.sutong.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @ClassName:  FileProcessMapper
 * @Description:
 * @author: pengwz
 * @date:   2019年12月23日 上午10:45:20
 */
@Repository
@Mapper
@Component
public interface FileProcessMapper {
	/**
	 *
	 * @Description 直接做入库操作
	 * @param map
	 * @return
	 * @author pengwz
	 * @date 2019年12月23日 上午11:10:17
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
	List<String> queryFilepath(@Param("table") String table,@Param("tableID")String tableID);
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
	List<String> queryFilepathByColumn(@Param("table")String table,
			@Param("tableID")String tableID,@Param("tableColumn")String tableColumn);
}
