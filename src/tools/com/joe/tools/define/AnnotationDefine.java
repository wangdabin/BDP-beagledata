package com.joe.tools.define;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang.StringUtils;

/**
 * 
 * @author qiaolong
 *
 */
public class AnnotationDefine implements Define{

	public static final String ANNOTATION_PRFIEX = "@";
	public static final String NULL = "";
	private String className;
	private Map<String,String> kvs = new HashMap<String, String>();
	
	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public void setClassName(Class<? extends Annotation> className) {
		this.className = className.getName();
	}

	public Map<String, String> getKvs() {
		return kvs;
	}
	
	/**
	 * 
	 * @param value
	 */
	public void setValue(String value){
		this.kvs.put(NULL, value);
	}
	
	/**
	 * 
	 * @param key
	 * @param value
	 */
	public void putKV(String key,String value){
		this.kvs.put(key, value);
	}

	
	//@Repository("hostDAO")
	@Override
	public Appendable define(Appendable sb) throws IOException {
		sb.append(ANNOTATION_PRFIEX).append(className).append("(");
		int size = kvs.size();
		int i = 0;
		for(Entry<String, String> entry : kvs.entrySet()){
			String key = entry.getKey();
			if(StringUtils.isBlank(key)){
				sb.append("\"").append(entry.getValue()).append("\"");
			}else{
				sb.append(key).append("=").append(entry.getValue());
			}
			if(i < size - 1){
				sb.append(",");
			}
			i++;
		}
		sb.append(")\n");
		return sb;
	}
	
	public static void main(String[] args) {
		Map<String,String> kvs = new HashMap<String, String>();
		kvs.put(null, "dddd");
		System.out.println(kvs.get(null));
	}
}
