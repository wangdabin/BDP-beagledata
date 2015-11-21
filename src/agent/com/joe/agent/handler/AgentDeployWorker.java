package com.joe.agent.handler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.configuration.Configuration;

import com.joe.agent.callback.impl.WaitTaskFinish;
import com.joe.agent.client.AgentTaskClient;
import com.joe.agent.manager.TaskManager;
import com.joe.core.support.AbstractDeployWorder;
import com.joe.core.support.Deployed;
import com.joe.core.utils.UrlUtil;
import com.joe.download.web.ConfigFileDownload;
import com.joe.host.utils.HostUtils;
import com.joe.host.vo.Host;
import com.sky.service.utils.Constants;
import com.sky.task.vo.Task;
import com.sky.task.vo.TranOrder;

/**
 * 
 * 通过Agent发送包
 * @author qiaolong
 *
 */
public class AgentDeployWorker extends AbstractDeployWorder{

	private TaskManager taskManager;
	private AgentTaskClient taskClient;
	
	public AgentDeployWorker(Configuration conf){
		super(conf);
		this.taskManager = TaskManager.getManager(conf);
		this.taskClient = new AgentTaskClient(conf);
	}
	
	@Override
	public void deploy(Deployed deployed) {
		List<Host> hosts = deployed.getAllHosts();
		if(hosts != null){
			List<WaitTaskFinish> callBacks = new ArrayList<WaitTaskFinish>();
			for(Host host : hosts){
				callBacks.add(this.deploy(host, deployed.getPair(),deployed.getName(),deployed.getVersion()));
			}
			try {
				for(WaitTaskFinish callBack : callBacks){
					callBack.waitFinish();
				}
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			}
		}
	}
	
	/**
	 * 发布到目标主机，目标目录
	 * @param host
	 * @param source
	 * @param dest
	 */
	public WaitTaskFinish deploy(Host host,List<Deployed.Pair> pairs,String name,String version){
		Task task = new Task();
		task.setHostname(HostUtils.buildKey(host));
		task.setName("deploy-to-" + task.getHostname());
		for(int i = 0;i < pairs.size();i ++){
			Deployed.Pair pair = pairs.get(i);
			TranOrder order = new TranOrder();
			order.setOrder(i);
			order.setSrc(pair.getSource());
			order.setDest(pair.getTarget());
			order.setType(pair.getType());
			order.setServiceName(name);
			order.setVersion(version);
			task.addOrder(order);
		}
		taskManager.beforeSubmit(task); //提交任务之前要做的事情，如保存这个任务等
		WaitTaskFinish callBack = new WaitTaskFinish(taskManager,task);
		taskManager.registerCallBack(callBack);
		taskClient.commitTask(HostUtils.buildKey(host), task);
		taskManager.afterSubmit(task); //提交任务之后需要做的事情，如等待任务执行完成等
		return callBack;
	}
	
	public void sendFileToHost(String destPath,String fileName,List<Host> hosts,boolean isOverride) {
	    try {
		//如果不覆盖,并且对应的文件已经发送,那么不进行发送
		if(!isOverride && ConfigFileDownload.isSent(fileName)) {
		    return;
		}
	    	ConfigFileDownload.addCommonFile(fileName);
		List<WaitTaskFinish> callBacks = new ArrayList<WaitTaskFinish>();
		for(Host host : hosts){
			Task task = new Task();
			task.setHostname(HostUtils.buildKey(host));
			task.setName("sendFile-to-" + HostUtils.buildKey(host));
			TranOrder order = new TranOrder();
			String configDownload = getConf().getString(Constants.DOWNLOAD_CONFIG_CONF);
			UrlUtil url = new UrlUtil(configDownload);
			url.putParam("fileName", fileName);
			order.setOrder(0);
			order.setSrc(url.getURL());
			order.setDest(destPath);
			order.setCoreFiles(new String[]{fileName});
			order.setType(TranOrder.TYPE_HTTP);
			task.addOrder(order);
			taskManager.beforeSubmit(task); //提交任务之前要做的事情，如保存这个任务等
			WaitTaskFinish callBack = new WaitTaskFinish(taskManager,task);
			taskManager.registerCallBack(callBack);
			callBacks.add(callBack);
			taskClient.commitTask(HostUtils.buildKey(host), task);
			taskManager.afterSubmit(task); //提交任务之后需要做的事情，如等待任务执行完成等
		}
			for(WaitTaskFinish callBack : callBacks){
				callBack.waitFinish();
			}
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
		    throw new RuntimeException(e);
		}
	}
	
	
}
