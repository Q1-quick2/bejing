package com.sutong.bjstjh.exception;

/**   
 * @ClassName:  FilesUploadException   
 * @Description:  文件上传失败时,抛出此异常
 * @author: pengwz
 * @date:   2019年12月25日 上午9:47:46      
 */
public class FilesUploadException extends BaseException {

	/**   
	 * @Fields serialVersionUID : 
	 */   
	private static final long serialVersionUID = -3337138899412881273L;

	public FilesUploadException() {
		super();
	}

	public FilesUploadException(Integer code, String msg) {
		super(code, msg);
	}

	public FilesUploadException(String message, Integer code, String msg) {
		super(message, code, msg);
	}

	public FilesUploadException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace,
			Integer code, String msg) {
		super(message, cause, enableSuppression, writableStackTrace, code, msg);
	}

	public FilesUploadException(String message, Throwable cause, Integer code, String msg) {
		super(message, cause, code, msg);
	}

	public FilesUploadException(Throwable cause, Integer code, String msg) {
		super(cause, code, msg);
	}

	
}
