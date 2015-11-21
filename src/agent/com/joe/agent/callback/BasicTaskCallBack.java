package com.joe.agent.callback;

import com.sky.config.Configed;
import com.sky.task.vo.Task;
import com.sky.task.vo.TranOrder;

/**
 * 基本的任务回到类
 * @author qiaolong
 *
 */
public class BasicTaskCallBack extends Configed implements TaskCallBack{

	@Override
	public void orderCompletion(String hostname, String ip, TranOrder order,
			double completion) {
	}

	@Override
	public void orderFinish(String hostname, String ip, TranOrder order) {
	}

	@Override
	public void orderKill(String hostname, String ip, TranOrder order) {
	}

	@Override
	public void taskFinish(String hostname, String ip, Task task) {
	}

	@Override
	public void taskKill(String hostname, String ip, Task task) {
	}

	@Override
	public boolean supportTask(long taskId) {
		return false;
	}

	@Override
	public boolean supportOrder(long orderId) {
		return false;
	}
}
