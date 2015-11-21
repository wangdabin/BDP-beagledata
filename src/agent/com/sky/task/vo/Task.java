package com.sky.task.vo;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

/**
 * 
 * @author Joe
 * 
 */
@XmlRootElement(name = "task")
@XmlAccessorType(XmlAccessType.FIELD)
@JsonIgnoreProperties(ignoreUnknown=true,value={"running"})
public class Task implements ParseAble, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static final int WAITING = 1;
	public static final int RUNNING = 2;
	public static final int FINISH = 3;
	public static final int ERROR = 4;
	public static final int KILLED = 5;

	private long id = -1;
	private String name;
	private int status;
	private long cTime; // 创建时间
	private long fTime; // 完成时间
	private String message;
	private String type;
	private double completion;
	private String hostname;

	private Set<TranOrder> orders = new HashSet<TranOrder>();
	/**
	 * @return the status
	 */
	public int getStatus() {
		return status;
	}

	/**
	 * @param status
	 *            the status to set
	 */
	public void setStatus(int status) {
		this.status = status;
	}

	/**
	 * @return the id
	 */
	public long getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the cTime
	 */
	public long getcTime() {
		return cTime;
	}

	/**
	 * @param cTime
	 *            the cTime to set
	 */
	public void setcTime(long cTime) {
		this.cTime = cTime;
	}

	/**
	 * @return the fTime
	 */
	public long getfTime() {
		return fTime;
	}

	/**
	 * 结束时间
	 * 
	 * @param fTime
	 *            the fTime to set
	 */
	public void setfTime(long fTime) {
		this.fTime = fTime;
	}

	public String getMessage() {
		return this.message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type
	 *            the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	public double getCompletion() {
		return completion;
	}

	public void setCompletion(double completion) {
		this.completion = completion;
	}

	public Set<TranOrder> getOrders() {
		return orders;
	}

	public void setOrders(Set<TranOrder> orders) {
		this.orders = orders;
	}

	/**
	 * 
	 * @param order
	 */
	public void addOrder(TranOrder order){
		this.orders.add(order);
	}

	public String getHostname() {
		return hostname;
	}

	public void setHostname(String hostname) {
		this.hostname = hostname;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
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
		Task other = (Task) obj;
		if (id != other.id)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	public boolean isRunning() {
		return status == RUNNING;
	}
}
