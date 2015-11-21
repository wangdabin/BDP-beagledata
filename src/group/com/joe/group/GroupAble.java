package com.joe.group;

import java.util.List;

import com.joe.group.vo.Group;
import com.joe.group.vo.GroupList;

/**
 * 
 * @author Joe
 *
 */
public interface GroupAble {

	/**
	 * 添加group
	 * @param group
	 */
	void addGroup(Group group);
	
	/**
	 * 
	 * @param groups
	 */
	void addGroups(List<Group> groups);
	
	/**
	 * 
	 * @param group
	 * @return
	 */
	boolean delete(Group group);
	
	/**
	 * 
	 * @return
	 */
	GroupList getGroups();
}
