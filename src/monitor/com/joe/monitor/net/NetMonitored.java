package com.joe.monitor.net;

import javax.management.MalformedObjectNameException;

import org.apache.commons.configuration.Configuration;

import com.joe.host.vo.Host;
import com.joe.monitor.AbstractMontiored;
import com.joe.monitor.ObjectID;
import com.joe.monitor.beans.Net;
import com.joe.monitor.vo.SkyObjectID;
import com.joe.monitor.vo.SkyObjectID.TableBuilder;

/**
 * 
 * @author qiaolong
 *
 */
public class NetMonitored extends AbstractMontiored{

	private ObjectID objectID;
	private Object mbean;
	
	private NetMonitored(Configuration conf,ObjectID objectID,Object mbean){
		super(conf);
		this.objectID = objectID;
		this.mbean = mbean;
	}
	
	@Override
	public ObjectID getObjectID() {
		return objectID;
	}

	@Override
	public Object getMBean() {
		return mbean;
	}
	
	/**
	 * 
	 * @param conf
	 * @param type
	 * @param name
	 * @param host
	 * @return
	 * @throws MalformedObjectNameException
	 * @throws NullPointerException
	 */
	public static NetMonitored getInstance(Configuration conf,String type,String name,Host host) throws MalformedObjectNameException, NullPointerException{
		TableBuilder builder = TableBuilder.create(type).put("name", name).put("ip", host.getIp()).put("hostname", host.getName());
		ObjectID id = SkyObjectID.getObjectName(type, builder.build());
		Net mbean = new Net(0);
		return new NetMonitored(conf,id,mbean);
	}
}
