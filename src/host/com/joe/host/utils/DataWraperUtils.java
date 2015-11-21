package com.joe.host.utils;

/**
 * 数据转换包装
 * @author xuend
 *
 */
public class DataWraperUtils {

	/**
	 * 以GB为单位取得数据,并取整
	 * @param data
	 * @return
	 */
	public static String getGBData(String data) {
		return NumberUtils.DFormat((Double.parseDouble(data))/(1024*1024*1024));
	}
	
	public static String getGBData(long data) {
		return NumberUtils.DFormat(data/(1024*1024*1024));
	}
	public static String getGBData(Double data) {
		return NumberUtils.DFormat(data/(1024*1024*1024));
	}
	/**
	 * 以GB为单位取得数据,并取保留两位小数
	 * @param data
	 * @return
	 */
	public static String getGBDataTwoDecimal(long data) {
		return NumberUtils.DFormatTwoDecimal(data/(1024*1024*1024));
	}
	public static String getGBDataTwoDecimal(Double data) {
		return NumberUtils.DFormatTwoDecimal(data/(1024*1024*1024));
	}
	/**
	 * 以MB为单位取得数据,小数点后保留两位小数
	 * @param data
	 * @return
	 */
	public static String getMBData(String data) {
		return NumberUtils.DFormatTwoDecimal((Double.parseDouble(data))/(1024*1024));
	}
	public static String getMBData(Double data) {
		return NumberUtils.DFormatTwoDecimal((data)/(1024*1024));
	}
	public static String getMBData(long data) {
		return NumberUtils.DFormatTwoDecimal(data/(1024*1024));
	}

}
