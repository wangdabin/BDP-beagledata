package com.joe.tools.define;

import java.io.IOException;

public interface Define {

	public static final String ACCESS_TYPE_PUBLIC = "public";
	public static final String ACCESS_TYPE_PROTECTED = "protected";
	public static final String ACCESS_TYPE_PRIVATE = "private";
	public static final String ACCESS_TYPE_DEFAULT = "";
	
	public static final String ENTER = "\n";
	public static final String SPACE = " ";
	
	/**
	 * 
	 * @param sb
	 * @return
	 */
	Appendable define(Appendable sb) throws IOException ;
}
