package com.joe.tools.define;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.joe.tools.define.callback.MethodBodyHandler;

/**
 * 
 * @author qiaolong
 *
 */
public class MethodDefine extends AbstractDefine {

	public static final String THROWS = "throws";
	/**
	 * 参数定义
	 */
	private List<Param> params = new ArrayList<Param>();
	private String throwsClass; //抛出异常类
	private MethodBodyHandler bodyHandler;
	private boolean constuctor;// 是否为构造方法

	/**
	 * 添加参数
	 * @param type
	 * @param name
	 */
	public void addParam(String type,String name){
		addParam(new Param(type, name));
	}
	
	public void addParam(Param param){
		params.add(param);
	}
	
	public void addParam(Param... params){
		this.params.addAll(Arrays.asList(params));
	}

	public List<Param> getParams() {
		return params;
	}

	public String getThrowsClass() {
		return throwsClass;
	}

	public void setThrowsClass(Class<? extends Throwable> throwsClass) {
		this.throwsClass = throwsClass.getName();
	}

	public MethodBodyHandler getBodyHandler() {
		return bodyHandler;
	}

	public void setBodyHandler(MethodBodyHandler bodyHandler) {
		this.bodyHandler = bodyHandler;
	}

	public boolean isConstuctor() {
		return constuctor;
	}

	public void setConstuctor(boolean constuctor) {
		this.constuctor = constuctor;
		if(constuctor){
			this.setType("");
		}
	}

	/**
	 * 
	 * @param sb
	 * @return
	 * @throws IOException 
	 */
	private Appendable appendParams(Appendable sb) throws IOException{
		if(!params.isEmpty()){
			int size = params.size();
			int i = 0;
			for(Param param : params){
				sb.append(param.getType()).append(SPACE).append(param.getName());
				if(i < size - 1){
					sb.append(",");
				}
				i++;
			}
		}
		return sb;
	}

	private Appendable appendThrowsClass(Appendable sb) throws IOException{
		if(!StringUtils.isBlank(throwsClass)){
			sb.append(THROWS).append(SPACE).append(this.throwsClass);
		}
		return sb;
	}
	
	@Override
	public Appendable define(Appendable sb) throws IOException {
		if(isConstuctor()){
			this.setType("");
		}
		// 开始定义类头
		sb = appendPrefix(sb); // 定义前缀
		
		//参数追加 开始
		sb.append("(");
		sb = appendParams(sb);
		sb.append(")").append(SPACE);
		//参数 结束
		
		//添加异常处理。。
		sb = appendThrowsClass(sb);
		//结束异常处理。。
		
		if(isAbstract()){
			appendLine(sb, "");
		}else{
			//方法的body方法的开始
			sb.append("{").append(ENTER);
			
			//处理方法体
			if(this.bodyHandler != null){
				sb = bodyHandler.handle(sb);
			}
			//方法结束
			sb.append("}").append(ENTER);
			return sb;
		}
		return sb;
	}
	
	public static final class Param {
		private String type;
		private String name;
		
		public Param(){}

		public Param(String type, String name) {
			super();
			this.type = type;
			this.name = name;
		}

		public String getType() {
			return type;
		}

		public void setType(String type) {
			this.type = type;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}
	}
}
