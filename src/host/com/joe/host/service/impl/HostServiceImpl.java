package com.joe.host.service.impl;

import java.io.IOException;
import java.io.Serializable;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.joe.agent.client.ConfigClient;
import com.joe.agent.handler.AgentCommandWorker;
import com.joe.agent.service.AgentService;
import com.joe.agent.vo.Agent;
import com.joe.core.config.CoreConfig;
import com.joe.core.support.CommandWorker;
import com.joe.core.utils.CoreConfigUtils;
import com.joe.core.utils.IPUtil;
import com.joe.core.vo.Total;
import com.joe.host.dao.HostCheck;
import com.joe.host.dao.HostDAO;
import com.joe.host.init.BasicComponentInstaller;
import com.joe.host.init.Status;
import com.joe.host.service.HostService;
import com.joe.host.ssh.SSHCheckPool;
import com.joe.host.utils.DataWraperUtils;
import com.joe.host.utils.HostUtils;
import com.joe.host.utils.NumberUtils;
import com.joe.host.vo.AllHostSummary;
import com.joe.host.vo.EchartSeries;
import com.joe.host.vo.Echarts;
import com.joe.host.vo.FindResult;
import com.joe.host.vo.Host;
import com.joe.host.vo.HostDisk;
import com.joe.host.vo.HostRange;
import com.joe.host.vo.HostSummary;
import com.joe.monitor.cpu.CpuDao;
import com.joe.monitor.cpu.CpuUnit;
import com.joe.monitor.filesystem.DiskInfo;
import com.joe.monitor.filesystem.DiskInfoDao;
import com.joe.monitor.filesystem.DiskUnit;
import com.joe.monitor.filesystem.FileSystemService;
import com.joe.monitor.filesystem.FsDao;
import com.joe.monitor.memory.MemoryDao;
import com.joe.monitor.memory.MemoryUnit;
import com.joe.monitor.net.NetDao;
import com.joe.monitor.net.NetInfo;
import com.joe.monitor.net.NetInfoDao;
import com.joe.monitor.net.NetInterfaceUnit;
import com.joe.system.vo.SysDictionary;
import com.joe.system.vo.SysDictionaryDao;
import com.sky.config.Configed;
import com.sky.service.define.CommandDefine;

/**
 * 
 * @author Joe
 * 
 */
@Service("hostService")
public class HostServiceImpl implements HostService {
    public static final Logger LOG = Logger.getLogger(HostServiceImpl.class);
    @Resource(name = "hostDAO")
    private HostDAO hostDAO;
    
    @Resource(name = "memoryDao")
    private MemoryDao memoryDao;
    
    @Resource(name = "cpuDao")
    private CpuDao cpuDao;
    
    @Resource(name = "fileSystemServiceImpl")
    private FileSystemService fsService;
    
    @Resource(name = "netDao")
    private NetDao netDao;
    
    @Resource(name = "netInfoDao")
    private NetInfoDao netInfoDao;
    
    @Resource(name = "diskInfoDao")
    private DiskInfoDao diskInfoDao;
    
    @Resource(name = "fsDao")
    private FsDao fsDao;
    
    @Resource(name = "agentService")
    private AgentService agentService;

    @Resource(name = "sshHostCheck")
    private HostCheck hostCheck;
    
    @Resource(name = CoreConfig.RESOURCE_NAME)
    private Configed conf;
    
    @Resource(name = "sysDictionaryDao")
    private SysDictionaryDao sysDictionaryDao;
    
    private CommandWorker commandWorker;
    private ConfigClient configClient;
    
    public HostServiceImpl(){
    	try {
    		Configuration conf = CoreConfigUtils.create();
    		this.commandWorker = new AgentCommandWorker(conf);
    		this.configClient = new ConfigClient(conf);
    	} catch (IOException e) {
			throw new RuntimeException(e);
		}
    }
    
    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void add(Host host) {
	try {
	    Host findHost = findByHostName(host.getIp());
	    if (findHost == null) {
		hostDAO.save(host);
		// 在添加host的时候同时初始化对应的agent
		Agent agent = new Agent();
		agent.setIp(host.getIp());
		agentService.add(agent);
		BasicComponentInstaller installer = new BasicComponentInstaller(host,agentService);
		installer.install();
	    }else {
		Agent agent = agentService.getAgentByIp(host.getIp());
		if(agent.getInstallProgress() == Status.FAIL) {
		    BasicComponentInstaller installer = new BasicComponentInstaller(host,agentService);
		    installer.install();
		}
	    }
	} catch (Exception e) {
	    throw new RuntimeException(e);
	}
    }

