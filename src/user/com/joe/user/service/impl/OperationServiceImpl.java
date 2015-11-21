package com.joe.user.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.joe.user.dao.OperationDAO;
import com.joe.user.service.OperationService;
import com.joe.user.vo.Operation;

/**
 * 
 * 权限数据服务层
 * 
 * @author Joe
 * 
 */
@Service("operationService")
@Transactional(rollbackFor = RuntimeException.class)
public class OperationServiceImpl extends EntityServiceImpl implements
		OperationService {

	@Resource(name = "operationDAO")
	private OperationDAO operationdao = null;

	public OperationServiceImpl() {
		
	}

	@Override
	public boolean NewOperation(Operation operation) {
		if (operation == null)
			return false;
		try {
			Operation tmp = this.GetOperationByName(operation.getName());
			if(tmp == null){
				//保存
				operationdao.save(operation);
				return true;
			}
		} catch (Exception ex) {
		}
		return false;
	}

	@Override
	public boolean ModifyOperation(Operation operation) {
		if (operation == null)
			return false;
		try {
			//保存
			operationdao.saveOrUpdate(operation);
			return true;
		} catch (Exception ex) {

		}
		return false;
	}

	@Override
	public List<Operation> GetAll() {
		return operationdao.getAll();
	}
	
	@Override
	public Operation GetOperationByName(String name) {
		return operationdao.get(name);
	}
}
