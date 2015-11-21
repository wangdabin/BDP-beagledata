package com.joe.tools.parser.impl;

import org.apache.commons.configuration.Configuration;
import org.apache.log4j.Logger;

import com.joe.core.annotation.RestResource;
import com.joe.core.resource.utils.CallBack;
import com.joe.core.resource.utils.PathInfo;
import com.joe.core.resource.utils.ResourceBean;
import com.joe.core.resource.utils.RestResourceCallback;
import com.joe.tools.define.callback.BasicBodyHandler;
import com.joe.tools.define.callback.MethodBodyHandler;
import com.joe.tools.parser.AbstractClientParser;

public class RestClientParser extends AbstractClientParser<RestResource> {

	public static final Logger LOG = Logger.getLogger(RestClientParser.class);
	
	public RestClientParser(Configuration conf) {
		super(conf, LOG, RestResource.class);
	}

	@Override
	protected String getPackage() {
		return "com.bdp.rest";
	}

	@Override
	protected String getKeyPrefix() {
		return "server.ws.resource";
	}

	@Override
	protected String getPathPrefix() {
		return "/ws/v1";
	}

	@Override
	protected String getPathConfPrefix() {
		return "\\${server.ws.resource.base}";
	}

	@Override
	protected MethodBodyHandler getBodyHandler(PathInfo pathInfo,
			String keyPrefix, String pathPrefix) {
		return new BasicBodyHandler(pathInfo, keyPrefix, pathPrefix);
	}

	@Override
	protected String getName(RestResource ann) {
		return ann.name();
	}

	@Override
	protected String getType(RestResource ann) {
		return ann.type();
	}

	@Override
	protected boolean isEnable(RestResource ann) {
		return ann.enable();
	}

	@Override
	protected CallBack<ResourceBean> getClasBack() {
		return new RestResourceCallback();
	}
	
	public static void main(String[] args) {
		String key = "/ws/v1/database";
		System.out.println(key.replaceFirst("/ws/v1", "\\${server.ws.resource.base}"));
	}

	@Override
	public String getType() {
		return "ws";
	}
}
