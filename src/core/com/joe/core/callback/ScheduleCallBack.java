package com.joe.core.callback;

/**
 * 安装进度回调
 * @author qiaolong
 *
 */
public interface ScheduleCallBack extends CallBack{

	/**
	 * 开始安装
	 */
	void start();
	
	/**
	 * 更新当前进度
	 * @param completion //当前进度
	 */
	void update(String message,double completion);
	
	/**
	 * 安装完成
	 */
	void finish();
	
	/**
	 * 被kill
	 */
	void kill();
}
