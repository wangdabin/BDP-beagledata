package com.joe.user.vo;


/**
*
* @description 实体基类
*
* @function
*
* @author ZhouZH
*
*/
public class BaseEntity {
	
	private int id; //唯一属性
	private long createDate; //创建时间
	private long lastUpdateDate;//最后更新时间
	
	public BaseEntity()
	{}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public long getCreateDate() {
		return createDate;
	}

	public void setCreateDate(long createDate) {
		this.createDate = createDate;
	}

	public long getLastUpdateDate() {
		return lastUpdateDate;
	}

	public void setLastUpdateDate(long lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}
	
}
