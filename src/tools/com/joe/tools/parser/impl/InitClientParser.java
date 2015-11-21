package com.joe.tools.parser.impl;

import org.apache.commons.configuration.Configuration;
import org.apache.log4j.Logger;

import com.joe.core.annotation.InitResource;
import com.joe.core.resource.utils.CallBack;
import com.joe.core.resource.utils.InitResourceCallback;
import com.joe.core.resource.utils.PathInfo;
import com.joe.core.resource.utils.ResourceBean;
import com.joe.tools.define.callback.BasicBodyHandler;
import com.joe.tools.define.callback.MethodBodyHandler;
import com.joe.tools.parser.AbstractClientParser;

public class InitClientParser extends AbstractClientParser<InitResource> {

	public static final Logger LOG = Logger.getLogger(InitClientParser.class);
	
	public InitClientParser(Configuration conf) {
		super(conf, LOG, InitResource.class);
	}

	@Override
	protected String getPackage() {
		return "com.bdp.rest";
	}

	@Override
	protected String getKeyPrefix() {
		return "server.init.resource";
	}

	@Override
	protected String getPathPrefix() {
		return "/init/v1";
	}

	@Override
	protected String getPathConfPrefix() {
		return "\\${server.init.resource.base}";
	}

	@Override
	protected MethodBodyHandler getBodyHandler(PathInfo pathInfo,
			String keyPrefix, String pathPrefix) {
		return new BasicBodyHandler(pathInfo, keyPrefix, pathPrefix);
	}

	@Override
	protected String getName(InitResource ann) {
		return ann.name();
	}

	@Override
	protected String getType(InitResource ann) {
		return ann.type();
	}

	@Override
	protected boolean isEnable(InitResource ann) {
		return ann.enable();
	}

	@Override
	protected CallBack<ResourceBean> getClasBack() {
		return new InitResourceCallback();
	}

	@Override
	public String getType() {
		return "init";
	}
}
