package com.joe.host.init;

import java.io.File;
import java.util.Map;

import org.apache.log4j.Logger;

import com.joe.host.ssh.JSchSFTP;
import com.joe.host.ssh.LoginConstants;

/**
 * 
 * @ClassName: SFTPOrder
 * @Description: 通过SFTP执行文件传输的任务
 * @author WDB
 * @date 2014-12-27 下午6:38:05
 * 
 */
public class SFTPOrder implements Order {
    public static final Logger LOG = Logger.getLogger(SFTPOrder.class);
    private String src;// 源文件路径
    private String dst;// 目的文件路径
    private JSchSFTP sftp;
    private Map<String, String> loginInfo;// 保存链接sftp需要的用户名,密码,Ip,端口

    public SFTPOrder(Map<String, String> loginInfo, String src, String dst) {
	this.loginInfo = loginInfo;
	this.src = src;
	this.dst = dst;
    }

    @Override
    public void init() {
	LOG.debug("初始化文件传输资源...");
	try {
	    sftp = new JSchSFTP(loginInfo);
	} catch (Exception e) {
	    throw new RuntimeException("sftp初始化异常", e);
	}
    }

    @Override
    public void execute() throws Exception {
	init();
	try {
	    File srcDir = new File(src);
	    for (File file : srcDir.listFiles()) {
		if (!file.isHidden()) {// 防止读取到隐藏的文件
		    String src = file.getAbsolutePath();
		    LOG.debug("发送" + src + "到 " + loginInfo.get(LoginConstants.IP) + ":" + dst);
		    sftp.getChannel().put(src, dst);
		}
	    }
	    LOG.debug(loginInfo.get(LoginConstants.IP) + "...主机接收基本组件成功!");
	} catch (Exception e) {
	    throw new RuntimeException("发送到 "+loginInfo.get(LoginConstants.IP) + "文件发送失败!", e);
	} finally {
	    destory();
	}
    }

    @Override
    public void destory() throws Exception {
	if (sftp != null) {
	    LOG.debug("释放sftp资源!");
	    sftp.closeChannel();
	}
    }
}
