package com.joe.host.utils;

import java.text.DecimalFormat;
import java.util.regex.Pattern;

/**
 * 类型转换
 * 
 * @author ZhouZH
 * 
 */
public class NumberUtils {

	/**
	 * 判断是否为整数
	 * 
	 * @param str
	 *            传入的字符串
	 * @return 是整数返回true,否则返回false
	 */
	public static boolean isInteger(String str) {
		Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
		return pattern.matcher(str).matches();
	}

	/**
	 * 判断是否为浮点数，包括double和float
	 * 
	 * @param str
	 *            传入的字符串
	 * @return 是浮点数返回true,否则返回false
	 */
	public static boolean isDouble(String str) {
		Pattern pattern = Pattern.compile("^[0-9]+\\.{0,1}[0-9]{0,2}$");
		return pattern.matcher(str).matches();
	}

	/**
	 * 字符串转整型
	 * <P>返回值等于Integer.MAX_VALUE时表示转换失败</p>
	 * @param intstr
	 * @return
	 */
	public static int stringToInt(String intstr) {
		if (isInteger(intstr)) {
			Integer integer;
			integer = Integer.valueOf(intstr);
			return integer.intValue();
		}

		return Integer.MAX_VALUE;
	}

	/**
	 * 字符串转浮点型
	 * <p>返回值如果是Float.MAX_VALUE，说明转换失败</p>
	 * @param floatstr
	 * @return
	 */
	public static float stringToFloat(String floatstr) {
		if (isDouble(floatstr)) {
			Float floatee;
			floatee = Float.valueOf(floatstr);
			return floatee.floatValue();
		}
		return Float.MAX_VALUE;
	}
	
	/**
	 * 字符串转浮点型
	 * <p>返回值如果是Float.MAX_VALUE，说明转换失败</p>
	 * @param floatstr
	 * @return
	 */
	public static double stringToDouble(String doublestr) {
		if (isDouble(doublestr)) {
			Double doubleee;
			doubleee = Double.valueOf(doublestr);
			return doubleee.doubleValue();
		}
		return Double.MAX_VALUE;
	}

	/**
	 * 格式化数据，浮点型数据取整
	 * 
	 * @param data
	 * @return
	 */
	public static String DFormat(double data) {
		return new DecimalFormat("#").format(data);
	}
	
	/**
	 * 格式化数据，浮点型数据,保留两位小数
	 * 
	 * @param data
	 * @return
	 */
	public static String DFormatTwoDecimal(double data) {
		return new DecimalFormat("#.##").format(data);
	}
	
	public static void main(String[] args) {
		String val = "12222.12122";
		 double result = stringToDouble(val);
		 System.out.println(result);
	}

}
