package com.joe.agent.utils;


public class Constants extends com.joe.core.utils.Constants{
	
	//agent 成功添加
	public static final String AGENT_SUCCESS_ADD = "bdp_agent_add_success";
	//agent 成功更新
	public static final String AGENT_SUCCESS_UPDATE = "bdp_agent_update_success";
	//agent 删除成功
	public static final String AGENT_SUCCESS_DELETE = "bdp_agent_delete_success";
	
	//TASK 任务处理成功
	public static final String TASK_PROCESS_SUCCESS = "bdp_task_process_success";
	//TASK 任务处理失败
	public static final String TASK_PROCESS_FAILURE = "bdp_task_process_failure";
	//TASK 任务删除成功
	public static final String TASK_KILL_SUCCESS    = "bdp_task_kill_success";
	//TASK 状态回写成功
	public static final String TASK_WRITE_BACK_SUCCESS="bdp_task_write_back_success";
	
	//agent task 执行主路径
	public static final String AGENT_TASK_MAIN_URL = "ws/v1/tasks" ;
	public static final String AGENT_TASK_KILL_URL = "/kill" ;
	
	//ws调用方式
	public static final int WS_OPERATION_POST   = 0 ;
	public static final int WS_OPERATION_GET    = 1 ;
	public static final int WS_OPERATION_DELETE = 2 ;
	public static final int WS_OPERATION_PUT    = 3 ;
	
	//ws参数传递方式
	public static final int WS_MT_TEXT_XML = 0 ;
	public static final int WS_MT_APP_XML  = 1 ;
	public static final int WS_MT_APP_JSON = 2 ;
	
}
