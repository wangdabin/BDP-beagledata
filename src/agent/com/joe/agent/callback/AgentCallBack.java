package com.joe.agent.callback;

import com.joe.core.callback.CallBack;
import com.sky.config.ConfigAble;

/**
 * 
 * 通过 com.joe.agent.annotation.AgentCallBack
 *  定义实现类
 * @author qiaolong
 *
 */
public interface AgentCallBack extends CallBack,ConfigAble{
	
	/**
	 * 启动的时候回调
	 * @param host
	 */
	void doStart(String hostname,String ip);
	
	/**
	 * ping的时候回调
	 * @param host
	 */
	void doPing(String hostname,String ip);
	
	/**
	 * 删除时候的回调
	 * @param host
	 */
	void doDelete(String hostname,String ip);
	
	/**
	 * 停止的时候回调
	 * @param host
	 */
	void doStop(String hostname,String ip);
}
