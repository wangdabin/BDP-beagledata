package com.joe.core.dao;
import java.io.Serializable;
import java.util.List;

import com.joe.core.utils.Assert;
import com.joe.core.utils.GenericUtils;

/**
 * 
 * @author joe
 */
@SuppressWarnings("unchecked")
public abstract class HibernateGenericDao<T> extends AbstractHibernateDao<T> implements EntityDAO<T>{
	protected Class<T> entityClass;
	
	public HibernateGenericDao(){
		/**
		 * this.getClass()的目的是返回当前对象运行时的类
		 * 通过工具类GenericUtils返回泛型T的实际类对象
		 */
		entityClass = GenericUtils.getSuperClassGenericType(getClass());
	}
	
	public T get(Serializable id) {
		return get(entityClass,id);
	}

	public List<T> getAll() {
		return getAll(entityClass);
	}

	/**
	 * 
	 * @return
	 */
	public long getTotal(){
		return getTotal(entityClass);
	}
	
	public void removeById(Serializable id) {
		removeById(entityClass,id);
	}

	public void save(T newInstance) {
		saveOrUpdate(newInstance);
	}
	/**
	 * 查询全部，带排序
	 * @param orderBy
	 * @param isAsc
	 * @return
	 */
	public List<T> getAllByOrder(String orderBy,boolean isAsc){
		return getAll(entityClass,orderBy,isAsc);
	}
	
	/**
	 * 根据属性名和属性值查询对象
	 * @param propertyName
	 * @param value
	 * @return
	 */
	public List<T> findBy(String propertyName,Object value){
		return findBy(entityClass, propertyName, value);
	}
	
	/**
	 * 根据属性名和属性值进行查询对象，带排序
	 * @param propertyName
	 * @param value
	 * @param isAsc
	 * @param orderBy
	 * @return
	 */
	public List<T> findBy(String propertyName,Object value,boolean isAsc,String orderBy){
		return findBy(entityClass, propertyName, value, orderBy, isAsc);
	}
	
	/**
	 * 根据属性名和属性值进行查询对象，带排序
	 * @param propertyName
	 * @param value
	 * @param isAsc
	 * @param orderBy
	 * @return
	 */
	public List<T> findBy(String propertyName,Object value,boolean isAsc,String orderBy,int maxSize){
		return findBy(entityClass, propertyName, value, orderBy, isAsc,maxSize);
	}
	
	/**
	 * 根据属性名和属性值进行查询对象，返回符合条件的唯一对象。
	 * 如果对象不唯一将抛出异常
	 * @param <T>
	 * @param propertyName
	 * @param value
	 * @return
	 */
	public <T> T findUniqueBy(String propertyName,Object value){
		return (T) findUniqueBy(entityClass, propertyName, value);
	}
	
	public void removeBy(String propertyName,Object value){
		Assert.hasText(propertyName);
		createSession().delete(findBy(propertyName, value));
	}
}
