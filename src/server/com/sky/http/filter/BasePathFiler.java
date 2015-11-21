package com.sky.http.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

public class BasePathFiler implements Filter {

	public static final String BASE_PATH = "--basePath--";
	public static final String ENCODING = "UTF-8";
	
	@Override
	public void destroy() {
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp,
			FilterChain chain) throws IOException, ServletException {
		req.setCharacterEncoding(ENCODING);
		resp.setCharacterEncoding(ENCODING);
		HttpServletRequest request = (HttpServletRequest) req;
		String path = request.getContextPath();
		String basePath = request.getScheme() + "://" + request.getServerName()
				+ ":" + request.getServerPort() + path + "/";
		request.setAttribute(BASE_PATH, basePath);
		chain.doFilter(request, resp);
	}

	@Override
	public void init(FilterConfig config) throws ServletException {

	}
}
