//package com.joe.agent.utils;
//
//import com.joe.service.vo.ServiceVo;
//import com.sky.task.vo.Task;
//
///**
// * 
// * @ClassName: StatusTranslator
// * @Description:服务状态和任务状态的映射工具
// * @author: LiuZhiJun
// * @date: 2015年1月14日 下午12:55:39
// */
//public class StatusTranslator {
//
//	/**
//	 * 将任务的状态映射到服务的状态
//	 * 
//	 * @param status
//	 * @return
//	 */
//	public static int taskToServiceStauts(int status) {
//		int serviceStatus;
//		switch (status) {
//		case Task.ERROR:
//			serviceStatus = ServiceVo.ServiceVoStatus.INSTALL_FAILED;
//			break;
//		case Task.FINISH:
//			serviceStatus = ServiceVo.ServiceVoStatus.INSTALL_SUCCESS;
//			break;
//		case Task.KILLED:
//			serviceStatus = ServiceVo.ServiceVoStatus.INSTALL_FAILED;
//			break;
//		case Task.RUNNING:
//			serviceStatus = ServiceVo.ServiceVoStatus.INSTALLING;
//			break;
//		case Task.WAITING:
//			serviceStatus = ServiceVo.ServiceVoStatus.INIT;
//			break;
//		default:
//			serviceStatus = ServiceVo.ServiceVoStatus.INIT;
//		}
//		return serviceStatus;
//	}
//
//}
