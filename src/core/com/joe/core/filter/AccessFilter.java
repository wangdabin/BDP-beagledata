package com.joe.core.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.joe.core.annotation.InitResource;
import com.joe.core.annotation.RestResource;
import com.joe.core.init.check.InitCheck;
import com.joe.core.init.check.config.ConfigInitCheck;
import com.joe.core.utils.CoreConfigUtils;
import com.joe.core.utils.WildcardMatcher;

/**
 * 访问控制
 * 在没有初始化之前是不允许访问 /ws/* 目录的就是rest提供的资源目录
 * 在初始化之后，是不允许访问 /init/* 初始化的资源的
 * @author Joe
 *
 */
public class AccessFilter implements Filter{
	public static final Logger LOG = Logger.getLogger(AccessFilter.class);
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;
		String path = req.getRequestURI();
		InitCheck check = new ConfigInitCheck(CoreConfigUtils.create());
		if(check.inited()){ //已经初始化了
			if(WildcardMatcher.match(path,InitResource.RESOURCE) || path.contains("createAdmin")){//如果访问路径是 初始化路径,或者createAdmin，则不允许访问。
				LOG.debug("Arleady inited .Path " + path + " forbidden access.");
				resp.sendError(HttpServletResponse.SC_FORBIDDEN, "Arleady inited .");
			}else{// 初始化之后访问 /ws/* 路径是允许的 
				LOG.debug("Arleady inited .Path" + path + " can access.");
				chain.doFilter(request, response);
			}
		}else{
			if(WildcardMatcher.match(path,RestResource.RESOURCE)){//如果没有初始化，是不允许访问，服务路径的 
				//访问 /ws/* 是不允许的
				LOG.debug("No inited .Path " + path + " forbidden access.");
				resp.sendError(HttpServletResponse.SC_PAYMENT_REQUIRED, "Service no inited,please init..");
			}else{
				// 访问  /init/* 是允许的
				LOG.debug("No inited .Path" + path + " can access.");
				chain.doFilter(request, response);
			}
		}
	}

	@Override
	public void destroy() {
	}
	
	public static void main(String[] args) {
		System.out.println(WildcardMatcher.match("/init/v1/database","/init/*"));
	}
}
