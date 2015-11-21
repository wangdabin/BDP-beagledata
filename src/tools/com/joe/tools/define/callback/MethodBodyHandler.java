package com.joe.tools.define.callback;

import java.io.IOException;

/**
 * 
 * 方法主题
 * @author qiaolong
 *
 */
public interface MethodBodyHandler {

	Appendable handle(Appendable sb) throws IOException ;
}
