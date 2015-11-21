package com.joe.monitor.beans;

import java.util.Iterator;

import javax.management.Attribute;
import javax.management.AttributeList;
import javax.management.AttributeNotFoundException;
import javax.management.DynamicMBean;
import javax.management.InvalidAttributeValueException;
import javax.management.JMException;
import javax.management.MBeanException;
import javax.management.MBeanInfo;
import javax.management.ReflectionException;
import javax.management.RuntimeOperationsException;


/**
 * 
 * @author qiaolong
 * 
 */
public abstract class AbstractDynamicMBean extends Basic implements
		DynamicMBean, MBeaned {
	String dClassName;

	public AbstractDynamicMBean() {
	}

	public AttributeList getAttributes(String[] attributeNames) {
		if (attributeNames == null) {
			throw new RuntimeOperationsException(new IllegalArgumentException(
					"attributeNames[] cannot be null"),
					"Cannot invoke a getter of " + this.dClassName);
		}

		AttributeList resultList = new AttributeList();

		if (attributeNames.length == 0) {
			return resultList;
		}

		for (int i = 0; i < attributeNames.length; i++) {
			try {
				Object value = getAttribute(attributeNames[i]);
				resultList.add(new Attribute(attributeNames[i], value));
			} catch (JMException e) {
				e.printStackTrace();
			} catch (RuntimeException e) {
				e.printStackTrace();
			}
		}
		return resultList;
	}

	public AttributeList setAttributes(AttributeList attributes) {
		if (attributes == null) {
			throw new RuntimeOperationsException(new IllegalArgumentException(
					"AttributeList attributes cannot be null"),
					"Cannot invoke a setter of " + this.dClassName);
		}

		AttributeList resultList = new AttributeList();

		if (attributes.isEmpty()) {
			return resultList;
		}

		for (Iterator i = attributes.iterator(); i.hasNext();) {
			Attribute attr = (Attribute) i.next();
			try {
				setAttribute(attr);
				String name = attr.getName();
				Object value = getAttribute(name);
				resultList.add(new Attribute(name, value));
			} catch (JMException e) {
				e.printStackTrace();
			} catch (RuntimeException e) {
				e.printStackTrace();
			}
		}
		return resultList;
	}


	public abstract MBeanInfo getMBeanInfo();

	public abstract Object invoke(String paramString,
			Object[] paramArrayOfObject, String[] paramArrayOfString)
			throws MBeanException, ReflectionException;

	public abstract void setAttribute(Attribute paramAttribute)
			throws AttributeNotFoundException, InvalidAttributeValueException,
			MBeanException, ReflectionException;

	public abstract Object getAttribute(String paramString)
			throws AttributeNotFoundException, MBeanException,
			ReflectionException;
}
