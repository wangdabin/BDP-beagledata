package com.joe.tools.define;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.lang.StringUtils;


/**
 * 类定义
 * @author qiaolong
 *
 */
public class ClassDefine extends AbstractDefine{

	public static final String PACKAGE = "package";
	public static final String IMPORT = "import";
	public static final String CLASS = "class";
	public static final String EXTENDS = "extends";
	public static final String IMPLEMENTS = "implements";
	
	private String packageName;// 报名
	private String extendsClass;//继承的类
	private List<String> importClasses = new ArrayList<String>();// 所有导入的依赖 import java.util.List;
	private List<String> implementClasses = new ArrayList<String>(); //实现的类
	private List<FieldDefine> fieldDefines = new ArrayList<FieldDefine>(); // 成员变量定义
	private List<MethodDefine> methodDefines = new ArrayList<MethodDefine>(); // 方法定义
	
	public String getPackageName() {
		return packageName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}
	
	public String getExtendsClass() {
		return extendsClass;
	}

	public void setExtendsClass(String extendsClass) {
		this.extendsClass = extendsClass;
	}
	
	public void setExtendsClass(Class<?> extendsClass) {
		this.extendsClass = extendsClass.getName();
	}

	public List<String> getImplementClasses() {
		return implementClasses;
	}
	
	public void addImplementClasses(String clazz){
		this.implementClasses.add(clazz);
	}
	
	public void addImplementClasses(Class<?> clazz){
		this.addImplementClasses(clazz.getName());
	}
	
	public List<String> getImportClasses() {
		return importClasses;
	}
	
	public void addImportClasses(String clazz){
		this.importClasses.add(clazz);
	}
	
	public void addImportClass(Class<?> clazz){
		this.addImportClasses(clazz.getName());
	}
	
	public void addImportClasses(Collection<String> clazzes){
		this.importClasses.addAll(clazzes);
	}
	
	public void addImportClassByClass(Collection<Class<?>> clazzes){
		for(Class<?> clazz : clazzes){
			this.addImportClass(clazz);
		}
	}

	public List<FieldDefine> getFieldDefines() {
		return fieldDefines;
	}

	public void addFieldDefine(FieldDefine fieldDefine){
		this.fieldDefines.add(fieldDefine);
	}
	
	public List<MethodDefine> getMethodDefines() {
		return methodDefines;
	}
	
	public void addMethodDefine(MethodDefine methodDefine){
		this.methodDefines.add(methodDefine);
	}

	@Override
	protected String getType() {
		return CLASS;
	}
	
	@Override
	public Appendable define(Appendable sb) throws IOException {
		if(!StringUtils.isBlank(packageName)){// 输出包名
			sb.append(PACKAGE).append(SPACE);
			sb = appendLine(sb, packageName);
			sb.append(ENTER);
		}
		
		if(!importClasses.isEmpty()){
			for(String importClass : importClasses){
				sb.append(IMPORT).append(SPACE);
				sb = appendLine(sb, importClass);
			}
			sb.append(ENTER);
		}
		
		//开始定义类头
		sb = appendPrefix(sb); // 定义前缀
		
		if(!StringUtils.isBlank(extendsClass)){
			sb.append(EXTENDS).append(SPACE).append(extendsClass).append(SPACE);
		}
		
		// implement 类的加载
		if(!implementClasses.isEmpty()){
			sb.append(IMPLEMENTS).append(SPACE);
			int size = implementClasses.size();
			int i = 0;
			for(String implementClass : implementClasses){
				sb.append(implementClass);
				if(i < size - 1){
					sb.append(",");
				}
				i++;
			}
		}
		sb.append("{").append(ENTER);
		//类的头部定义结束
		
		// 加载所有的成员变量
		for(Define define : fieldDefines){
			sb = define.define(sb);
		}
		sb.append(ENTER);
		
		//加载所有的方法
		for(Define define : methodDefines){
			sb = define.define(sb);
		}
		sb.append(ENTER);
		
		sb.append("}"); //定义类结束
		return sb;
	}
}
