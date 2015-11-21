package com.joe.user.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

import com.joe.core.service.EntityService;
import com.joe.user.exception.LoginException;
import com.joe.user.utils.UserUtils;
import com.joe.user.vo.User;

/**
 * 登陆过滤器，判断用户是否已经登录
 * @author Joe
 */
public class LoginFilter implements Filter{

	private EntityService<User> service;
	@Override
	public void destroy() {
	}
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		String token = ((HttpServletRequest)request).getHeader(UserUtils.USER_TOKEN_KEY); //用户的token
		if(StringUtils.isBlank(token)){ //用户没有登陆
			throw new LoginException(UserUtils.USER_NO_LOGIN);
		}
	}

	@Override
	public void init(FilterConfig config) throws ServletException {
		
	}
}
