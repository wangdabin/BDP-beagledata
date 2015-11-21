package com.joe.monitor.define;


/**
 * 
 * 定义专门用来观察尺度属性值。
 * @author qiaolong
 *
 */
public interface GaugeDefine extends Define{

//	/**
//	 * 被监听的对象
//	 * @return
//	 */
//	ObjectName getObjectName();
	
	/**
	 * 获取所有观察到的 MBean 的公共高阈值通知 (high notification) 的开/关切换值。
	 * @return
	 */
	boolean notifyHigh();
	
	/**
	 * 获取所有观察到的 MBean 的公共低阈值通知 (low notification) 的开/关切换值。
	 * @return
	 */
	boolean notifyLow();
	
	/**
	 * 得到最高值
	 * @return
	 */
	Number getHighValue();
	
	/**
	 * 得到最低值
	 * @return
	 */
	Number getLowValue();
}
