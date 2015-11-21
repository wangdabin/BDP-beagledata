package com.joe.monitor.message.service.impl;

import java.io.Serializable;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.joe.monitor.message.dao.BDPMessageDAO;
import com.joe.monitor.message.service.BDPMessageService;
import com.joe.monitor.vo.BDPMessage;
import com.joe.monitor.vo.BDPMessageModel;

/**
 * @ClassName: BDPMessageServiceImpl
 * @Description: 监控消息管理服务
 * @author WDB
 * @date 2015-5-4 上午11:23:22
 */
@Service("bDPMessageService")
@Transactional(rollbackFor = RuntimeException.class)
public class BDPMessageServiceImpl implements BDPMessageService {

	@Resource(name = "bdpMessageDAO")
	private BDPMessageDAO bdpMessageDAO;

	@Override
	public BDPMessage get(Serializable id) {
		return bdpMessageDAO.get(id);
	}

	@Override
	public List<BDPMessage> getAll() {
		return bdpMessageDAO.getAll();
	}

	@Override
	public void add(BDPMessage newInstance) {
		bdpMessageDAO.save(newInstance);
	}

	@Override
	public void remove(BDPMessage transientObject) {
		bdpMessageDAO.remove(transientObject);
	}

	@Override
	public void removeById(Serializable id) {
		bdpMessageDAO.removeById(id);
	}

	@Override
	public BDPMessage update(BDPMessage t) {
		bdpMessageDAO.save(t);
		return t;
	}

	@Override
	public void addBatch(List<BDPMessage> instanceBatch) {
		for (BDPMessage message : instanceBatch) {
			bdpMessageDAO.save(message);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<BDPMessage> query(BDPMessageModel model) {
		StringBuffer sqlBuffer = new StringBuffer();
		sqlBuffer.append("FROM BDPMessage model where 1=1");
		if(model.getType()!=null){
			sqlBuffer.append(" AND model.type = '" + model.getType() + "'");
		}
		if(model.getLevel()!=null){
			sqlBuffer.append(" AND model.level = '" + model.getLevel() +"'");
		}
		if(model.getReaded()!=null){
			sqlBuffer.append(" AND model.readed = " + model.getReaded());
		}
		if(model.getCreateTimeStart()!= null){
			sqlBuffer.append(" AND model.createTime >= '" + model.getCreateTimeStart() +"'");
		}
		if(model.getCreateTimeEnd()!= null){
			sqlBuffer.append(" AND model.createTime <= '" + model.getCreateTimeEnd() + "'");
		}
		if(model.getReadTimeStart()!= null){
			sqlBuffer.append(" AND model.readTime >= '" + model.getReadTimeStart() + "'");
		}
		if(model.getReadTimeEnd()!= null){
			sqlBuffer.append(" AND model.readTime <= '" + model.getReadTimeEnd() + "'");
		}
		String hql = sqlBuffer.toString();
		List<BDPMessage> messages  = bdpMessageDAO.find(hql, null);
		return messages;
	}

	@Override
	public void read(Serializable id) {
		BDPMessage bdpMessage = bdpMessageDAO.get(id);
		bdpMessage.setReaded(true);
	}

}
