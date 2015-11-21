package com.sky.task.vo;

/**
 * 
 * @author Joe
 *
 */
public abstract class AbstractOrder implements Order{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private long id;
	private boolean success;
    private String message;
    private long tid;//所属的taskid
    
	/**
	 * @return the tid
	 */
	public long getTid() {
		return tid;
	}
	/**
	 * @param tid the tid to set
	 */
	public void setTid(long tid) {
		this.tid = tid;
	}
	/**
	 * @return the id
	 */
	public long getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(long id) {
		this.id = id;
	}
	/**
	 * @return the success
	 */
	public boolean isSuccess() {
		return success;
	}
	/**
	 * @param success the success to set
	 */
	public void setSuccess(boolean success) {
		this.success = success;
	}
	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}
	/**
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}
}
