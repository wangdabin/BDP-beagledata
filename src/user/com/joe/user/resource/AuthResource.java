package com.joe.user.resource;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;

import com.joe.agent.utils.Constants;
import com.joe.core.annotation.SecurityResource;
import com.joe.core.config.CoreConfig;
import com.joe.core.exception.HttpClientErrorException;
import com.joe.core.rest.RestClient;
import com.joe.core.utils.HttpStatus;
import com.joe.core.version.BDPVersion;
import com.joe.user.vo.AuthResult;
import com.joe.user.vo.AuthUser;
import com.sky.config.ConfigAble;
import com.sky.http.filter.BasePathFiler;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.core.util.MultivaluedMapImpl;

@SecurityResource(name = AuthResource.NAME)
@Path(BDPVersion.BASE_PATH + AuthResource.PATH)
public class AuthResource{

	public static final String AUTH_PARAM_USERNAME = "j_username";
	public static final String AUTH_PARAM_PASSWORD = "j_password";
	public static final String COOKIE_NAME_JSESSIONID = "JSESSIONID";
	
	public static final String NAME = "auth";
	public static final String PATH = "/auth";
	public static final String AUTH_PATH = "sky.user.auth.path";
	@Context
	private HttpServletRequest request;
	
	@Resource(name = CoreConfig.RESOURCE_NAME)
	private ConfigAble config;
	/**
	 * 
	 * @return
	 * @throws IOException 
	 */
	@POST
	@Produces(value = {MediaType.APPLICATION_JSON, MediaType.TEXT_XML,MediaType.APPLICATION_XML})
	public AuthResult auth(AuthUser user){
		String basePath = (String) request.getAttribute(BasePathFiler.BASE_PATH);
		String authResource = basePath + config.getConf().getString(AUTH_PATH);
		
		MultivaluedMap<String, String> querys = new MultivaluedMapImpl();
		querys.add(AUTH_PARAM_USERNAME, user.getUsername());
		querys.add(AUTH_PARAM_PASSWORD, user.getPassword());
		
		ClientResponse resp = RestClient.post(ClientResponse.class, authResource, querys);
		if(resp.getStatus() == 200){
			AuthResult result = new AuthResult();
			result.setErrcode(Constants.NOT_ERROR);
			result.setRet(Constants.RET_SUCCESS);
			for(Cookie cookie : resp.getCookies()){
				if(cookie.getName().equals(COOKIE_NAME_JSESSIONID)){
					result.setToken(cookie.getValue());
				}
        	}
			return result;
		}
		throw new HttpClientErrorException(HttpStatus.valueOf(resp.getStatus()));
	}
}
