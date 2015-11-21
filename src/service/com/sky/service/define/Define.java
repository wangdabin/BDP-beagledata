package com.sky.service.define;

import org.w3c.dom.Element;

import com.joe.core.version.Name;

/**
 * 服务的定义接口
 * @author qiaolong
 */
public interface Define extends Name{

	void parse(Element element);
}
