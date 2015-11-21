package com.joe.host.init;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;

import org.apache.commons.configuration.Configuration;
import org.apache.log4j.Logger;

import com.joe.agent.service.AgentService;
import com.joe.core.utils.CoreConfigUtils;
import com.joe.host.ssh.LoginConstants;
import com.joe.host.ssh.Src2Dest;
import com.joe.host.vo.Host;
public class BasicComponentInstaller {
    public static final Logger LOG = Logger
	    .getLogger(BasicComponentInstaller.class);
    private Host host;// 对应的主机
    private List<Order> orders;// 初始化要执行的任务集合,如发送组件,解压,初始化,启动
    private AgentService agentService;// 对应修改Agent的状态信息
    /**
     * 当执行对应的安装操作时,需要提供对应的agentService去修改对应的agent的状态
     */
    public BasicComponentInstaller(Host host,AgentService agentService) {
	this.agentService = agentService;
	this.host = host;
    }
    /**
     * 当执行对应的回滚操作的时候,无需提供agentService
     */
    public BasicComponentInstaller(Host host) {
   	this.host = host;
    }
    public void initOrders() throws IOException {
	LOG.debug(host.getIp() + "...初始化基本组件安装任务....");
	Map<String, String> loginInfo = getLoginInfo(host);
	// 初始化orders的过程
	orders = new ArrayList<Order>();
	// 组件在BDP中的目录
	String src = Src2Dest.BASIC.getSrc();
	// 组件放置在主机上的目录
	String desc = Src2Dest.BASIC.getDesc();
	LOG.debug(host.getIp() + "...基本组件在BDP中的目录:" + src + "...组件发送到远程主机上的目录:"
		+ desc);
	Configuration conf = CoreConfigUtils.create();
	// agent tar包的名称
	String agentTarName = conf.getString("bigdata.agent");
	// jdk tar包的名称
	String jdkTarName = conf.getString("bigdata.java");
	// 发送基本组件到host
	SFTPOrder send = new SFTPOrder(loginInfo, src, desc);
	// bdp服务所处的主机IP
	String bdpIp = conf.getString("bdp.service.ip");

	orders.add(send);
	// 解压,jdk和agent,修改jdk的目录名
	Command jdkTar = new Command("tarc", desc + jdkTarName, desc);
	Command agentTar = new Command("tarc", desc + agentTarName, desc);
	Command mvJdk = new Command("mv", desc + "jdk*/", desc + "java/");
	SSHOrder extract = new SSHOrder(loginInfo, jdkTar, agentTar, mvJdk);
	orders.add(extract);
	// 初始化
	Command export = new Command("export", "JAVA_JOME", desc + "java/jre/");
	Command agent_init = new Command("bigdata.agent.init", desc
		+ "bigdata-agent/", desc + "java/jre", bdpIp, host.getIp(),host.getName());
	SSHOrder init = new SSHOrder(loginInfo, export, agent_init);
	orders.add(init);
	
//	// 生成当前主机的钥匙
//	Command agent_genkey = new Command("genkey",desc + "bigdata-agent/");
//	SSHOrder genkey = new SSHOrder(loginInfo, agent_genkey);
//	orders.add(genkey);
	// 启动
	Command agent_start = new Command("bigdata.agent.start", desc
		+ "bigdata-agent/");
	SSHOrder start = new SSHOrder(loginInfo, agent_start);
	orders.add(start);
	LOG.debug(host.getIp() + "...初始化基本组件安装任务成功!");
	agentService.updateInstallProgress(host.getIp(),Status.INIT);// 更新进度
    }

    public Map<String, String> getLoginInfo(Host host) {
	Map<String, String> loginInfo = new HashMap<String, String>();
	// 设置主机ip,用户名,密码
	loginInfo.put(LoginConstants.IP, host.getIp());
	loginInfo.put(LoginConstants.USERNAME, host.getRoot());
	loginInfo.put(LoginConstants.PASSWORD, host.getRootPass());
	return loginInfo;
    }

    public void install() {
	Executors.newSingleThreadExecutor().execute(new Runnable() {
	    public void run() {
		try {
		    initOrders();
		    LOG.debug(host.getIp() + "...开始执行基本组件安装...");
		    for (int i = 0;i<orders.size();i++) {
			double status = Status.START / orders.size() * (i + 1);
			agentService.updateInstallProgress(host.getIp(),status);// 更新进度
			orders.get(i).execute();
			LOG.debug(host.getIp() + "...基本组件安装进度:"
				+ status + "%");
		    }
		    //设置agent的启动时间
		    agentService.addStartTime(host.getIp(),System.currentTimeMillis());
		    LOG.debug(host.getIp() + "...基本组件安装完成!");
		} catch (Exception e) {
		    agentService.updateInstallProgress(host.getIp(),Status.FAIL);
		    LOG.error(host.getIp() + "...基本组件安装失败!", e);
		    //TODO 在安装过程中由于安装导致的错误(非服务不能启动),将对应的主机安装的目录,agent,java删除
		    rollbackOrders();
		}
	    }
	});
    }

    /**
     * @throws IOException
     * @Title: rollbackOrders
     * @Description: 当安装过程中安装失败,执行对应的回滚操作
     */
    public void rollbackOrders(){
	try {
	    LOG.debug(host.getIp() + "...初始化回滚任务....");
	    Map<String, String> loginInfo = getLoginInfo(host);
	    // 回滚orders的过程
	    orders = new ArrayList<Order>();
	    // 组件放置在主机上的目录
	    String desc = Src2Dest.BASIC.getDesc();
	    Configuration conf = CoreConfigUtils.create();
	    // agent tar包的名称
	    String agentTarName = conf.getString("bigdata.agent");
	    // jdk tar包的名称
	    String jdkTarName = conf.getString("bigdata.java");
	    // 删除已经存在的目录,根据任务进度的不同,可能要删除不同的目录,这里不进行判断
	    Command rmJdk = new Command("rmf", desc + "java/");
	    Command rmJava = new Command("rmf", desc + "jdk*/");
	    Command rmJdkTar = new Command("rmf", desc + jdkTarName);
	    Command rmAgentTar = new Command("rmf", desc + agentTarName);
	    Command rmAgent = new Command("rmf",desc + "bigdata-agent/");
	    SSHOrder rollback = new SSHOrder(loginInfo, rmJdk, rmJava,
		    rmJdkTar, rmAgentTar,rmAgent);
	    orders.add(rollback);
	    LOG.debug(host.getIp() + "...初始化回滚任务成功!");
	    LOG.debug(host.getIp() + "...开始执行执行回滚操作...");
	    for (Order order : orders) {
		order.execute();
	    }
	    LOG.debug(host.getIp() + "...回滚操作执行完成!");
	} catch (Exception e) {// 回滚失败
	    LOG.error(host.getIp() + "...回滚操作执行失败!");
	    throw new RuntimeException(e);
	}
    }

}
