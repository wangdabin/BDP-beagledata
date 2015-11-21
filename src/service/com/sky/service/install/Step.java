package com.sky.service.install;

import java.util.List;

/**
 * 步骤定义
 * @author qiaolong
 */
public interface Step extends Entry{

	/**
	 * 得到组
	 * @return
	 */
	Group getGroup();
	
	/**
	 * 是启动
	 * @return
	 */
	boolean isStart();
	
	/**
	 * 是否最后
	 * @return
	 */
	boolean isEnd();
	
	/**
	 * 是基本配置
	 * @return
	 */
	boolean isBasic();
	
	/**
	 * 是主机
	 * @return
	 */
	boolean isHost();
	
	/**
	 * 是选择
	 * @return
	 */
	boolean isSelect();
	
	/**
	 * 
	 * @return
	 */
	List<Option> options();
	
	/**
	 * 是否有下一步
	 * @return
	 */
	boolean hasNext();
	
	/**
	 * 是否有上一步
	 * @return
	 */
	boolean hasReverse();
	
	/**
	 * 设置组
	 * @param group
	 */
	void setGroup(Group group);
	
	/**
	 * 得到父服务
	 * @return
	 */
	String getParentService();
}
