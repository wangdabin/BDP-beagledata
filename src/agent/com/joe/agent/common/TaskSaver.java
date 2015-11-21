package com.joe.agent.common;

import org.apache.log4j.Logger;

import com.joe.agent.annotation.ACommonTaskCallBack;
import com.joe.agent.callback.CommonTaskCallBack;
import com.joe.agent.service.IssuedService;
import com.joe.core.factory.BeanFactory;
import com.joe.host.utils.HostUtils;
import com.sky.config.Configed;
import com.sky.task.vo.Task;
import com.sky.task.vo.TranOrder;

/**
 * 用于任务的保存
 * @author qiaolong
 *
 */
@ACommonTaskCallBack(name="保存任务",type="taskSaver")
public class TaskSaver extends Configed implements CommonTaskCallBack{

	private static final Logger LOG = Logger.getLogger(TaskSaver.class);
	
	public static final double COMPLETION_FINISH = 100.0;
	public static final double COMPLETION_KILLED = -1.0;
	
	private IssuedService taskService;
	public TaskSaver(){
		taskService = BeanFactory.getBean("issuedService", IssuedService.class);
	}
	
	@Override
	public void orderCompletion(String hostname, String ip, TranOrder order,
			double completion) {
		LOG.debug("Host " + HostUtils.buildKey(hostname, ip) + " order completion " + completion);
		taskService.updateOrder(order.getId(), completion);
	}

	@Override
	public void orderFinish(String hostname, String ip, TranOrder order) {
		taskService.updateOrder(order.getId(), COMPLETION_FINISH);
		LOG.debug("Host " + HostUtils.buildKey(hostname, ip) + " order finish!");
	}

	@Override
	public void orderKill(String hostname, String ip, TranOrder order) {
		LOG.debug("Host " + HostUtils.buildKey(hostname, ip) + " order kill!");
		taskService.updateOrder(order.getId(),COMPLETION_KILLED);
	}

	@Override
	public void taskFinish(String hostname, String ip, Task task) {
		LOG.debug("Host " + HostUtils.buildKey(hostname, ip) + " task finish! ");
		taskService.update(task);
	}

	@Override
	public void taskKill(String hostname, String ip, Task task) {
		LOG.debug("Host " + HostUtils.buildKey(hostname, ip) + " task kill!");
		taskService.update(task);
	}

	@Override
	public boolean supportTask(long taskId) {
		return true;
	}

	@Override
	public boolean supportOrder(long orderId) {
		return true;
	}

	@Override
	public void beforeSubmit(Task task) {
		LOG.debug("Will save task");
		taskService.add(task);
	}

	@Override
	public void afterSubmit(Task task) {
		
	}
}
