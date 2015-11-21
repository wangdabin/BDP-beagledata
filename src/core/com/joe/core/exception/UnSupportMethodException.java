package com.joe.core.exception;

public class UnSupportMethodException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public UnSupportMethodException(){
		super("The method not support");
	}
	
	public static final UnSupportMethodException getException(){
		return new UnSupportMethodException();
	}
}
