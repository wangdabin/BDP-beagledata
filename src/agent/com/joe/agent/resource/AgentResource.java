package com.joe.agent.resource;

import java.util.List;

import javax.annotation.Resource;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.springframework.stereotype.Controller;

import com.joe.agent.manager.AgentManager;
import com.joe.agent.service.AgentService;
import com.joe.agent.utils.AgentUtils;
import com.joe.agent.utils.Constants;
import com.joe.agent.vo.Agent;
import com.joe.core.annotation.RestResource;
import com.joe.core.config.CoreConfig;
import com.joe.core.i18n.I18nMessage;
import com.joe.core.version.BDPVersion;
import com.joe.core.vo.ReCode;
import com.joe.core.vo.ReCode.Data;
import com.sky.config.ConfigAble;

@RestResource(name = AgentUtils.NAME)
@Path(BDPVersion.BASE_PATH + AgentResource.PATH)
@Controller
public class AgentResource {
	
	public static final String PATH = "/agents"; //资源路径
	
	@Resource(name = "agentService")
    private AgentService agentService;
	
	@Resource(name = "agentI18n")
	private I18nMessage agentI18n;
	
	@Resource(name = CoreConfig.RESOURCE_NAME)
	private ConfigAble conf;
	
	/**
	 * agent端调用启动
	 * @param hostname
	 * @param ip
	 * @return
	 */
	@PUT
	@Produces(value = { MediaType.TEXT_XML, MediaType.APPLICATION_XML,
			MediaType.APPLICATION_JSON })
	@Path("/{hostname}/dostart")
	public ReCode doStart(@PathParam("hostname") String hostname,@QueryParam("ip") String ip){
		AgentManager manager = getAgentManager();
		manager.doStart(hostname, ip);
		ReCode reCode = new ReCode();
		reCode.setData(new Data(ip));
		reCode.setMsg(agentI18n.getMessage(Constants.AGENT_SUCCESS_ADD));
		reCode.setErrcode(Constants.NOT_ERROR);
		reCode.setRet(Constants.RET_SUCCESS);
		return reCode;
	}
	
	/**
	 * agent端调用停止
	 * @param hostname
	 * @param ip
	 * @return
	 */
	@PUT
	@Produces(value = { MediaType.TEXT_XML, MediaType.APPLICATION_XML,
			MediaType.APPLICATION_JSON })
	@Path("/{hostname}/dostop")
	public ReCode doStop(@PathParam("hostname") String hostname,@QueryParam("ip") String ip){
		AgentManager manager = getAgentManager();
		manager.doStop(hostname, ip);
		ReCode reCode = new ReCode();
		reCode.setData(new Data(ip));
		reCode.setMsg(agentI18n.getMessage(Constants.AGENT_SUCCESS_ADD));
		reCode.setErrcode(Constants.NOT_ERROR);
		reCode.setRet(Constants.RET_SUCCESS);
		return reCode;
	}
	
	/**
	 * agent端调用删除
	 * @param hostname
	 * @param ip
	 * @return
	 */
	@DELETE
	@Produces(value = { MediaType.TEXT_XML, MediaType.APPLICATION_XML,
			MediaType.APPLICATION_JSON })
	@Path("/{hostname}/dodelete")
	public ReCode doDelete(@PathParam("hostname") String hostname,@QueryParam("ip") String ip){
		AgentManager manager = getAgentManager();
		manager.doDelete(hostname, ip);
		ReCode reCode = new ReCode();
		reCode.setData(new Data(ip));
		reCode.setMsg(agentI18n.getMessage(Constants.AGENT_SUCCESS_ADD));
		reCode.setErrcode(Constants.NOT_ERROR);
		reCode.setRet(Constants.RET_SUCCESS);
		return reCode;
	}
	
	/**
	 * agent端调用删除
	 * @param hostname
	 * @param ip
	 * @return
	 */
	@PUT
	@Produces(value = { MediaType.TEXT_XML, MediaType.APPLICATION_XML,
			MediaType.APPLICATION_JSON })
	@Path("/{hostname}/doping")
	public ReCode doPing(@PathParam("hostname") String hostname,@QueryParam("ip") String ip){
		AgentManager manager = getAgentManager();
		manager.doPing(hostname, ip);
		ReCode reCode = new ReCode();
		reCode.setData(new Data(ip));
		reCode.setMsg(agentI18n.getMessage(Constants.AGENT_SUCCESS_ADD));
		reCode.setErrcode(Constants.NOT_ERROR);
		reCode.setRet(Constants.RET_SUCCESS);
		return reCode;
	}
	
