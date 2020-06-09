package com.sutong.bjstjh.exception;

/**
 *	 当业务未按照设定的规则执行时,抛出此异常
 * @ClassName:  BreakRulesException   
 * @Description:  
 * @author: pengwz
 * @date:   2019年12月28日 上午11:39:25
 */
public class BreakRulesException extends BaseException {

	private static final long serialVersionUID = 6865366455284951865L;

	public BreakRulesException() {
		super();
	}

	public BreakRulesException(Integer code, String msg) {
		super(code, msg);
	}

	public BreakRulesException(String message, Integer code, String msg) {
		super(message, code, msg);
	}

	public BreakRulesException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace,
			Integer code, String msg) {
		super(message, cause, enableSuppression, writableStackTrace, code, msg);
	}

	public BreakRulesException(String message, Throwable cause, Integer code, String msg) {
		super(message, cause, code, msg);
	}

	public BreakRulesException(Throwable cause, Integer code, String msg) {
		super(cause, code, msg);
	}
	
}
