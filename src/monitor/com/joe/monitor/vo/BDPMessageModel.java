package com.joe.monitor.vo;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @ClassName: BDPMessageModel
 * @Description: 封装对应的message条件查询
 * @author WDB
 * @date 2015-5-4 下午2:16:15
 */
@XmlRootElement
public class BDPMessageModel {
	private String type;
	private String level;
	private Boolean readed;
	private Long createTimeStart;
	private Long createTimeEnd;
	private Long readTimeStart;
	private Long readTimeEnd;
	public Long getCreateTimeEnd() {
		return createTimeEnd;
	}
	public Long getCreateTimeStart() {
		return createTimeStart;
	}
	public String getLevel() {
		return level;
	}
	public Boolean getReaded() {
		return readed;
	}
	public Long getReadTimeEnd() {
		return readTimeEnd;
	}
	public Long getReadTimeStart() {
		return readTimeStart;
	}
	public String getType() {
		return type;
	}
	public void setCreateTimeEnd(Long createTimeEnd) {
		this.createTimeEnd = createTimeEnd;
	}
	public void setCreateTimeStart(Long createTimeStart) {
		this.createTimeStart = createTimeStart;
	}
	public void setLevel(String level) {
		this.level = level;
	}
	public void setReaded(Boolean readed) {
		this.readed = readed;
	}
	public void setReadTimeEnd(Long readTimeEnd) {
		this.readTimeEnd = readTimeEnd;
	}
	public void setReadTimeStart(Long readTimeStart) {
		this.readTimeStart = readTimeStart;
	}
	public void setType(String type) {
		this.type = type;
	}
	
}