    @Override
    public Host get(Serializable id) {
	return hostDAO.get(id);
    }

    @Override
    public List<Host> getAll() {
	return hostDAO.getAll();
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void remove(Host transientObject) {
	hostDAO.remove(transientObject);
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void removeById(Serializable id) {
	Host host = hostDAO.get(id);
	Agent agent = agentService.getAgentByIp(host.getIp());
	agentService.remove(agent);
	hostDAO.removeById(id);
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public Host update(Host t) {
	hostDAO.saveOrUpdate(t);
	return t;
    }

    @Override
    public Host findByHostName(String hostName) {
	return hostDAO.findUniqueBy("ip", hostName);
    }

    @Override
    public Total getTotal() {
	long total = this.hostDAO.getTotal();
	return new Total(HostUtils.NAME, total);
    }

    @Override
    public FindResult find(HostRange hostRange) {
	List<Host> hosts = new ArrayList<Host>();
	long startLong = IPUtil.ipToLong(hostRange.getStartHost());
	long endLong = IPUtil.ipToLong(hostRange.getStopHost());
	for (long i = startLong; i <= endLong; i++) {
	    String ip = IPUtil.longToIP(i);
	    Host host = new Host();
	    host.setIp(ip);
	    host.setRoot(hostRange.getUser());
	    host.setRootPass(hostRange.getPassword());
	    hosts.add(host);
	}
	SSHCheckPool pool = new SSHCheckPool(hostCheck, hosts.size());
	FindResult result = pool.process(hosts);
	pool.destroy();
	return result;
    }

    @Override
    public FindResult find(Host[] hosts) {
	SSHCheckPool pool = new SSHCheckPool(hostCheck, hosts.length);
	List<Host> hostList = Arrays.asList(hosts);
	FindResult result = pool.process(hostList);
	pool.destroy();
	return result;
    }

    @Override
    public void addBatch(List<Host> instanceBatch) {
	// TODO Auto-generated method stub

    }
    @Override
    public Status monitor(Host host) {
	Agent agent = agentService.getAgentByIp(host.getIp());
	double progress = agent.getInstallProgress();
	Long startTime = agent.getStartTime();
	// 当监控状态为99%的时候并且startTime未设置,在对应的数据库中添加对应的初始启动时间
	if (Status.START == progress) {
	    long currentTime = System.currentTimeMillis();
	    if (startTime != null){//如果启动之后再特定的超时时间agent没有发送对应的响应,将安装状态置为失败
		long interval = currentTime - startTime;
		long timeout = conf.getConf().getLong("agent.timeout",60000);//添加默认超时响应时间
		if(interval > timeout) {
		    progress = Status.FAIL;
		    agentService.updateInstallProgress(host.getIp(),progress);
		    LOG.debug(host.getIp() + "...agent启动超时,安装失败!");
		    //TODO 这里不进行对应的回滚操作,当client执行删除主机的时候,执行删除java和agent目录
//		    BasicComponentInstaller installer = new BasicComponentInstaller(host, agentService);
//		    installer.rollbackOrders();//执行回滚操作
		}
	    }
	}
	Status status = new Status();
	status.setProgress(progress);
	return status;
    }

	@SuppressWarnings("static-access")
	@Override
	public AllHostSummary findAllHostSummary() {
		int dead = 0;
		int active = 0;
		SysDictionary hostsquence = sysDictionaryDao.findUniqueBy("dictName", "index.hosts");
		List<String> sqList = new ArrayList<String>();
		if (hostsquence != null) {
			String dictValue = hostsquence.getDictValue();
			if (!StringUtils.isEmpty(dictValue)) {
				sqList = Arrays.asList(dictValue.split(","));
			}
		}
		//根据host表中的每个hostIp获取serviceHost
		List<Host> sortHosts = new LinkedList<Host>();
		List<Host> allHost = hostDAO.getAll();
		Map<String,Host> map = new HashMap<String,Host>();
		for (Host host : allHost) {
			if (host.STATUS_ACTIVE.equals(host.getStatus())) {
				active++;
			} else {
				dead++;
			}
			map.put(String.valueOf(host.getId()), host);
		}
		if (sqList.size() > 0) {
			for (int i = 0; i < sqList.size(); i++) {
				sortHosts.add(i, map.get(sqList.get(i)));
			}
		}
		AllHostSummary shs = new AllHostSummary();
		shs.setDead(dead);
		shs.setActive(active);
		shs.setHostList(sortHosts);
		return shs;
	}

	@Override
	public HostSummary findHostSummary(String hostName) {
		HostSummary hs = new HostSummary();
		//内存
		MemoryUnit memory = memoryDao.getLatestByIp(hostName);
		hs.setMemoryTotal(memory.getTotal());
		hs.setMemoryUsed(memory.getUsedPercent());
		//cpu
		CpuUnit cpu = cpuDao.getLatestByIp(hostName);
//		double total_pre = cpu.getUser()+cpu.getSys()+cpu.getNice()+cpu.getIdle()+cpu.getWait()+cpu.getIrq()+cpu.getSoftIrq();
		double total_pre = 1d - cpu.getIdle();
		hs.setCpuUsed(total_pre);
		//网络计算，前一个时间点网卡读写和-后一个时间时间点读写和/秒，分有效网卡
		List<NetInfo> netList = netInfoDao.getAll();
		List<NetInfo> ethList = new ArrayList<NetInfo>();
		List<NetInfo> emList = new ArrayList<NetInfo>();
		for (NetInfo netInfo : netList) {
			if (netInfo.getName().contains("eth")) {
				ethList.add(netInfo);
			}
			if (netInfo.getName().contains("em")) {
				emList.add(netInfo);
			}
		}
		long ethpre = 0;
		long ethscond = 0;
		double time = 0;
		for (NetInfo netInfo : ethList) {
			List<NetInterfaceUnit> netUnit = netDao.findBy("netInfo.name", netInfo.getName(), false, "timeStamp");
			if (netUnit != null && netUnit.size() >1) {
				ethpre += netUnit.get(0).getRxBytes();
				ethpre += netUnit.get(0).getTxBytes();
				ethscond += netUnit.get(1).getRxBytes();
				ethscond +=netUnit.get(1).getTxBytes();
				int flag = 0;
				if (flag == 0) {
					time = (netUnit.get(0).getTimeStamp() - netUnit.get(1).getTimeStamp()) / 1000;
					flag++;
				}
			}
		}
		long empre = 0;
		long emscond = 0;
		for (NetInfo netInfo : emList) {
			List<NetInterfaceUnit> netUnit = netDao.findBy("info_name", netInfo.getName(), false, "timestamp");
			if (netUnit != null && netUnit.size() >1) {
				empre += netUnit.get(0).getRxBytes();
				empre += netUnit.get(0).getTxBytes();
				emscond += netUnit.get(1).getRxBytes();
				emscond +=netUnit.get(1).getTxBytes();
			}
		}
		if (time != 0) {
			double ethPer = (ethpre-ethscond)/time;
			double emPer = (empre - emscond)/time;
			
			hs.setEthUsed(ethPer);
			hs.setEmUsed(emPer);
		}
		//硬盘分多个盘,分有效磁盘
		List<DiskUnit> realTimeDiskList = fsService.getRealTimeData();
		List<HostDisk> diskList = new ArrayList<HostDisk>();
		for (DiskUnit diskUnit : realTimeDiskList) {
			if (hostName.equals(diskUnit.getDiskInfo().getIp())) {
				HostDisk disk = new HostDisk();
				disk.setDiskTotal(diskUnit.getDiskInfo().getTotal());
				disk.setDiskUsed(diskUnit.getUsedPercent());
				disk.setDiskName(diskUnit.getDiskInfo().getDevName());
				diskList.add(disk);
			}
		}
		hs.setDiskList(diskList);
		return hs;
	}

	@Override
	public Echarts findCpuEcharts(String hostName, long stime, long etime) {
		List<CpuUnit> cpuList = cpuDao.findCpuEcharts(hostName, stime, etime);
		Echarts  chart = new Echarts();
		
		List<String> xAxisData = new ArrayList<String>();
		List<EchartSeries> seriesData = new ArrayList<EchartSeries>();
		EchartSeries ss = new EchartSeries();
		ss.setName("cpu使用率");
		List<String> data = new ArrayList<String>();
		for (CpuUnit cpu : cpuList) {
			SimpleDateFormat  sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			xAxisData.add(sdf.format(cpu.getTimeStamp()));
			data.add(NumberUtils.DFormatTwoDecimal(100-(cpu.getIdle())*100));
		}
		ss.setData(data);
		seriesData.add(ss);
		chart.setSeriesData(seriesData);
		chart.setxAxisData(xAxisData);
		return chart;
	}
	/**
	 * 获取一段时间内内存的使用情况
	 */
	@Override
	public Echarts findMemoryEcharts(String hostName, long stime, long etime) {
		List<MemoryUnit> memoryList = memoryDao.findCpuEcharts(hostName, stime, etime);
		Echarts chart = new Echarts();
		
		List<String> xAxisData = new ArrayList<String>();
		List<EchartSeries> seriesData = new ArrayList<EchartSeries>();
		EchartSeries ss = new EchartSeries();
		ss.setName("内存使用量");
		List<String> data = new ArrayList<String>();
		for (MemoryUnit memory : memoryList) {
			SimpleDateFormat  sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			xAxisData.add(sdf.format(memory.getTimeStamp()));
			data.add(DataWraperUtils.getGBData(memory.getUsed()));
		}
		ss.setData(data);
		seriesData.add(ss);
		chart.setSeriesData(seriesData);
		chart.setxAxisData(xAxisData);
		return chart;
	}

	@Override
	public Echarts findDiskEcharts(String hostName, long stime, long etime) {
		List<DiskInfo> availableDiskList = diskInfoDao.findAvailableDisk(hostName);
		Echarts chart = new Echarts();
		
		List<String> xAxisData = new ArrayList<String>();
		List<EchartSeries> seriesData = new ArrayList<EchartSeries>();
		int xaxis = 0;
		for (DiskInfo diskInfo : availableDiskList) {
			List<DiskUnit> diskList = fsDao.findDiskEcharts(hostName,diskInfo.getDevName(), stime, etime);
			xaxis++;
			EchartSeries ss = new EchartSeries();
			ss.setName(diskInfo.getDevName());
			List<String> data = new ArrayList<String>();
			for (DiskUnit disk : diskList) {
				data.add(DataWraperUtils.getGBData(disk.getUsed()));
				if (xaxis == 1) {
					SimpleDateFormat  sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					xAxisData.add(sdf.format(disk.getTimestamp()));
				}
			}
			ss.setData(data);
			seriesData.add(ss);
		}
		chart.setSeriesData(seriesData);
		chart.setxAxisData(xAxisData);
		return chart;
	}

	@Override
	public Echarts findNetEcharts(String hostName, long stime, long etime) {
		List<NetInfo> availableNetList = netInfoDao.findAvailableNet(hostName);
		Echarts chart = new Echarts();
		List<String> xAxisData = new ArrayList<String>();
		List<EchartSeries> seriesData = new ArrayList<EchartSeries>();
		int xaxis = 0;
		for (NetInfo netInfo : availableNetList) {
			List<NetInterfaceUnit> netList = netDao.findNetEcharts(hostName,netInfo.getName(), stime, etime);
			EchartSeries ss = new EchartSeries();
			ss.setName(netInfo.getName());
			List<String> data = new ArrayList<String>();
			xaxis++;
			for (NetInterfaceUnit net : netList) {
				data.add(DataWraperUtils.getGBData(net.getSpeed()));
				if (xaxis == 1) {
					SimpleDateFormat  sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					xAxisData.add(sdf.format(net.getTimeStamp()));
				}
			}
			ss.setData(data);
			seriesData.add(ss);
		}
		chart.setSeriesData(seriesData);
		chart.setxAxisData(xAxisData);
		return chart;
	}

	@Override
	public void sshKeygen() {
		List<Host> all = getAll();
		List<String> hosts = new ArrayList<String>();
		for(Host host: all){
			hosts.add(host.getIp());
		}
		CommandDefine sshKeygen = new CommandDefine();
		sshKeygen.setName("sshKeygen");
		sshKeygen.setBasePath("/opt/bigdata-agent/bin");
		sshKeygen.setShell("ssh-keygen.sh");
		sshKeygen.setVersion("cdh5.3.0");
		this.commandWorker.execute(hosts,sshKeygen);
	}
	@Override
	public void clusterTrust() throws Exception {
		//将所有host上的id_rsa.pub进行汇总,生成对应的authorized_keys,并发送到各个主机上
		StringBuffer authorized_keys  = new StringBuffer();
		List<Host> all = getAll();
		for(Host host: all){
			String idRsaPubFile = "/root/.ssh/id_rsa.pub";
			idRsaPubFile = URLEncoder.encode(idRsaPubFile, "UTF-8");
			String authorized_key = configClient.findTextContent(host.getIp(),idRsaPubFile);
			authorized_keys.append(authorized_key).append("\n");
		}
		for(Host host:all){
			String idRsaPubFile = "/root/.ssh/authorized_keys";
			idRsaPubFile = URLEncoder.encode(idRsaPubFile, "UTF-8");
			configClient.sendTextContent(host.getIp(),idRsaPubFile, authorized_keys.toString());
		}
	}
}
