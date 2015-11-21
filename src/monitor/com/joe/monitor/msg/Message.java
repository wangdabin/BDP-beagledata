package com.joe.monitor.msg;

import com.joe.core.json.JsonAble;

/**
 * 消息
 * @author qiaolong
 *
 */
public interface Message extends JsonAble{

	/**
	 * 消息名称
	 * @return
	 */
	String getName();
	
	/**
	 * 消息内容
	 * @return
	 */
	String getMessage();
}
