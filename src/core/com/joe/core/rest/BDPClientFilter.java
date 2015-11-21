package com.joe.core.rest;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.Cookie;

import org.apache.commons.lang.StringUtils;

import com.sun.jersey.api.client.ClientHandlerException;
import com.sun.jersey.api.client.ClientRequest;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.filter.ClientFilter;

/**
 * 
 * @author qiaolong
 *
 */
public class BDPClientFilter extends ClientFilter{

	public static final String TOKEN_KEY = "JSESSIONID";
	private static final ThreadLocal<List<Object>> cookiesLocal = new ThreadLocal<List<Object>>();

    @Override
    public ClientResponse handle(ClientRequest request) throws ClientHandlerException {
    	List<Object> cookies = cookiesLocal.get();
        if (cookies != null) {
            request.getHeaders().put("Cookie", cookies);
        }
        ClientResponse response = getNext().handle(request);
        if (response.getCookies() != null) {
            if (cookies == null) {
                cookies = new ArrayList<Object>();
            }
            cookies.addAll(response.getCookies());
            cookiesLocal.set(cookies);
        }
        return response;
    }

    /**
     * 
     * @param token
     */
    public static final void addToken(String token){
		Cookie tokenCookie = new Cookie(TOKEN_KEY, token);
    	List<Object> cookies = cookiesLocal.get();
    	if(cookies != null){
    		Cookie tCookie = null;
    		for(Object cookie : cookies){
    			Cookie c = (Cookie) cookie;
    			if(TOKEN_KEY.equalsIgnoreCase(c.getName())){
    				tCookie = c;
    			}
    		}
    		if(tCookie != null){
    			cookies.remove(tCookie);
    			if(!StringUtils.isBlank(token)){
    				cookies.add(tokenCookie);
    			}
    		}
    	}else{
    		if(!StringUtils.isBlank(token)){
    			cookies = new ArrayList<Object>();
        		cookies.add(tokenCookie);
        		cookiesLocal.set(cookies);
    		}
    	}
    }
    
    /**
     * 
     */
    public static final void remove(){
    	cookiesLocal.remove();
    }
}
