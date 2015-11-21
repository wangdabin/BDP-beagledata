package com.joe.monitor.resource;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.springframework.stereotype.Controller;

import com.joe.core.annotation.RestResource;
import com.joe.core.config.CoreConfig;
import com.joe.core.version.BDPVersion;
import com.joe.core.vo.ReCode;
import com.joe.core.vo.ReCode.Data;
import com.joe.monitor.Monitor;
import com.joe.monitor.callback.url.URLManager;
import com.joe.monitor.manager.Manager;
import com.joe.monitor.manager.MonitorManager;
import com.joe.monitor.vo.MonitorVO;
import com.sky.config.ConfigAble;
import com.sky.monitor.utils.Constants;

/**
 * 
 * 监控资源
 * 
 * @author Joe
 * 
 */
@RestResource(name = Constants.NAME)
@Path(BDPVersion.BASE_PATH + MonitorResource.PATH)
@Produces(value = {MediaType.APPLICATION_JSON,MediaType.TEXT_XML,MediaType.APPLICATION_XML})
@Consumes(value = {MediaType.APPLICATION_JSON,MediaType.TEXT_XML,MediaType.APPLICATION_XML})
@Controller
public class MonitorResource {

	public static final String PATH = "/monitor";
	
	@Resource(name = CoreConfig.RESOURCE_NAME)
	private ConfigAble confAble;
	
	@Resource(name = "URLManager")
	private URLManager urlManager;
	
	/**
	 * 当前系统支持的database
	 * @return
	 */
	@GET
	
	@Path("/support")
	public List<MonitorVO> getSupperts(){
		List<MonitorVO> monitors = new ArrayList<MonitorVO>();
		Manager manager = MonitorManager.getInstance(confAble.getConf());
		for(Monitor monitor : manager.getMonitors()){
			monitors.add(new MonitorVO(monitor.getMonitorID().getType(),monitor.getMonitorID().getName()));
		}
		return monitors;
	}
	
	@POST
	@Path("/register/{type}/{name}")
	public ReCode register(@PathParam("type") String type,@PathParam("name") String name,@QueryParam("url") String url){
		urlManager.register(type, name, url);
		ReCode reCode = new ReCode();
		reCode.setData(new Data(type + ":name=" + name + ",url=" + url));
		reCode.setMsg("regist success");
		reCode.setErrcode(Constants.NOT_ERROR);
		reCode.setRet(Constants.RET_SUCCESS);
		return reCode;
	}
	
	@DELETE
	@Path("/register/{type}/{name}")
	public ReCode delete(@PathParam("type") String type,@PathParam("name") String name,@QueryParam("url") String url){
		urlManager.remove(type, name, url);
		ReCode reCode = new ReCode();
		reCode.setData(new Data(type + ":name=" + name + ",url=" + url));
		reCode.setMsg("Delete success");
		reCode.setErrcode(Constants.NOT_ERROR);
		reCode.setRet(Constants.RET_SUCCESS);
		return reCode;
	}
}
