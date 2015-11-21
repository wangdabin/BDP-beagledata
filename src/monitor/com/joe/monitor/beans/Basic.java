package com.joe.monitor.beans;

import java.util.Enumeration;
import java.util.Vector;

import javax.management.InstanceAlreadyExistsException;
import javax.management.InstanceNotFoundException;
import javax.management.MBeanRegistrationException;
import javax.management.MBeanServer;
import javax.management.NotCompliantMBeanException;
import javax.management.ObjectName;

import org.apache.log4j.Logger;

import com.sky.config.Configed;

public abstract class Basic extends Configed implements BasicMBean{

	MBeanServer server;
	private final Vector mbeanList = new Vector();

	public void preDeregister() {
		getLogger().debug("preDeregister called.");

		Enumeration iterator = this.mbeanList.elements();
		while (iterator.hasMoreElements()) {
			ObjectName name = (ObjectName) iterator.nextElement();
			try {
				this.server.unregisterMBean(name);
			} catch (InstanceNotFoundException e) {
				getLogger().warn("Missing MBean " + name.getCanonicalName());
			} catch (MBeanRegistrationException e) {
				getLogger().warn(
						"Failed unregistering " + name.getCanonicalName());
			}
		}
	}

	public void postDeregister() {
		getLogger().debug("postDeregister is called.");
	}

	public void postRegister(Boolean registrationDone) {
	}

	public ObjectName preRegister(MBeanServer server, ObjectName name) {
		getLogger().debug(
				"preRegister called. Server=" + server + ", name=" + name);
		this.server = server;
		return name;
	}

	protected void registerMBean(Object mbean, ObjectName objectName)
			throws InstanceAlreadyExistsException, MBeanRegistrationException,
			NotCompliantMBeanException {
		this.server.registerMBean(mbean, objectName);
		this.mbeanList.add(objectName);
	}
	
	protected abstract Logger getLogger();
}
