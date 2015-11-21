package com.joe.user.vo;

import java.util.HashSet;
import java.util.Set;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * 
 * 权限（对资源进行包装）
 * @author Joe
 *
 */
@XmlRootElement
public class Permission {
	
	private String name;// 权限名称
	private String url;// http访问url 主键 key 
	private String description;// 描述
	// 权限和操作时多对一的关系，一种操作可以形成多种权限（搭配不同的url）
	// 暂时来说操作目前有GET、POST、PUT、DELETE四种操作，不排除未来会有其他操作
	private Set<Operation> operations = new HashSet<Operation>();// 此URL地址含有的操作
	private long createDate; //创建时间
	private long lastUpdateDate;//最后更新时间

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
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}
	/**
	 * @param url the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
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
	 * @return the operations
	 */
	public Set<Operation> getOperations() {
		return operations;
	}
	/**
	 * @param operations the operations to set
	 */
	public void setOperations(Set<Operation> operations) {
		this.operations = operations;
	}
	
	/**
	 * 
	 * @param operation
	 */
	public void addOperation(Operation operation){
		this.operations.add(operation);
	}
	/**
	 * @return the createDate
	 */
	public long getCreateDate() {
		return createDate;
	}
	/**
	 * @param createDate the createDate to set
	 */
	public void setCreateDate(long createDate) {
		this.createDate = createDate;
	}
	/**
	 * @return the lastUpdateDate
	 */
	public long getLastUpdateDate() {
		return lastUpdateDate;
	}
	/**
	 * @param lastUpdateDate the lastUpdateDate to set
	 */
	public void setLastUpdateDate(long lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}
}