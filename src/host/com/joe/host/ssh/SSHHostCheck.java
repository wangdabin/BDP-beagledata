package com.joe.host.ssh;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.joe.host.dao.HostCheck;
import com.joe.host.vo.Host;
/**
 * 
 * @ClassName: SSHHostCheck
 * @Description: 通过ssh的方式检查对应的主机是否存在
 * @author WDB
 * @date 2014-12-29 上午9:33:43
 *
 */
@Repository("sshHostCheck")
public class SSHHostCheck implements HostCheck {

	private static final Logger LOG = Logger.getLogger(SSHHostCheck.class
			.getName());

	@Override
	public boolean checkExist(Host host) {
		LOG.debug("start check Host:" + host.getIp());
		Session session = null;
		try {
			session = SessionUtils.getSessionAndConnect(host.getIp(),host.getRoot(),host.getRootPass());
			return true;
		} 
		catch (IOException e) {
			LOG.debug("config read fail");
			return false;
		}
		catch (JSchException jSchException) {
			LOG.debug("Connected fail to host",jSchException);
			return false;
		} finally {
			SessionUtils.close(session);
		}
	}

}
