package com.joe.monitor.msg;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;


/**
 * 
 * @author qiaolong
 *
 */
public abstract class AbstractMsg implements Message{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String name;
	private JSONObject jsonObject = new JSONObject();
	
	public AbstractMsg(){
	}
	
	private void init(){
		jsonObject = new JSONObject();
		this.put("name", getName());
		this.put("msg", getMessage());
		this.parse();
	}
	
	protected void put(String key,Object value){
		try {
			jsonObject.put(key, value);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	@Override
	public String toJson() {
		this.init();
		return jsonObject.toString();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * 解析
	 */
	protected abstract void parse();
}
