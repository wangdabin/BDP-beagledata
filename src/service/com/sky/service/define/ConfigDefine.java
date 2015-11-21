package com.sky.service.define;

import java.io.IOException;

import org.w3c.dom.Element;

/**
 * 配置定义
 * @author qiaolong
 *
 */
public class ConfigDefine extends EnvironmentDefine{

	public ConfigDefine() throws IOException {
		super();
	}

	public static final ConfigDefine build(Element element) throws IOException {
		ConfigDefine define = new ConfigDefine();
		define.parse(element);
		return define;
	}
}
