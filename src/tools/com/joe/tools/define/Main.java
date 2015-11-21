package com.joe.tools.define;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Array;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

import javax.ws.rs.QueryParam;

import com.sun.jersey.api.client.GenericType;


public class Main {

	public List<String> test(){
		System.out.println("");
		return null;
	}
	
	public String test2(){
		System.out.println("");
		return null;
	}
	
	
	public Map<String,String> test3(){
		System.out.println("");
		return null;
	}
	
	public String[] test4(){
		System.out.println("");
		return null;
	}
	
	public List test5(){
		System.out.println("");
		return null;
	}
	
	public void test6(@QueryParam("id") List<String> key,String test){
		System.out.println("");
	}
	
	public static void main(String[] args) throws IOException, Exception, Exception {
//		FieldDefine fd = new FieldDefine();
//		fd.setName("test");
//		fd.setType("String");
//		ClassDefine cd = new ClassDefine();
//		cd.setPackageName("com.joe.tools.define");
//		cd.setName("Test");
//		cd.addImportClass(Main.class);
//		cd.setExtendsClass(Main.class);
//		cd.addImplementClasses(Main.class);
//		cd.addImplementClasses(Main.class);
//		cd.addImplementClasses(Main.class);
//		cd.addFieldDefine(fd);
//		cd.setAbstract(true);
//		
//		MethodDefine md = new MethodDefine();
//		md.setType("String");
//		md.setName("test");
//		md.setComment("测试");
//		md.setThrowsClass(Exception.class);
//		cd.addMethodDefine(md);
//		md.addParam("String", "t");
//		md.addParam("int", "t");
//		md.setAbstract(true);
//		StringBuilder sb = new StringBuilder();
//		
//		cd.define(sb);
//		System.out.println(sb);
		Method methods[] = Main.class.getMethods();
		for(Method method : methods){
			if(method.getName().equals("test6")){
				Annotation ann[][] = method.getParameterAnnotations();
				for(Annotation[] an : ann){
					System.out.println(ann.length);
					for(Annotation a : an){
						System.out.println(an.length);
						System.out.println("a.equals(QueryParam.class) = " + a.equals(QueryParam.class));
						System.out.println("a instanceof QueryParam = " + (a instanceof QueryParam));
						System.out.println(a.annotationType());
					}
				}
				System.out.println("=====================");
				Class<?>[] params = method.getParameterTypes();
				for(Class<?> c : params){
					System.out.println(c.getAnnotations().length);
					System.out.println(c);
				}
//				Type t = method.getGenericReturnType();
//				GenericType gt = new GenericType(t);
//				System.out.println("gt.getClass = " + gt.getRawClass());
//				System.out.println("t instanceof Class = " + (t instanceof Class));
//				System.out.println("t instanceof ParameterizedType = " + (t instanceof ParameterizedType));
//				System.out.println(((Class) t).getComponentType());
//				System.out.println("gt.getType = " + gt.getType());
			}
		}
		
	}
}
