package com.sky.service.install;

import java.util.List;

/**
 * 步骤组
 * @author qiaolong
 *
 */
public interface Group extends Entry{

	/**
	 * 所有的组
	 * @return
	 */
	List<Group> getGroups();
	
	/**
	 * 所有的步骤
	 * @return
	 */
	List<Step> getSteps();
	
	/**
	 * 
	 * @param parent
	 */
	void setParent(Group parent);
}
