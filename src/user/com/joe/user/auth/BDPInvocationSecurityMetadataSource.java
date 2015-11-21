package com.joe.user.auth;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RegexRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import com.joe.user.service.RoleService;
import com.joe.user.vo.Operation;
import com.joe.user.vo.Permission;
import com.joe.user.vo.Role;
import com.joe.user.vo.RoleRelation;

/**
 * 
 * 此类在初始化时，应该取到所有资源及其对应角色的定义
 * 
 * @author ZhouZH
 * 
 */
public class BDPInvocationSecurityMetadataSource implements
		FilterInvocationSecurityMetadataSource {

	@Resource(name = "roleService")
	private RoleService roleService;

	private boolean expire = false; // 过期标识

	private RequestMatcher requestMatcher; // 匹配规则

	private String matcher; // 规则标识

	private static Map<String, Collection<ConfigAttribute>> resourceMap = null;

	public BDPInvocationSecurityMetadataSource() {

	}

	/**
	 * 加载角色与资源之间的关系
	 */
	private void loadResourceDefine() {
		resourceMap = new HashMap<String, Collection<ConfigAttribute>>();
		/*
		 * ConfigAttribute ca = new SecurityConfig("ROLE_ADMIN"); atts.add(ca);
		 * resourceMap.put("/index.jsp", atts); resourceMap.put("/i.jsp", atts);
		 */
		for (Role role : roleService.getAll()) {
			
			for (RoleRelation relation : role.getRolerelation()) {
				
				//确定操作资源
				Permission permission = relation.getPermission();
				//确定操作方式
				Operation operation = relation.getOperation();
				//定义权限集合
				Collection<ConfigAttribute> atts = null;
				
				//定义操作权限
				SecurityConfigEntity ca = new SecurityConfigEntity(role.getName());
				
				ca.setOperation(operation.getName());
				
				if(resourceMap.containsKey(permission.getUrl()))
				{
					atts = resourceMap.get(permission.getUrl());
				}
				else
				{
					atts = new ArrayList<ConfigAttribute>();
				}
				
				atts.add(ca);
				
				resourceMap.put(permission.getUrl(), atts);
			}
		}
	}

	/**
	 * 确定资源需要的访问角色
	 */
	public Collection<ConfigAttribute> getAttributes(Object object)
			throws IllegalArgumentException {

		// 如果资源Map为空的时候则重新加载一次
		if (null == resourceMap || resourceMap.isEmpty())
			loadResourceDefine();

		// guess object is a URL.
		HttpServletRequest request = ((FilterInvocation) object).getRequest();
		// System.out.println("requestUrl is " + request.getRequestURI());

		// 检测是否刷新了资源
		if (isExpire()) {
			// 清空原本资源
			resourceMap.clear();
			expire = false;

			loadResourceDefine();
		}
		
		boolean caseSensitive = false;
		
		Collection<ConfigAttribute> attrs = resourceMap.get(BDPRequestMatcher.getRequestPath(request, caseSensitive));
		if(attrs != null){
			return attrs;
		}else{
			// 检测请求与当前资源匹配的正确性
			Iterator<String> iterator = resourceMap.keySet().iterator();
			while (iterator.hasNext()) {
				String uri = iterator.next();
				if (matcher.toLowerCase().equals("ant")) {
					requestMatcher = new AntPathRequestMatcher(uri);
				}else if (matcher.toLowerCase().equals("regex")) {
					requestMatcher = new RegexRequestMatcher(uri,
							request.getMethod(), true);
				}else if(matcher.toLowerCase().equals("bdp")){
					requestMatcher = new BDPRequestMatcher(uri,caseSensitive);
				}else{
					requestMatcher = new BDPRequestMatcher(uri,caseSensitive);
				}
				if (requestMatcher.matches(request))
					return resourceMap.get(uri);
			}
		}
		return null;
	}

	public boolean supports(Class<?> clazz) {
		return true;
	}

	public Collection<ConfigAttribute> getAllConfigAttributes() {

		return null;
	}

	public void setMatcher(String matcher) {
		this.matcher = matcher;
	}

	public boolean isExpire() {
		return expire;
	}

	public void expireNow() {
		this.expire = true;
	}

}
