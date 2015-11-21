package com.joe.monitor.net;

import java.io.Serializable;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.joe.host.dao.HostDAO;

@Service("netServiceImpl")
@Transactional
public class NetServiceImpl implements NetWorkService {

	@Resource(name = "netDao")
	private NetDao netDao;

	@Resource(name = "netInfoDao")
	private NetInfoDao netInfoDao;

	@Resource(name = "hostDAO")
	private HostDAO hostDAO;

	@Override
	public NetInterfaceUnit get(Serializable id) {
		return netDao.get(id);
	}

	@Override
	public List<NetInterfaceUnit> getAll() {
		return netDao.getAll();
	}

	@Override
	public void add(NetInterfaceUnit newInstance) {
		netDao.save(newInstance);
	}

	@Override
	public void remove(NetInterfaceUnit transientObject) {
		netDao.remove(transientObject);
	}

	@Override
	public void removeById(Serializable id) {
		netDao.removeById(id);
	}

	@Override
	public NetInterfaceUnit update(NetInterfaceUnit newInstance) {
		netDao.save(newInstance);
		return newInstance;
	}

	@Override
	public List<NetInterfaceUnit> getRealTimeData() {

		/*
		 * SELECT AVG(free),AVG(used),AVG(avail),AVG(files),AVG(freefiles),AVG(
		 * diskReadBytes),AVG(diskWriteBytes),AVG(usedPercent) FROM diskunit
		 * n JOIN diskinfo i ON n.timestamp =i.timestamp
		 */
		String sql = "select new NetInterfaceUnit(n.id, n.netInfo, n.rxBytes,"
				+ "n.txBytes, n.speed, n.timeStamp, n.day,"
				+ "n.minute, n.hour) FROM NetInterfaceUnit n join n.netInfo i "
				+ "where n.timeStamp =i.timeStamp ";

		// String sql = "from NetInterfaceUnit n order by n.id desc";
		List<NetInterfaceUnit> units = netDao.createQuery(sql, null)
				.setFirstResult(0).list();
		return units;
	}

	/**
	 * 获取制定时间单位（月、天、时、分）下的数据进行展示
	 * 
	 * @return
	 */
	public List<NetInterfaceUnit> getDataByInterval() {

		return null;

	}

	@Override
	public List<NetInfo> findByIp(String ip) {
		return netInfoDao.findBy("ip", ip);
	}

	@Override
	public List<NetInterfaceUnit> findByNetInfo(NetInfo netInfo) {
		return netDao.findBy("netInfo", netInfo, false, "timeStamp");
	}

	@Override
	public NetInterfaceUnit getRealTimeData(NetInfo netInfo) {
		List<NetInterfaceUnit> units = netDao.findBy("netInfo", netInfo, false,
				"timeStamp", 1);
		if (units != null && !units.isEmpty()) {
			return units.get(0);
		}
		return null;
	}

	@Override
	public void addBatch(List<NetInterfaceUnit> instanceBatch) {
		// TODO Auto-generated method stub

	}
}
