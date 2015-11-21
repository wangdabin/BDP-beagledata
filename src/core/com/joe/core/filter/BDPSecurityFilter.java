package com.joe.core.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.configuration.Configuration;
import org.apache.log4j.Logger;
import org.springframework.web.filter.DelegatingFilterProxy;

import com.joe.core.utils.CoreConfigUtils;
import com.joe.core.utils.WildcardMatcher;

/**
 * 安全过滤器
 * @author Joe
 *
 */
public class BDPSecurityFilter extends DelegatingFilterProxy{

	public static final Logger LOG = Logger.getLogger(BDPSecurityFilter.class);
	//安全配置项
	public static final String SECURITY_CONFIG = "security/security.properties";
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain filterChain) throws ServletException, IOException {
		HttpServletRequest req = (HttpServletRequest) request;
		String path = req.getRequestURI();
		Configuration conf = CoreConfigUtils.createConfig(SECURITY_CONFIG);
		String[] excludePaths = conf.getStringArray("exclude.paths");
		boolean match = false;
		if(excludePaths != null && excludePaths.length > 0){ //在不过的路径中包含
			for(String exclude : excludePaths){
				match = WildcardMatcher.match(path, exclude);
				if(match) break;
			}
		}
		if(match){//不过滤,不进行安全认证
			LOG.debug("Path " + path + " no security filter");
			filterChain.doFilter(request, response);
		}else{//需要进行安全认证
			LOG.debug("Path " + path + " do security filter");
			super.doFilter(request, response, filterChain);
		}
	}
}
