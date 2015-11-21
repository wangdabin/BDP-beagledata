package com.joe.download.web;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.joe.config.plugin.ConfigUtils;

public class ConfigFileDownload extends HttpServlet {

//	private static final Map<String,Config> configs = Collections.synchronizedMap(new HashMap<String, Config>());
	private static final Set<String> commonfiles = Collections.synchronizedSet(new HashSet<String>());
	private static final long serialVersionUID = 1L;
	private static final Logger LOG = Logger.getLogger(ConfigFileDownload.class);
	
//	/**
//	 * 
//	 * @param name
//	 * @param verion
//	 * @param configFile
//	 * @return
//	 */
//	private static String buildKey(String name,String version,String configFile){
//		return name + "-" +  version + "-" + configFile;
//	}
//
//	/**
//	 * 添加配置提供下载
//	 * @param name
//	 * @param version
//	 * @param configFile
//	 * @param config
//	 */
//	public static final void addConfig(String name,String version,String configFile,Config config){
//		configs.put(buildKey(name, version, configFile), config);
//	}
	
	/**
	 * @Title: addCommonFile
	 * @Description: 添加对应的普通文件
	 * @param fileName 文件名
	 * @throws
	 */
	public static final void addCommonFile(String fileName){
		commonfiles.add(fileName);
	}
	/**
	 * @Title: isSent
	 * @Description: 判断对应的文件是否已经发送
	 * @param  fileName    设定文件
	 * @return boolean    返回类型
	 * @throws
	 */
	public static boolean isSent(String fileName){
		return commonfiles.contains(fileName);
	}
	
	
	
//	/**
//	 * 删除配置文件
//	 * @param name
//	 * @param version
//	 * @param configFile
//	 */
//	public static final void removeConfig(String name,String version,String configFile){
//		configs.remove(buildKey(name, version, configFile));
//	}
	
	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		try {
//			String serviceName = req.getParameter("serviceName");
//			String version = req.getParameter("version");
			String fileName = req.getParameter("fileName");

//			String message = "Will download service name " + serviceName + ",version " + version + ",config file " + configFile;
			String message = "Will download file :" + fileName ;
			LOG.debug(message);
			if (StringUtils.isBlank(fileName)){
				resp.sendError(HttpServletResponse.SC_NOT_FOUND, "parameter error!");
				return;
			}
			
//			Config config = configs.get(buildKey(serviceName, version, configFile));
//			if(config == null){
//				throw new FileNotFoundException("The service name " + serviceName + ", version " + version + ",config file " + configFile + " not found");
//			}
			reset(fileName, resp);
			OutputStream output = resp.getOutputStream();
			URL file = ConfigUtils.locate(fileName);
			copy(file,output);
			
		} catch (Exception e) {
			LOG.error(e);
			resp.sendError(HttpServletResponse.SC_SERVICE_UNAVAILABLE, e.getMessage());
		}
	}

	private void reset(String fileName, HttpServletResponse response) throws IOException {
		response.reset();
		response.setContentType("application/x-msdownload");
		response.setHeader("Content-Disposition", "attachment; filename=" + fileName);
//		response.addHeader("Content-Length", "" + inputStream.available());
		response.setContentType("application/octet-stream;charset=UTF-8");
	}
	
	public  void copy(URL file, OutputStream outputStream) throws IOException
	   {
	      FileInputStream inputStream = new FileInputStream(file.getFile());
	      byte[] buff = new byte[65535];
	      int bytesRead; 
	      
	      while((bytesRead = inputStream.read(buff, 0, buff.length)) != -1)
	        outputStream.write(buff, 0, bytesRead);

	      outputStream.flush();
	      
	      if(inputStream != null)
	        inputStream.close();
	     
	   }

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		this.doPost(req, resp);
	}
	
	public static void main(String[] args) {
	    URL file = ConfigUtils.locate("skycloudrackawareness.jar");
	    System.out.println(file.getFile());
	}
}
