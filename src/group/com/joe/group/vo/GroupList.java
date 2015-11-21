package com.joe.group.vo;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author Joe
 *
 */
public class GroupList{

	private List<Group> groups = new ArrayList<Group>();

	/**
	 * @return the groups
	 */
	public List<Group> getGroups() {
		return groups;
	}

	/**
	 * 
	 * @param group
	 */
	public void addGroup(Group group){
		groups.add(group);
	}
	
	/**
	 * 如果组中存在则返回group反之返回null
	 * @param group
	 * @return 返回group 或者 null
	 */
	public boolean delete(Group group){
		return groups.remove(group);
	}
	
	/**
	 * 添加所有的组
	 * @param groups
	 */
	public void addGroups(List<Group> groups){
		this.groups.addAll(groups);
	}
}
