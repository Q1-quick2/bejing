package com.sutong.bjstjh.exception;

/**
 * 	当参数不合法时,抛出此异常
 * @ClassName:  IllegalParameterException   
 * @Description:  
 * @author: pengwz
 * @date:   2019年12月28日 上午11:40:09
 */
public class IllegalParameterException extends BaseException {

	private static final long serialVersionUID = 1341665661868181292L;

	public IllegalParameterException() {
		super();
	}

	public IllegalParameterException(Integer code, String msg) {
		super(code, msg);
	}

	public IllegalParameterException(String message, Integer code, String msg) {
		super(message, code, msg);
	}

	public IllegalParameterException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace, Integer code, String msg) {
		super(message, cause, enableSuppression, writableStackTrace, code, msg);
	}

	public IllegalParameterException(String message, Throwable cause, Integer code, String msg) {
		super(message, cause, code, msg);
	}

	public IllegalParameterException(Throwable cause, Integer code, String msg) {
		super(cause, code, msg);
	}

}
