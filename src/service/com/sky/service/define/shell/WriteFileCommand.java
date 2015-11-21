package com.sky.service.define.shell;

import java.util.List;

import com.sky.service.define.CommandDefine;

/**
 * 向文件中写内容
 * @author qiaolong
 *
 */
public class WriteFileCommand extends CommandDefine{
	public static final String SHELL = "sh";
	
	/**
	 * 
	 * @param file 文件
	 * @param content 内容
	 */
	public WriteFileCommand(String file,String content){
		this.setShell(SHELL);
		this.addParam("-c");
		this.addParam(buildParam(file, content));
	}
	
	/**
	 * 
	 * @param file 文件
	 * @param content 内容
	 */
	public WriteFileCommand(String file,Object content){
		this.setShell(SHELL);
		this.addParam("-c");
		this.addParam(buildParam(file, content));
	}
	
	/**
	 * 用echo 的方法把内容写到文件中
	 * @param file
	 * @param content
	 * @return
	 */
	private String buildParam(String file,Object content){
		StringBuffer sb = new StringBuffer();
		sb.append("echo ").append("'").append(content).append("' > ").append(file);
		return sb.toString();
	}
	
	/**
	 * 
	 * @param file 文件
	 * @param content 内容
	 */
	public WriteFileCommand(String file,List<Object> contents){
		this.setShell(SHELL);
		this.addParam("-c");
		this.addParam(buildParam(file, contents));
	}
	
	/**
	 * 用echo 的方法把内容写到文件中
	 * @param file
	 * @param content
	 * @return
	 */
	private String buildParam(String file,List<Object> contents){
		StringBuffer sb = new StringBuffer();
		for(int i = 0;i < contents.size(); i++){
			Object obj = contents.get(i);
			if(i == 0){
				sb.append("echo ").append("'").append(obj).append("' > ").append(file).append(";");//覆盖写
			}else{
				sb.append("echo ").append("'").append(obj).append("' >> ").append(file).append(";");//追加写
			}
		}
		return sb.toString();
	}
}
