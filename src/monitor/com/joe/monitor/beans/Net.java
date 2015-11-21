package com.joe.monitor.beans;

/**
 * net使用情况监控
 * 
 * @author qiaolong
 *
 */
public class Net implements NetMBean {

	private float use;

	public Net(float use) {
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
