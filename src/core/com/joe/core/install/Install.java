package com.joe.core.install;

import java.io.IOException;
import java.util.List;

import com.joe.core.callback.ScheduleCallBack;
import com.joe.plugin.Pluggable;
import com.joe.plugin.annotation.XPoint;
import com.sky.service.define.KeyValue;

/**
 * 安装
 * @author qiaolong
 *
 */
@XPoint
public interface Install extends Pluggable{
	public final static String X_POINT_ID = Install.class.getName();
	
	void init() throws IOException;
	/**
	 * 是否有下一步
	 * @return
	 */
	boolean hasNext();
	
	/**
	 * 是否有上一步
	 * @return
	 */
	boolean hasReverse();
	
	/**
	 * 执行下一步
	 */
	void next();
	
	/**
	 * 执行上一步
	 */
	void reverse();
	
	/**
	 * 当前步骤支持的基本配置
	 * @return
	 */
	List<KeyValue> supportsBasic();
	
	/**
	 * 当前步骤支持的基本配置
	 * @return
	 */
	List<KeyValue> supportsAdvanced();
	
	/**
	 * 添加配置
	 * @param keyValue
	 */
	void addKeyValue(KeyValue keyValue);
	
	/**
	 * 执行安装并加入进度回调器
	 * @param callBack
	 */
	void install(ScheduleCallBack callBack);
}
