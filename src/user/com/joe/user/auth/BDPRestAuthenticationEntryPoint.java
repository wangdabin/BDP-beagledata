package com.joe.user.auth;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

/**
*
* @description rest认证失败消息类
*
* @function
* 		401 没有登录
* 		403没有权限
*
* @author ZhouZH
*
*/
@Component( "restAuthenticationEntryPoint" )
public class BDPRestAuthenticationEntryPoint implements AuthenticationEntryPoint{

	@Override
	public void commence(HttpServletRequest arg0, HttpServletResponse arg1,
			AuthenticationException arg2) throws IOException, ServletException {
		// TODO Auto-generated method stub
		arg1.sendError( HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized" );
	}

	
	
}
