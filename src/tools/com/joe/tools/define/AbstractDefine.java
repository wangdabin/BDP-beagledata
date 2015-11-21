package com.joe.tools.define;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;


/**
 * 
 * @author qiaolong
 *
 */
public abstract class AbstractDefine implements Define{
	
	public static final String STATIC = "static";
	public static final String FINAL = "final";
	public static final String ABSTRACT = "abstract";
	private String name;
	private String type;
	private boolean _abstract = false; //是否为抽象方法
	private String accessType = ACCESS_TYPE_PUBLIC;// 访问类型 public、protected、private
	private boolean _static = false;// 是否是静态的
	private boolean _final = false; //是否为最终类
	private String comment; //注释
	private List<AnnotationDefine> annotationDefines = new ArrayList<AnnotationDefine>(); // 方法定义
	
	public boolean isStatic() {
		return _static;
	}

	public void setStatic(boolean _static) {
		this._static = _static;
	}

	public boolean isFinal() {
		return _final;
	}

	public void setFinal(boolean _final) {
		this._final = _final;
	}

	public boolean isAbstract(){
		return _abstract;
	}
	
	public void setAbstract(boolean _abstract){
		this._abstract = _abstract;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAccessType() {
		return accessType;
	}

	public void setAccessType(String accessType) {
		this.accessType = accessType;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}
	
	/**
	 * 
	 * @return
	 */
	public List<AnnotationDefine> getAnnotationDefines() {
		return annotationDefines;
	}
	
	/**
	 * 添加ann定义
	 * @param annDefine
	 */
	public void addAnnotationDefine(AnnotationDefine annDefine){
		this.annotationDefines.add(annDefine);
	}
	
	/**
	 * 
	 * @param sb
	 * @return
	 * @throws IOException
	 */
	protected Appendable appendableAnnotations(Appendable sb) throws IOException{
		for(AnnotationDefine annDefine : annotationDefines){
			sb = annDefine.define(sb);
		}
		return sb;
	}
	/**
	 * 
	 * @param sb
	 * @param comment
	 * @return
	 * @throws IOException 
	 */
	private Appendable appendComment(Appendable sb) throws IOException{
		if(!StringUtils.isBlank(comment)){
			if(comment.contains("\n")){
				sb.append("/**").append("\n").append(" *").append(comment.replaceAll("\n","\n *")).append("\n").append(" *").append("\n").append("*/");
			}else{
				sb.append("//").append(comment);
			}
			return sb.append(ENTER);
		}else{
			return sb;
		}
	}
	
	/**
	 * 
	 * @param sb
	 * @param line
	 * @return
	 * @throws IOException 
	 */
	protected Appendable appendLine(Appendable sb,String line) throws IOException{
		return sb.append(line).append(";").append(ENTER);
	}
	
	/**
	 * 
	 * @param sb
	 * @return
	 * @throws IOException 
	 */
	private Appendable appendStatic(Appendable sb) throws IOException{
		if(isStatic()){
			sb.append(SPACE).append(STATIC).append(SPACE);
		}else{
			sb.append(SPACE);
		}
		return sb;
	}
	
	/**
	 * 
	 * @param sb
	 * @return
	 * @throws IOException 
	 */
	private Appendable appendFinal(Appendable sb) throws IOException{
		if(isFinal()){
			sb.append(SPACE).append(FINAL).append(SPACE);
		}else{
			sb.append(SPACE);
		}
		return sb;
	}
	
	private Appendable appendAbstract(Appendable sb) throws IOException{
		return sb.append(SPACE).append(ABSTRACT).append(SPACE);
	}
	
	private Appendable appendType(Appendable sb) throws IOException{
		return sb.append(SPACE).append(getType()).append(SPACE);
	}
	
	private Appendable appendName(Appendable sb) throws IOException{
		return sb.append(SPACE).append(getName()).append(SPACE);
	}

	/**
	 * 定义前缀 public static final ..
	 * @param sb
	 * @return
	 * @throws IOException 
	 */
	protected Appendable appendPrefix(Appendable sb) throws IOException{
		appendComment(sb); //加入注释
		appendableAnnotations(sb); //加入注解
		if(isAbstract()){
			return appendName(appendType(appendAbstract(sb.append(getAccessType())))); //定义类名的开头
		}else{
			return appendName(appendType(appendFinal(appendStatic(sb.append(getAccessType()))))); //定义类名的开头
		}
	}
	
	
	public void setType(String type) {
		this.type = type;
	}

	protected String getType(){
		return this.type;
	}
}
