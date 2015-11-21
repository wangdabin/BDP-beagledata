package com.joe.monitor;

import java.util.List;

/**
 * 
 * 
 * @author qiaolong
 *
 */
public interface StatusList {

	/**
	 * 
	 * @return
	 */
	List<Status> getStatuses();
	
	/**
	 * 
	 * @param status
	 */
	void addStatus(Status status);
}