	@POST
	@Produces(value = { MediaType.TEXT_XML, MediaType.APPLICATION_XML,
			MediaType.APPLICATION_JSON })
	@Path("/add")
	public ReCode add(Agent agent) {
		agentService.add(agent);
		ReCode reCode = new ReCode();
		reCode.setData(new Data());
		reCode.setMsg(agentI18n.getMessage(Constants.AGENT_SUCCESS_ADD));
		reCode.setErrcode(Constants.NOT_ERROR);
		reCode.setRet(Constants.RET_SUCCESS);
		return reCode;
	}
	
	/**
	 * 获取单个代理机器的信息(aid:主键)
	 * @return
	 */
	@GET
	@Produces(value = { MediaType.TEXT_XML, MediaType.APPLICATION_XML,
			MediaType.APPLICATION_JSON })
	@Path("/{aid}")
	public Agent getSingleInfo(@PathParam("aid") int id) {
		System.out.println(agentI18n.getClass().toString());
		return  agentService.get(id);
	}
	
	/**
	 * 根据ip获取单个代理机器的信息
	 * @return
	 */
	@GET
	@Produces(value = { MediaType.TEXT_XML, MediaType.APPLICATION_XML,
			MediaType.APPLICATION_JSON })
	@Path("/ip/{ip}")
	public Agent getAgentByIp(@PathParam("ip") String ip) {
		System.out.println(agentI18n.getClass().toString());
		return  agentService.getAgentByIp(ip);
	}
	
	/**
	 * 获取代理机器列表信息
	 * @return
	 */
	@GET
	@Produces(value = { MediaType.TEXT_XML, MediaType.APPLICATION_XML,
			MediaType.APPLICATION_JSON })
	@Path("/list")
	public List<Agent> getAgentInfo() {
		System.out.println(agentI18n.getClass().toString());
		return  agentService.getAll();
	}
	
	 /**
     * 默认访问资源路径,返回全部数据
     * @return
     */
    @GET
    @Produces(value = {MediaType.TEXT_XML,MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
    public List<Agent> getAll(){
    	return agentService.getAll();
    }
    
	/**
	 * 删除指定id的代理机器
	 * @param id
	 * @return
	 */
	@DELETE
	@Produces(value = { MediaType.TEXT_XML, MediaType.APPLICATION_XML,
			MediaType.APPLICATION_JSON })
	@Path("/{aid}")
	public ReCode delete(@PathParam("aid") int id) {
		agentService.removeById(id);
		ReCode reCode = new ReCode();
		reCode.setData(new Data(id));
		reCode.setMsg(agentI18n.getMessage(Constants.AGENT_SUCCESS_DELETE));
		reCode.setErrcode(Constants.NOT_ERROR);
		reCode.setRet(Constants.RET_SUCCESS);
		return reCode;
	}
	
	@PUT
	@Produces(value = { MediaType.TEXT_XML, MediaType.APPLICATION_XML,
			MediaType.APPLICATION_JSON })
	@Path("/run/{ip}")
	public ReCode updateRunStatus(@PathParam("ip") String ip ,
			@QueryParam("status")  String status) {
		agentService.updateStatus(ip,status,"run");
		ReCode reCode = new ReCode();
		reCode.setData(new Data());
		reCode.setMsg(agentI18n.getMessage(Constants.AGENT_SUCCESS_ADD));
		reCode.setErrcode(Constants.NOT_ERROR);
		reCode.setRet(Constants.RET_SUCCESS);
		return reCode;
	}
	
	@PUT
	@Produces(value = { MediaType.TEXT_XML, MediaType.APPLICATION_XML,
			MediaType.APPLICATION_JSON })
	@Path("/install/{ip}")
	public ReCode updateInstallStatus(@PathParam("ip") String ip ,
			@QueryParam("status")  double progress) {
		agentService.updateStatus(ip,String.valueOf(progress),"install");
		ReCode reCode = new ReCode();
		reCode.setData(new Data());
		reCode.setMsg(agentI18n.getMessage(Constants.AGENT_SUCCESS_ADD));
		reCode.setErrcode(Constants.NOT_ERROR);
		reCode.setRet(Constants.RET_SUCCESS);
		return reCode;
	}
	
	@PUT
	@Produces(value = { MediaType.TEXT_XML, MediaType.APPLICATION_XML,
			MediaType.APPLICATION_JSON })
	@Path("/update")
	public ReCode update(Agent agent) {
		agentService.update(agent);
		ReCode reCode = new ReCode();
		reCode.setData(new Data());
		reCode.setMsg(agentI18n.getMessage(Constants.AGENT_SUCCESS_ADD));
		reCode.setErrcode(Constants.NOT_ERROR);
		reCode.setRet(Constants.RET_SUCCESS);
		return reCode;
	}
	
	/**
	 * 
	 * @return
	 */
	private AgentManager getAgentManager(){
		return AgentManager.getManager(conf.getConf());
	}
}
