package com.sky.config.service;

import java.io.Serializable;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sky.config.BDPConfigVO;
import com.sky.config.dao.BDPConfigDAO;

@Service("bDPConfigService")
@Transactional(rollbackFor = RuntimeException.class)
public class BDPConfigServiceImpl implements BDPConfigService {
	@Resource(name = "bdpConfigDAO")
	private BDPConfigDAO bdpConfigDAO;
	@Override
	public BDPConfigVO get(Serializable id) {
		return bdpConfigDAO.get(id);
	}

	@Override
	public List<BDPConfigVO> getAll() {
		return bdpConfigDAO.getAll();
	}

	@Override
	public void add(BDPConfigVO newInstance) {
		bdpConfigDAO.save(newInstance);
	}

	@Override
	public void remove(BDPConfigVO transientObject) {
		bdpConfigDAO.remove(transientObject);
	}

	@Override
	public void removeById(Serializable id) {
		bdpConfigDAO.removeById(id);
	}

	@Override
	public BDPConfigVO update(BDPConfigVO t) {
		bdpConfigDAO.saveOrUpdate(t);
		return t;
	}

	@Override
	public void addBatch(List<BDPConfigVO> instanceBatch) {
		for(BDPConfigVO configVO : instanceBatch){
			bdpConfigDAO.save(configVO);
		}
	}

	@Override
	public BDPConfigVO findByKey(String key) {
		return bdpConfigDAO.findByKey(key);
	}

	@Override
	public void updateValueByKey(String key, String value) {
		BDPConfigVO bdpConfigVODb = bdpConfigDAO.findByKey(key);
		bdpConfigVODb.setValue(value);
	}
}
