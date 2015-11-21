package com.joe.monitor.cpu;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("cpuServiceImpl")
@Transactional
public class CpuServiceImpl implements CpuService {

	@Autowired
	private CpuDao cpuDao;

	@Override
	public List<CpuUnit> getAll() {
		System.out.println(cpuDao.toString());
		return cpuDao.getAll();
	}

	@Override
	public CpuUnit get(Serializable id) {
		return cpuDao.get(id);
	}

	@Override
	public void add(CpuUnit newInstance) {

		this.cpuDao.save(newInstance);
	}

	@Override
	public void removeById(Serializable id) {
		cpuDao.removeById(id);
	}

	@Override
	public void remove(CpuUnit transientObject) {
		// TODO Auto-generated method stub

	}

	@Override
	public CpuUnit update(CpuUnit t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<CpuUnit> doRealTimeMonitor() {
		String realTimeHql = "select * from cpuunit where timestamp >? group by ? ";

		cpuDao.createQuery(realTimeHql, "20140908", "day").list();
		return null;
	}

	@Override
	public void addBatch(List<CpuUnit> instanceBatch) {
		// TODO Auto-generated method stub

	}

}
