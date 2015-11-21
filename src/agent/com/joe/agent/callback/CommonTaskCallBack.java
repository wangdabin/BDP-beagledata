package com.joe.agent.callback;

import com.sky.config.ConfigAble;
import com.sky.task.vo.Task;

/**
 * 通用的taskCallback，是通过
 * com.joe.agent.annotation.ATaskCallBack 
 * 注解加载到的callbask类
 * @author qiaolong
 *
 */
public interface CommonTaskCallBack extends TaskCallBack,ConfigAble{

	/**
	 * 提交任务之前
	 * @param task
	 */
	void beforeSubmit(Task task);
	
	/**
	 * 提交任务之后
	 * @param task
	 */
	void afterSubmit(Task task);
}
