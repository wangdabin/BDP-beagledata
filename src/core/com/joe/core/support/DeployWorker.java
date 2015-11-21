package com.joe.core.support;

import java.util.List;

import com.joe.host.vo.Host;
import com.sky.config.ConfigAble;


/**
 * 部署工人，负责把安装包部署到指定的主机的指定位置
 * @author qiaolong
 *
 */
public interface DeployWorker extends ConfigAble{

	/**
	 * 发布被发布者
	 * @param deployed
	 */
	void deploy(Deployed deployed);
	
	/**
	 * @Title: sendFileToHost
	 * @Description: 发布对应的文件到指定主机的指定目录下
	 * @param  destPath
	 * @param  fileName
	 * @param  isOverride 是否要覆盖原有的文件
	 * @throws
	 */
	public void sendFileToHost(String destPath,String fileName,List<Host> hosts,boolean isOverride);
}