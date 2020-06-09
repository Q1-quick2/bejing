package com.sutong.bjstjh.exception;

/**   
 * @ClassName:  PackagingFailedException   
 * @Description: 打包失败异常
 * @author: pengwz
 * @date:   2019年12月25日 上午9:15:39      
 */
public class PackagingFailedException extends BaseException {

	private static final long serialVersionUID = 2588339150644450586L;

	/**   
	 * @Description: 
	 * @param:  
	 * @throws   
	 */  
	public PackagingFailedException() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**   
	 * @Description: 
	 * @param:  
	 * @throws   
	 */  
	public PackagingFailedException(Integer code, String msg) {
		super(code, msg);
		// TODO Auto-generated constructor stub
	}

	/**   
	 * @Description: 
	 * @param:  
	 * @throws   
	 */  
	public PackagingFailedException(String message, Integer code, String msg) {
		super(message, code, msg);
		// TODO Auto-generated constructor stub
	}

	/**   
	 * @Description: 
	 * @param:  
	 * @throws   
	 */  
	public PackagingFailedException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace, Integer code, String msg) {
		super(message, cause, enableSuppression, writableStackTrace, code, msg);
		// TODO Auto-generated constructor stub
	}

	/**   
	 * @Description: 
	 * @param:  
	 * @throws   
	 */  
	public PackagingFailedException(String message, Throwable cause, Integer code, String msg) {
		super(message, cause, code, msg);
		// TODO Auto-generated constructor stub
	}

	/**   
	 * @Description: 
	 * @param:  
	 * @throws   
	 */  
	public PackagingFailedException(Throwable cause, Integer code, String msg) {
		super(cause, code, msg);
		// TODO Auto-generated constructor stub
	}
	
}
