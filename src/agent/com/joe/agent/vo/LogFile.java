package com.joe.agent.vo;

import java.io.File;
import java.io.InputStream;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class LogFile {
	
	private long id;
	private String name;
	private String fileName;
	private String url;
	//日志三种形式
	private File logFile;
	private InputStream ism;
	private String data ;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public InputStream getIsm() {
		return ism;
	}

	public void setIsm(InputStream ism) {
		this.ism = ism;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public File getLogFile() {
		return logFile;
	}

	public void setLogFile(File logFile) {
		this.logFile = logFile;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

}
