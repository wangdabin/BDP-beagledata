package com.sky.config;

import java.io.Serializable;

import com.joe.core.version.Name;
import com.joe.core.version.Named;

/**
 * 
 * 配置
 * @author qiaolong
 * 
 */
public class ConfigVO extends Named implements Name,Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final String TYPE_CONFIG = "config"; //基本类型
	public static final String TYPE_ENVIRONMENT = "environment";//高级配置
	public static final String FAVORITE_BASIC = "basic";//高级配置
	public static final String FAVORITE_ADVANCED = "advanced";//高级配置
	
	private String key;// key
	private String value;// value
	private String defValue; //默认值
	private String description;//描述
	private String file;// 配置所在的文件
	private String type;// 配置类型 配置 | 环境变量
	private String favorite; //收藏夹
	private long createTime;//创建时间
	private long updateTime;//更新时间

	public ConfigVO(){}
	
	public ConfigVO(String name, String version, String key) {
		super(name,version);
		this.key = key;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getFile() {
		return file;
	}

	public void setFile(String file) {
		this.file = file;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getFavorite() {
		return favorite;
	}

	public void setFavorite(String favorite) {
		this.favorite = favorite;
	}

	public String getDefValue() {
		return defValue;
	}

	public void setDefValue(String defValue) {
		this.defValue = defValue;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}

	public long getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(long updateTime) {
		this.updateTime = updateTime;
	}
}
