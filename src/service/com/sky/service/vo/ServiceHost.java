package com.sky.service.vo;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang.StringUtils;

import com.sky.task.vo.Task;

/**
 * 服務主機對應關係
 * 
 * @author why
 * 
 */
@XmlRootElement
public class ServiceHost implements Serializable {

	private static final long serialVersionUID = 910800538966973099L;
	public static final int STATUS_ACTIVE = 1;
	private int serviceId;

	private String name;

	private int status;
	/*
	 * 主机Ip
	 */
	private String hostIp;
	/*
	 * 角色
	 */
	private String role;
	/*
	 * 创建时间
	 */
	private long createDate;
	/*
	 * 最后修改时间
	 */
	private long lastUpdateDate;

	private List<Task> tasks;

	public enum ServiceRole {
		hdfs_namenode, hdfs_datanode, mr_jobtaracker, mr_tasktracker, hmaster, hregionserver, hdfs_jounralnode, storm_nimbus, storm_supervisor;

		private String value;

		public String getValue() {
			return this.value;
		}
	};

	public int getServiceId() {
		return serviceId;
	}

	public void setServiceId(int serviceId) {
		this.serviceId = serviceId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getHostIp() {
		if(StringUtils.isBlank(hostIp)){
			return name;
		}else{
			return hostIp;
		}
	}

	public void setHostIp(String hostIp) {
		this.hostIp = hostIp;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public long getCreateDate() {
		return createDate;
	}

	public void setCreateDate(long createDate) {
		this.createDate = createDate;
	}

	public long getLastUpdateDate() {
		return lastUpdateDate;
	}

	public void setLastUpdateDate(long lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

	public List<Task> getTasks() {
		return tasks;
	}

	public void setTasks(List<Task> tasks) {
		this.tasks = tasks;
	}

	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((hostIp == null) ? 0 : hostIp.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ServiceHost other = (ServiceHost) obj;
		if (hostIp == null) {
			if (other.hostIp != null)
				return false;
		} else if (!hostIp.equals(other.hostIp))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	public void finishTask(Task task, int status) {
		for (Task tasktmp : tasks) {
			if (tasktmp.equals(task)) {
				tasktmp.setStatus(status);
				break;
			}
		}
	}

	public void finishAllTask(int status) {
		for (Task tasktmp : tasks) {
			tasktmp.setStatus(status);
		}
	}

	@Override
	public String toString() {
		return "ServiceHost [serviceId=" + serviceId + ", name=" + name
				+ ", status=" + status + ", hostIp=" + hostIp + ", role="
				+ role + ", createDate=" + createDate + ", lastUpdateDate="
				+ lastUpdateDate + ", tasks=" + tasks + "]";
	}

}
