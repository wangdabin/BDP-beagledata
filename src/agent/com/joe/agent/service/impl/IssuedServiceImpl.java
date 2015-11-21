package com.joe.agent.service.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.joe.agent.dao.IssuedDao;
import com.joe.agent.dao.LogFileDao;
import com.joe.agent.dao.TranOrderDao;
import com.joe.agent.service.IssuedService;
import com.joe.agent.service.supportor.TaskHandler;
import com.joe.agent.utils.Constants;
import com.joe.agent.utils.DateUtils;
import com.joe.agent.vo.LogFile;
import com.joe.core.config.CoreConfig;
import com.joe.core.rest.RestClient;
import com.joe.core.vo.ReCode;
import com.joe.core.vo.ReCode.Data;
import com.sky.config.ConfigAble;
import com.sky.service.vo.TaskHost;
import com.sky.task.vo.Message;
import com.sky.task.vo.Task;
import com.sky.task.vo.TranOrder;

@Service("issuedService")
@Transactional
public class IssuedServiceImpl implements IssuedService {

	// 日志标识
	public static Logger Log = Logger.getLogger(IssuedServiceImpl.class); // 日志类

	@Resource(name = "issuedDao")
	private IssuedDao issuedDao;

	@Resource(name = "logFileDao")
	private LogFileDao logFileDao;

	@Resource(name = "tranOrderDao")
	private TranOrderDao tranOrderDao;

	// 指定配置项
	@Resource(name = CoreConfig.RESOURCE_NAME)
	private ConfigAble config;

	@Resource(name="handleService")
	private TaskHandler handler ;

	public IssuedServiceImpl() {
		Log.info("开始准备CallBackContainer！！！");
	}

	public Task get(Serializable id) {
		return issuedDao.get(id);
	}

	public List<Task> getAll() {
		return issuedDao.getAll();
	}

	public void add(Task instance) {
		issuedDao.save(instance);
	}

	public void remove(Task transientObject) {
		issuedDao.remove(transientObject);
	}

	public void removeById(Serializable id) {
		issuedDao.removeById(id);
	}

	public Task update(Task t) {
		issuedDao.saveOrUpdate(t);
		return t;
	}

	public IssuedDao getIssuedDao() {
		return issuedDao;
	}

	public void setIssuedDao(IssuedDao issuedDao) {
		this.issuedDao = issuedDao;
	}

	@Transactional
	public long setOwner(Task task) {

		String message = "任务[" + task.getName() + "]入库中,开始时间['"
				+ DateUtils.getStartTime() + "']";
		Log.info(message);
		add(task);
		String endMessage = "任务[" + task.getName() + "]入库结束,开始时间['"
				+ DateUtils.getStartTime() + "']";
		Log.info(endMessage);
		return task.getId();
	}

	/**
	 * 提交任务到各个agent节点处理
	 * 
	 * @param task
	 * @return 任务处理标识
	 * @throws InterruptedException
	 */
	private boolean processTask(Task task, int oType, String operUrl)
			throws Exception {
		boolean sucFlag = false;
//		Set<TaskHost> hosts = task.getHosts();
//		sucFlag = commitTask(task, oType, operUrl, hosts);

		return sucFlag;
	}

	private boolean commitTask(Task task, int oType, String operUrl,
			Set<TaskHost> hosts) throws Exception {
		boolean sucFlag = false;
		Object obj;
		// 1、执行调度处理程序
		for (TaskHost ht : hosts) {
			sucFlag = true;
			// 2、提交任务到对应的agent节点处理
			obj = getResult(task, ht, oType, operUrl);
			// 3、任务提交完成状况
			sucFlag &= processResult(obj);
		}
		return sucFlag;
	}

	/**
	 * 解析JSON数据
	 * 
	 * @param json
	 * @return
	 */
	private boolean processResult(Object obj) {
		boolean sucFlag = false;
		if (obj != null) {
			// 结果类型转换
			ReCode reCode = (ReCode) obj;
			// 是否提交成功,ret=0 为成功
			if (reCode != null && reCode.getRet() == 0) {
				sucFlag = true;
			}
		}
		return sucFlag;
	}

