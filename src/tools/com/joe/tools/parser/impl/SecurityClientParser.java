package com.joe.tools.parser.impl;

import org.apache.commons.configuration.Configuration;
import org.apache.log4j.Logger;

import com.joe.core.annotation.SecurityResource;
import com.joe.core.resource.utils.CallBack;
import com.joe.core.resource.utils.PathInfo;
import com.joe.core.resource.utils.ResourceBean;
import com.joe.core.resource.utils.SecurityResourceCallback;
import com.joe.tools.define.callback.BasicBodyHandler;
import com.joe.tools.define.callback.MethodBodyHandler;
import com.joe.tools.parser.AbstractClientParser;

public class SecurityClientParser extends AbstractClientParser<SecurityResource> {

	public static final Logger LOG = Logger.getLogger(SecurityClientParser.class);
	
	public SecurityClientParser(Configuration conf) {
		super(conf, LOG, SecurityResource.class);
	}

	@Override
	protected String getPackage() {
		return "com.bdp.rest";
	}

	@Override
	protected String getKeyPrefix() {
		return "server.security.resource";
	}

	@Override
	protected String getPathPrefix() {
		return "/security/v1";
	}

	@Override
	protected String getPathConfPrefix() {
		return "\\${server.security.resource.base}";
	}

	@Override
	protected MethodBodyHandler getBodyHandler(PathInfo pathInfo,
			String keyPrefix, String pathPrefix) {
		return new BasicBodyHandler(pathInfo, keyPrefix, pathPrefix);
	}

	@Override
	protected String getName(SecurityResource ann) {
		return ann.name();
	}

	@Override
	protected String getType(SecurityResource ann) {
		return ann.type();
	}

	@Override
	protected boolean isEnable(SecurityResource ann) {
		return ann.enable();
	}

	@Override
	protected CallBack<ResourceBean> getClasBack() {
		return new SecurityResourceCallback();
	}

	@Override
	public String getType() {
		return "security";
	}
}
