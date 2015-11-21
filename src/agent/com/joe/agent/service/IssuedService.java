package com.joe.agent.service;

import java.io.Serializable;
import java.util.List;

import com.joe.agent.dao.IssuedDao;
import com.joe.agent.vo.LogFile;
import com.joe.core.vo.ReCode;
import com.sky.task.vo.Message;
import com.sky.task.vo.Task;
import com.sky.task.vo.TranOrder;

public interface IssuedService {

	public Task get(Serializable id);

	public List<Task> getAll();

	public void add(Task instance);

	public void remove(Task transientObject);

	public void removeById(Serializable id);

	public Task update(Task t);

	public IssuedDao getIssuedDao();

	public void setIssuedDao(IssuedDao issuedDao);

	// 新增删除任务功能
	public ReCode killTask(long id);

	// 获取所有task运行日志文件信息
	public List<LogFile> taskLogs(int pageNo, int pageSize);

	// 获取单个task运行日志
	public LogFile singleLog(long id);

	// 根据taskId获取orders
	public List<TranOrder> getTaskOrders(long taskId);

	// 根据orderId获取指定TranOrder
	public TranOrder getOrder(long orderId);

	// 更新具体Order执行的进度
	public void updateOrder(long oid, double completion);

	// 计算任务进度
	public Task getTaskProgress(long id);


	/**
	 * 执行callBack
	 * 
	 * @param callBackId
	 */
	public void callBack(Message intent);

	/**
	 * 執行该callback后，从服务中删除该callback
	 * <p>
	 * 只有intent的返回标志中存在删除的时候，才进行删除
	 * </p>
	 * 
	 * @param callBackId
	 */
	public void removeAfterCallBack(Message intent);

}
