package com.joe.agent.resource;

import java.util.List;

import javax.annotation.Resource;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;

import com.joe.agent.manager.TaskManager;
import com.joe.agent.service.IssuedService;
import com.joe.agent.utils.AgentUtils;
import com.joe.agent.utils.Constants;
import com.joe.agent.vo.LogFile;
import com.joe.core.annotation.RestResource;
import com.joe.core.config.CoreConfig;
import com.joe.core.i18n.I18nMessage;
import com.joe.core.version.BDPVersion;
import com.joe.core.vo.ReCode;
import com.joe.core.vo.ReCode.Data;
import com.sky.config.ConfigAble;
import com.sky.task.vo.Message;
import com.sky.task.vo.Task;
import com.sky.task.vo.TranOrder;

/**
 * agent会调用
 * @author qiaolong
 *
 */
@RestResource(name = AgentUtils.TNAME,type=AgentUtils.TTYPE)
@Path(BDPVersion.BASE_PATH + TaskResource.PATH)
@Produces(value = { MediaType.APPLICATION_JSON,MediaType.TEXT_XML, MediaType.APPLICATION_XML})
@Consumes(value = { MediaType.APPLICATION_JSON,MediaType.TEXT_XML, MediaType.APPLICATION_XML})
@Controller
public class TaskResource {
	public static final Logger LOG = Logger.getLogger(TaskResource.class);
	
	public static final String PATH = "/tasks"; //资源路径
	
	@Resource(name = "issuedService")
	private IssuedService issuedService;

	@Resource(name = "agentI18n")
	private I18nMessage agentI18n;
	
	@Resource(name = CoreConfig.RESOURCE_NAME)
	private ConfigAble conf;
	

	/**
	 * 获取指定id任务的运行状况
	 * 
	 * @return
	 */
	@GET
	@Path("/{tid}")
	public Task getSingleTask(@PathParam("tid") long id) {
		LOG.debug("Get task by id" + id);
		return issuedService.getTaskProgress(id);
	}

	/**
	 * 获取指定taskId任务下的子进程
	 * 
	 * @return
	 */
	@GET
	@Path("/{tid}/orders")
	public List<TranOrder> getOrders(@PathParam("tid") long taskId) {
		LOG.debug("Get orders by task id" + taskId);
		return issuedService.getTaskOrders(taskId);
	}

	/**
	 * 获取指定orderId对应的ShellOrder
	 * 
	 * @return
	 */
	@GET
	@Path("/task/orders/{oid}")
	public TranOrder getOrder(@PathParam("oid") long orderId) {
		return issuedService.getOrder(orderId);
	}

	/**
	 * 获取所有任务运行状态
	 * 
	 * @return
	 */
	@GET
	@Path("/list")
	public List<Task> getTaskInfo() {
		LOG.debug(agentI18n.getClass().toString());
		return issuedService.getAll();
	}

	/**
	 * 默认访问资源路径,返回全部任务运行状态
	 * 
	 * @return
	 */
	@GET
	public List<Task> getAll() {
		return issuedService.getAll();
	}

	/**
	 * 删除指定id代表的任务
	 * @param id
	 * @return
	 */
	@PUT
	@Path("/{tid}/kill")
	public ReCode kill(@PathParam("tid") long id) {
		ReCode recode = issuedService.killTask(id);
		return recode;
	}

	/**
	 * 获取所有任务的运行日志
	 * 
	 * @return
	 */
	@GET
	@Path("/logs")
	public List<LogFile> getLogs(@QueryParam("pageNo") int pageNo,
			@QueryParam("pageSize") int pageSize) {
		LOG.debug(agentI18n.getClass().toString());
		return issuedService.taskLogs(pageNo, pageSize);
	}

	/**
	 * 获取指定taskId任务的运行日志
	 * 
	 * @return
	 */
	@GET
	@Path("/logs/{tid}")
	public LogFile getSingleLog(@PathParam("tid") long id) {
		LOG.debug(agentI18n.getClass().toString());
		return issuedService.singleLog(id);
	}

	/**
	 * 任务执行完成后的回写
	 * @param hostname
	 * @param ip
	 * @param task
	 * @return
	 */
	@PUT
	@Path("/{hostname}/{tid}/status")
	public ReCode taskFinish(@PathParam("hostname") String hostname,@QueryParam("ip") String ip,@PathParam("tid") long tid,Task task) {
		LOG.debug(agentI18n.getClass().toString());
		TaskManager manager = getManager();
		manager.taskFinish(hostname, ip, task);
		ReCode reCode = new ReCode();
		reCode.setData(new Data(tid));
		reCode.setMsg(agentI18n.getMessage(Constants.TASK_WRITE_BACK_SUCCESS));
		reCode.setErrcode(Constants.NOT_ERROR);
		reCode.setRet(Constants.RET_SUCCESS);
		//封装Intent,作为回掉传递消息体
		Message intent = new Message();
		intent.setMsgObj(task);
		intent.setMsg(ip);
		issuedService.removeAfterCallBack(intent);
		return reCode;
	}

	/**
	 * 
	 * @param hostname
	 * @param oid
	 * @param ip
	 * @param completion
	 * @return
	 */
	@PUT
	@Path("/{hostname}/task/{oid}")
	public ReCode orderCompletion(@PathParam("hostname") String hostname,@PathParam("oid") long oid,@QueryParam("ip") String ip,
			@QueryParam("completion") double completion) {
		TaskManager manager = getManager();
		TranOrder order = issuedService.getOrder(oid);
		manager.orderCompletion(hostname, ip, order, completion);
		ReCode reCode = new ReCode();
		reCode.setData(new Data(oid));
		reCode.setMsg(agentI18n.getMessage(Constants.TASK_WRITE_BACK_SUCCESS));
		reCode.setErrcode(Constants.NOT_ERROR);
		reCode.setRet(Constants.RET_SUCCESS);
		return reCode;
	}
	
	/**
	 * 
	 * @return
	 */
	private TaskManager getManager(){
		return TaskManager.getManager(conf.getConf());
	}
}
