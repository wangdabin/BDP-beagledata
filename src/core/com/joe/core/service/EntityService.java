package com.joe.core.service;

import java.io.Serializable;
import java.util.List;

/**
 * 
 * @author Joe
 *
 * @param <T>
 */
public interface EntityService<T> {

	/**
	 * 根据Id查找一个类型为T的对象。
	 * 
	 * @param id 传入的Id的值
	 * @return 一个类型为T的对象
	 */
	T get(Serializable id);
	
	/**
	 * 获取对象的全部集合，集合中的对象为T
	 * @return 一组T对象的List集合
	 */
	List<T> getAll();
	
	/**
	 * 添加一个实体类
	 * @param newInstance 需要持久化的对象
	 */
	void add(T newInstance);
	
	/**
	 * 删除一个已经被持久化的对象，该对象类型为T
	 * @param transientObject 需要删除的持久化对象
	 */
	void remove(T transientObject);
	
	/**
	 * 根据对象id删除一个对象，该对象类型为T
	 * @param id 需要删除的对象的id
	 */
	void removeById(Serializable id);
	
	/**
	 * 更新
	 * @param t
	 * @return
	 */
	T update(T t);
	
	void  addBatch(List<T> instanceBatch);
}
