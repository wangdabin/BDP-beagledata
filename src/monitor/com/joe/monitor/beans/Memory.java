package com.joe.monitor.beans;

/**
 * disk使用情况监控
 * 
 * @author qiaolong
 *
 */
public class Memory implements MemoryMBean {

	private float use;

	public Memory(float use) {
		this.use = use;
	}
	
	public void setUse(float use) {
		this.use = use;
	}

	@Override
	public float getUse() {
		return use;
	}
}
