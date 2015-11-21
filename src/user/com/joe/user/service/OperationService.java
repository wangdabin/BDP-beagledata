package com.joe.user.service;

import java.util.List;

import com.joe.user.vo.Operation;

/**
*
* @description 操作信息管理类
*
* @function 
* 		新建操作
* 		编辑操作
* 		查询所有操作
*
* @author ZhouZH
*
*/
public interface OperationService {

	/**
	 * 新建操作
	 * @param operation 操作实例
	 * @return
	 */
	public boolean NewOperation(Operation operation);
	/**
	 * 编辑操作
	 * @param operation 操作实例
	 * @return
	 */
	public boolean ModifyOperation(Operation operation);
	/**
	 * 查询所有操作
	 * @return 
	 */
	public List<Operation> GetAll();
	/**
	 * 根据Id查询操作
	 * @param id
	 * @return
	 */
	Operation GetOperationByName(String name);
	
}
