package com.joe.monitor.filesystem;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.joe.core.utils.CoreConfigUtils;

@Service("fileSystemServiceImpl")
@Transactional
public class FileSystemServiceImpl implements FileSystemService {

	Log logger = LogFactory.getLog(this.getClass());
	@Resource(name = "fsDao")
	private FsDao fsDao;

	@Resource(name = "diskInfoDao")
	private DiskInfoDao diskInfoDao;

	/**
	 * 缓存集群磁盘的信息，减少io次数
	 */
	public static Map<String, List<DiskInfo>> diskCache = new HashMap<String, List<DiskInfo>>();

	@Override
	public DiskUnit get(Serializable id) {
		return fsDao.get(id);
	}

	@Override
	public List<DiskUnit> getAll() {
		return fsDao.getAll();
	}

	@Override
	public void add(DiskUnit newInstance) {
		fsDao.save(newInstance);
	}

	@Override
	public void remove(DiskUnit transientObject) {
		fsDao.remove(transientObject);
	}

	@Override
	public void removeById(Serializable id) {
		fsDao.removeById(id);
	}

	@Override
	public DiskUnit update(DiskUnit t) {
		fsDao.save(t);
		return t;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<DiskUnit> getRealTimeData() {
		String hql = "select new DiskUnit(n.id,"
				   +"n.diskInfo," 
				   +"n.free,"
				   +"n.used,"
				   +"n.avail,"
				   +"n.files,"
				   +"n.freefiles,"
				   +"n.diskReadBytes,"
				   +"n.diskWriteBytes,"
				   +"n.usedPercent,"
				   +"n.month,"
				   +"n.day,"
				   +"n.minute,"
				   +"n.hour,"
				   +"n.timestamp) FROM DiskUnit n join n.diskInfo i "
				+ "where n.timestamp =i.timeStamp and i.devName LIKE :param";
		try {
			Configuration conf = CoreConfigUtils.create();
			String devNameFlag = conf.getString("disk.active.name.flag", "dev");
			if (!devNameFlag.contains(",")) {
				Query query = fsDao.createQuery(hql);
				query.setString("param", "%"+devNameFlag+"%");
				return query.list();
			} else {
				List<DiskUnit> diskList = new ArrayList<DiskUnit>();
				String[] devFlags = devNameFlag.split(",");
				for (String flag : devFlags) {
					Query queryTemp = fsDao.createQuery(hql);
					queryTemp.setString("param", "%"+flag+"%");
					diskList.addAll(queryTemp.list());
				}
				return diskList;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void addBatch(List<DiskUnit> instanceBatch) {
		if (diskCache.size() == 0) {
			diskInfoDao.loadDiskInfo(diskCache);
		}
		boolean isChanged = false;
		for (DiskUnit diskUnit : instanceBatch) {
			if (!diskCache.containsValue(diskUnit.getDiskInfo())) {
				logger.info("Cluster disk units  changed");
				isChanged = true;
				break;
			}
		}

		if (isChanged) {
			//清除当前缓存的记录
			diskCache.put(instanceBatch.get(0).getDiskInfo().getIp(),
					new ArrayList<DiskInfo>());
			// 更新缓存
			for (DiskUnit diskUnit : instanceBatch) {
				diskCache.get(diskUnit.getDiskInfo().getIp()).add(
						diskUnit.getDiskInfo());
			}
			// 删除 指定ip的记录
			for (DiskUnit diskUnit : instanceBatch) {
				diskInfoDao.removeBy("ip", diskUnit.getDiskInfo().getIp());
			}
		}
		for (DiskUnit diskUnit : instanceBatch) {
			fsDao.saveOrUpdate(diskUnit);
		}

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<DiskInfo> getAllDiskInfo() {
		try {
			Configuration conf = CoreConfigUtils.create();
			String devNameFlag = conf.getString("disk.active.name.flag", "dev");
			String hql = "FROM DiskInfo as t where t.devName LIKE :param";
			Query query = diskInfoDao.createQuery(hql);
			if (!devNameFlag.contains(",")) {
				query.setString("param", "%"+devNameFlag+"%");
				return query.list();
			} else {
				List<DiskInfo> devList = new ArrayList<DiskInfo>();
				String[] devFlags = devNameFlag.split(",");
				for (String flag : devFlags) {
					Query queryTemp = diskInfoDao.createQuery(hql);
					queryTemp.setString("param", "%"+flag+"%");
					devList.addAll(queryTemp.list());
				}
				return devList;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

}
