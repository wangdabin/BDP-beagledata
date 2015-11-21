package com.joe.agent.handler;

import java.util.List;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.lang.StringUtils;

import com.joe.agent.callback.impl.WaitTaskFinish;
import com.joe.agent.client.AgentTaskClient;
import com.joe.agent.manager.TaskManager;
import com.joe.core.exception.Exceptions;
import com.joe.core.exception.UnSupportMethodException;
import com.joe.core.support.AbstractCommandWorker;
import com.sky.service.define.CommandDefine;
import com.sky.task.vo.Task;
import com.sky.task.vo.TranOrder;

public class AgentCommandWorker extends AbstractCommandWorker{

	private TaskManager taskManager;
	private AgentTaskClient taskClient;
	
	public AgentCommandWorker(Configuration conf){
		super(conf);
		this.taskManager = TaskManager.getManager(conf);
		this.taskClient = new AgentTaskClient(conf);
	}
	
	@Override
	public void execute(CommandDefine command) {
		throw UnSupportMethodException.getException();
	}

	@Override
	public void execute(String host, CommandDefine command) {
		execute(host,command,false);
	}

	@Override
	public void execute(List<String> hosts, CommandDefine command) {
		for(String host : hosts){
			execute(host,command,false);
		}
	}

	@Override
	public void execute(List<String> hosts, CommandDefine command, boolean syn) {
		for(String host : hosts){
			execute(host,command,syn);
		}
	}

	/**
	 * 
	 * @param host
	 * @param command
	 * @param waitFinish
	 */
	public void execute(String host,CommandDefine command,boolean waitFinish){
		Task task = new Task();
		task.setHostname(host);
		task.setName("execute-to-" + host);
		TranOrder order = new TranOrder();
		order.setOrder(0);
		String basePath = command.getBasePath();
		String shell = command.getShell();
		if(!StringUtils.isBlank(basePath)){
			shell = basePath + "/" + shell;
		}
		order.setCommand(shell);
		List<String> params = command.getParams();
		if(params != null && !params.isEmpty()){
			order.setParams(params.toArray(new String[params.size()]));
		}
		order.setType(TranOrder.TYPE_SHELL);
		task.addOrder(order);
		taskManager.beforeSubmit(task); //提交任务之前要做的事情，如保存这个任务等
		if(waitFinish){
			WaitTaskFinish callBack = new WaitTaskFinish(taskManager,task);
			taskManager.registerCallBack(callBack);
			taskClient.commitTask(host, task);
			try {
				callBack.waitFinish();
			} catch (InterruptedException e) {
				throw Exceptions.create(e);
			}
		}else{
			taskClient.commitTask(host, task);
		}
		taskManager.afterSubmit(task); //提交任务之后需要做的事情，如等待任务执行完成等
	}
}
