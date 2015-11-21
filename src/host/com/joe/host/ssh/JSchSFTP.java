package com.joe.host.ssh;

import java.io.IOException;
import java.util.Map;

import org.apache.log4j.Logger;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

//SFTP工具类
public class JSchSFTP {
	private Session session = null;
	private Channel channel = null;
	private Map<String, String> loginInfo;

	private static final Logger LOG = Logger.getLogger(JSchSFTP.class
			.getName());

	public JSchSFTP(Map<String, String> loginInfo){
		this.loginInfo = loginInfo;
	}
	public ChannelSftp getChannel()
			throws JSchException, IOException {
		String ip = loginInfo.get(LoginConstants.IP);
		String username = loginInfo.get(LoginConstants.USERNAME);
		String password = loginInfo.get(LoginConstants.PASSWORD);
		session = SessionUtils.getSessionAndConnect(ip, username, password);
		LOG.debug("Opening Channel.");
		channel = session.openChannel("sftp"); // 打开SFTP通道
		channel.connect(); // 建立SFTP通道的连接
		LOG.debug("Connected successfully to ip = " + ip + ",as username = "
				+ username + ", returning: " + channel);
		return (ChannelSftp) channel;
	}
	public void closeChannel() throws Exception {
		if (channel != null) {
			channel.disconnect();
		}
		if (session != null) {
			session.disconnect();
		}
	}
}