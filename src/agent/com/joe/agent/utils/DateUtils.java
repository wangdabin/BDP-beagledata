package com.joe.agent.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DateUtils {
	
	private static final String pattern = "yyyy-MM-dd HH:mm:ss" ;
	private static final DateFormat dm  = new SimpleDateFormat(pattern);
	
	//启动时间
	public static String getStartTime() {
		return dm.format(Calendar.getInstance().getTime());
	}
	
	//结束时间
	public static String getEndTime(){
		return dm.format(Calendar.getInstance().getTime());
	}
	
	//任务启动时间
	public static long taskStartTime(){
		return System.currentTimeMillis();
	}
	
	//任务结束时间
	public static long taskEndTime(){
		return System.currentTimeMillis();
	}
	
}
