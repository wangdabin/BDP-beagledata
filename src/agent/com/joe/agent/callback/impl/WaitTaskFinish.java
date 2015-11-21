package com.joe.agent.callback.impl;

import com.joe.agent.callback.BasicTaskCallBack;
import com.joe.agent.manager.TaskManager;
import com.sky.task.vo.Task;

/**
 * 等待任务执行完成
 * @author qiaolong
 *
 */
public class WaitTaskFinish extends BasicTaskCallBack{

	private static final long PER_TIME = 1000;
	private long perWait;
	private Task task;
	private boolean wait = true;
	private TaskManager manager;
	
	public WaitTaskFinish(TaskManager manager,Task task){
		this(task,PER_TIME);
		this.manager = manager;
	}
	
	public WaitTaskFinish(Task task,long perWait){
		this.task = task;
		this.perWait = perWait;
	}
	
	@Override
	public void taskFinish(String hostname, String ip, Task task) {
		this.wait = false;
//		manager.removeCallBack(this); //删除callback
	}

	@Override
	public void taskKill(String hostname, String ip, Task task) {
		this.wait = false;
//		manager.removeCallBack(this); //删除callback
	}

	/**
	 * 支持的任务ID
	 */
	@Override
	public boolean supportTask(long taskId) {
		return task.getId() == taskId;
	}
	
	/**
	 * 等待任务结束
	 * @throws InterruptedException
	 */
	public void waitFinish() throws InterruptedException{
		while(wait){
			Thread.sleep(perWait);
		}
	}
}
