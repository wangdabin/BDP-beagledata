package com.joe.core.support;

import java.util.ArrayList;

/**
 * 分页对象
 * 
 * @author joe
 */
@SuppressWarnings( { "unused", "unchecked" })
public class Page {

	public static int DEFAULT_PAGE_SIZE = 20;

	private int pageSize = DEFAULT_PAGE_SIZE; // 每页的记录数

	private long start; // 当前页第一条数据在List中的位置,从0开始

	private Object data; // 当前页中存放的记录,类型一般为List

	private long totalCount; // 总记录数
	
	public Page() {
		this(0, 0, 20, new ArrayList());
	}

	public Page(long start, long totalCount, int pageSize, Object data) {
		this.start = start;
		this.totalCount = totalCount;
		this.pageSize = pageSize;
		this.data = data;
	}
	
	/**
	 * 取得总记录数
	 * @return
	 */
	public long getTotalCount() {
		return this.totalCount;
	}
	/**
	 * 取当前页中的数据
	 * @return
	 */
	public Object getResult() {
		return this.data;
	}
	
	/**
	 * 取每页的数据容量
	 * @return
	 */
	public int getPageSize() {
		return this.pageSize;
	}
	/**
	 * 取总页数
	 * @return
	 */
	public long getTotalPageCount() {
		return totalCount % pageSize == 0 ? totalCount / pageSize : totalCount
				/ pageSize + 1;
	}
	
	/**
	 * 取当前的页号
	 * @return
	 */
	public long getCurrentPageNo(){
		return start%pageSize+1;
	}
	
	/**
	 * 该页是否有下一页.
	 */
	public boolean hasNextPage() {
		return this.getCurrentPageNo() < this.getTotalPageCount() - 1;
	}

	/**
	 * 该页是否有上一页.
	 */
	public boolean hasPreviousPage() {
		return this.getCurrentPageNo() > 1;
	}

	/**
	 * 获取任一页第一条数据在数据集的位置，每页条数使用默认值.
	 *
	 * @see #getStartOfPage(int,int)
	 */
	protected static int getStartOfPage(int pageNo) {
		return getStartOfPage(pageNo, DEFAULT_PAGE_SIZE);
	}

	/**
	 * 获取任一页第一条数据在数据集的位置.
	 *
	 * @param pageNo   从1开始的页号
	 * @param pageSize 每页记录条数
	 * @return 该页第一条数据
	 */
	public static int getStartOfPage(int pageNo, int pageSize) {
		return (pageNo - 1) * pageSize;
	}
}
