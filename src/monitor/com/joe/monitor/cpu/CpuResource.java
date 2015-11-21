package com.joe.monitor.cpu;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;
import javax.management.MalformedObjectNameException;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.apache.commons.configuration.Configuration;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;

import com.joe.core.annotation.RestResource;
import com.joe.core.i18n.I18nMessage;
import com.joe.core.utils.CoreConfigUtils;
import com.joe.core.version.BDPVersion;
import com.joe.core.vo.ReCode;
import com.joe.core.vo.ReCode.Data;
import com.joe.host.utils.Constants;
import com.joe.host.vo.Host;
import com.joe.monitor.Monitor;
import com.joe.monitor.Monitored;
import com.joe.monitor.ObjectID;
import com.joe.monitor.beans.Cpu;
import com.joe.monitor.manager.MonitorManager;
import com.joe.monitor.vo.MonitorObjectID;
import com.joe.monitor.vo.SkyObjectID;
import com.joe.monitor.vo.SkyObjectID.TableBuilder;
import com.sky.monitor.utils.MonitorUtils;

/**
 * CPU 资源监控服务
 * 
 * @author liuzhijun
 * 
 */
@RestResource(name = CpuConstants.NAME)
@Path(BDPVersion.BASE_PATH + CpuConstants.PATH)
@Controller
public class CpuResource {
	private Logger LOG = Logger.getLogger(CpuResource.class);
	@Resource(name = "cpuServiceImpl")
	private CpuService cpuService;

	@Resource(name = "hostI18n")
	private I18nMessage cpuI18n;

	@POST
	@Produces(value = { MediaType.TEXT_XML, MediaType.APPLICATION_XML,
			MediaType.APPLICATION_JSON })
	@Path("/add")
	public ReCode add(CpuUnit cpuUnit) {

		// 添加监控记录的时间
		cpuUnit.setDay(MonitorUtils.getDay(cpuUnit.getTimeStamp()));
		cpuUnit.setHour(MonitorUtils.getHour(cpuUnit.getTimeStamp()));
		cpuUnit.setMinute(MonitorUtils.getMinute(cpuUnit.getTimeStamp()));

		cpuService.add(cpuUnit);

		try {
			MonitorUtils.setCpuMonitored(Float.parseFloat(String.valueOf(cpuUnit.getUser()+cpuUnit.getSys())));
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		ReCode reCode = new ReCode();
		reCode.setData(new Data());
		reCode.setMsg(cpuI18n.getMessage(Constants.SUCCESS_ADD));
		reCode.setErrcode(Constants.NOT_ERROR);
		reCode.setRet(Constants.RET_SUCCESS);
		return reCode;
	}

	@GET
	@Produces(value = {MediaType.TEXT_XML, MediaType.APPLICATION_XML,
			MediaType.APPLICATION_JSON })
	@Path("/{cpuid}")
	public CpuUnit getCpuUnit(@PathParam("cpuid") int id) {
		return cpuService.get(id);
	}

	/**
	 * 获取CPU固定值
	 * 
	 * @return
	 */
	@GET
	@Produces(value = { MediaType.TEXT_XML, MediaType.APPLICATION_XML,
			MediaType.APPLICATION_JSON })
	@Path("/list")
	public List<CpuUnit> getAll() {
		System.out.println(cpuI18n.getClass().toString());
		return cpuService.getAll();
	}

	/**
	 * 获取CPU固定值
	 * 
	 * @return
	 */
	@GET
	@Produces(value = { MediaType.TEXT_XML, MediaType.APPLICATION_XML,
			MediaType.APPLICATION_JSON })
	public List<CpuUnit> getAllDefault() {
		System.out.println(cpuI18n.getClass().toString());
		return cpuService.getAll();
	}

	/**
	 * 求整个集群的cpu总量和使用量
	 * 
	 * @return
	 */
	@GET
	@Produces(value = { MediaType.TEXT_XML, MediaType.APPLICATION_XML,
			MediaType.APPLICATION_JSON })
	@Path("/statistic")
	public CpuCluster getStatInfo() {
		List<CpuUnit> cpuInfos = this.getAll();
		LOG.info("Cpuinfos size is : " + cpuInfos.size());
		Set<CpuUnit> cpuSet = new HashSet<CpuUnit>();
		cpuSet.addAll(cpuInfos);
		CpuCluster cpuCluster = new CpuCluster();
		cpuCluster.setCpuUnits(cpuSet);
		cpuCluster.setDesc("testCpu");
		return cpuCluster;
	}

	/**
	 * 删除指定id的CPU
	 * 
	 * @param id
	 * @return
	 */
	@DELETE
	@Produces(value = { MediaType.TEXT_XML, MediaType.APPLICATION_XML,
			MediaType.APPLICATION_JSON })
	@Path("/delete/{hid}")
	public ReCode delete(@PathParam("hid") int id) {
		cpuService.removeById(id);
		ReCode reCode = new ReCode();
		reCode.setData(new Data(id));
		reCode.setMsg(cpuI18n.getMessage(Constants.SUCCESS_ADD));
		reCode.setErrcode(Constants.NOT_ERROR);
		reCode.setRet(Constants.RET_SUCCESS);
		return reCode;
	}

	
	@GET
	@Produces(value = { MediaType.TEXT_XML, MediaType.APPLICATION_XML,
			MediaType.APPLICATION_JSON })
	@Path("/test")
	public ReCode test(@QueryParam("f") float f) {

		try {
//			Host h = new Host();
//			h.setName("172.16.6.152");
//			h.setIp("172.16.6.152");
//			TableBuilder builder = TableBuilder.create("cpu").put("name", "monitor").put("ip", "172.16.6.152").put("hostname", "172.16.6.152");
//			ObjectID id = SkyObjectID.getObjectName("172.16.6.152", builder.build());
			Configuration conf = CoreConfigUtils.create();
			CpuMonitor cpuMonitor = (CpuMonitor) MonitorManager.getInstance(conf).getByObjectID(MonitorObjectID.getCpuMonitor());
			List<Monitored> cpuMonitored = cpuMonitor.getAllMonitored();// CpuMonitored.getInstance(conf, Constants.NAME, "cpu", h);
			System.out.println(cpuMonitored.get(0).getObjectID());
			Cpu cpuMbean = (Cpu) cpuMonitored.get(0).getMBean();
			cpuMbean.setUse(f);						//TODO  添加
		} catch (IOException e) {
			e.printStackTrace();
		} catch (NullPointerException e) {
			e.printStackTrace();
		}
		ReCode reCode = new ReCode();
		reCode.setData(new Data());
		reCode.setMsg(cpuI18n.getMessage(Constants.SUCCESS_ADD));
		reCode.setErrcode(Constants.NOT_ERROR);
		reCode.setRet(Constants.RET_SUCCESS);
		return reCode;
	}
}
