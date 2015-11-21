package com.sky.monitor.jmx;

import java.lang.management.ManagementFactory;

import javax.management.InstanceAlreadyExistsException;
import javax.management.MBeanServer;
import javax.management.ObjectName;

import org.apache.log4j.Logger;

/**
 * 
 * @author qiaolong
 *
 */
public class MBeans {
	private static final Logger LOG = Logger.getLogger(MBeans.class);

	/**
	 * 
	 * @return
	 */
	public static MBeanServer getMBeanServer(){
		return ManagementFactory.getPlatformMBeanServer();
	}
	
	/**
	 * 
	 * @param objectName
	 * @param theMbean
	 * @return
	 */
	static public ObjectName register(ObjectName objectName, Object theMbean) {
		final MBeanServer mbs = getMBeanServer();
		try {
			mbs.registerMBean(theMbean, objectName);
			LOG.debug("Registered " + objectName);
			return objectName;
		} catch (InstanceAlreadyExistsException iaee) {
			if (LOG.isTraceEnabled()) {
				LOG.trace("Failed to register MBean \"" + objectName + "\"",
						iaee);
			} else {
				LOG.warn("Failed to register MBean \"" + objectName
						+ "\": Instance already exists.");
			}
		} catch (Exception e) {
			LOG.warn("Failed to register MBean \"" + objectName + "\"", e);
		}
		return null;
	}

	/**
	 * 
	 * @param objectName
	 */
	static public void unregister(ObjectName objectName) {
		LOG.debug("Unregistering " + objectName);
		final MBeanServer mbs = getMBeanServer();
		if (objectName == null) {
			LOG.debug("Stacktrace: ", new Throwable());
			return;
		}
		try {
			mbs.unregisterMBean(objectName);
		} catch (Exception e) {
			LOG.warn("Error unregistering " + objectName, e);
		}
	}
}
