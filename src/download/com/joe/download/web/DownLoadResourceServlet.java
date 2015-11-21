package com.joe.download.web;

import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.joe.core.utils.CoreConfigUtils;
import com.joe.download.utils.StreamUtils;
import com.sky.config.ConfigUtil;

/**
 * 
 * @ClassName: DownLoadResourceServlet
 * @Description: 根据组件名称提供下载服务
 * @author WDB
 * @date 2014-12-28 下午9:05:15
 * 
 */
public class DownLoadResourceServlet extends HttpServlet {
    private static final long serialVersionUID = -6636890311791084167L;

    private static final Logger LOG = Logger
	    .getLogger(DownLoadResourceServlet.class);

    public void doGet(HttpServletRequest request, HttpServletResponse response)
	    throws ServletException, IOException {
	this.doPost(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
	    throws ServletException, IOException {
	try {
	    // 资源所对应的当前版本
	    String version = request.getParameter("version");
	    if(StringUtils.isBlank(version)){
	    	version = CoreConfigUtils.create().getString("component.current.version","cdh5.1.0");
	    }
	    String serviceName = request.getParameter("serviceName");//当前需要下载的组件名
	    //当前组件对应的文件名
	    String fileName = ConfigUtil.createConfig("service_" + version + ".properties").getString(serviceName);

	    if (StringUtils.isBlank(fileName)) {
		//TODO
		response.sendError(HttpServletResponse.SC_NOT_FOUND,serviceName + "不提供下载服务!");
		LOG.error(serviceName + "不提供下载服务");
		return;
	    }
	    // 对应各个资源*.tar.gz在平台存放的根路径
	    String rootDir = CoreConfigUtils.create().getString(
		    "host.component.root.dir");
	    if(!rootDir.endsWith("/")) {
		rootDir = rootDir + "/";
	    }
	    InputStream inputStream = StreamUtils.getInputStreamByFileName(
		    rootDir + version, fileName);

	    fileName = URLEncoder.encode(fileName, "UTF-8");
	    response.reset();
	    response.setContentType("application/x-msdownload");
	    response.setHeader("Content-Disposition", "attachment; filename="
		    + fileName);
	    response.addHeader("Content-Length", "" + inputStream.available());
	    response.setContentType("application/octet-stream;charset=UTF-8");
	    StreamUtils.input2Output(inputStream, response.getOutputStream());
	} catch (Exception e) {
	    LOG.error(e.getMessage(), e);
	    response.sendError(HttpServletResponse.SC_SERVICE_UNAVAILABLE,
		    e.getMessage());
	}
    }
}