package com.joe.host.init;
/**
 * 
 * @ClassName: Order
 * @Description: 提供命令抽象接口
 * @author WDB
 * @date 2014-12-29 上午9:23:34
 *
 */
public interface Order {
	//初始化资源
	public void init() throws Exception;
	//开始执行命令
	public void execute() throws Exception;
	//命令完成,释放资源
	public void destory() throws Exception;
}
