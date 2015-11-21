package com.joe.core.resource;

import java.util.HashMap;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.joe.core.annotation.RestResource;
import com.joe.core.bean.CglibBean;
import com.joe.core.version.BDPVersion;

/**
 * 
 * @author Joe
 *
 */
@RestResource(name = CoreResource.NAME)
@Path(BDPVersion.BASE_PATH + CoreResource.PATH)
public class CoreResource {
	
	public static final String NAME = "core";  //资源名称，方便管理
	public static final String PATH = "/core"; //资源路径
	
    @GET
    @Produces(value = {MediaType.TEXT_XML,MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
//    @Produces(value = {MediaType.APPLICATION_JSON})
    public Object createSimpleBean() throws ClassNotFoundException {
//        return new TestBean("a", 1, 1L);
    	HashMap propertyMap = new HashMap();
		propertyMap.put("address", String.class);
		// 生成动态 Bean
		CglibBean bean = new CglibBean(propertyMap);

		// 给 Bean 设置值
		bean.setValue("id", new Integer(123));

		bean.setValue("name", "454");

		bean.setValue("address", "789");
		return bean.getObject();
    	
//        Entity en = new EntityImpl();
//    	en.setId(10);
//    	en.setDesc("llleee33");
//    	en.setName("nam2221e");
////    	en.setType(Type.GROUP);
//		return en;
    }
}
