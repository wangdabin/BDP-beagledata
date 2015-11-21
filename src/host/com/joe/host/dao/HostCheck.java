package com.joe.host.dao;

import com.joe.host.vo.Host;

/**
 * 检查host
 * @author qiaolong
 *
 */
public interface HostCheck {
	
	/**
	 * 根据用户名密码，检查主机是否存在
	 * 如果存在，则把hostname设置到host上。
	 * @param host
	 * @return
	 */
	boolean checkExist(Host host);
}
