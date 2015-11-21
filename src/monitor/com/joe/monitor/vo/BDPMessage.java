package com.joe.monitor.vo;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;
/**
 * @ClassName: BDPMessage
 * @Description: BDP监控消息
 * @author WDB
 * @date 2015-5-4 上午11:04:31
 */
@XmlRootElement
public class BDPMessage implements Serializable{
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String name;
	private String type;
	private String level;
	private String description;
	private Boolean readed;
	private Long createTime;
	private Long readTime;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getLevel() {
		return level;
	}
	public void setLevel(String level) {
		this.level = level;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Boolean getReaded() {
		return readed;
	}
	public void setReaded(Boolean readed) {
		this.readed = readed;
	}
	public Long getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}
	public Long getReadTime() {
		return readTime;
	}
	public void setReadTime(Long readTime) {
		this.readTime = readTime;
	}
}
