package com.joe.agent.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.joe.agent.vo.LogFile;
import com.joe.core.dao.HibernateGenericDao;
import com.joe.core.support.Page;

@Repository("logFileDao")
public class LogFileDao extends HibernateGenericDao<LogFile> {
	private Logger Log = Logger.getLogger(LogFileDao.class);

	@SuppressWarnings("unchecked")
	public List<LogFile> taskLogs(int pageNo, int pageSize) {
		List<LogFile> lgfs = new ArrayList<LogFile>();

		if (pageNo == 0)
			pageNo = 1;
		if (pageSize == 0)
			pageSize = Page.DEFAULT_PAGE_SIZE;

		Log.info("pageNo=['" + pageNo + "'],pageSize=['" + pageSize + "']");

		String hql = "From LogFile";

		Page datas = pagedQuery(hql, pageNo, pageSize, null);
		if (datas != null) {
			lgfs = (List<LogFile>) datas.getResult();
		}
		return lgfs;
	}

}
