package com.sutong.bjstjh.util.enumerate;
/**
 * 
 * @ClassName:  FileTypeEnum   
 * @Description:  允许上传的文件类型
 * @author: pengwz
 * @date:   2019年12月23日 下午2:54:17
 */
public enum FileTypeEnum {

	GIF,
	HTML,
	JS,
	TXT,
	JPG,
	JPEG,
	PNG,
	ZIP,
	MP4,
	RAR,
	JSON;
	
	/**
	 * 
	 * @Description 查询FileTypeEnum是否包含指定的文件类型
	 * @param type
	 * @return 不包含返回false
	 * @author pengwz
	 * @date 2019年12月23日 上午11:43:55
	 */
	public static boolean contains(String type) {
		FileTypeEnum[] values = FileTypeEnum.values();
		for (int i = 0; i < values.length; i++) {
			if(type.equalsIgnoreCase(values[i].toString())) {
				return true;
			}
		}
		return false;
	}
	public static void main(String[] args) {
		System.err.println(FileTypeEnum.contains("zip".substring(1)));
	}
}
