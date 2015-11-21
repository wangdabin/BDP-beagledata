package com.joe.tools.define.callback;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Type;

import javax.ws.rs.CookieParam;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;

import com.joe.core.resource.utils.PathInfo;
import com.joe.tools.parser.AbstractClientParser;
import com.sun.jersey.api.client.GenericType;

/**
 * 一个基础的
 * @author qiaolong
 *
 */
public class BasicBodyHandler implements MethodBodyHandler{

	private PathInfo pathInfo;
	private String keyPrefix;
	private String pathPrefix;
	/**
	 * 
	 * @param method
	 * @param keyPrefix
	 */
	public BasicBodyHandler(PathInfo pathInfo,String keyPrefix,String pathPrefix){
		this.pathInfo = pathInfo;
		this.keyPrefix = keyPrefix;
		this.pathPrefix = pathPrefix;
	}
	
	@Override
	public Appendable handle(Appendable sb) throws IOException {
		String key = AbstractClientParser.pathTokey(pathInfo.getPath(),keyPrefix,pathPrefix);
		sb.append("String resource = getConf().getString(\"").append(key).append("\");\n");
		String httpMethod = pathInfo.getMethod();
		Method realMethod = pathInfo.getRealMethod();
		GenericType gt = new GenericType(realMethod.getGenericReturnType());
		String className = gt.getType().toString();
		if(gt.getType() instanceof Class){
			Class<?> c = (Class<?>)gt.getType();
			className = c.getName() + ".class";
			if(c.isArray()){
				className = c.getComponentType().getName() + "[].class"; 
			}
		}else{
			className = "new GenericType<" + className + ">(){}";
		}
		
		Annotation[][] annotations = realMethod.getParameterAnnotations();
		realMethod.getParameterAnnotations();
		
		sb.append("MultivaluedMap<String, String> queryParams = new MultivaluedMapImpl();\n");
		sb.append("Map<String, String> headerParams = new HashMap<String, String>();\n");
		sb.append("List<Cookie> cookies = new ArrayList<Cookie>();\n");
//		Map<String,String> pathParams = new HashMap<String, String>();
		
		String obj = null;
		for(int i = 0;i < annotations.length;i ++){
			Annotation[] anns = annotations[i];
			String param =  AbstractClientParser.METHOD_PARAM_PREFIX + i;
			if(anns.length == 0){
				obj = param;
			}
			for(Annotation ann : anns){
				if(ann instanceof PathParam){
					PathParam pAnn = (PathParam)ann;
//						pathParams.put(pAnn.value(), param);
					sb.append("resource = resource.replaceAll(\"\\\\{").append(pAnn.value()).append("\\\\}\",\"\"+").append(param).append(");\n");
				}else if(ann instanceof QueryParam){
					QueryParam qAnn = (QueryParam)ann;
					sb.append("queryParams.add(\"").append(qAnn.value()).append("\",\"\"+").append(param).append(");\n");
				}else if(ann instanceof HeaderParam){
					HeaderParam hAnn = (HeaderParam)ann;
					sb.append("headerParams.put(\"").append(hAnn.value()).append("\",\"\"+").append(param).append(");\n");
				}else if(ann instanceof CookieParam){
					CookieParam cAnn = (CookieParam)ann;
					sb.append("cookies.add(new Cookie(\"").append(cAnn.value()).append("\",\"\"+").append(param).append("));\n");
				}
			}
		}
		if(obj == null){
			obj = "null";
		}
		if(httpMethod.equalsIgnoreCase("get")){
			sb.append("return ").append("this.doGet(").append(className).append(",resource, queryParams, headerParams, cookies);\n");
		}else if(httpMethod.equalsIgnoreCase("post")){
			sb.append("return ").append("this.doPost(").append(className).append(",resource, queryParams, headerParams, cookies,").append(obj).append(");\n");
		}else if(httpMethod.equalsIgnoreCase("put")){
			sb.append("return ").append("this.doPut(").append(className).append(",resource, queryParams, headerParams, cookies,").append(obj).append(");\n");
		}else if(httpMethod.equalsIgnoreCase("delete")){
			sb.append("return ").append("this.doDelete(").append(className).append(",resource, queryParams, headerParams, cookies,").append(obj).append(");\n");
		}
		return sb;
	}
	
	public static void main(String[] args) {
		
	}
}
