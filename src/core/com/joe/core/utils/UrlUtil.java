package com.joe.core.utils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletRequest;

public class UrlUtil {

	private Map<String,Object> paramMap;
	
	private String actionPath;
	
	public UrlUtil(){
		paramMap = new HashMap<String,Object>();
	}
	
	public UrlUtil(String actionPath){
		this.actionPath = actionPath;
		paramMap = new HashMap<String,Object>();
	}
	
	public UrlUtil(Map params,String actionPath){
		this.paramMap = params;
		this.actionPath = actionPath;
	}
	
	public void removeParam(String name){
		this.paramMap.remove(name);
	}
	
	public Object getParam(String name){
		return this.paramMap.get(name);
	}
	
	/**
	 * 清空所有的参数，包括action
	 */
	public void clear(){
		this.actionPath = "";
		this.paramMap.clear();
	}
	
	public void clearParams(){
		this.paramMap.clear();
	}
	
	public void putParam(String name,Object value){
		if(value != null && name != null)
			this.paramMap.put(name,value);
	}
	
	public String getURL() throws IOException{
		StringBuffer url = new StringBuffer();
		for(String name : paramMap.keySet()){
			url.append(name).append("=").append(java.net.URLEncoder.encode(paramMap.get(name).toString(), "utf-8")).append("&");
		}
		if(url.length() > 0){
			url.setLength(url.length() - 1);
			if(this.actionPath != null){
				return this.actionPath +"?" + url.toString();
			}else{
				return url.toString();
			}
		}else{
			return null;
		}
	}
	
	public static String getURL(ServletRequest request,String actionPath) throws IOException{
		StringBuffer url = new StringBuffer();
		for(Object name : request.getParameterMap().keySet()){
			url.append(name).append("=").append(java.net.URLEncoder.encode(request.getParameter(name.toString()).toString(), "utf-8")).append("&");
		}
		if(url.length() > 0){
			url.setLength(url.length() - 1);
			if(actionPath != null){
				return actionPath +"?" + url.toString();
			}else{
				return url.toString();
			}
		}else{
			return null;
		}
	}
	
	
	public static String getURL(ServletRequest request,String actionPath,String ignoreParam) throws IOException{
		StringBuffer url = new StringBuffer();
		for(Object name : request.getParameterMap().keySet()){
			if(!name.equals(ignoreParam))
				url.append(name).append("=").append(java.net.URLEncoder.encode(request.getParameter(name.toString()).toString(), "utf-8")).append("&");
		}
		if(url.length() > 0){
			url.setLength(url.length() - 1);
			if(actionPath != null){
				return actionPath +"?" + url.toString();
			}else{
				return url.toString();
			}
		}else if(actionPath != null){
			return actionPath;
		}else{
			return null;
		}
	}
	
	public static String getEncodeURL(ServletRequest request,String actionPath) throws IOException{
		StringBuffer url = new StringBuffer();
		for(Object name : request.getParameterMap().keySet()){
			url.append(name).append("=").append(java.net.URLEncoder.encode(request.getParameter(name.toString()).toString(), "utf-8")).append("&amp;");
		}
		if(url.length() > 0){
			url.setLength(url.length() - 5);
			if(actionPath != null){
				return actionPath +"?" + url.toString();
			}else{
				return url.toString();
			}
		}else{
			return null;
		}
	}
	
	public String getEncodeURL() throws IOException{
		StringBuffer url = new StringBuffer();
		for(String name : paramMap.keySet()){
			url.append(name).append("=").append(java.net.URLEncoder.encode(paramMap.get(name).toString(), "utf-8")).append("&amp;");
		}
		if(url.length() > 0){
			url.setLength(url.length() - 5);
			if(this.actionPath != null){
				return this.actionPath +"?" + url.toString();
			}else{
				return url.toString();
			}
		}else{
			return null;
		}
	}

	public String getActionPath() {
		return actionPath;
	}

	public void setActionPath(String actionPath) {
		this.actionPath = actionPath;
	}
}
