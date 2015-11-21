package com.joe.agent.manager;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.configuration.Configuration;
import org.apache.log4j.Logger;

import com.joe.agent.annotation.ACommonTaskCallBack;
import com.joe.agent.callback.CommonTaskCallBack;
import com.joe.agent.callback.TaskCallBack;
import com.joe.core.factory.AbstractFactory;
import com.sky.task.vo.Task;
import com.sky.task.vo.TranOrder;

/**
 * 
 * 任务执行管理者
 * 
 * @author qiaolong
 * 
 */
public class TaskManager extends AbstractFactory<ACommonTaskCallBack, CommonTaskCallBack> implements TaskCallBack {
	private static final Logger LOG = Logger.getLogger(TaskManager.class);
	
	private final Set<TaskCallBack> callBacks = Collections.synchronizedSet(new HashSet<TaskCallBack>());
	
	//任务的ID
//	Map<Long,Set<TaskCallBack>> callBacks = Collections.synchronizedMap(new HashMap<Long,Set<TaskCallBack>>());

	// 单例实体类
	private Set<CommonTaskCallBack> singletonInstance = new HashSet<CommonTaskCallBack>();
	private static TaskManager manager;
	
	private TaskManager(Configuration conf) {
		super(conf, LOG, ACommonTaskCallBack.class);
	}

	/**
	 * 
	 * @return
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	public synchronized Set<CommonTaskCallBack> getAllInstances() throws InstantiationException, IllegalAccessException{
		if(singletonInstance.isEmpty()){
			Collection<Class<CommonTaskCallBack>> classes = this.getAll();
			for(Class<CommonTaskCallBack> clazz : classes){
				CommonTaskCallBack callBack = clazz.newInstance();
				callBack.setConf(getConf());
				singletonInstance.add(callBack);
			}
		}
		return singletonInstance;
	}
	/**
	 * 得到当前的
	 * 
	 * @return
	 */
	public Set<TaskCallBack> getCallbacks() {
		return callBacks;
//		Set<TaskCallBack> backs = new HashSet<TaskCallBack>();
//		for(Set<TaskCallBack> back : callBacks.values()){
//			if(back != null && !back.isEmpty()){
//				backs.addAll(back);
//			}
//		}
//		return backs;
	}

	/**
	 * 提交任务之前
	 * @param task
	 */
	public void beforeSubmit(Task task){
		try {
			Set<CommonTaskCallBack> callBacks = getAllInstances();
			synchronized(callBacks){
				for(CommonTaskCallBack callBack : callBacks){
					callBack.beforeSubmit(task);
				}
			}
		} catch (Exception e) {
			LOG.error(e.getMessage(),e);
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * 提交任务之后
	 * @param task
	 */
	public void afterSubmit(Task task){
		try {
			Set<CommonTaskCallBack> callBacks = getAllInstances();
			synchronized(callBacks){
				for(CommonTaskCallBack callBack : callBacks){
					callBack.afterSubmit(task);
				}
			}
		} catch (Exception e) {
			LOG.error(e.getMessage(),e);
			throw new RuntimeException(e);
		}
	}
	
	@Override
	public void orderCompletion(String hostname, String ip, TranOrder order,
			double completion) {
		try {
			Set<CommonTaskCallBack> callBacks = getAllInstances();
			for(CommonTaskCallBack callBack : callBacks){
				if(callBack.supportOrder(order.getId())){
					callBack.orderCompletion(hostname, ip, order, completion);	
				}
			}
		} catch (Exception e) {
			LOG.error(e.getMessage(),e);
			throw new RuntimeException(e);
		}
		
		for (TaskCallBack callBack : callBacks) {
			if (callBack.supportOrder(order.getId())) {
				callBack.orderCompletion(hostname, ip, order, completion);
			}
		}
	}

	@Override
	public void orderFinish(String hostname, String ip, TranOrder order) {
		try {
			Set<CommonTaskCallBack> callBacks = getAllInstances();
			for(CommonTaskCallBack callBack : callBacks){
				callBack.orderFinish(hostname, ip, order);
			}
		} catch (Exception e) {
			LOG.error(e.getMessage(),e);
			throw new RuntimeException(e);
		}
		
		for (TaskCallBack callBack : callBacks) {
			if (callBack.supportOrder(order.getId())) {
				callBack.orderFinish(hostname, ip, order);
			}
		}
	}

	@Override
	public void orderKill(String hostname, String ip, TranOrder order) {
		try {
			Set<CommonTaskCallBack> callBacks = getAllInstances();
			for(CommonTaskCallBack callBack : callBacks){
				if (callBack.supportOrder(order.getId())) {
					callBack.orderKill(hostname, ip, order);
				}
			}
		} catch (Exception e) {
			LOG.error(e.getMessage(),e);
			throw new RuntimeException(e);
		}
		
		for (TaskCallBack callBack : callBacks) {
			if (callBack.supportOrder(order.getId())) {
				callBack.orderKill(hostname, ip, order);
			}
		}
	}

	@Override
	public void taskFinish(String hostname, String ip, Task task) {
		try {
			Set<CommonTaskCallBack> callBacks = getAllInstances();
			for(CommonTaskCallBack callBack : callBacks){
				if (callBack.supportTask(task.getId())) {
					callBack.taskFinish(hostname, ip, task);
				}
			}
		} catch (Exception e) {
			LOG.error(e.getMessage(),e);
			throw new RuntimeException(e);
		}
		
		Set<TaskCallBack> removeBacks = new HashSet<TaskCallBack>();
		for (TaskCallBack callBack : callBacks) {
			if (callBack.supportTask(task.getId())) {
				callBack.taskFinish(hostname, ip, task);
				removeBacks.add(callBack);
			}
		}
		callBacks.remove(removeBacks);
		removeBacks.clear();
	}

	@Override
	public void taskKill(String hostname, String ip, Task task) {
		try {
			Set<CommonTaskCallBack> callBacks = getAllInstances();
			for(CommonTaskCallBack callBack : callBacks){
				if (callBack.supportTask(task.getId())) {
					callBack.taskFinish(hostname, ip, task);
				}
			}
		} catch (Exception e) {
			LOG.error(e.getMessage(),e);
			throw new RuntimeException(e);
		}
		
		Set<TaskCallBack> removeBacks = new HashSet<TaskCallBack>();
		for (TaskCallBack callBack : callBacks) {
			if (callBack.supportTask(task.getId())) {
				callBack.taskKill(hostname, ip, task);
			}
		}
		callBacks.remove(removeBacks);
		removeBacks.clear();
	}

	/**
	 * 注册
	 * @param callBack
	 */
	public void registerCallBack(TaskCallBack callBack) {
		callBacks.add(callBack);
	}

	/**
	 * 删除回调
	 * @param callBack
	 */
	public void removeCallBack(TaskCallBack callBack) {
		callBacks.remove(callBack);
	}

	@Override
	public boolean supportTask(long taskId) {
		return false;
	}

	@Override
	public boolean supportOrder(long orderId) {
		return false;
	}

	@Override
	protected String getName(ACommonTaskCallBack ann) {
		return ann.name();
	}

	@Override
	protected String getType(ACommonTaskCallBack ann) {
		return ann.type();
	}

	@Override
	protected boolean isEnable(ACommonTaskCallBack ann) {
		return ann.enable();
	}
	
	/**
	 * 
	 * @param conf
	 * @return
	 */
	public synchronized static TaskManager getManager(Configuration conf){
		if(manager == null){
			manager = new TaskManager(conf);
		}
		return manager;
	}
}
