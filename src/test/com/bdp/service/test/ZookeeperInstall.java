package com.bdp.service.test;

import java.io.IOException;

import org.apache.commons.configuration.Configuration;

import com.joe.core.utils.CoreConfigUtils;
import com.joe.host.vo.Host;
import com.sky.rest.RestAPI;
import com.sky.rest.RestAPIFacade;
import com.sky.rest.entity.Install;
import com.sky.service.define.KeyValue;

/**
 * 
 * @author qiaolong
 *
 */
public class ZookeeperInstall {

	public static void main(String[] args) throws IOException {
		String name = "zookeeper";
		String version = "cdh5.3.0";
		
		Host host = new Host();
		host.setIp("192.168.90.2");
		host.setName("v-master");
		
		Configuration conf = CoreConfigUtils.create();
		RestAPI restAPI = getRestAPI(conf);
		Install install = restAPI.serviceInstall(name, version);
		System.out.println(install.hasNext());
		install.next();
		install.addKeyValue(new KeyValue("key", "test"));
//		System.out.println(JsonUtils.objectToJsonString(new KeyValue("test", "test")));
		
//		System.out.println("install.hasNext(); .......");
//		
//		while(install.hasNext()){
//			System.out.println("install.next(); .......");
//			install.next();
//			System.out.println("install.supportsBasic(); .......");
//			List<KeyValue> kvs = install.supportsBasic();
//			System.out.println("kvs.size() = "  + kvs.size());
//			System.out.println("kvs.get(0) = " + kvs.get(0));
//			System.out.println("=============================");
//		}
		
		
		
//		for(KeyValue kv : kvs){
//			System.out.println(kv.getConfigKey());
//			System.out.println(kv.getValues());
//		}
	}
	
	public static final RestAPI getRestAPI(Configuration conf){
		return new RestAPIFacade(conf);
	}
}
