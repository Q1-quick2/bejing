package com.sutong.bjstjh.exception;

/**   
 * @ClassName:  DataStaleException   
 * @Description:  参数过时异常
 * @author: pengwz
 * @date:   2019年12月19日 下午7:43:05      
 */
public class DataStaleException extends BaseException{

	private static final long serialVersionUID = -6840818353600048535L;

	
	/**   
	 * @Description: 
	 * @param:  
	 * @throws   
	 */  
	public DataStaleException() {
		super();
	}

	/**   
	 * @Description: 
	 * @param:  
	 * @throws   
	 */  
	public DataStaleException(Integer code, String msg) {
		super(code, msg);
	}

	/**   
	 * @Description: 
	 * @param:  
	 * @throws   
	 */  
	public DataStaleException(String message, Integer code, String msg) {
		super(message, code, msg);
	}

	/**   
	 * @Description: 
	 * @param:  
	 * @throws   
	 */  
	public DataStaleException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace,
			Integer code, String msg) {
		super(message, cause, enableSuppression, writableStackTrace, code, msg);
	}

	/**   
	 * @Description: 
	 * @param:  
	 * @throws   
	 */  
	public DataStaleException(String message, Throwable cause, Integer code, String msg) {
		super(message, cause, code, msg);
	}

	/**   
	 * @Description: 
	 * @param:  
	 * @throws   
	 */  
	public DataStaleException(Throwable cause, Integer code, String msg) {
		super(cause, code, msg);
	}

	
}
