package com.joe.core.xml;

import org.w3c.dom.Element;

/**
 * 可以解析指定的字段的
 * @author qiaolong
 *
 */
public interface ElementParser {

	/**
	 * 解析字段
	 * @param element
	 */
	void parse(Element element);
}
