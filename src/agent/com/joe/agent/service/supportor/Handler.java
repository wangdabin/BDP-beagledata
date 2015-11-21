package com.joe.agent.service.supportor;

import com.joe.core.vo.ReCode;
import com.sky.task.vo.Task;

public abstract class Handler {

	public abstract ReCode handle(Task task) throws Exception;
}
