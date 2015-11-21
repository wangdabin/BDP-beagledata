package com.joe.tools.define;

import java.io.IOException;

/**
 * 变量定义
 * @author qiaolong
 *
 */
public class FieldDefine extends AbstractDefine{

	@Override
	public boolean isAbstract() {
		return false;
	}
	
	public void setAbstract(boolean _abstract) {
		//do nothing 
	}
	
	@Override
	public Appendable define(Appendable sb) throws IOException {
		return appendLine(appendPrefix(sb),"");
	}
}
