package com.sky.task.vo;

import java.io.Serializable;

/**
 * 
 * @author Joe
 * 
 */
public interface Order extends Serializable,ParseAble{

	
	/**
	 * 获取id
	 * @return
	 */
	public long getId();
	
	/**
	 * 设置id
	 * @param id
	 */
	public void setId(long id);
	/**
	 * 信息
	 * @return
	 */
	public String getMessage();
	
	/**
	 * 信息
	 * @param message
	 */
	public void setMessage(String message);
	
	/**
	 * 是否成功
	 * @return
	 */
	public boolean isSuccess();
	
	/**
	 * 设置是否成功
	 * @param success
	 */
	public void setSuccess(boolean success);
	
	/**
	 * 
	 * @return
	 */
	public long getTid();
	
	/**
	 * 
	 * @param tid
	 */
	public void setTid(long tid);
}
