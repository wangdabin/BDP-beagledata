package com.sky.monitor.utils;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import org.apache.commons.configuration.Configuration;

import com.joe.core.utils.CoreConfigUtils;
import com.joe.monitor.Monitored;
import com.joe.monitor.beans.Cpu;
import com.joe.monitor.beans.Disk;
import com.joe.monitor.beans.Memory;
import com.joe.monitor.beans.Net;
import com.joe.monitor.cpu.CpuMonitor;
import com.joe.monitor.filesystem.DiskMonitor;
import com.joe.monitor.manager.MonitorManager;
import com.joe.monitor.memory.MemoryMonitor;
import com.joe.monitor.net.NetMonitor;
import com.joe.monitor.vo.MonitorObjectID;

public class MonitorUtils {

	private static java.text.DecimalFormat df = new java.text.DecimalFormat(
			"#.##");
	
	private static DateFormat dayDf = new SimpleDateFormat("yyyy-MM-dd");
	private static DateFormat hourDf = new SimpleDateFormat("HH");
	private static DateFormat monthDf = new SimpleDateFormat("MM");
	private static DateFormat minuteDf = new SimpleDateFormat("mm");
	

	/**
	 * 格式化数据，保留小数点后两位
	 * 
	 * @param data
	 * @return
	 */
	public static String DFormat(double data) {
		if(Double.toString(data).equals(Double.toString(Double.NaN))){
			return "0.00";
		}else{
			return df.format(data);
		}
	}

	public static String getMonth(long timeStamp) {
		return monthDf.format(timeStamp);
	}

	public static String getDay(long timeStamp) {
		return dayDf.format(timeStamp);
	}

	public static String getHour(long timeStamp) {
		return hourDf.format(timeStamp);
	}
	
	public static String getMinute(long timeStamp){
		return minuteDf.format(timeStamp);
	}

	public static void setCpuMonitored(float f) throws IOException{
		Configuration conf = CoreConfigUtils.create();
		CpuMonitor cpuMonitor = (CpuMonitor) MonitorManager.getInstance(conf).getByObjectID(MonitorObjectID.getCpuMonitor());
		List<Monitored> cpuMonitored = cpuMonitor.getAllMonitored();// CpuMonitored.getInstance(conf, Constants.NAME, "cpu", h);
		Cpu cpuMbean = (Cpu) cpuMonitored.get(0).getMBean();
		cpuMbean.setUse(f);		
	}
	
	public static void setDiskMonitored(float f) throws IOException{
		Configuration conf = CoreConfigUtils.create();
		DiskMonitor diskMonitor = (DiskMonitor) MonitorManager.getInstance(conf).getByObjectID(MonitorObjectID.getDiskMonitor());
		List<Monitored> diskMonitored = diskMonitor.getAllMonitored();// CpuMonitored.getInstance(conf, Constants.NAME, "cpu", h);
		Disk diskMbean = (Disk) diskMonitored.get(0).getMBean();
		diskMbean.setUse(f);		
	}
	
	public static void setMemMonitored(float f) throws IOException{
		Configuration conf = CoreConfigUtils.create();
		MemoryMonitor memMonitor = (MemoryMonitor) MonitorManager.getInstance(conf).getByObjectID(MonitorObjectID.getMemMonitor());
		List<Monitored> memMonitored = memMonitor.getAllMonitored();// CpuMonitored.getInstance(conf, Constants.NAME, "cpu", h);
		Memory memMbean = (Memory) memMonitored.get(0).getMBean();
		memMbean.setUse(f);		
	}
	
	public static void setNetMonitored(float f) throws IOException{
		Configuration conf = CoreConfigUtils.create();
		NetMonitor netMonitor = (NetMonitor) MonitorManager.getInstance(conf).getByObjectID(MonitorObjectID.getNetMonitor());
		List<Monitored> netMonitored = netMonitor.getAllMonitored();// CpuMonitored.getInstance(conf, Constants.NAME, "cpu", h);
		Net netMbean = (Net) netMonitored.get(0).getMBean();
		netMbean.setUse(f);		
	}
	
	public static void main(String[] args) {
//		System.out.println(getDay(System.currentTimeMillis()));
//		System.out.println(getMonth(System.currentTimeMillis()));
//		System.out.println(getHour(System.currentTimeMillis()));
//		System.out.println(getMinute(System.currentTimeMillis()));
		System.out.println(0.0/0.0);
		double data = 0.0/0.0;
		System.out.println(Double.toString(data).equals(Double.toString(Double.NaN)));
		System.out.println(df.format(Double.NaN));
		System.out.println(Double.parseDouble("" + Double.NaN));
	}
}
