package com.joe.agent.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.joe.core.dao.HibernateGenericDao;
import com.sky.task.vo.TranOrder;

@Repository("tranOrderDao")
public class TranOrderDao extends HibernateGenericDao<TranOrder> {
	public List<TranOrder> getTaskOrders(long taskId) {
		if (taskId < 0L)
			return null;
		String hql = "From ShellOrder where taskId=? ";
		Object[] values = { taskId };

		@SuppressWarnings("unchecked")
		List<TranOrder> orders = find(hql, values);

		return (orders == null ? new ArrayList<TranOrder>() : orders);
	}
}
