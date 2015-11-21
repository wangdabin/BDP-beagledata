package com.joe.tools.main;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.configuration.Configuration;

import com.joe.core.utils.CoreConfigUtils;
import com.joe.tools.ClientParser;
import com.joe.tools.define.ClassDefine;
import com.joe.tools.parser.impl.InitClientParser;
import com.joe.tools.parser.impl.RestClientParser;
import com.joe.tools.parser.impl.SecurityClientParser;

/**
 * 
 * @author qiaolong
 * @version 
 */
public class Main {

	
	private static final List<ClientParser> clientParsers = new ArrayList<ClientParser>();
	
	static{
		try {
			Configuration conf = CoreConfigUtils.create();
			clientParsers.add(new InitClientParser(conf));
			clientParsers.add(new RestClientParser(conf));
			clientParsers.add(new SecurityClientParser(conf));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public static void main(String[] args) throws IOException {
		String basePath = "F:\\test";
		Map<String,Map<String,Map<String,String>>> configs = new HashMap<String,Map<String,Map<String,String>>>();
		for(ClientParser cp : clientParsers){
			Map<String,Map<String,String>> results = new HashMap<String,Map<String,String>>();
			System.out.println(cp.getClass());
			List<ClassDefine> cds = cp.generateDefine();
			for(ClassDefine cd : cds){
				String path = cd.getPackageName();
				File file = new File(basePath,path.replaceAll("\\.", "/"));
				file.mkdirs();
				System.out.println("file" + file);
				FileWriter fw = new FileWriter(new File(file,cd.getName() + ".java"));
				cd.define(fw);
				fw.close();
			}
			results.putAll(cp.generateRestConfig());
			configs.put(cp.getType(), results);
		}
		File file = new File(basePath,"rest_service.properties");
		FileWriter fw = new FileWriter(file);
		for(Entry<String,Map<String, Map<String,String>>> type : configs.entrySet()){
			fw.append("#").append("===================").append(" REST TYPE ").append(type.getKey().toUpperCase()).append(" ===================").append("\n");
			for(Entry<String, Map<String,String>> result : type.getValue().entrySet()){
				fw.append("#").append("-------------------").append(" REST NAME ").append(result.getKey().toUpperCase()).append("-------------------").append("\n");
				for(Entry<String, String> entry : result.getValue().entrySet()){
					fw.append(entry.getKey()).append(" = ").append(entry.getValue()).append("\n");
				}
			}
			fw.append("\n");
		}
		fw.close();
	}
}
