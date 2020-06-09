package com.sutong.bjstjh.exception;

/**   
 * @ClassName:  DownloadException   
 * @Description:  文件下载异常
 * @author: pengwz
 * @date:   2019年12月19日 下午7:43:05      
 */
public class DownloadException extends BaseException{

	/**   
	 * @Fields serialVersionUID : 
	 */   
	private static final long serialVersionUID = -2642403121794105631L;

	/**   
	 * @Description: 
	 * @param:  
	 * @throws   
	 */  
	public DownloadException() {
		super();
	}
	/**   
	 * @Description: 
	 * @param:  
	 * @throws   
	 */  
	public DownloadException(Integer code, String msg) {
		super(code, msg);
	}

	/**   
	 * @Description: 
	 * @param:  
	 * @throws   
	 */  
	public DownloadException(String message, Integer code, String msg) {
		super(message, code, msg);
	}

	/**   
	 * @Description: 
	 * @param:  
	 * @throws   
	 */  
	public DownloadException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace,
			Integer code, String msg) {
		super(message, cause, enableSuppression, writableStackTrace, code, msg);
	}

	/**   
	 * @Description: 
	 * @param:  
	 * @throws   
	 */  
	public DownloadException(String message, Throwable cause, Integer code, String msg) {
		super(message, cause, code, msg);
	}

	/**   
	 * @Description: 
	 * @param:  
	 * @throws   
	 */  
	public DownloadException(Throwable cause, Integer code, String msg) {
		super(cause, code, msg);
	}

	
}
