package com.joe.monitor.filesystem;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;
import javax.ws.rs.Consumes;
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

@RestResource(name = FileSystemConstants.NAME)
@Path(BDPVersion.BASE_PATH + FileSystemConstants.PATH)
@Controller
public class FileSystemResource {

	@Resource(name = "fileSystemServiceImpl")
	private FileSystemService fsService;
	@Resource(name = "hostI18n")
	private I18nMessage i18nMessage;

	@POST
	@Produces(value = { MediaType.TEXT_XML, MediaType.APPLICATION_XML,
			MediaType.APPLICATION_JSON })
	@Path("/add")
	public ReCode add(DiskUnit fsData) {
		// 添加监控记录的时间
		fsData.setMonth(MonitorUtils.getMonth(fsData.getDiskInfo()
				.getTimeStamp()));
		fsData.setDay(MonitorUtils.getDay(fsData.getDiskInfo().getTimeStamp()));
		fsData.setHour(MonitorUtils
				.getHour(fsData.getDiskInfo().getTimeStamp()));
		fsData.setMinute(MonitorUtils.getMinute(fsData.getDiskInfo()
				.getTimeStamp()));

		fsService.add(fsData);
		
		
		try {
			MonitorUtils.setDiskMonitored(Float.parseFloat(String.valueOf(fsData.getUsedPercent())));
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

	@POST
	@Produces(value = { MediaType.TEXT_XML, MediaType.APPLICATION_XML,
			MediaType.APPLICATION_JSON })
	@Consumes(value = { "application/octet-stream" })
	@Path("/addbatch")
	public ReCode addBatch(List<DiskUnit> fsData) {
		// 添加监控记录的时间
		for (DiskUnit diskUnit : fsData) {
			diskUnit.setMonth(MonitorUtils.getMonth(diskUnit.getTimestamp()));
			diskUnit.setDay(MonitorUtils.getDay(diskUnit.getTimestamp()));
			diskUnit.setHour(MonitorUtils.getHour(diskUnit.getTimestamp()));
			diskUnit.setMinute(MonitorUtils.getMinute(diskUnit.getTimestamp()));
		}

		fsService.addBatch(fsData);
		ReCode reCode = new ReCode();
		reCode.setData(new Data());
		reCode.setMsg(i18nMessage.getMessage(Constants.SUCCESS_ADD));
		reCode.setErrcode(Constants.NOT_ERROR);
		reCode.setRet(Constants.RET_SUCCESS);
		return reCode;
	}

	/**
	 * 获取FileSystem固定值
	 * 
	 * @return
	 */
	@GET
	@Produces(value = { MediaType.TEXT_XML, MediaType.APPLICATION_XML,
			MediaType.APPLICATION_JSON })
	@Path("/list")
	public List<DiskInfo> getConsInfo() {
		return fsService.getAllDiskInfo();
	}

	@GET
	@Produces(value = { MediaType.TEXT_XML, MediaType.APPLICATION_XML,
			MediaType.APPLICATION_JSON })
	@Path("/statistic")
	public DiskCluster getStatInfo() {

		List<DiskUnit> diskUnitList = fsService.getRealTimeData();
		DiskCluster diskCluster = new DiskCluster();
		diskCluster.setDiskUnits(diskUnitList);
		return diskCluster;
	}

	/**
	 * 删除指定id的文件系统信息
	 * 
	 * @param id
	 * @return
	 */
	@DELETE
	@Produces(value = { MediaType.TEXT_XML, MediaType.APPLICATION_XML,
			MediaType.APPLICATION_JSON })
	@Path("/{hid}")
	public ReCode delete(@PathParam("hid") int id) {
		fsService.removeById(id);
		ReCode reCode = new ReCode();
		reCode.setData(new Data(id));
		reCode.setMsg(i18nMessage.getMessage(Constants.SUCCESS_ADD));
		reCode.setErrcode(Constants.NOT_ERROR);
		reCode.setRet(Constants.RET_SUCCESS);
		return reCode;
	}

}
