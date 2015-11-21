package com.joe.user.page;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;
/**
 * @ClassName: DataTableParames
 * @Description: 分页数据传输的对象
 * @author WDB
 * @date 2015-3-4 上午9:23:37
 */
@XmlRootElement
public class DataTableParames implements Serializable{

    	private static final long serialVersionUID = 1L;
    	
    	//起始点,Display start point in the current data set.
    	private int iDisplayStart ;
    	//每次返回的数据长度,Number of records that the table can display in the current draw. It is expected that the number of records returned will be equal to this number, unless the server has fewer records to return.
    	private int iDisplayLength ;
    	
    	//Information for DataTables to use for rendering.
	private String sEcho ;

	//全局搜索的字段     Global search field 
	private String sSearch ;
	
	//bRegex True if the global filter should be treated as a regular expression for advanced filtering, false if not.
	private boolean bRegex;
	
	 // 排序列的数量    Number of columns to sort on
	private int iSortingCols;
	
	// 显示的列数 Number of columns being displayed 
	private int iColumns;
	// 当前要排序的字段在表中的位置,从0开始
	private int iSortCol_0;
	// 当前要排序的字字段的排序方式,asc或者desc
	private String sSortDir_0;
	
	
	public int getiColumns() {
	    return iColumns;
	}

	
	public int getiDisplayLength() {
		return iDisplayLength;
	}

	public int getiDisplayStart() {
		return iDisplayStart;
	}

	public int getiSortCol_0() {
		return iSortCol_0;
	}

	public int getiSortingCols() {
		return iSortingCols;
	}



	public String getsEcho() {
		return sEcho;
	}

	public String getsSearch() {
		return sSearch;
	}

	public String getsSortDir_0() {
		return sSortDir_0;
	}

	public boolean isbRegex() {
	    return bRegex;
	}


	public void setbRegex(boolean bRegex) {
	    this.bRegex = bRegex;
	}


	public void setiColumns(int iColumns) {
	    this.iColumns = iColumns;
	}


	public void setiDisplayLength(int iDisplayLength) {
		this.iDisplayLength = iDisplayLength;
	}

	public void setiDisplayStart(int iDisplayStart) {
		this.iDisplayStart = iDisplayStart;
	}

	public void setiSortCol_0(int iSortCol_0) {
		this.iSortCol_0 = iSortCol_0;
	}

	public void setiSortingCols(int iSortingCols) {
		this.iSortingCols = iSortingCols;
	}

	public void setsEcho(String sEcho) {
		this.sEcho = sEcho;
	}

	public void setsSearch(String sSearch) {
		this.sSearch = sSearch;
	}

	public void setsSortDir_0(String sSortDir_0) {
		this.sSortDir_0 = sSortDir_0;
	}
}
