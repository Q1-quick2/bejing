package com.sutong.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.sutong.bjstjh.exception.DataStaleException;
import com.sutong.bjstjh.result.ResultCode;
import com.sutong.bjstjh.util.StringUtils;
import com.sutong.mapper.FileProcessMapper;
import com.sutong.service.IFileProcessService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

/**
 * @ClassName:  FileProcessServiceImpl
 * @Description:
 * @author: pengwz
 * @date:   2019年12月23日 上午10:42:39
 */
@Service(timeout = 3000)
public class FileProcessServiceImpl implements IFileProcessService {

	private static final Logger log = LoggerFactory.getLogger(FileProcessServiceImpl.class);

	@Autowired
	private FileProcessMapper fileProcessMapper;

	@Override
	public Integer saveFilepath(Map<String,String> map) {
		if(!map.containsKey("id")) {
			try {
				throw new DataStaleException(ResultCode.DATA_ERROR,"文件入库操作必须指定所属业务表id");
			} catch (DataStaleException e) {
				log.error(e.getMessage());
				e.printStackTrace();
			}
			return -1 ;
		}
		String fileID = StringUtils.generateUUID();
		map.put("fileid", fileID);
		Integer save = fileProcessMapper.saveFilepath(map);
		return save;
	}

	@Override
	public List<String> queryFilepath(Object tableName, String tableID) {
		String table = String.valueOf(tableName);
		if(table == null ||tableID ==null
				||table.isEmpty() || tableID.isEmpty()) {
			try {
				throw new DataStaleException(ResultCode.DATA_ERROR,"查询参数值不合法");
			} catch (DataStaleException e) {
				log.error(e.getMessage());
				e.printStackTrace();
			}
			return null;
		}
		List<String> queryFilepath = fileProcessMapper.queryFilepath(table, tableID);
		return queryFilepath;
	}

	@Override
	public List<String> queryFilepathByColumn(Object tableName, String tableID, Object tableColumns) {
		String table = String.valueOf(tableName);
		String tableColumn = String.valueOf(tableColumns);
		if(table == null ||tableID ==null ||tableColumn == null ) {
			try {
				throw new DataStaleException(ResultCode.DATA_ERROR,"查询参数值不合法");
			} catch (DataStaleException e) {
				log.error(e.getMessage());
				e.printStackTrace();
			}
			return null;
		}
		List<String> queryFilepath = fileProcessMapper.queryFilepathByColumn(table, tableID, tableColumn);
		return queryFilepath;
	}
}
