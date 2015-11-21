package com.sky.service.resource;

import java.io.IOException;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.springframework.stereotype.Controller;

import com.joe.core.annotation.RestResource;
import com.joe.core.install.Install;
import com.joe.core.rest.callback.RestScheduleCallBack;
import com.joe.core.version.BDPVersion;
import com.joe.core.vo.Bool;
import com.joe.core.vo.ReCode;
import com.joe.plugin.resource.PluginResource;
import com.sky.service.define.KeyValue;
import com.sky.service.utils.Constants;

@RestResource(name = Constants.NAME_INSTALL)
@Path(BDPVersion.BASE_PATH + InstallResource.PATH)
@Produces(value = {MediaType.APPLICATION_JSON})
@Consumes(value = {MediaType.APPLICATION_JSON})
@Controller
public class InstallResource extends PluginResource{
	public static final String PATH = "/services/install";
	public static final String type = "service";
	
	@GET
	@Path("/{name}/{version}/hasnext")
	public Bool hasNext(@PathParam("name") String name, @PathParam("version") String version){
		Install install = getInstall(name,version);
		return new Bool(install.hasNext());
	}
	
	@PUT
	@Path("/{name}/{version}/next")
	public ReCode next(@PathParam("name") String name, @PathParam("version") String version){
		Install install = getInstall(name,version);
		install.next();
		return createReCode(name + "-" + version,"next success!");
	}
	
	@GET
	@Path("/{name}/{version}/hasReverse")
	public Bool hasReverse(@PathParam("name") String name, @PathParam("version") String version){
		Install install = getInstall(name,version);
		return new Bool(install.hasReverse());
	}
	
	@PUT
	@Path("/{name}/{version}/reverse")
	public ReCode reverse(@PathParam("name") String name, @PathParam("version") String version){
		Install install = getInstall(name,version);
		install.reverse();
		return createReCode(name + "-" + version,"reverse success!");
	}
	
	@GET
	@Path("/{name}/{version}/supportsbasic")
	public List<KeyValue> supportsBasic(@PathParam("name") String name, @PathParam("version") String version){
		Install install = getInstall(name,version);
		return install.supportsBasic();
	}
	
	@GET
	@Path("/{name}/{version}/supportsadvanced")
	public List<KeyValue> supportsAdvanced(@PathParam("name") String name, @PathParam("version") String version){
		Install install = getInstall(name,version);
		return install.supportsAdvanced();
	}
	
	@POST
	@Produces(value = {MediaType.APPLICATION_JSON})
	@Consumes(value = {MediaType.APPLICATION_JSON})
	@Path("/{name}/{version}/addkeyvalue")
	public ReCode addKeyValue(@PathParam("name") String name,
			@PathParam("version") String version,KeyValue keyValue){
		Install install = getInstall(name,version);
		install.addKeyValue(keyValue);
		return createReCode(name + "-" + version,"add keyvalue success!");
	}
	
	/**
	 * 
	 * @param name
	 * @param version
	 * @param callBackUrl
	 */
	@POST
	@Path("/{name}/{version}/install")
	public ReCode install(@PathParam("name") String name,
			@PathParam("version") String version, @QueryParam("url") String callBackUrl) {
		Install install = getInstall(name, version);
		install.install(new RestScheduleCallBack(install, callBackUrl));
		return createReCode(name, "Submit install task success");
	}
	
	/**
	 * 
	 * @param name
	 * @param version
	 * @return
	 */
	private Install getInstall(String name, String version){
		Install install = getInstance(Install.class, Constants.TYPE, name, version, Install.X_POINT_ID);
		try {
			install.init();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		return install;
	}
}