	/**
	 * 调用rest服务,返回处理结果
	 * 
	 * @param task
	 * @param ht
	 * @param oType
	 * @param mType
	 * @return
	 * @throws Exception
	 */
	private Object getResult(Task task, TaskHost ht, int oType, String operUrl)
			throws Exception {
		String agentPort = config.getConf().getString("sky.agent.port", "8099");
		// 请求资源路径
		String resource = "http://" + ht.getHostIp() + ":" + agentPort + "/"
				+ Constants.AGENT_TASK_MAIN_URL;
		// String resource =
		// "http://192.168.32.66:"+agentPort+"/"+Constants.AGENT_TASK_MAIN_URL;
		if (!StringUtils.isEmpty(operUrl))
			resource += "/" + operUrl;
		// 请求方式判断
		switch (oType) {
		case Constants.WS_OPERATION_POST:
			return RestClient.post(ReCode.class, resource, task);
		case Constants.WS_OPERATION_DELETE:
			return RestClient.delete(ReCode.class, resource, task);
		case Constants.WS_OPERATION_GET:
			return RestClient.delete(ReCode.class, resource, task);
		case Constants.WS_OPERATION_PUT:
			return RestClient.put(ReCode.class, resource, task);
		}
		return null;
	}

	/**
	 * 获取任务状态
	 * 
	 * @param task
	 * @return 返回值调用时确认
	 * @throws InterruptedException
	 */
	public boolean getTaskStatus(Task task) throws InterruptedException {
		boolean sucFlag = false;
		long millis = 2 * 1000;
		// 任务处理成功是,跳出循环,获取任务状态,每隔2s调用一次
		while (!sucFlag) {
			// 1、调度程序处理任务 处理逻辑未实现
			sucFlag = getStatus(task);

			Thread.sleep(millis);
		}
		return sucFlag;
	}

	private boolean getStatus(Task task) {
		return false;
	}

	/**
	 * kill正在執行的任務操作
	 * 
	 * @param id
	 *            任務id
	 * @return 返回值调用时确认
	 */
	public ReCode killTask(long id) {
		int oType = Constants.WS_OPERATION_DELETE;
		ReCode reCode = null;
		Task task = null;
		boolean revFlag = false;
		String message = ""; // 处理结果信息
		String operUrl = Constants.AGENT_TASK_KILL_URL;
		int status = Task.RUNNING;
		try {
			// 1、获取库中存在的数据执行的状态
			task = get(id);
			status = task.getStatus();
			message = task.getMessage();

			// boolean sucFlag = getTaskStatus(task);
			// 任务已执行完毕,无需杀死
			if (status == Task.FINISH) {
				status = Task.FINISH;
				message = "任务['" + task.getName() + "']已执行完毕";
			}
			// 如果正在执行,执行杀死操作
			else if (status == Task.RUNNING) {
				// 2、删除正在执行的任务
				operUrl = task.getId() + operUrl;
				revFlag = processTask(task, oType, operUrl);// 调用点
				status = Task.KILLED;
				message = "任务['" + task.getName() + "'] kill 成功...";

				// 3、删除数据库存在的任务记录
				if (revFlag)
					task.setStatus(status);

				update(task);
			}
			// 4、返回处理信息
			reCode = processInfo(id, message, status);
		} catch (Exception esp) {
			Log.error(esp.getMessage(), esp);
			// 3、更改任务处理状态
			status = Task.ERROR;
			message = esp.getMessage();
			task.setStatus(status);
			update(task);
			// 4、返回处理信息
			reCode = processInfo(id, message, status);
		}

		return reCode;
	}

