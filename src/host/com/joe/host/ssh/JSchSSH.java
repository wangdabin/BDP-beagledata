package com.joe.host.ssh;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.oro.text.regex.MalformedPatternException;

import com.jcraft.jsch.ChannelShell;
import com.jcraft.jsch.Session;
import com.joe.host.init.BasicComponentInstaller;
import com.joe.host.init.Command;

import expect4j.Closure;
import expect4j.Expect4j;
import expect4j.ExpectState;
import expect4j.matches.Match;
import expect4j.matches.RegExpMatch;
/**
 * 
 * @ClassName: JSchSSH
 * @Description: 执行ssh的工具类
 * @author WDB
 * @date 2014-12-28 下午9:25:36
 */
public class JSchSSH {
    public static final Logger LOG = Logger
	    .getLogger(BasicComponentInstaller.class);
    public static final String ENTER_CHARACTER = "\r";
    public static final String CTRL_C = "\003";
    //正则匹配，用于处理服务器返回的结果
    public static final String CENTOS_PROMPT = "(^\\[.*@.*\\][#,$] $|(?=.*)\\[.*@.*\\][#,$] $)";
    public static final String CONTINUE_PROMPT = "(^> $|\n> $)";
    private static String[] linuxPromptRegEx = new String[] { CENTOS_PROMPT, CONTINUE_PROMPT };
    private Session session = null;
    private ChannelShell channel = null;
    private Expect4j expect = null;

    boolean hasExecuted = false; // 用于判断是否有命令发送去执行，只有执行的返回才会加入outbuf
    boolean readyToInput = false; // 标记shell命令输入就绪，可以输入
    
    private StringBuilder outbuf = new StringBuilder();
    private Closure closure = new Closure() {
	public void run(ExpectState expectState) throws Exception {
	    if (hasExecuted)
		outbuf.append(expectState.getBuffer());
	}
    };
    // 所有要expect的pattern都添加到这单个listPattern中，默认有linux输出提示符号
    private List<Match> matchPattern = new ArrayList<Match>();
    private String username;
    private String password;
    private String ip;
    private boolean isInit = false;

    private void init() throws IOException, Exception {
	addBindMatch(linuxPromptRegEx, closure);
	session = SessionUtils.getSessionAndConnect(ip, username, password);
	channel = (ChannelShell) session.openChannel("shell");
	expect = new Expect4j(channel.getInputStream(),
		channel.getOutputStream());
	// 取消命令执行的超时机制
	expect.setDefaultTimeout(Expect4j.TIMEOUT_FOREVER);
	channel.connect();
    }

    public JSchSSH(String ip, String username, String password)
	    throws Exception {
	this.ip = ip;
	this.username = username;
	this.password = password;
    }

    public JSchSSH(Map<String, String> loginInfo) throws Exception {
	this.ip = loginInfo.get(LoginConstants.IP);
	this.username = loginInfo.get(LoginConstants.USERNAME);
	this.password = loginInfo.get(LoginConstants.PASSWORD);
    }

    public JSchSSH(String ip, String username, String password, int port) {
	this.ip = ip;
	this.username = username;
	this.password = password;
    }

    /**
     * 将promptRegEx对应的处理closure添加到listPattern中并返回
     */
    private void addBindMatch(String[] promptRegEx, Closure closure) {
	for (String regexEle : promptRegEx) {
	    addBindMatch(regexEle, closure);
	}
    }

    private void addBindMatch(String promptRegEx, Closure closure) {
	try {
	    matchPattern.add(new RegExpMatch(promptRegEx, closure));
	} catch (MalformedPatternException e) {
	    e.printStackTrace();
	}
    }
    /**
     * 
     * 功能：单线程调用 完成的必要条件：执行完成,但并无校验结果是否正确 异常处理：抛出
     * 
     * @author WDB
     * @throws Exception
     */
    public String execute(String cmd) throws Exception {
	if (!isInit) {
	    init();
	    isInit = true;
	}
	cmd = cmd == null ? "" : cmd;

	outbuf.delete(0, outbuf.length()); // 清空输出
	hasExecuted = false;//用于判断是否有命令发送去执行，只有执行的返回才会加入outbuf
	boolean executeEnd = false;//默认命令没有被执行

	while (!executeEnd) {//判断命令是否执行完毕
	    if (!readyToInput) {// 标记shell命令输入就绪，也就是根据对应系统返回的登录的字符串信息
		//根据对应登录linux系统的提示信息判断是否已经进行命令行
		int ret = expect.expect(matchPattern);
		if (ret < 0) {//如何与系统的命令行提示不匹配
		    LOG.error("ret:" + ret);
		    throw new RuntimeException(outbuf.toString());
		}
		boolean isPrompt = true;
		if (isPrompt) {
		    readyToInput = true;
		    // 把outbuf最后的输入提示去掉
		    if (hasExecuted)
			outbuf.delete(outbuf.length(),
				outbuf.length());
		}
	    } else {
		if (hasExecuted) {
		    executeEnd = true;//标记执行完毕
		} else {
		    expect.send(cmd);//向对应的远程服务端发送命令
		    expect.send(ENTER_CHARACTER);//一条命令结束符
		    readyToInput = false; // 一次readyToInput发送一次命令
		    hasExecuted = true;//标记已经执行
		}
	    }
	}
	return outbuf.toString();//将对应命令执行的结果返回
    }
    /**
     * 功能：一次执行多个命令
     * 
     * @author WDB
     * @throws Exception
     */
    public void executeAndCheckResult(Command[] commands) throws Exception {
	String cmd = "";
	for (Command command : commands) {
	    cmd = cmd + command.get() + "&&";
	}
	if(cmd.endsWith("&&")) {
	    cmd = cmd.substring(0,cmd.lastIndexOf("&&"));
	}
	LOG.debug("远程主机:" + ip + "...开始执行shell命令:" + cmd);
	String exeReulst = execute(cmd);
	String result = execute("echo $?");
	if (!result.contains("0")) {
	    throw new RuntimeException("远程主机:" + ip + "...执行命令:" + cmd + "失败!" + exeReulst);
	}
	LOG.debug("远程主机:" + ip + "...执行shell命令" + cmd + "成功!");
    }
    /**
     * 功能：一次执行一个命令
     * @author WDB
     * @throws Exception
     */
    public void executeAndCheckResult(Command command) throws Exception {
	String cmd = command.get();
	LOG.debug(ip + "开始执行命令:" + cmd);
	String exeReulst = execute(cmd);
	String result = execute("echo $?");
	if (!result.contains("0")) {
	    throw new RuntimeException(ip + "命令:" + cmd + "执行失败!!" + exeReulst);
	}
	LOG.debug(ip + cmd + "...执行完成");
    }

    public void closeConnection() {
	if (expect != null) {
	    expect.close();
	}
	if (channel != null) {
	    channel.disconnect();
	}
	if (session != null) {
	    session.disconnect();
	}
    }
    public Expect4j getExpect() {
	return expect;
    }

    public void setExpect(Expect4j expect) {
	this.expect = expect;
    }
}
