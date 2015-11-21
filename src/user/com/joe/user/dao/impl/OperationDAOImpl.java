package com.joe.user.dao.impl;

import org.springframework.stereotype.Repository;

import com.joe.user.dao.OperationDAO;
import com.joe.user.vo.Operation;

/**
*
* @description 操作持久化类，基础基类实现
*
* @function
*
* @author ZhouZH
*
*/
@Repository(value = "operationDAO")
public class OperationDAOImpl extends EntityDAOImpl<Operation> implements OperationDAO {

	

}
