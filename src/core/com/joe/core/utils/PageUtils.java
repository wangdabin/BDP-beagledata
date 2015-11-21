package com.joe.core.utils;

import java.util.ArrayList;
import java.util.List;

import com.joe.user.page.DataTableParames;
import com.joe.user.page.Page;
/**
 * @ClassName: PageUtils
 * @Description: 分页有关的工具类
 * @author WDB
 * @date 2015-3-23 下午1:49:07
 */
public class PageUtils {

    /**
     * @Title: getPage
     * @Description: 将全量数据分页展示
     * @param objects
     * @param dataTableParames
     * @return Page<T> 返回每一页的信息
     * @throws
     */
    public static <T> Page<T> getPage(List<T> objects,
	    DataTableParames dataTableParames) {
	Page<T> page = new Page<T>();
	// 对list进行分页处理
	if (objects != null) {
	    // 总记录数
	    int totalRows = objects.size();
	    // 每页显示数据的终止数
	    int pageEndRow = dataTableParames.getiDisplayStart()
		    + dataTableParames.getiDisplayLength();
	    if (pageEndRow > totalRows) {
		pageEndRow = totalRows;
	    }
	    List<T> subObjects = objects.subList(
		    dataTableParames.getiDisplayStart(), pageEndRow);
	    page.setRecords(subObjects);
	    page.setTotalCount(Long.valueOf(totalRows));
	} else {
	    page.setRecords(new ArrayList<T>());
	    page.setTotalCount(Long.valueOf(0));
	}
	return page;
    }
}
