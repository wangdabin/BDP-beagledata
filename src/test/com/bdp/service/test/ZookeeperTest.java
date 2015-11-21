package com.bdp.service.test;

import java.io.IOException;

import org.apache.commons.configuration.Configuration;

import com.joe.core.utils.CoreConfigUtils;
import com.joe.host.vo.Host;
import com.sky.rest.RestAPI;
import com.sky.rest.RestAPIFacade;
import com.sky.rest.entity.Service;
import com.sky.service.define.KeyValue;

/**
 * 
 * @author qiaolong
 *
 */
public class ZookeeperTest {

	public static void main(String[] args) throws IOException {
		String name = "zookeeper";
		String version = "cdh5.1.0";
		
		Host host = new Host();
		host.setIp("192.168.90.2");
		host.setName("v-master");
		
		Configuration conf = CoreConfigUtils.create();
		RestAPI restAPI = getRestAPI(conf);
		Service service = restAPI.createService(name, version);
		
		System.out.println("set install dir");
//		service.setInstallDir("/opt");
		System.out.println("set install dir success");
		
		System.out.println("Add host");
		service.addHost(host);
		System.out.println("Add host success");
		


		
		System.out.println("Add configs...");
		service.addConfig(new KeyValue("tickTime","2000"));
		service.addConfig(new KeyValue("initLimit","10"));
		service.addConfig(new KeyValue("syncLimit","5"));
		service.addConfig(new KeyValue("dataDir","/opt/zookeeper/data"));
		service.addConfig(new KeyValue("clientPort","2181"));
		service.addConfig(new KeyValue("maxClientCnxns","10"));
		System.out.println("Add configs success ..");

		
		System.out.println("Install ");
//		service.install("test");
		System.out.println("Install success ");
	}
	
	public static final RestAPI getRestAPI(Configuration conf){
		return new RestAPIFacade(conf);
	}
}
