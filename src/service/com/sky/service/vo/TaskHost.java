package com.sky.service.vo;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

import com.sky.task.vo.Task;

@XmlRootElement
public class TaskHost implements Serializable {

	private static final long serialVersionUID = 1234314051469359871L;

	private int id;
	private String hostName;
	private String hostIp;
	private String taskId;
	private int status;
	private Task task;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getHostName() {
		return hostName;
	}

	public void setHostName(String hostName) {
		this.hostName = hostName;
	}

	public String getHostIp() {
		return hostIp;
	}

	public void setHostIp(String hostIp) {
		this.hostIp = hostIp;
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Task getTask() {
		return task;
	}

	public void setTask(Task task) {
		this.task = task;
	}

	@Override
	public String toString() {
		return "TaskHost [id=" + id + ", hostName=" + hostName + ", hostIp="
				+ hostIp + ", taskId=" + taskId + ", status=" + status
				+ ", task=" + task + "]";
	}
}
