package com.joe.agent.service.supportor;

import java.util.Iterator;
import java.util.TreeSet;

import com.sky.task.vo.Task;

/**
 * 
 * @ClassName: TaskQueue
 * @Description:任务队列
 * @author: LiuZhiJun
 * @date: 2015年1月28日 下午1:31:02
 */

public class TaskQueue {

	private long ownerId;

	public long getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(long ownerId) {
		this.ownerId = ownerId;
	}

	public static TaskQueue create() {
		return new TaskQueue();
	}

	private TreeSet<Task> queue = new TreeSet<Task>();

	public void setTaskList(TreeSet<Task> queue) {
		this.queue = queue;
	}

	// 添加一项任务
	public synchronized void addTask(Task task) {
		if (task != null) {
			queue.add(task);
		}
	} // 完成任务后将它从任务队列中删除

	public synchronized void finishTask(Task task) {
		if (task != null) {
			task.setStatus(Task.FINISH);
			queue.remove(task);
		}
	} // 取得一项待执行任务

	public synchronized Task next() {
		Iterator<Task> it = queue.iterator();
		Task task;
		while (it.hasNext()) {
			task = it.next(); // 寻找一个新建的任务
			if (Task.WAITING == task.getStatus()) { // 把任务状态置为运行中
				task.setStatus(Task.RUNNING);
				return task;
			}
		}
		return null;
	}

}