	// 封装返回处理结果信息函数
	public ReCode processInfo(long taskId, String msg, int errCode) {
		ReCode reCode = new ReCode();
		reCode.setData(new Data(taskId)); // 任务id
		reCode.setMsg(msg);
		reCode.setErrcode(errCode + "");
		reCode.setRet(Constants.RET_SUCCESS);
		return reCode;
	}

	/**
	 * 獲取日誌列表
	 */
	public List<LogFile> taskLogs(int pageNo, int pageSize) {
		return logFileDao.taskLogs(pageNo, pageSize);
	}

	@Override
	public LogFile singleLog(long id) {
		BufferedReader bud = null;
		LogFile logFile = null;
		InputStream ism = null;
		StringBuffer data = null;
		File ogFile = null;
		try {
			logFile = logFileDao.get(id);
			// 獲取文件流數據
			String url = logFile.getUrl();

			ogFile = new File(new URI(url));
			ism = new FileInputStream(ogFile);
			logFile.setLogFile(ogFile);
			logFile.setIsm(ism);

			bud = new BufferedReader(new InputStreamReader(ism));
			data = new StringBuffer();

			String line = "";
			while ((line = bud.readLine()) != null) {
				data.append(line);
			}
			logFile.setData(data.toString());
		} catch (FileNotFoundException e) {
			Log.error(e.getMessage(), e);
		} catch (URISyntaxException e) {
			Log.error(e.getMessage(), e);
		} catch (IOException e) {
			Log.error(e.getMessage(), e);
		} finally {
			if (ism != null) {
				try {
					ism.close();
				} catch (IOException e) {
					Log.error(e.getMessage(), e);
				}
			}
		}
		return logFile;
	}

	/**
	 * 根据任务号,获取任务下的子进程
	 */
	@Override
	public List<TranOrder> getTaskOrders(long taskId) {

		return tranOrderDao.getTaskOrders(taskId);
	}

	/**
	 * 获取单个shellOrder
	 */
	@Override
	public TranOrder getOrder(long orderId) {
		if (orderId < 0L)
			return null;
		return tranOrderDao.get(orderId);
	}

	@Override
	public void updateOrder(long oid, double completion) {
		// 获取持久化对象
		TranOrder order = tranOrderDao.get(oid);
		// 更新任务进度
		order.setCompletion(completion);
		// 更新进度
		tranOrderDao.saveOrUpdate(order);
	}

	public void addBatch(List<Task> instanceBatch) {
	}

	/**
	 * 计算任务的完成进度
	 */
	@Override
	public Task getTaskProgress(long id) {
		// 获取持久化任务对象
		Task task = get(id);
//		// 根据order完成进度来计算任务完成进度
//		// 首先获取该任务下面所有子任务的所有的orders
//		Set<SubTask> subTasks = task.getSubTasks();
//		Set<TranOrder> orders = new HashSet<TranOrder>();
//		for (SubTask subTask : subTasks) {
//			orders.addAll(subTask.getOrders());
//		}
//
//		Iterator<TranOrder> iter = orders.iterator();
//		// 进行因子计算操作
//		double completion = 0d;
//		double orderCountCompletion = orders.size();
//		TranOrder order = null;
//		while (iter.hasNext()) {
//			order = iter.next();
//			completion += order.getCompletion();
//		}
//		// 任务的完成进度计算
//		double progress = completion / orderCountCompletion;
//		task.setCompletion(progress);
		// 4/5 or 35.2 %
		return task;
	}


	@Override
	public void callBack(Message intent) {
//		SubTask task = (SubTask) intent.getMsgObj();
//		CallBackContainer.callCallBack(task.getTask().getId(), intent);
	}

	@Override
	public void removeAfterCallBack(Message intent) {
//		SubTask callBackTask = (SubTask) intent.getMsgObj();
//		Message backIntent = CallBackContainer.callCallBack(
//				callBackTask.getTask().getId(), intent);
//		if (Message.INTENT_REMOVE.equals(backIntent.getMsg())) {
//			CallBackContainer.remove(callBackTask.getId());
//		}
	}

}
