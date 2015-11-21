package com.joe.host.init;

import java.util.Map;

import org.apache.log4j.Logger;

import com.joe.host.ssh.JSchSSH;

/**
 * 
 * @ClassName: SSHOrder
 * @Description: 执行ssh命令的任务类
 * @author WDB
 * @date 2014-12-29 上午9:27:29
 *
 */
public class SSHOrder implements Order {
    public static final Logger LOG = Logger.getLogger(SSHOrder.class);

    private Command[] commands;// 对应命令的集合,一个可以执行多条linux命令
    private Map<String, String> loginInfo;// 保存链接ssh需要的用户名,密码,Ip,端口
    private JSchSSH ssh;

    public SSHOrder(Map<String, String> loginInfo, Command... commands) {
	this.loginInfo = loginInfo;
	this.commands = commands;
    }

    @Override
    public void init() {
	try {
	    ssh = new JSchSSH(loginInfo);
	} catch (Exception e) {
	    throw new RuntimeException("shell初始化异常", e);
	}
    }

    @Override
    public void execute() throws Exception {
	init();
	try {
	    ssh.executeAndCheckResult(commands);
	} finally {
	    destory();
	}
    }

    @Override
    public void destory() throws Exception {
	if (ssh != null) {
	    LOG.debug("释放shell资源!");
	    ssh.closeConnection();
	}
    }
}
