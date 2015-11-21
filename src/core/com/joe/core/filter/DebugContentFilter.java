package com.joe.core.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.joe.core.rest.RestClient;
import com.joe.core.utils.WildcardMatcher;

/**
 * 访问控制
 * 在没有初始化之前是不允许访问 /ws/* 目录的就是rest提供的资源目录
 * 在初始化之后，是不允许访问 /init/* 初始化的资源的
 * @author Joe
 *
 */
public class DebugContentFilter implements Filter{
	public static final Logger LOG = Logger.getLogger(DebugContentFilter.class);
	
	private boolean filter = false;
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		filter = Boolean.valueOf(filterConfig.getInitParameter("filter"));
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;
		if(filter){
			LOG.debug("real content = " + RestClient.getContent(req.getInputStream()));
		}
		chain.doFilter(req, resp);
	}

	@Override
	public void destroy() {
	}
	
	public static void main(String[] args) {
		System.out.println(WildcardMatcher.match("/init/v1/database","/init/*"));
	}
}
