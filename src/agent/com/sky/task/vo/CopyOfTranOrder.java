package com.sky.task.vo;

import javax.xml.bind.annotation.XmlRootElement;


/**
 * 传输清单
 * @author Dragon Joey
 *
 */
@XmlRootElement
public class CopyOfTranOrder {

    private boolean success;
    private String message;
    private boolean dir;
    private String src;
    private String dest;
    private String clazz;
    private String[] params;
    
    public CopyOfTranOrder(){}
    
    /**
     * @param id
     * @param src
     * @param dest
     */
    public CopyOfTranOrder(boolean dir, String src, String dest) {
	super();
	this.dir = dir;
	this.src = src;
	this.dest = dest;
    }

    public String getClazz() {
		return clazz;
	}

	public void setClazz(String clazz) {
		this.clazz = clazz;
	}
	
	public String[] getParams() {
		return params;
	}

	public void setParams(String[] params) {
		this.params = params;
	}

	/**
     * @return the dir
     */
    public boolean isDir() {
        return dir;
    }

    /**
     * @param dir the dir to set
     */
    public void setDir(boolean dir) {
        this.dir = dir;
    }

    /**
     * @return the success
     */
    public boolean isSuccess() {
        return success;
    }

    /**
     * @param success the success to set
     */
    public void setSuccess(boolean success) {
        this.success = success;
    }

    /**
     * @return the src
     */
    public String getSrc() {
        return src;
    }

    /**
     * @param src the src to set
     */
    public void setSrc(String src) {
        this.src = src;
    }

    /**
     * @return the dest
     */
    public String getDest() {
        return dest;
    }

    /**
     * @param dest the dest to set
     */
    public void setDest(String dest) {
        this.dest = dest;
    }
    
    /**
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * @param message the message to set
     */
    public void setMessage(String message) {
        this.message = message;
    }
}
