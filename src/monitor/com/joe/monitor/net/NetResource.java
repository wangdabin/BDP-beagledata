package com.joe.monitor.net;

import java.io.IOException;
import java.util.List;

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

@RestResource(name = NetConstants.NAME)
@Path(BDPVersion.BASE_PATH + NetConstants.PATH)
@Controller
public class NetResource {

	@Resource(name="netServiceImpl")
	private NetWorkService netWorkService;
	@Resource(name = "hostI18n")
	private I18nMessage i18nMessage;

	@POST
	@Produces(value = { MediaType.TEXT_XML, MediaType.APPLICATION_XML,
			MediaType.APPLICATION_JSON })
	@Path("/add")
	public ReCode add(NetInterfaceUnit netData) {
		netData.setDay(MonitorUtils.getDay(netData.getTimeStamp()));
		netData.setHour(MonitorUtils.getHour(netData.getTimeStamp()));
		netData.setMinute(MonitorUtils.getMinute(netData.getTimeStamp()));
		
		netWorkService.add(netData);
		
		
		try {
			MonitorUtils.setNetMonitored(Float.parseFloat(String.valueOf(netData.getSpeed())));
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
	 * 获取Net固定值
	 * 
	 * @return
	 */
	@GET
	@Produces(value = { MediaType.TEXT_XML, MediaType.APPLICATION_XML,
			MediaType.APPLICATION_JSON })
	@Path("/list")
	public List<NetInterfaceUnit> getConsInfo() {
		return netWorkService.getAll();
	}

	@GET
	@Produces(value = { MediaType.TEXT_XML, MediaType.APPLICATION_XML,
			MediaType.APPLICATION_JSON })
	@Path("/statistic")
	public NetInterfaceCluster getStatInfo() {

		List<NetInterfaceUnit> netUnitList = netWorkService.getRealTimeData();
		NetInterfaceCluster netCluster = new NetInterfaceCluster();
		netCluster.setNetUnits(netUnitList);
		return netCluster;
	}
	/**
	 * 删除指定id的网络配置
	 * @param id
	 * @return
	 */
	@DELETE
	@Produces(value = { MediaType.TEXT_XML, MediaType.APPLICATION_XML,
			MediaType.APPLICATION_JSON })
	@Path("/delete/{hid}")
	public ReCode delete(@PathParam("hid") int id) {
		netWorkService.removeById(id);
		ReCode reCode = new ReCode();
		reCode.setData(new Data(id));
		reCode.setMsg(i18nMessage.getMessage(Constants.SUCCESS_ADD));
		reCode.setErrcode(Constants.NOT_ERROR);
		reCode.setRet(Constants.RET_SUCCESS);
		return reCode;
	}

}
