package com.joe.system.vo;

import javax.persistence.Entity;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jackson.annotate.JsonAutoDetect;

import com.joe.core.vo.AbstractEntity;

/**
 * 服务器
 * @author Joe
 *
 */
@XmlRootElement
@JsonAutoDetect
@Entity
@XmlAccessorType(XmlAccessType.FIELD)
public class SysDictionary extends AbstractEntity{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String dictName;
	private String dictValue;
	private String desc;
	public String getDictName() {
		return dictName;
	}
	public void setDictName(String dictName) {
		this.dictName = dictName;
	}
	public String getDictValue() {
		return dictValue;
	}
	public void setDictValue(String dictValue) {
		this.dictValue = dictValue;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
}