package com.joe.host.init;

import java.io.IOException;
import java.text.MessageFormat;

import org.apache.commons.configuration.Configuration;

import com.sky.config.ConfigUtil;
/**
 * 
 * @ClassName: Command
 * @Description: 对应执行的shell命令,对应ssh_comm.properties配置文件
 * @author WDB
 * @date 2014-12-29 上午9:21:32
 *
 */
public class Command {
	//命令的类型
	private String commandType;
	//命令需要的参数
	private String[] commandArgs;
	
	public Command(String commandType,String... commandArgs){
		this.commandType = commandType;
		this.commandArgs = commandArgs;
	}
	/**
	 * 功能：返回对应命令的字符串
	 * @author WDB
	 * @throws IOException 
	 */
	public String get() throws IOException{
		Configuration config = ConfigUtil.createConfig("ssh_comm.properties");
		return MessageFormat.format(config.getString(commandType),commandArgs);
	}
	
	public static void main(String[] args) throws Exception {
		Command mvJdk = new Command("mv", "/opt/jdk*/","/opt/java/");
		String string = mvJdk.get();
		System.out.println(string);
	}

}
