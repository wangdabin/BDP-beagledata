package com.joe.user.auth;

import java.util.Collection;
import java.util.Iterator;

import org.apache.commons.collections.iterators.FilterListIterator;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.FilterInvocation;

/**
 * 
 * @description 资源访问控制类
 * 
 * @function
 * 		判断当前请求是否可以访问资源	
 * 
 * @author ZhouZH
 * 
 */
public class BDPAccessDecisionManager implements AccessDecisionManager {

	/**
	 * 确定是否可以访问
	 */
	public void decide(Authentication authentication, Object object,
			Collection<ConfigAttribute> configAttributes)
			throws AccessDeniedException, InsufficientAuthenticationException {
		if (configAttributes == null) {
			return;
		}
		FilterInvocation filter = (FilterInvocation)object;
		Iterator<ConfigAttribute> ite = configAttributes.iterator();
		while (ite.hasNext()) {
			ConfigAttribute ca = ite.next();
			//匹配请求方式
			if(((SecurityConfigEntity)ca).getOperation().toLowerCase().equals(filter.getRequest().getMethod().toLowerCase()))
			{
				//匹配当前角色
				String needRole = ((SecurityConfigEntity) ca).getAttribute();
				for (GrantedAuthority ga : authentication.getAuthorities()) {
					if (needRole.equals(ga.getAuthority())) { // ga is user's role.
						return;
					}
				}
			}
		}
		throw new AccessDeniedException("no right");
	}

	@Override
	public boolean supports(ConfigAttribute attribute) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return true;
	}
}
