package com.joe.core.support;

import java.util.List;

import com.sky.service.define.CommandDefine;

/**
 * 执行任务
 * @author qiaolong
 *
 */
public interface CommandWorker {

	/**
	 * 执行任务脚本
	 * @param command
	 */
	void execute(CommandDefine command);
	
	/**
	 * 指定机器执行脚本
	 * @param host
	 * @param command
	 */
	void execute(String host,CommandDefine command);
	
	/**
	 * 在多台机器上执行脚本
	 * @param hosts
	 * @param command
	 */
	void execute(List<String> hosts,CommandDefine command);
	
	/**
	 * 在多台机器上同步执行脚本
	 * @param hosts
	 * @param command
	 * @param syn
	 */
	void execute(List<String> hosts,CommandDefine command,boolean syn);
	
	/**
	 * 等待结束
	 * @param host
	 * @param command
	 * @param waitFinish
	 */
	public void execute(String host,CommandDefine command,boolean waitFinish);
}
