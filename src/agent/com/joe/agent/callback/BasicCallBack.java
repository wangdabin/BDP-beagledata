package com.joe.agent.callback;

import java.io.IOException;
import com.joe.agent.annotation.CallBack;
import com.joe.agent.rest.AgentMonitorClient;
import com.joe.core.vo.ReCode;
import com.sky.config.Configed;

/**
 * 
 * @author qiaolong
 *
 */
@CallBack(name = "host",type="host")
public class BasicCallBack extends Configed implements AgentCallBack{

	@Override
	public void doStart(String hostname, String ip) {
		try {
			AgentMonitorClient amc = new AgentMonitorClient(hostname, getConf().getInt("sky.agent.port"));
			String[] monitor = getConf().getStringArray("monitor");
			for (String m : monitor) {
				ReCode r = amc.addMonitor("host", m, getConf().getString("monitor_"+m));
				System.out.println("Add monitor "+ m + ", result is " +r.getMsg());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void doPing(String hostname, String ip) {
		
	}

	@Override
	public void doDelete(String hostname, String ip) {
		
	}

	@Override
	public void doStop(String hostname, String ip) {
		
	}

}
