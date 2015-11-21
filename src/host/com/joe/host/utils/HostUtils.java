package com.joe.host.utils;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.joe.host.vo.Host;

/**
 * @author Joe
 *
 */
public class HostUtils {

	public static final String TYPE = "host";
	public static final String NAME = "host";
	

	/**
	 * 返回一个真正的key
	 * 标记这真正定位主机的地址
	 * @param host
	 * @return
	 */
	public static final String buildKey(Host host){
		return buildKey(host.getName(),host.getIp());
	}
	
	/**
	 * 返回一个真正的key
	 * 只有当ip是 null 时，才会返回主机名
	 * @param hostname
	 * @param ip
	 * @return
	 */
	public static final String buildKey(String hostname,String ip){
		return StringUtils.isBlank(ip) ? hostname : ip;
	}
	
	/**
	 * 
	 * @param hosts
	 * @return
	 */
	public static final List<String> parse(List<Host> hosts){
		List<String> list = new ArrayList<String>();
		if(hosts != null){
			for(Host host : hosts){
				list.add(buildKey(host));
			}
		}
		return list;
	}
}
