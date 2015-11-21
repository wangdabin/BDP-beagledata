package com.sky.task.vo;

import java.io.Serializable;
import java.util.Map;

import com.joe.core.vo.ReCode;

/**
 * 
 * @ClassName: Intent
 * @Description: 消息传递内容类
 * @author: LiuZhiJun
 * @date: 2015年1月14日 下午1:29:27
 */
public class Message extends ReCode implements Serializable {
	private static final long serialVersionUID = 1419771876400146071L;

	private Object msgObj;
	private Map<String, Object[]> msgExtra;
	private int msgInt;
	private long msgLong;

	public static final String INTENT_REMOVE = "remove";
	public static final String INTENT_NO_REMOVE = "no_remove";

	public Object getMsgObj() {
		return msgObj;
	}

	public void setMsgObj(Object msgObj) {
		this.msgObj = msgObj;
	}

	public Map<String, Object[]> getMsgExtra() {
		return msgExtra;
	}

	public void setMsgExtra(Map<String, Object[]> msgExtra) {
		this.msgExtra = msgExtra;
	}

	public int getMsgInt() {
		return msgInt;
	}

	public void setMsgInt(int msgInt) {
		this.msgInt = msgInt;
	}

	public long getMsgLong() {
		return msgLong;
	}

	public void setMsgLong(long msgLong) {
		this.msgLong = msgLong;
	}

}
