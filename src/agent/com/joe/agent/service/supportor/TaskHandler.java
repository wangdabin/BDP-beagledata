package com.joe.agent.service.supportor;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.joe.agent.utils.DateUtils;
import com.joe.core.vo.ReCode;
import com.sky.service.utils.ReCodeUtils;
import com.sky.task.vo.Task;

/**
 * 
 * @ClassName: TaskHandler
 * @Description:处理接收Task的handler
 * @author: LiuZhiJun
 * @date: 2015年1月28日 下午2:06:51
 */
@Service("handleService")
public class TaskHandler extends Handler {

	private final Logger Log = Logger.getLogger(TaskHandler.class);

	private final Map<Long, Task> taskMap = new HashMap<Long, Task>();

	private Looper looper = new Looper();


	@Override
	public ReCode handle(Task task) {

		ReCode reCode = null;
		String message = ""; // 处理结果信息
		long startTime = DateUtils.taskStartTime(); // 任务开始时间
		try {

			// 将任务添加到任务taskMsap中
			taskMap.put(task.getId(), task);
			// 1、调用执行相应的处理任务程序
			Log.info("任务['" + task.getName() + "']提交中,开始时间['"
					+ DateUtils.getStartTime() + "']");

			reCode = looper.loop(task);
			// commitTask(task, oType, operUrl);
			message = "任务['" + task.getName() + "']提交完成,完成时间['"
					+ DateUtils.getEndTime() + "']";
			Log.info(message);
			
			// 2、更新任务状态
			task.setMessage(message);
			// 3、返回相应结果信息
			reCode = ReCodeUtils.processInfo(task.getId(), message,
					task.getStatus()+"", ReCode.RET_SUCCESS);

			// 4、计算任务花费时间
			long endTime = DateUtils.taskStartTime(); // 任务结束时间
			Log.info("任务['" + task.getName() + "']花费时间['"
					+ (endTime - startTime) / 1000 + "']s");
		} catch (Exception esp) {
			String errMsg = "任务['" + task.getName() + "']提交失败 ";
			// 任务处理失败
			Log.error(
					"任务['" + task.getName() + "']提交失败 "
							+ DateUtils.getStartTime(), esp);
			message = esp.getMessage();
			task.setStatus(Task.ERROR);
			task.setMessage(errMsg);
			reCode.setRet(ReCode.RET_FAIL);
			// 返回相应结果信息
		} 
		return reCode;

	}

	// 处理Handler接收到的任务
	private class Looper {

		public final synchronized ReCode loop(Task task) throws Exception {
			ReCode recode = new ReCode();
//			// 首先将任务中的子任务拿出来
//			Set<SubTask> subTasks = task.getSubTasks();
//
//			// 下发第一个subtask
//			SubTask subTask = subTasks.iterator().next();
//			if (subTask != null)
//				try {
//					recode = subTask.commitTask(Constants.WS_OPERATION_POST, null);
//				} catch (Exception e) {
//					// TODO Auto-generated catch block
//					throw e;
//				}

			return recode;
		}

	}

}
