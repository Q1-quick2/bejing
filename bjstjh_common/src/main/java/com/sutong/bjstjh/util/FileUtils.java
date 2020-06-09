package com.sutong.bjstjh.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.sutong.bjstjh.entity.AuditFileTable;
import com.sutong.bjstjh.entity.AuditIssueVeh;
import com.sutong.bjstjh.exception.DownloadException;
import com.sutong.bjstjh.exception.FilesUploadException;
import com.sutong.bjstjh.exception.PackagingFailedException;
import com.sutong.bjstjh.result.ResultCode;
import com.sutong.bjstjh.util.enumerate.FileConfigEnum;
import com.sutong.bjstjh.util.enumerate.FileTypeEnum;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.servlet.ServletRequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class FileUtils {

	private static final Logger log = LoggerFactory.getLogger(FileUtils.class);

	private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
	/**单个文件允许上传的最大字节*/
	private static final Integer MAX_SIZE = Integer.MAX_VALUE;
	/**上传文件的总大小*/
	private static final Integer MAX_TOTAL_SIZE = Integer.MAX_VALUE;
	/**
	 *
	 * @Description 获取文件名命名,不包含文件后缀
	 * @param tableName
	 * @return
	 * @author pengwz
	 * @date 2019年12月22日 下午12:53:11
	 */
	public static String fileName(Object tableName,Object tableColumn) {
		//表名+'_'+业务类型+'_'+当前时间戳
		String tn = String.valueOf(tableName);
		String timestamp = sdf.format(new Date());
		String concat = tn.concat("_").concat(FileConfigEnum.getFileType(tn)+"")
				.concat("_").concat(String.valueOf(tableColumn)+"_"+timestamp);
		return concat;
	}
	/**
	 * 校验单个文件大小
	 * @param list
	 * @throws FileUploadException
	 */
	public static void checkSize(List<FileItem> list) throws FileUploadException {
		for (FileItem fileItem : list) {
			if(fileItem.getSize() > MAX_SIZE)
				throw new FileUploadException("上传的单个文件过大，最大文件不允许超过"+MAX_SIZE+"个字节");
		}
	}
	/**
	 * 校验上传文件大小
	 * @throws FileUploadException
	 *
	 */
	public static void requestCheckSize(HttpServletRequest request) throws FileUploadException {
		if(request.getContentLength() > MAX_TOTAL_SIZE
				|| request.getContentLength() < 0) {
			throw new FileUploadException("上传的文件过大，总大小不允许超过"+MAX_SIZE+"个字节");
		}
	}
	/**
	 * <red>jar包冲突,该方法已弃用</red>
	 * @Description 传入请求参数和表名,传入的表名由FileConfigEnum枚举指定,接收单个上传文件,
	 * 				返回的Map包含:原始文件名[old_file_name],文件类型[content_type]
	 * 				文件大小[ size],保存的文件路径[file_path],表名[table_name],表字段[table_column]
	 * @param request
	 * @param tableName
	 * @return <red>上传失败会抛出异常或者返回空</>
	 * @throws FileUploadException
	 * @author pengwz
	 * @date 2019年12月23日 上午9:59:45
	 */
	@Deprecated
	public static List<Map<String,String>> upload(HttpServletRequest request,Object tableName,
			Object tableColumn,String id) throws FileUploadException {
		if(request == null || tableName == null ||
				tableColumn == null || id==null||"".equals(id))
			throw new FileUploadException("文件上传失败，缺少必须的参数");
		requestCheckSize(request);
		String tn = String.valueOf(tableName);
		String filePath = FileConfigEnum.getFilePath(tn);
		if(filePath == null)
			throw new FileUploadException("文件上传失败，无法获取文件存放的路径");
		File file = new File(filePath);
		// 判断上传文件的保存目录是否存在
		if (!file.exists() && !file.isDirectory()) {
			file.mkdirs();
		}
		DiskFileItemFactory factory = new DiskFileItemFactory();
		factory.setSizeThreshold(1024*1024);
		ServletFileUpload upload = new ServletFileUpload(factory);
		upload.setHeaderEncoding("UTF-8");
		// 判断提交上来的数据是否是上传表单的数据
		if (!ServletFileUpload.isMultipartContent(request))
			throw new FileUploadException("文件上传失败");
		List<FileItem> list = upload.parseRequest(new ServletRequestContext(request));
		List<Map<String,String>> listMap = new ArrayList<Map<String,String>>();
		for (FileItem item : list) {
			if (item.isFormField()) {
				log.info("---------------------传入了文本数据------------------------");
				throw new FileUploadException("传入了文本数据");
			} else {
				checkSize(list);
				String filename = item.getName();
				Map<String,String> resultMap = new HashMap<String, String>();
				if (filename == null || filename.trim().equals("")) {
					continue;
				}else {
					resultMap.put("old_file_name", filename);
					InputStream in = null;
					FileOutputStream out = null;
					try {
						String suffix = filename.substring(filename.lastIndexOf("."),filename.length());
						if(!FileTypeEnum.contains(suffix.substring(1)))
							throw new FileUploadException("不允许的文件类型");
						filePath = filePath+File.separatorChar+(fileName(tn,tableColumn).concat(suffix));
						in = item.getInputStream();
						out = new FileOutputStream(filePath);
						byte buffer[] = new byte[1024*10];
						int len = 0;
						while ((len = in.read(buffer)) > 0) {
							out.write(buffer, 0, len);
						}
						//将成功上传的文件加入到集合中
						resultMap.put("content_type", item.getContentType());
						resultMap.put("size", item.getSize()+"");
						resultMap.put("file_path", filePath);
						resultMap.put("table_name", tn);
						resultMap.put("id", id);
						resultMap.put("table_column", String.valueOf(tableColumn).toUpperCase());
						listMap.add(resultMap);
						filePath = FileConfigEnum.getFilePath(tn);
					} catch (Exception e) {
						log.error(e.getMessage());
						e.printStackTrace();
					} finally {
						if(out != null) {
							try {
								out.close();
							} catch (IOException e) {
								e.printStackTrace();
							}
						}
						if(in != null) {
							try {
								in.close();
							} catch (IOException e) {
								e.printStackTrace();
							}
						}
						item.delete();
					}
				}
			}
		}
		return listMap;
	}
	/**
	 *
	 * @Description
	 * @param file 接收上传的文件
	 * @param tableName 业务所对应表名
	 * @param tableColumn 业务所对应表字段
	 * @param id 业务所对应表主键
	 * @return 返回文件上传后的Map,Map中包含了文件路径,文件大小等
	 * @throws FileUploadException
	 * @author pengwz
	 * @date 2019年12月25日 上午10:03:45
	 */
	public static Map<String,String> upload2(MultipartFile file,Object tableName,
			Object tableColumn,String id) throws FilesUploadException {
		if(file == null || file.isEmpty()) {
			throw new FilesUploadException(ResultCode.DATA_ERROR,"没有需要上传的文件");
		}
		Map<String,String> resultMap = new HashMap<String, String>();
		FileOutputStream out = null;
		InputStream in = null;
		try {
			in = file.getInputStream();
		} catch (IOException e1) {
			e1.printStackTrace();
			throw new FilesUploadException(ResultCode.DATA_ERROR,"获取文件流异常");
		}
		String fileName = fileName(tableName,tableColumn);
		String oldname = file.getOriginalFilename();
		String suffix = oldname.substring(oldname.lastIndexOf("."));
		String targetPath = FileConfigEnum.getFilePath(String.valueOf(tableName))
				/* +"_"+String.valueOf(tableColumn).toUpperCase()+"_" */
				+File.separatorChar+fileName.concat(suffix);
		try {
			if(!FileTypeEnum.contains(suffix.substring(1)))
				throw new FilesUploadException(ResultCode.ERROR,"不允许的文件类型");
			out = new FileOutputStream(targetPath);
			byte buffer[] = new byte[1024*10];
			int len = 0;
			while ((len = in.read(buffer)) > 0) {
				out.write(buffer, 0, len);
			}
			//将成功上传的文件加入到集合中
			resultMap.put("content_type", file.getContentType());
			resultMap.put("original_filename", file.getOriginalFilename());
			resultMap.put("size", file.getSize()+"");
			resultMap.put("file_path", targetPath);
			resultMap.put("table_name",String.valueOf(tableName));
			resultMap.put("id", id);
			resultMap.put("table_column", String.valueOf(tableColumn).toUpperCase());
		} catch (Exception e) {
			log.error(e.getMessage());
			e.printStackTrace();
			throw new FilesUploadException(ResultCode.ERROR,e.getMessage());
		} finally {
			if(out != null) {
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if(in != null) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return resultMap;
	}
	/**
	 * 文件下载
	 * @param response
	 * @param filepath 需要下载的文件
	 * @param isDelete 是否删除文件
	 * @throws DownloadException 下载失败时,抛出此异常
	 * @author pengwz
	 * @date 2019年12月26日 下午1:06:21
	 */
	public static void download(HttpServletResponse response,String filepath,boolean isDelete)
			throws DownloadException {
		//得到要下载的文件
		FileInputStream in = null;
		OutputStream out = null;
		File file = new File(filepath);
		try {
			String name = filepath.substring(filepath.lastIndexOf("\\")+1);
			//如果文件不存在
			if(!file.exists()){
				log.error("------------下载的资源不存在-----------");
				throw new DownloadException(ResultCode.DATA_ERROR,"下载的资源不存在");
			}
			//设置响应头，控制浏览器下载该文件
//			response.setContentType("application/octet-stream");
			response.setHeader("content-disposition", "attachment;filename=" + URLEncoder.encode(name, "UTF-8"));
			in = new FileInputStream(filepath);
			out = response.getOutputStream();
			byte buffer[] = new byte[1024*10];
			int len = 0;
			while((len=in.read(buffer))>0){
				out.write(buffer, 0, len);
			}
		} catch (Exception e) {
			log.error(e.getMessage());
			e.printStackTrace();
		}finally {
			if(in != null) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if(out != null) {
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if(isDelete) {
				delete(file);
			}
		}
	}
	public static void download(HttpServletResponse response,InputStream is,
			String filename) throws DownloadException{
		//得到要下载的文件
		FileInputStream in = (FileInputStream)(is);
		OutputStream out = null;
		try {
			//设置响应头，控制浏览器下载该文件
			response.setHeader("content-disposition", "attachment;filename=" + URLEncoder.encode(filename, "UTF-8"));
			out = response.getOutputStream();
			byte buffer[] = new byte[1024*10];
			int len = 0;
			while((len=in.read(buffer))>0){
				out.write(buffer, 0, len);
			}
		} catch (Exception e) {
			log.error(e.getMessage());
			e.printStackTrace();
			throw new DownloadException(ResultCode.ERROR,e.getMessage());
		}finally {
			if(in != null) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if(out != null) {
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 *
	 * @Description 打包层级 一级目录json文件,二级目录图片,该方法强制要求调用者必须捕获
	 * PackagingFailedException异常
	 * @param obj 需要转为json的实体类,<red>该集合内对象不可重复</red>
	 * @param innerDicName 二级文件夹名称,默认img
	 * @param fileList 二级目录内需要打包的文件
	 * @throws PackagingFailedException 不知道为啥,方法不会主动抛出这个异常,需要手动捕获......
	 * @return 打包后的文件路径
	 * @author pengwz
	 * @date 2019年12月25日 上午9:11:19
	 */
	public /* synchronized */ static String zipOne(List<Object> obj,
			String innerDicName, List<String> fileList)  throws PackagingFailedException  {
		if(obj == null) {
			throw new PackagingFailedException(ResultCode.DATA_ERROR, "所必须的参数值为null");
		}
		long distinctedSize = obj.stream().distinct().count();
		boolean hasRepeat = obj.size() > distinctedSize;
		if(hasRepeat) {
			throw new PackagingFailedException(ResultCode.DATA_ERROR, "集合内传入了重复的对象");
		}
//		List<Object> objList = new ArrayList<Object>();
		Map<String,Object> objMap = new HashMap<String, Object>();
		for (Object object : obj) {
			String string = object.getClass().toString();
			String jsonClassName = string.substring(string.lastIndexOf(".")+1);
			objMap.put(jsonClassName, object);
		}
		String root = FileConfigEnum.getFilePath("ZIP_ROOT");
		String jsonString = JSON.toJSONString(objMap,SerializerFeature.WriteMapNullValue);
		//json名命名为map
		String str = objMap.getClass().toString();
//		int lenght;
//		log.info("-------当前Json对象: "+str+",待打包的文件数量: "+
//				(lenght = fileList == null ? 0: fileList.size())+" --------");
		String jsonName = str.substring(str.lastIndexOf(".")+1);
		//zip包命名
		String fileName = jsonName+"_"+sdf.format(new Date());
		String targetRoot = root+File.separatorChar+fileName;
		if(!new File(targetRoot).exists())
			new File(targetRoot).mkdirs();
		PrintWriter pw = null;
		try {
			pw = new PrintWriter(
					new BufferedWriter(
							new OutputStreamWriter(
									new FileOutputStream(targetRoot+File.separatorChar+jsonName+".json",true),"UTF-8")),true);
			pw.println(jsonString);
		}catch (IOException e) {
			log.error(e.getMessage());
			e.printStackTrace();
			throw new PackagingFailedException(ResultCode.ERROR, e.getMessage());
		} finally {
			if(pw != null) {
				pw.close();
			}
		}
		//默认img
		innerDicName = innerDicName == null || innerDicName.isEmpty() ? "img":innerDicName;
		String imgRoot = targetRoot+File.separatorChar+innerDicName;
		if(!new File(imgRoot).exists())
			new File(imgRoot).mkdirs();
		for (String string : fileList) {
			String imgName = string.substring(string.lastIndexOf(File.separatorChar)+1);
			BufferedOutputStream bos = null;
			BufferedInputStream bis = null;
			try {
				bos = new BufferedOutputStream(new FileOutputStream(imgRoot+File.separatorChar+imgName));
				bis = new BufferedInputStream(new FileInputStream(string));
				int d = -1;
				while((d = bis.read())!=-1) {
					bos.write(d);
				}
			} catch (Exception e) {
				log.error(e.getMessage());
				e.printStackTrace();
				throw new PackagingFailedException(ResultCode.ERROR, e.getMessage());
			} finally {
				if(bos != null) {
					try {
						bos.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				if(bis != null) {
					try {
						bis.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
		toZip(targetRoot+File.separator, root+File.separator, fileName+".zip");
		return root+File.separator+fileName+".zip";
	}

	/**
	 * 对对象和文件进行打包操作,文件前缀名就是最后一个对象的名字
	 * @param objList 需要转为json的多个对象,每个对象对应一个json文件
	 * @param innerDicName 二级目录名称,为 null 时默认img
	 * @param fileList 需要打包的多个文件,这些文件均在二级目录下
	 * @return zip包存放路径
	 * @throws PackagingFailedException 打包失败后,抛出此异常
	 * @author pengwz
	 * @date 2019年12月26日 上午9:50:01
	 */
	public /* synchronized */ static String zip(List<Object> objList,
			String innerDicName, List<String> fileList)  throws PackagingFailedException  {
		if(objList == null || objList.isEmpty()) {
			throw new PackagingFailedException(ResultCode.DATA_ERROR, "所必须的参数值为空");
		}
		String root = FileConfigEnum.getFilePath("ZIP_ROOT");
		String fileName = "";
		String targetRoot = root+File.separatorChar+"temp";
		for (int i = 0; i < objList.size(); i++) {
			Object obj = objList.get(i);
			String jsonString = JSON.toJSONString(obj,SerializerFeature.WriteMapNullValue);
			String str = obj.getClass().toString();
			String jsonName = str.substring(str.lastIndexOf(".")+1);
			fileName = jsonName+"_"+sdf.format(new Date());
			//targetRoot = root+File.separatorChar+fileName;
			if(!new File(targetRoot).exists())
				new File(targetRoot).mkdirs();

			PrintWriter pw = null;
			try {
				pw = new PrintWriter(
						new BufferedWriter(
								new OutputStreamWriter(
										new FileOutputStream(targetRoot+File.separatorChar+jsonName+".json",true),"UTF-8")),true);
				pw.println(jsonString);
			}catch (IOException e) {
				log.error(e.getMessage());
				e.printStackTrace();
				throw new PackagingFailedException(ResultCode.ERROR, e.getMessage());
			} finally {
				if(pw != null) {
					pw.close();
				}
			}
		}
		//默认img
		innerDicName = innerDicName == null || innerDicName.isEmpty() ? "img":innerDicName;
		String imgRoot = targetRoot+File.separatorChar+innerDicName;
		if(!new File(imgRoot).exists())
			new File(imgRoot).mkdirs();
		for (String string : fileList) {
			String imgName = string.substring(string.lastIndexOf(File.separatorChar)+1);
			BufferedOutputStream bos = null;
			BufferedInputStream bis = null;
			try {
				bos = new BufferedOutputStream(new FileOutputStream(imgRoot+File.separatorChar+imgName));
				bis = new BufferedInputStream(new FileInputStream(string));
				int d = -1;
				while((d = bis.read())!=-1) {
					bos.write(d);
				}
			} catch (Exception e) {
				log.error(e.getMessage());
				e.printStackTrace();
				throw new PackagingFailedException(ResultCode.ERROR, e.getMessage());
			} finally {
				if(bos != null) {
					try {
						bos.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				if(bis != null) {
					try {
						bis.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
		toZip(targetRoot+File.separator, root+File.separator, fileName+".zip");
		return root+File.separator+fileName+".zip";
	}
	/**
	 *
	 * @Description
	 * @param sourceFilePath 源文件夹路径
	 * @param zipFilePath 压缩后路径
	 * @param zipFilename 压缩文件名
	 * @author pengwz
	 * @date 2019年12月24日 下午6:24:54
	 */
	public static void toZip(String sourceFilePath, String zipFilePath, String zipFilename) {
		File sourceFile = new File(sourceFilePath);
		File zipPath = new File(zipFilePath);
		if (!zipPath.exists()) {
			zipPath.mkdirs();
		}
		File zipFile = new File(zipPath + File.separator + zipFilename);
		try (ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(zipFile))) {
			writeZip(sourceFile, "", zos);
			//文件压缩完成后，删除被压缩文件
			delete(sourceFile);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
			throw new PackagingFailedException(ResultCode.ERROR, e.getMessage());
		}
	}
	/**
	 *
	 * @Description 遍历文件夹,进行打包操作
	 * @param file
	 * @param parentPath
	 * @param zos
	 * @author pengwz
	 * @date 2019年12月24日 下午5:55:12
	 */
	private static void writeZip(File file, String parentPath, ZipOutputStream zos)
			throws PackagingFailedException{
		if (file.isDirectory()) {
			parentPath += file.getName() + File.separator;
			File[] files = file.listFiles();
			for (File f : files) {
				writeZip(f, parentPath, zos);
			}
		} else {
			try (BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file))) {
				//对于父级目录截取,直接显示子文件或其子文件目录
				String str = parentPath.substring(parentPath.indexOf(File.separator)+1);
				//指定zip文件夹
				ZipEntry zipEntry = new ZipEntry(
						str = str.trim().equals(File.separator) ? file.getName():str+file.getName());
				zos.putNextEntry(zipEntry);
				int len;
				byte[] buffer = new byte[1024 * 10];
				while ((len = bis.read(buffer, 0, buffer.length)) != -1) {
					zos.write(buffer, 0, len);
					zos.flush();
				}
			} catch (IOException e) {
				e.printStackTrace();
				log.error(e.getMessage());
				throw new PackagingFailedException(ResultCode.ERROR, e.getMessage());
			}
		}
	}
	/**
	 *
	 * @Description 删除文件
	 * @param dir
	 * @return
	 * @author pengwz
	 * @date 2019年12月24日 下午5:53:25
	 */
	public static boolean delete(File dir) {
		if(!dir.exists())
			return true;
		if (dir.isDirectory()) {
			String[] children = dir.list();
			for (int i = 0; i < children.length; i++) {
				boolean success = delete(new File(dir, children[i]));
				if (!success) {
					return false;
				}
			}
		}
		//删除空文件夹
		return dir.delete();
	}


	public static void main(String[] args) {
		Map<String,Object> map = new HashMap<String, Object>();
		AuditFileTable obj = new AuditFileTable();
		obj.setFileid("hahah");
		obj.setCreateTime(new Date());
		AuditIssueVeh issueVeh = new AuditIssueVeh();
		issueVeh.setEtcNo("123458");
		map.put("AuditFileTable", obj);
		map.put("AuditIssueVeh", issueVeh);
		String jsonString = JSON.toJSONString(map,SerializerFeature.WriteMapNullValue);
		System.err.println(jsonString);

		List<Object> list = new ArrayList<Object>();
		list.add(obj);
		list.add(issueVeh);
		list.add(issueVeh);
		long distinctedSize = list.stream().distinct().count();
		System.err.println("distinctedSize="+distinctedSize);
		boolean hasRepeat = list.size() > distinctedSize;
		System.err.println("hasRepeat="+hasRepeat);
	}
}



















