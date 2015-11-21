package com.sky.service.define.shell;

import java.util.List;

import com.sky.service.define.CommandDefine;

public class MkdirCommand extends CommandDefine{

	public static final String SHELL = "mkdir";
	
	/**
	 * 默认是用 -p 参数
	 * @param dir
	 */
	public MkdirCommand(String dir){
		this(new String[]{dir},true);
	}
	
	/**
	 * 默认是用 -p 参数
	 * @param dir
	 */
	public MkdirCommand(String[] dirs){
		this(dirs,true);
	}
	
	/**
	 * 
	 * @param dirs
	 * @param withP
	 */
	public MkdirCommand(String[] dirs,boolean withP){
		this.setShell(SHELL);
		if(withP){
			this.addParam("-p");
		}
		for(String dir : dirs){
			this.addParam(dir);
		}
	}
	
	/**
	 * 
	 * @param dirs
	 */
	public MkdirCommand(List<String> dirs){
		this(dirs,true);
	}
	
	/**
	 * 
	 * @param dirs
	 * @param withP
	 */
	public MkdirCommand(List<String> dirs,boolean withP){
		this.setShell(SHELL);
		if(withP){
			this.addParam("-p");
		}
		for(String dir : dirs){
			this.addParam(dir);
		}
	}
}
