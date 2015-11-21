package com.joe.core.exception;

/**
 * 
 * @author Joe
 *
 */
public class CoreException extends BaseException{
	
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
	public CoreException(String code) {
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
	public CoreException(Throwable cause, String code) {
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
	public CoreException(String code, Object[] values) {
		super(code, null, code, values);
	}

	/**
	 * Constructors
	 * 
	 * @param cause
	 *            异常接口
	 * @param code
	 *            错误代码
	 * @param values
	 *            一组异常信息待定参数
	 */
	public CoreException(Throwable cause, String code, Object[] values) {
		super(code, null, code, values);
	}
}
