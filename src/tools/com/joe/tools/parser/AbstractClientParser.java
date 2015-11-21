package com.joe.tools.parser;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.MultivaluedMap;

import org.apache.commons.configuration.Configuration;
import org.apache.log4j.Logger;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.stereotype.Repository;

import com.joe.core.factory.AbstractFactory;
import com.joe.core.resource.utils.CallBack;
import com.joe.core.resource.utils.PathInfo;
import com.joe.core.resource.utils.ResourceBean;
import com.joe.core.resource.utils.ResourceManager;
import com.joe.core.rest.AbstractClient;
import com.joe.core.rest.RestClient;
import com.joe.core.utils.JsonUtils;
import com.joe.core.vo.ErrorMessage;
import com.joe.core.vo.ReCode;
import com.joe.tools.ClientParser;
import com.joe.tools.define.AnnotationDefine;
import com.joe.tools.define.ClassDefine;
import com.joe.tools.define.MethodDefine;
import com.joe.tools.define.MethodDefine.Param;
import com.joe.tools.define.callback.MethodBodyHandler;
import com.sky.config.ConfigAble;
import com.sky.config.Configed;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.GenericType;
import com.sun.jersey.core.util.MultivaluedMapImpl;

/**
 * 
 * @author qiaolong
 *
 */
public abstract class AbstractClientParser<A extends Annotation> extends AbstractFactory<A,Object> implements ClientParser{

	private static final Set<Class<?>> importClasses = new  HashSet<Class<?>>();
	private static final Set<String> importClassesStr = new  HashSet<String>();
	private static final String version = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(System.currentTimeMillis());
	private static final String comment = "@author computer\n@version "+ version + "\n Automatic generation";
	public static final String METHOD_PARAM_PREFIX = "param";
	private static final String KEY_REGEX = "\\{|\\}";
	private static final Pattern KEY_PATTERN = Pattern.compile(KEY_REGEX);
	
	static{
		importClasses.add(Configuration.class);//设置Configuration类
		importClasses.add(Configed.class);//设置Configed类
		importClasses.add(ConfigAble.class);//设置ConfigAble类
		importClasses.add(RestClient.class);//设置RestClient类
		importClasses.add(ReCode.class);//设置ReCode类
		importClasses.add(JSONObject.class);//设置JSONObject类
		importClasses.add(JSONArray.class);//设置JSONArray类
		importClasses.add(Iterator.class);//设置Iterator类
		importClasses.add(JsonUtils.class);//设置JsonUtils类
		importClasses.add(ErrorMessage.class);//设置ErrorMessage类
		importClasses.add(ArrayList.class);//设置ArrayList类
		importClasses.add(List.class);//设置List类
		importClasses.add(MessageFormat.class);//设置MessageFormat类
		importClasses.add(ClientResponse.class);//设置ClientResponse类
		importClasses.add(GenericType.class);//设置ClientResponse类
		importClasses.add(MultivaluedMap.class);//设置MultivaluedMap类
		importClasses.add(MultivaluedMapImpl.class);//设置MultivaluedMapImpl类
		importClasses.add(Map.class);//设置Map类
		importClasses.add(HashMap.class);//设置HashMap类
		importClasses.add(List.class);//设置HashMap类
		importClasses.add(ArrayList.class);//设置HashMap类
		importClasses.add(Cookie.class);//设置Cookie类
		importClasses.add(IOException.class);//设置IOException类
		importClasses.add(AbstractClient.class);//设置AbstractClient类
		
		importClassesStr.add("com.bdp.config.ClientConfigUtils");//设置ClientConfigUtils类
		
	}
	
	
	
	public AbstractClientParser(Configuration conf, Logger LOG,
			Class<A> clazz) {
		super(conf, LOG, clazz);
	}

	protected  List<ResourceBean> findResourceBeans(CallBack<ResourceBean> callBack){
		Collection<Class<Object>> classes = this.getAll();
		return ResourceManager.parse(classes,callBack);
	}
	
	@Override
	public List<ClassDefine> generateDefine() {
		List<ClassDefine> cds = new ArrayList<ClassDefine>();
		List<ResourceBean> resourceBeans = findResourceBeans(getClasBack());
		for(ResourceBean rb : resourceBeans){
			getLog().debug("Parse class " + rb.getClassName());
			ClassDefine cd = new ClassDefine();
			String name = rb.getName();
//			cd.setPackageName(getPackage() + "." + name.toLowerCase()); //设置包名
			cd.setPackageName(getPackage());
			cd.addImportClasses(importClassesStr); // 定义引入
			cd.addImportClassByClass(importClasses); // 定义引入
			cd.setName(parseClassName(name));
			cd.setExtendsClass("AbstractClient"); //设置继承类
			cd.setComment(comment); // 设置注释
			cd.addAnnotationDefine(createRepository(cd.getName()));
			cd.addMethodDefine(createConstuctorMethod(cd));
			Param param = new Param(Configuration.class.getName(), "conf");
			cd.addMethodDefine(createConstuctorMethod(cd,param)); // 添加config
			
			for(PathInfo pathInfo : rb.getPathInfos()){
				MethodDefine md = new MethodDefine();
				Method realMod = pathInfo.getRealMethod();
				String methodName = pathInfo.getRealMethod().getName();
				md.setName(methodName);//方法名
				Type reType = realMod.getGenericReturnType();
				md.setType(parseType(reType)); // 设置返回值
				md.setComment(pathInfo.getMethod()); //注释
				Type[] params = realMod.getGenericParameterTypes();
				if(params != null){
					for(int i = 0;i < params.length;i ++){
						String paramType = parseType(params[i]);
						String paramName = METHOD_PARAM_PREFIX + i;
						md.addParam(paramType, paramName);
					}
				}
				md.setBodyHandler(getBodyHandler(pathInfo, getKeyPrefix(), getPathPrefix()));
				cd.addMethodDefine(md);
			}
			cds.add(cd);
		}
		return cds;
	}
	
