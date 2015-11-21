package com.joe.user.vo;

import javax.ws.rs.HttpMethod;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 操作
 * @author Joe
 *
 */
@XmlRootElement
public class Operation implements Comparable<Operation>{
	
	private String name; //操作名称
	private String description; //操作描述
	private long createDate; //创建时间
	private long lastUpdateDate;//最后更新时间
	
	public static final Operation GET = new Operation();
	public static final Operation POST = new Operation();
	public static final Operation PUT = new Operation();
	public static final Operation DELETE = new Operation();

	static{
		long createData = System.currentTimeMillis();
		GET.setCreateDate(createData);
		GET.setLastUpdateDate(createData);
		GET.setDescription(HttpMethod.GET);
		GET.setName(HttpMethod.GET);
		
		POST.setCreateDate(createData);
		POST.setLastUpdateDate(createData);
		POST.setDescription(HttpMethod.POST);
		POST.setName(HttpMethod.POST);
		
		PUT.setCreateDate(createData);
		PUT.setLastUpdateDate(createData);
		PUT.setDescription(HttpMethod.PUT);
		PUT.setName(HttpMethod.PUT);
		
		DELETE.setCreateDate(createData);
		DELETE.setLastUpdateDate(createData);
		DELETE.setDescription(HttpMethod.DELETE);
		DELETE.setName(HttpMethod.DELETE);
	}
	
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

	/* (non-Javadoc)
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(Operation obj) {
		if(name == null){
			return -1;
		}
		if(obj.name == null){
			return 1;
		}
		return name.compareTo(obj.name);
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Operation other = (Operation) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
}