package com.joe.monitor.filesystem;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.configuration.Configuration;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.joe.core.dao.HibernateGenericDao;
import com.joe.core.utils.CoreConfigUtils;

@Repository("diskInfoDao")
public class DiskInfoDao extends HibernateGenericDao<DiskInfo> {

	/**
	 * 加载数据库中的disk的信息
	 * 
	 * @param diskCache
	 */
	public void loadDiskInfo(Map<String, List<DiskInfo>> diskCache) {
		List<DiskInfo> disks = this.getAll();
		for (DiskInfo diskInfo : disks) {
			if (diskCache.containsKey(diskInfo.getIp())) {
				diskCache.get(diskInfo.getIp()).add(diskInfo);
			} else {
				List<DiskInfo> infos = new ArrayList<DiskInfo>();
				infos.add(diskInfo);
				diskCache.put(diskInfo.getIp(), infos);
			}
		}
	}
	@SuppressWarnings("unchecked")
	public List<DiskInfo> findAvailableDisk(String hostName){
		try {
			Configuration conf = CoreConfigUtils.create();
			String devNameFlag = conf.getString("disk.active.name.flag", "/dev");
			String hql = "from DiskInfo where devName LIKE :param and ip = :hostName";
			if (!devNameFlag.contains(",")) {
				Query query = createQuery(hql);
				query.setString("param", "%"+devNameFlag+"%");
				query.setString("hostName", hostName);
				return query.list();
			} else {
				List<DiskInfo> diskList = new ArrayList<DiskInfo>();
				String[] devFlags = devNameFlag.split(",");
				for (String flag : devFlags) {
					Query queryTemp = createQuery(hql);
					queryTemp.setString("param", "%"+flag+"%");
					queryTemp.setString("hostName", hostName);
					diskList.addAll(queryTemp.list());
				}
				return diskList;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
