package com.joe.agent.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.codehaus.jettison.json.JSONObject;
import org.dom4j.Document;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;

public class AgentUtils {
	public static final String TYPE = "agent";
	public static final String NAME = "agent";
	public static final String TTYPE= "task";
	public static final String TNAME= "task";
	public static final String pattern = "yyyyMMddHHmmss" ;
	
	public static Logger Log = Logger.getLogger("com.joe.agent.utils.AgentUtils");
	
	/**
	 * 生成任务id
	 * 1、默认生成方式,格式参见:pattern
	 * 2、指定生成方式,格式参见:format
	 * @return
	 */
	public static long gernateId(String format) {
		long taskId   = 0L;
		DateFormat dm = null ;
		try{
			//是否指定主键生成方式,如果不指定,则默认
			if(!StringUtils.isEmpty(format)) {
				dm = new SimpleDateFormat(format);
				Log.info("指定任务主键生成策略格式为['"+format+"']");
			} else {
				dm = new SimpleDateFormat(pattern);
				Log.info("默认任务主键生成策略格式为['"+pattern+"']");
			}
			//格式化后的日期字符串
			String dateStr = dm.format(new Date());
			//主键
			taskId = Long.valueOf(dateStr);
			Log.info("任务主键生成成功,主键为['"+taskId+"']");
		} catch(Exception e) {
			Log.error("任务主键生成失败,异常信息为:"+e.getMessage(), e);
		} 
		return taskId;
	}
	
	/**
     * 
     * @param resource ： url
     * @param type : GET|POST|DELETE|PUT
     * @param obj
     */
    public static Object invoke(String resource,int oType,int mType,Object obj){
    	Client c = Client.create();  
        WebResource r = c.resource(resource);  

        //获取接收参数类型和返回值类型
        List mr = getMR(mType);
        Object returnObj = null ;
        
        String mediaType = "" ;
        Class  resulType = null;
        if(mr.size() > 0) {
        	mediaType = (String) mr.get(0);
        	resulType = (Class) mr.get(1);
        	
	        //匹配执行
	        switch(oType) {
	        	case Constants.WS_OPERATION_POST :
	        		returnObj = r.accept(mediaType).post(resulType,obj);
	        		break ;
	        	case Constants.WS_OPERATION_GET :
	        		returnObj = r.accept(mediaType).get(resulType);
	        		break ;
	        	case Constants.WS_OPERATION_DELETE :
	        		returnObj = r.accept(mediaType).delete(resulType);
	        		break ;
	        	case Constants.WS_OPERATION_PUT :
	        		returnObj = r.accept(mediaType).put(resulType);
	        		break ;
	        }
        }
        return returnObj ;
    }

    /**
     * 获取接收参数类型和返回值类型
     * @param mType
     * @return
     */
	private static List getMR(int mType) {
		List mr = new ArrayList();
		
		//匹配参数类型及返回值类型
		switch(mType) {
			case Constants.WS_MT_TEXT_XML :
				mr.add(0,Constants.WS_MT_TEXT_XML);
				mr.add(1,Document.class);
				
			case Constants.WS_MT_APP_XML :
				mr.add(0,Constants.WS_MT_APP_XML);
				mr.add(1,Document.class);
				
			case Constants.WS_MT_APP_JSON : 
				mr.add(0,Constants.WS_MT_APP_JSON);
				mr.add(1,JSONObject.class);
		}
		return mr;
	}
     
	
}
