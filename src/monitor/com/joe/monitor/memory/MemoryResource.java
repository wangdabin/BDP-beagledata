package com.joe.monitor.memory;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.stereotype.Controller;

import com.joe.core.annotation.RestResource;
import com.joe.core.i18n.I18nMessage;
import com.joe.core.version.BDPVersion;
import com.joe.core.vo.ReCode;
import com.joe.core.vo.ReCode.Data;
import com.joe.host.utils.Constants;
import com.sky.monitor.utils.MonitorUtils;

@RestResource(name = MemoryConstants.NAME)
@Path(BDPVersion.BASE_PATH + MemoryConstants.PATH)
@Controller
public class MemoryResource {

	@Resource(name = "memServiceImpl")
	private MemoryService memService;
	@Resource(name = "hostI18n")
	private I18nMessage i18nMessage;

	/**
	 * 添加内存配置信息
	 * 
	 * @param memData
	 * @return
	 */
	@POST
	@Produces(value = { MediaType.TEXT_XML, MediaType.APPLICATION_XML,
			MediaType.APPLICATION_JSON })
	@Path("/add")
	public ReCode add(MemoryUnit memUnit) {
		memUnit.setDay(MonitorUtils.getDay(memUnit.getTimeStamp()));
		memUnit.setHour(MonitorUtils.getHour(memUnit.getTimeStamp()));
		memUnit.setMinute(MonitorUtils.getMinute(memUnit.getTimeStamp()));

		memService.add(memUnit);
		
		try {
			MonitorUtils.setMemMonitored(Float.parseFloat(String.valueOf(memUnit.getUsedPercent())));
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		ReCode reCode = new ReCode();
		reCode.setData(new Data());
		reCode.setMsg(i18nMessage.getMessage(Constants.SUCCESS_ADD));
		reCode.setErrcode(Constants.NOT_ERROR);
		reCode.setRet(Constants.RET_SUCCESS);
		return reCode;
	}

	/**
	 * 获取Memory固定信息
	 * 
	 * @return
	 */
	@GET
	@Produces(value = { MediaType.TEXT_XML, MediaType.APPLICATION_XML,
			MediaType.APPLICATION_JSON })
	@Path("/list")
	public List<MemoryUnit> getConsInfo() {
		return memService.getAllMemInfoGroupByHost();
	}

	@GET
	@Produces(value = { MediaType.TEXT_XML, MediaType.APPLICATION_XML,
			MediaType.APPLICATION_JSON })
	@Path("/statistic")
	public MemoryCluster getMemoryStatistic() {
		List<MemoryUnit> memUnits = memService.getAllMemInfoGroupByHost();
		Set<MemoryUnit> set = new HashSet<MemoryUnit>();
		set.addAll(memUnits);
		MemoryCluster memData = new MemoryCluster();
		memData.setUnits(set);
		return memData;
	}

	/**
	 * 删除指定id的Memory信息
	 * 
	 * @param id
	 * @return
	 */
	@DELETE
	@Produces(value = { MediaType.TEXT_XML, MediaType.APPLICATION_XML,
			MediaType.APPLICATION_JSON })
	@Path("/{hid}")
	public ReCode delete(@PathParam("hid") int id) {
		memService.removeById(id);
		ReCode reCode = new ReCode();
		reCode.setData(new Data(id));
		reCode.setMsg(i18nMessage.getMessage(Constants.SUCCESS_ADD));
		reCode.setErrcode(Constants.NOT_ERROR);
		reCode.setRet(Constants.RET_SUCCESS);
		return reCode;
	}
}
