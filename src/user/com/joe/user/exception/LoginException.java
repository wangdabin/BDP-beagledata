package com.joe.user.exception;

import com.joe.core.exception.BaseException;

/**
 * 
 * @author Joe
 *
 */
public class LoginException extends BaseException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructors
	 * 
	 * @param code
	 *            错误代码
	 */
	public LoginException(String code) {
		super(code, null, code, null);
	}

	/**
	 * Constructors
	 * 
	 * @param cause
	 *            异常接口
	 * @param code
	 *            错误代码
	 */
	public LoginException(Throwable cause, String code) {
		super(code, cause, code, null);
	}

	/**
	 * Constructors
	 * 
	 * @param code
	 *            错误代码
	 * @param values
	 *            一组异常信息待定参数
	 */
	public LoginException(String code, Object[] values) {
		super(code, null, code, values);
	}
	
	/**
	 * 
	 * @param message
	 * @param cause
	 * @param code
	 * @param values
	 */
	public LoginException(String message, Throwable cause, String code,
			Object[] values) {
		super(message, cause, code, values);
	}

}
