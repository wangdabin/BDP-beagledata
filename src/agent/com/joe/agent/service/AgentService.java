package com.joe.agent.service;

import com.joe.agent.vo.Agent;
import com.joe.core.service.EntityService;

public interface AgentService extends EntityService<Agent>{

	//更新Agent运行状态
	void updateStatus(String ip, String status,String comment);

	//根据ip获取代理机器的信息
	Agent getAgentByIp(String ip);
	
	//更新安装进度
	void updateInstallProgress(String ip,double progress);
	
	//添加初始启动时间
	void addStartTime(String ip,long startTime);
}
