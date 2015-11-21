package com.joe.monitor.msg;

public class MonitorMsg extends AbstractMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String field;// 字段
	private Object use; // 使用情况
	private Object trigger; // 阀值

	@Override
	public String getMessage() {
		return null;
	}

	@Override
	protected void parse() {
		this.put("field", field);
		this.put("use", use);
		this.put("valve", trigger);
	}

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

	public Object getUse() {
		return use;
	}

	public void setUse(Object use) {
		this.use = use;
	}

	public Object getTrigger() {
		return trigger;
	}

	public void setTrigger(Object trigger) {
		this.trigger = trigger;
	}
}
