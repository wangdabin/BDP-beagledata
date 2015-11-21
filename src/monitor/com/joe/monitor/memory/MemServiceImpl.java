package com.joe.monitor.memory;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.joe.host.dao.HostDAO;
import com.joe.host.vo.Host;

@Service("memServiceImpl")
@Transactional
public class MemServiceImpl implements MemoryService {

	@Resource(name = "memoryDao")
	private MemoryDao memDao;
	
	@Resource(name = "hostDAO")
	private HostDAO hostDAO;

	@Override
	public MemoryUnit get(Serializable id) {
		return memDao.get(id);
	}

	@Override
	public List<MemoryUnit> getAll() {
		return memDao.getAll();
	}

	@Override
	public void add(MemoryUnit newInstance) {
		memDao.save(newInstance);
	}

	@Override
	public void remove(MemoryUnit transientObject) {
		memDao.remove(transientObject);
	}

	@Override
	public void removeById(Serializable id) {
		memDao.removeById(id);
	}

	@Override
	public MemoryUnit update(MemoryUnit t) {
		memDao.save(t);
		return t;
	}

	@Override
	public void addBatch(List<MemoryUnit> instanceBatch) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<MemoryUnit> getAllMemInfoGroupByHost() {
		List<MemoryUnit> allMemoryUnit = new ArrayList<MemoryUnit>();
		List<Host> allHost = hostDAO.getAll();
		for (Host host : allHost) {
			MemoryUnit memoryUnit = memDao.getLatestByIp(host.getIp());
			if (memoryUnit != null) {
				allMemoryUnit.add(memoryUnit);
			}
		}
		return allMemoryUnit;
	}

}
