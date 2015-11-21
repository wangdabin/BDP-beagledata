package com.joe.host.ssh;

import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.joe.core.utils.CoreConfigUtils;

/**
 * 
 * @ClassName: SessionUtils
 * @Description: Session工具类
 * @author WDB
 * @date 2014-12-29 上午9:32:39
 *
 */
public class SessionUtils {
	private static final Logger LOG = Logger
			.getLogger(SessionUtils.class.getName());
	/**
	 * 功能：通过对应的ip,name,password得到对应的Session对象
	 * @author WDB
	 * @throws IOException 
	 */
	public static Session getSessionAndConnect(String ip, String username,
			String password) throws JSchException, IOException {
		JSch jsch = new JSch(); // 创建JSch对象
		Session session = jsch.getSession(username, ip); // 根据用户名，主机ip，端口获取一个Session对象
		LOG.debug("Session created.");
		if (password != null) {
			session.setPassword(password); // 设置密码
		}
		Properties config = new Properties();
		config.put("StrictHostKeyChecking", "no");
		session.setConfig(config); // 为Session对象设置properties
		LOG.debug("Time out : " + (CoreConfigUtils.create().getInt("ssh.timeout")));
		session.setTimeout(CoreConfigUtils.create().getInt("ssh.timeout")); // 设置timeout时间
		session.connect(); // 通过Session建立链接
		LOG.debug("Session connected.");
		return session;
	}
	public static void close(Session session) {
		if (session != null) {
			session.disconnect();
		}
	}
}
