package com.joe.agent.callback;

import com.joe.core.callback.CallBack;
import com.sky.task.vo.Task;
import com.sky.task.vo.TranOrder;

/**
 * 任务执行回调
 * @author qiaolong
 *
 */
public interface TaskCallBack extends CallBack{

	/**
	 * 订单完成进度
	 * @param order
	 * @param completion
	 */
	void orderCompletion(String hostname,String ip,TranOrder order,double completion);
	
	/**
	 * 订单完成回调
	 * @param orderId
	 */
	void orderFinish(String hostname,String ip,TranOrder order);
	
	/**
	 * 订单被kill的回调
	 * @param orderId
	 */
	void orderKill(String hostname,String ip,TranOrder order);
	
	/**
	 * 任务完成时的回到
	 * @param taskId
	 */
	void taskFinish(String hostname,String ip,Task task);
	
	/**
	 * 任务被kill时的回调
	 * @param task
	 */
	void taskKill(String hostname,String ip,Task task);
	
	/**
	 * 是否支持此任务id
	 * @param taskId
	 */
	boolean supportTask(long taskId);
	
	/**
	 * 是否支持此订单id
	 * @param orderId
	 * @return
	 */
	boolean supportOrder(long orderId);
}
