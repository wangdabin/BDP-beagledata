package com.joe.agent.resource;

import java.util.List;

import javax.annotation.Resource;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.springframework.stereotype.Controller;

import com.joe.agent.service.IssuedService;
import com.joe.agent.utils.AgentUtils;
import com.joe.agent.utils.Constants;
import com.joe.agent.vo.LogFile;
import com.joe.core.annotation.RestResource;
import com.joe.core.i18n.I18nMessage;
import com.joe.core.version.BDPVersion;
import com.joe.core.vo.ReCode;
import com.joe.core.vo.ReCode.Data;
import com.sky.task.vo.Message;
import com.sky.task.vo.Task;
import com.sky.task.vo.TranOrder;
//
//@RestResource(name = AgentUtils.TNAME)
//@Path(BDPVersion.BASE_PATH + IssuedResource.PATH)
//@Controller
public class IssuedResource {

	public static final String PATH = "/issueds"; // 资源路径

	@Resource(name = "issuedService")
	private IssuedService issuedService;

	@Resource(name = "agentI18n")
	private I18nMessage agentI18n;

	

	/**
	 * 获取指定id任务的运行状况
	 * 
	 * @return
	 */
	@GET
	@Produces(value = { MediaType.TEXT_XML, MediaType.APPLICATION_XML,
			MediaType.APPLICATION_JSON })
	@Path("/{tid}")
	public Task getSingleTask(@PathParam("tid") long id) {
		System.out.println(agentI18n.getClass().toString());
		return issuedService.getTaskProgress(id);
	}

	/**
	 * 获取指定taskId任务下的子进程
	 * 
	 * @return
	 */
	@GET
	@Produces(value = { MediaType.TEXT_XML, MediaType.APPLICATION_XML,
			MediaType.APPLICATION_JSON })
	@Path("/task/{tid}")
	public List<TranOrder> getOrders(@PathParam("tid") long taskId) {
		System.out.println(agentI18n.getClass().toString());
		return issuedService.getTaskOrders(taskId);
	}

	/**
	 * 获取指定orderId对应的ShellOrder
	 * 
	 * @return
	 */
	@GET
	@Produces(value = { MediaType.TEXT_XML, MediaType.APPLICATION_XML,
			MediaType.APPLICATION_JSON })
	@Path("/orders/{tid}")
	public TranOrder getOrder(@PathParam("tid") long orderId) {
		System.out.println(agentI18n.getClass().toString());
		return issuedService.getOrder(orderId);
	}

	/**
	 * 获取所有任务运行状态
	 * 
	 * @return
	 */
	@GET
	@Produces(value = { MediaType.TEXT_XML, MediaType.APPLICATION_XML,
			MediaType.APPLICATION_JSON })
	@Path("/list")
	public List<Task> getTaskInfo() {
		System.out.println(agentI18n.getClass().toString());
		return issuedService.getAll();
	}

	/**
	 * 默认访问资源路径,返回全部任务运行状态
	 * 
	 * @return
	 */
	@GET
	@Produces(value = { MediaType.TEXT_XML, MediaType.APPLICATION_XML,
			MediaType.APPLICATION_JSON })
	public List<Task> getAll() {
		return issuedService.getAll();
	}

	/**
	 * 删除指定id代表的任务
	 * 
	 * @param id
	 * @return
	 */
	@DELETE
	@Produces(value = { MediaType.TEXT_XML, MediaType.APPLICATION_XML,
			MediaType.APPLICATION_JSON })
	@Path("/kill/{tid}")
	public ReCode kill(@PathParam("tid") long id) {
		ReCode reCode = issuedService.killTask(id);
		return reCode;
	}

	/**
	 * 获取所有任务的运行日志
	 * 
	 * @return
	 */
	@GET
	@Produces(value = { MediaType.TEXT_XML, MediaType.APPLICATION_XML,
			MediaType.APPLICATION_JSON })
	@Path("/logs")
	public List<LogFile> getLogs(@QueryParam("pageNo") int pageNo,
			@QueryParam("pageSize") int pageSize) {
		System.out.println(agentI18n.getClass().toString());
		return issuedService.taskLogs(pageNo, pageSize);
	}

	/**
	 * 获取指定taskId任务的运行日志
	 * 
	 * @return
	 */
	@GET
	@Produces(value = { MediaType.TEXT_XML, MediaType.APPLICATION_XML,
			MediaType.APPLICATION_JSON })
	@Path("/logs/{tid}")
	public LogFile getSingleLog(@PathParam("tid") long id) {
		System.out.println(agentI18n.getClass().toString());
		return issuedService.singleLog(id);
	}

	/**
	 * 回写任务执行状态
	 */
	@PUT
	@Produces(value = { MediaType.TEXT_XML, MediaType.APPLICATION_XML,
			MediaType.APPLICATION_JSON })
	@Path("/task/status/{hostIp}")
	public ReCode writeBackTask(Task task,@PathParam("hostIp") String hostIp) {
		System.out.println(agentI18n.getClass().toString());
		issuedService.update(task);
		ReCode reCode = new ReCode();
		reCode.setData(new Data(task.getId()));
		reCode.setMsg(agentI18n.getMessage(Constants.TASK_WRITE_BACK_SUCCESS));
		reCode.setErrcode(Constants.NOT_ERROR);
		reCode.setRet(Constants.RET_SUCCESS);
		//封装Intent,作为回掉传递消息体
		Message intent = new Message();
		intent.setMsgObj(task);
		intent.setMsg(hostIp);
		issuedService.removeAfterCallBack(intent);
		return reCode;
	}

	/**
	 * 回写具体的Order的执行进度
	 */
	@PUT
	@Produces(value = { MediaType.TEXT_XML, MediaType.APPLICATION_XML,
			MediaType.APPLICATION_JSON })
	@Path("/order/{oid}")
	public ReCode writeBackOrder(@PathParam("oid") long oid,
			@QueryParam("completion") double completion) {
		System.out.println(agentI18n.getClass().toString());
		issuedService.updateOrder(oid, completion);
		ReCode reCode = new ReCode();
		reCode.setData(new Data(oid));
		reCode.setMsg(agentI18n.getMessage(Constants.TASK_WRITE_BACK_SUCCESS));
		reCode.setErrcode(Constants.NOT_ERROR);
		reCode.setRet(Constants.RET_SUCCESS);
		return reCode;
	}
}
