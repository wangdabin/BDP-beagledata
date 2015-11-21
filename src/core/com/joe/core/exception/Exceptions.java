package com.joe.core.exception;

/**
 * 异常机制
 * @author qiaolong
 *
 */
public class Exceptions {

	/**
	 * 指定消息创建异常
	 * @param message
	 * @return
	 */
	public static final RuntimeException create(String message){
		return new RuntimeException(message);
	}
	
	/**
	 * 
	 * @param message
	 * @return
	 */
	public static final RuntimeException create(Throwable throwable){
		return new RuntimeException(throwable);
	}
	
	/**
	 * 
	 * @param message
	 * @param throwable
	 * @return
	 */
	public static final RuntimeException create(String message,Throwable throwable){
		return new RuntimeException(message,throwable);
	}
}