	/**
	 * 
	 * @param cd
	 * @return
	 */
	protected MethodDefine createConstuctorMethod(ClassDefine cd){
		MethodDefine md = new MethodDefine();
		md.setConstuctor(true);
		md.setName(cd.getName());
		md.setThrowsClass(IOException.class);
		md.setBodyHandler(new MethodBodyHandler() {
			@Override
			public Appendable handle(Appendable sb) throws IOException {
				return sb.append("super(ClientConfigUtils.create());\n");
			}
		});
		return md;
	}
	
	/**
	 * 
	 * @param cd
	 * @return
	 */
	protected MethodDefine createConstuctorMethod(ClassDefine cd,Param...params){
		MethodDefine md = new MethodDefine();
		md.setConstuctor(true);
		md.setName(cd.getName());
		md.setThrowsClass(IOException.class);
		md.addParam(params);
		md.setBodyHandler(new MethodBodyHandler() {
			@Override
			public Appendable handle(Appendable sb) throws IOException {
				return sb.append("super(conf);\n");
			}
		});
		return md;
	}
	
	/**
	 * 
	 * @param className
	 * @return
	 */
	protected AnnotationDefine createRepository(String className){
		StringBuilder sb = new StringBuilder(className);
		sb.setCharAt(0, Character.toLowerCase(sb.charAt(0)));
		AnnotationDefine annDefine = new AnnotationDefine();
		annDefine.setClassName(Repository.class);
		annDefine.setValue(sb.toString());
		return annDefine;
	}
	/**
	 * 
	 * @param restName
	 * @return
	 */
	private String parseClassName(String restName){
		getLog().debug("restName = " + restName);
		String className = restName.toLowerCase().replaceFirst("" + Character.toLowerCase(restName.charAt(0)), ("" + restName.charAt(0)).toUpperCase()) + "Client";
		return className;
	}
	
	private String parseType(Type genericType){
		GenericType gt = new GenericType(genericType);
		Type gtType = gt.getType();
		if(gtType instanceof Class){
			Class c = (Class)gtType;
			if(c.isArray()){//处理数组
				return c.getComponentType().getName() + "[]"; 
			}else{
				return c.getName();
			}
		}else{
			return gtType.toString();
		}
	}
	
	/**
	 * 
	 * @return
	 */
	protected abstract CallBack<ResourceBean> getClasBack();
	
	/**
	 * 
	 * @return
	 */
	protected abstract String getPackage();
	
	/**
	 * key的前缀 KEY_PREFIX = "server.ws.resource"
	 * @return
	 */
	protected abstract String getKeyPrefix();
	
	/**
	 * 路径前缀 /ws/v1
	 * @return
	 */
	protected abstract String getPathPrefix();
	
	/**
	 * ${server.ws.resource.base}
	 * @return
	 */
	protected abstract String getPathConfPrefix();
	
	protected abstract MethodBodyHandler getBodyHandler(PathInfo pathInfo,String keyPrefix,String pathPrefix);
	

	/**
	 * 把 server.ws.resource.hosts.{hid} 替换成 server.ws.resource.hosts.hid
	 * @param key
	 * @return
	 */
	public static final String pathTokey(String path,String keyPrefix,String pathPrefix){
		String key = keyPrefix + path.replaceFirst(pathPrefix, "").replaceAll("/", ".");
		return KEY_PATTERN.matcher(key).replaceAll("");
	}
	
	@Override
	public Map<String,Map<String,String>> generateRestConfig() {
		Map<String,Map<String,String>> results = new HashMap<String,Map<String,String>>();
		List<ResourceBean> resourceBeans = findResourceBeans(getClasBack());
		for(ResourceBean rb : resourceBeans){
			Map<String, String> restConfig = new HashMap<String, String>();
			for(PathInfo pathInfo : rb.getPathInfos()){
				getLog().debug("getKeyPrefix() = " + getKeyPrefix());
				getLog().debug("getPathPrefix() = " + getPathPrefix());
				getLog().debug("pathInfo.getPath() = " + pathInfo.getPath());
				getLog().debug("getPathConfPrefix() = " + getPathConfPrefix());
				String key = pathTokey(pathInfo.getPath(),getKeyPrefix(),getPathPrefix());
				String path = pathInfo.getPath().replaceFirst(getPathPrefix(),getPathConfPrefix());
				restConfig.put(key, path);
			}
			results.put(rb.getName(), restConfig);
		}
		return results;
	}
}
