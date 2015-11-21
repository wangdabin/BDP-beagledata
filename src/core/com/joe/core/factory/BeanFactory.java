package com.joe.core.factory;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;

import com.joe.core.listener.BDPContextLoaderListener;

/**
 * 
 * spring bean factory 提供
 * 
 * @author qiaolong
 * 
 */
public class BeanFactory {

	private static final ApplicationContext getContext() {
		return BDPContextLoaderListener.getCurrentWebApplicationContext();
	}

	/**
	 * 
	 * @param beanName
	 * @return
	 * @throws BeansException
	 */
	public static final Object getBean(String beanName) throws BeansException {
		return getContext().getBean(beanName);
	}

	/**
	 * 
	 * @param beanName
	 * @param beanClass
	 * @return
	 * @throws BeansException
	 */
	public static final <T> T getBean(String beanName, Class<T> beanClass)
			throws BeansException {
		return getContext().getBean(beanName, beanClass);
	}

	/**
	 * 
	 * @param beanClass
	 * @return
	 * @throws BeansException
	 */
	public static final <T> T getBean(Class<T> beanClass) throws BeansException {
		return getContext().getBean(beanClass);
	}

	/**
	 * 
	 * @param beanName
	 * @param paramVarArgs
	 * @return
	 * @throws BeansException
	 */
	public static final Object getBean(String beanName, Object... paramVarArgs)
			throws BeansException {
		return getContext().getBean(beanName, paramVarArgs);
	}

	/**
	 * 
	 * @param beanClass
	 * @param paramVarArgs
	 * @return
	 * @throws BeansException
	 */
	public static final <T> T getBean(Class<T> beanClass, Object... paramVarArgs)
			throws BeansException {
		return getContext().getBean(beanClass, paramVarArgs);
	}

	/**
	 * 
	 * @param beanName
	 * @return
	 */
	public static final boolean containsBean(String beanName) {
		return getContext().containsBean(beanName);
	}

	/**
	 * 
	 * @param beanName
	 * @return
	 * @throws NoSuchBeanDefinitionException
	 */
	public static final boolean isSingleton(String beanName)
			throws NoSuchBeanDefinitionException {
		return getContext().isSingleton(beanName);
	}

	/**
	 * 
	 * @param beanName
	 * @return
	 * @throws NoSuchBeanDefinitionException
	 */
	public static final boolean isPrototype(String beanName)
			throws NoSuchBeanDefinitionException {
		return getContext().isPrototype(beanName);
	}

	/**
	 * 
	 * @param beanName
	 * @param paramClass
	 * @return
	 * @throws NoSuchBeanDefinitionException
	 */
	public static final boolean isTypeMatch(String beanName, Class<?> paramClass)
			throws NoSuchBeanDefinitionException {
		return getContext().isTypeMatch(beanName, paramClass);
	}

	/**
	 * 
	 * @param beanName
	 * @return
	 * @throws NoSuchBeanDefinitionException
	 */
	public static final Class<?> getType(String beanName)
			throws NoSuchBeanDefinitionException {
		return getContext().getType(beanName);
	}

	/**
	 * 
	 * @param beanName
	 * @return
	 */
	public static final String[] getAliases(String beanName) {
		return getContext().getAliases(beanName);
	}
}
