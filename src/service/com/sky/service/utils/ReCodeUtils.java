package com.sky.service.utils;

import com.joe.core.vo.ReCode;
import com.joe.core.vo.ReCode.Data;

public class ReCodeUtils {

	/**
	 * 封住返回处理结果信息函数
	 * 
	 * @param taskId
	 * @param msg
	 * @param errCode
	 * @param ret
	 * @return
	 */
	public static ReCode processInfo(long taskId, String msg, String errCode,
			int ret) {
		ReCode reCode = new ReCode();
		reCode.setData(new Data(taskId)); // 任务id
		reCode.setMsg(msg);
		reCode.setErrcode(errCode );
		reCode.setRet(ret);
		return reCode;
	}
	
	public static ReCode makeReCode(String msg,String errCode,int ret){
		ReCode reCode = new ReCode();
		reCode.setMsg(msg);
		reCode.setErrcode(errCode );
		reCode.setRet(ret);
		return reCode;
	}
}
