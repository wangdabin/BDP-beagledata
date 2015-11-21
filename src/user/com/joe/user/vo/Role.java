package com.joe.user.vo;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * 
 * 角色
 * @author Joe
 *
 */
@XmlRootElement
public class Role extends BaseEntity implements Serializable{
	
    	private static final long serialVersionUID = 320193465212398043L;
	private String name;// 角色名称
	private String description;// 角色描述
	@Override
	public int hashCode() {
	    final int prime = 31;
	    int result = 1;
	    result = prime * result
		    + ((description == null) ? 0 : description.hashCode());
	    result = prime * result + getId();
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
	    Role other = (Role) obj;
	    if (description == null) {
		if (other.description != null)
		    return false;
	    } else if (!description.equals(other.description))
		return false;
	    if (getId() != other.getId())
		return false;
	    if (name == null) {
		if (other.name != null)
		    return false;
	    } else if (!name.equals(other.name))
		return false;
	    return true;
	}
	// 角色用户多对多关系
//	@JsonManagedReference
	private Set<User> users = new HashSet<User>();// 用户
	// 角色权限多对多关系
	private Set<RoleRelation> rolerelation = new HashSet<RoleRelation>();// 权限
	
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	/**
	 * @return the users
	 */
	public Set<User> getUsers() {
		return users;
	}
	/**
	 * @param users the users to set
	 */
	public void setUsers(Set<User> users) {
		this.users = users;
	}
	/**
	 * @param users the users to get
	 */
	public Set<RoleRelation> getRolerelation() {
		return rolerelation;
	}
	/**
	 * @param users the users to set
	 */
	public void setRolerelation(Set<RoleRelation> rolerelation) {
		this.rolerelation = rolerelation;
	}
}