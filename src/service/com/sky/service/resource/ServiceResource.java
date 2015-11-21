package com.sky.service.resource;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;

import com.joe.core.annotation.RestResource;
import com.joe.core.config.CoreConfig;
import com.joe.core.utils.CoreConfigUtils;
import com.joe.core.version.BDPVersion;
import com.joe.core.vo.ReCode;
import com.joe.core.vo.ReCode.Data;
import com.joe.host.utils.HostUtils;
import com.joe.host.vo.Host;
import com.joe.plugin.manager.TypePluginFactory;
import com.sky.config.ConfigAble;
import com.sky.service.Service;
import com.sky.service.define.KeyValue;
import com.sky.service.service.ServiceHostService;
import com.sky.service.utils.Constants;
import com.sky.service.utils.ServiceUtils;
import com.sky.service.vo.AllServiceSummary;
import com.sky.service.vo.ServiceDetails;
import com.sky.service.vo.ServiceStatusVo;
import com.sky.service.vo.SubServiceDetails;
import com.sky.service.vo.SubServices;

/**
 * 服务资源
 * 
 * @author qiaolong
 * 
 */
@RestResource(name = Constants.NAME)
@Path(BDPVersion.BASE_PATH + ServiceResource.PATH)
@Produces(value = {MediaType.APPLICATION_JSON})
@Consumes(value = {MediaType.APPLICATION_JSON})
@Controller
public class ServiceResource {
	public static final String PATH = "/services";

	@Resource(name = CoreConfig.RESOURCE_NAME)
	private ConfigAble confAble;

	@Resource(name = "serviceHostService")
	public ServiceHostService serviceHostService;
	
	/**
	 * 的到当前平台所有支持的服务
	 * 
	 * @return
	 * @throws IOException
	 */
	@GET
	@Path("/supports")
	public Map<String, List<String>> supports() throws IOException {
		try {
			TypePluginFactory factory = getFactory();
			return factory.getSupports();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 
	 * 根据服务名称得到支持的所有的版本
	 * 
	 * @param serviceName
	 * @return
	 */
	@GET
	@Path("/{name}/versions")
	public List<String> versions(@PathParam("name") String serviceName) {
		try {
			TypePluginFactory factory = getFactory();
			return factory.getSupports(serviceName);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 添加配置项
	 * 
	 * @param name
	 * @param version
	 * @param keyValue
	 * @return
	 */
	@POST
	@Path("/{name}/{version}/configs")
	public ReCode addConfig(@PathParam("name") String name,
			@PathParam("version") String version, KeyValue keyValue) {
		Service service = getService(name, version);
		service.addConfig(keyValue);
		ReCode re = new ReCode();
		re.setData(new Data(keyValue.getConfigKey()));
		re.setErrcode(Constants.NOT_ERROR);
		re.setRet(Constants.RET_SUCCESS);
		re.setMsg("add config success");
		return re;
	}

	/**
	 * 添加配置项
	 * 
	 * @param name
	 * @param version
	 * @param keyValue
	 * @return
	 */
	@POST
	@Path("/{name}/{version}/batchconfigs")
	public ReCode addBatchConfig(@PathParam("name") String name,
			@PathParam("version") String version, @QueryParam("topName") String topName,List<KeyValue> keyValues) {
		Service service ;
		if (StringUtils.isEmpty(topName)) {
			service = getService(name, version);
		} else {
			service = ServiceUtils.getService(topName, version, name);
		}
		for (KeyValue keyValue : keyValues) {
			service.addConfig(keyValue);
		}
		ReCode re = new ReCode();
		re.setErrcode(Constants.NOT_ERROR);
		re.setRet(Constants.RET_SUCCESS);
		re.setMsg("add config success");
		return re;
	}
	/**
	 * 删除配置项
	 * 
	 * @param name
	 * @param version
	 * @param keyValue
	 * @return
	 */
	@DELETE
	@Path("/{name}/{version}/configs/{key}")
	public ReCode removeConfig(@PathParam("name") String name,
			@PathParam("version") String version, @PathParam("key") String key) {
		Service service = getService(name, version);
		service.removeConfig(key);
		ReCode re = new ReCode();
		re.setData(new Data(key));
		re.setErrcode(Constants.NOT_ERROR);
		re.setRet(Constants.RET_SUCCESS);
		re.setMsg("delete config success");
		return re;
	}

	/**
	 * 得到当前服务的模式
	 * 
	 * @return
	 */
	@GET
	@Path("/{name}/{version}/model")
	public String getModel(@PathParam("name") String name,
			@PathParam("version") String version) {
		Service service = getService(name, version);
		return service.getModel();
	}

	/**
	 * 得到当前服务详细信息
	 * 
	 * @return
	 */
	@GET
	@Path("/{name}/{version}")
	public List<ServiceDetails> getServiceDetail(@PathParam("name") String name,
			@PathParam("version") String version) {
		List<ServiceDetails> serviceDetails = new ArrayList<ServiceDetails>();
		if ("hadoop".equalsIgnoreCase(name)) {
			Service service = getService(name, version);
			List<Service> seconds = service.getSubService();
			for (Service second : seconds) {
				ServiceDetails serviceDetail = getServiceDetails(second.getName(), second.getVersion(),true,name);
				serviceDetails.add(serviceDetail);
			}
		} else {//ZOOKEEPER HBASE
			ServiceDetails serviceDetail = getServiceDetails(name, version,false,null);
			serviceDetails.add(serviceDetail);
		}
		return serviceDetails;
	}

	/**
	 * 得到当前服务所安装的主机
	 * 
	 * @return
	 */
	@GET
	@Path("/{name}/{version}/hosts")
	public List<Host> getHosts(@PathParam("name") String name,
			@PathParam("version") String version) {
		Service service = getService(name, version);
		return service.getHosts();
	}

	/**
	 * 删除指定的主机
	 * 
	 * @param name
	 * @param version
	 * @param host
	 */
	@DELETE
	@Path("/{name}/{version}/hosts")
	public ReCode removeHost(@PathParam("name") String name,
			@PathParam("version") String version, Host host) {
		Service service = getService(name, version);
		service.removeHost(host);
		return createReCode(HostUtils.buildKey(host), "Delete host success");
	}

	/**
	 * 得到当前服务的用户
	 * 
	 * @return
	 */
	@GET
	@Path("/{name}/{version}/owner")
	public String getOwner(@PathParam("name") String name,
			@PathParam("version") String version) {
		Service service = getService(name, version);
		return service.getOwner();
	}

	/**
	 * 删除此服务
	 * 
	 * @param name
	 * @param version
	 */
	@DELETE
	@Path("/{name}/{version}")
	public ReCode remove(@PathParam("name") String name,
			@PathParam("version") String version) {
		Service service = getService(name, version);
		service.remove();
		return createReCode(name, "Delete service success");
	}

	/**
	 * 启动此服务
	 * 
	 * @param name
	 * @param version
	 * @return
	 */
	@PUT
	@Path("/{name}/{version}/start")
	public ReCode start(@PathParam("name") String name,
			@PathParam("version") String version) {
		Service service = getService(name, version);
		service.start();
		return createReCode(name, "Service start success");
	}

	/**
	 * 
	 * @param name
	 * @param version
	 */
	@PUT
	@Path("/{name}/{version}/stop")
	public ReCode stop(@PathParam("name") String name,
			@PathParam("version") String version) {
		Service service = getService(name, version);
		service.stop();
		return createReCode(name, "Service stop success");
	}
	
	/**
	 * 
	 * @param name
	 * @param version
	 */
	@PUT
	@Path("/{name}/{version}/stopsub")
	public ReCode stop(@PathParam("name") String name,
			@PathParam("version") String version,@QueryParam("subname") String subName) {
		Service service = getService(name, version,subName);
		service.stop();
		return createReCode(name, "Service stop success");
	}
	
	/**
	 * 
	 * @param name
	 * @param version
	 */
	@PUT
	@Path("/{name}/{version}/stopsubhost")
	public ReCode stop(@PathParam("name") String name,
			@PathParam("version") String version,@QueryParam("subname") String subName,@QueryParam("host")String host) {
		Service service = getService(name, version,subName);
		service.stop(new Host(host, host));
		return createReCode(name, "Service stop success");
	}

	/**
	 * 启动此服务
	 * 
	 * @param name
	 * @param version
	 * @return
	 */
	@PUT
	@Path("/{name}/{version}/startsub")
	public ReCode start(@PathParam("name") String name,
			@PathParam("version") String version,@QueryParam("subname") String subName) {
		Service service = getService(name, version,subName);
		service.start();
		return createReCode(name, "Service start success");
	}
	
	/**
	 * 启动此服务
	 * 
	 * @param name
	 * @param version
	 * @return
	 */
	@PUT
	@Path("/{name}/{version}/startsubhost")
	public ReCode start(@PathParam("name") String name,
			@PathParam("version") String version,@QueryParam("subname") String subName,@QueryParam("host")String host) {
		Service service = getService(name, version,subName);
		service.start(new Host(host,host));
		return createReCode(name, "Service start success");
	}
	
	/**
	 * 添加配置项
	 * 
	 * @param name
	 * @param version
	 * @param keyValue
	 * @return
	 */
	@POST
	@Path("/{name}/{version}/environments")
	public ReCode addEnvironment(@PathParam("name") String name, @PathParam("version") String version, KeyValue keyValue) {
		Service service = getService(name, version);
		service.addEnvironment(keyValue);
		return createReCode(keyValue.getConfigKey(), "Add environment success!");
	}
	
	@POST
	@Path("/{name}/{version}/batchenvironments")
	public ReCode addBatchEnvironment(@PathParam("name") String name, @PathParam("version") String version,@QueryParam("topName") String topName, List<KeyValue> keyValues) {
		Service service ;
		if (StringUtils.isEmpty(topName)) {
			service = getService(name, version);
		} else {
			service = ServiceUtils.getService(topName, version, name);
		}
		for (KeyValue keyValue : keyValues) {
			service.addEnvironment(keyValue);
		}
		return createReCode("", "Add environment success!");
	}

	/**
	 * 删除配置项
	 * 
	 * @param name
	 * @param version
	 * @param keyValue
	 * @return
	 */
	@DELETE
	@Path("/{name}/{version}/environments/{key}")
	public ReCode removeEnvironment(@PathParam("name") String name,
			@PathParam("version") String version, @PathParam("key") String key) {
		Service service = getService(name, version);
		service.removeEnvironment(key);
		return createReCode(key, "Delete environment success!");
	}

	/**
	 * 设置安装目录
	 * 
	 * @param name
	 * @param version
	 * @param installDir
	 * @return
	 */
	@POST
	@Path("/{name}/{version}/installdir")
	public ReCode setInstallDir(@PathParam("name") String name,
			@PathParam("version") String version,
			@QueryParam("installdir") String installDir) {
		Service service = getService(name, version);
		service.setInstallDir(installDir);
		return createReCode(name, "Set install dir success!");
	}

	/**
	 * 创建返回值
	 * 
	 * @param key
	 * @param msg
	 * @return
	 */
	private ReCode createReCode(String key, String msg) {
		ReCode re = new ReCode();
		re.setData(new Data(key));
		re.setErrcode(Constants.NOT_ERROR);
		re.setRet(Constants.RET_SUCCESS);
		re.setMsg(msg);
		return re;
	}

	/**
	 * 根据名称和版本得到服务
	 * 
	 * @param name
	 * @param version
	 * @return
	 */
	private Service getService(String name, String version) {
		return ServiceUtils.getService(name, version, null);
	}
	
	/**
	 * 根据名称和版本得到服务
	 * 
	 * @param name
	 * @param version
	 * @return
	 */
	private Service getService(String name, String version,String subName) {
		return ServiceUtils.getService(name, version, subName);
	}

	/**
	 * 得到当前的插件工程类
	 * 
	 * @return
	 * @throws IOException
	 */
	private TypePluginFactory getFactory() throws IOException {
		return TypePluginFactory.getFactory(CoreConfigUtils.create(),Constants.TYPE);
	}
	
	public static void main(String[] args) throws IOException {
		TypePluginFactory factory = TypePluginFactory.getFactory(CoreConfigUtils.create(),Constants.TYPE);
		System.out.println(factory.getSupports());
	}
	
	@GET
	@Produces(value = { MediaType.TEXT_XML, MediaType.APPLICATION_XML,
			MediaType.APPLICATION_JSON })
	@Path("/summary")
	public AllServiceSummary serviceHostService() {
		return serviceHostService.findAllServiceSummary();
	}
	
	/**
	 * 显示服务的配置项
	 * 
	 * @param name
	 * @param version
	 * @return
	 */
	@GET
	@Path("/config/{name}/{version}")
	public String config(@PathParam("name") String name, @PathParam("version") String version,
						 @QueryParam("topname") String topname) {
		Service service;
		List<KeyValue> configs = new ArrayList<KeyValue>();
		List<KeyValue> envs = new ArrayList<KeyValue>();
		if (!org.apache.commons.lang.StringUtils.isEmpty(topname)) {
			service = ServiceUtils.getService(topname, version, name);
		} else {
			service = getService(name, version);
		}
		if (service.getAllConfig() != null) {
			configs.addAll(service.getAllConfig());
		}
		if (service.getAllEnvironment() != null) {
			envs.addAll(service.getAllEnvironment());
		}
		JSONObject json = new JSONObject();
		json.put("configs", JSONArray.fromObject(configs));
		json.put("envs", JSONArray.fromObject(envs));
		return json.toString();
	}
	
	private ServiceDetails getServiceDetails(String name, String version,boolean isSub,String topName) {
		List<SubServices> subServices = new ArrayList<SubServices>();
		ServiceDetails serviceDetail = new ServiceDetails();
		serviceDetail.setName(name); //
		Service service ;
		if (isSub) {
			service = ServiceUtils.getService(topName, version, name);
		} else {
			service = getService(name, version);
		}
		List<Service> seconds = service.getSubService();
		if (seconds == null || seconds.size() == 0) {
			seconds.add(service);
		}
		for (Service second : seconds) {
			SubServices subService= new SubServices();
			subService.setSubName(second.getName());//
			subService.setActive(0);//
			subService.setDead(0);//
			int active = 0;
			int dead =0 ;
			List<SubServiceDetails> SubServiceDetailsList = new ArrayList<SubServiceDetails>();
			List<ServiceStatusVo> serviceStatus = second.getServiceStatus(second.getName(), second.getVersion());
			for (ServiceStatusVo serviceStatusVo : serviceStatus) {
				if (serviceStatusVo.getServiceStatus() == 1) {
					active++;
				} else {
					dead++;
				}
				SubServiceDetails subServiceStatus = new SubServiceDetails();
				subServiceStatus.setHostIp(serviceStatusVo.getHost().getIp());
				subServiceStatus.setHostName(serviceStatusVo.getHost().getName());
				subServiceStatus.setServiceStatus(String.valueOf(serviceStatusVo.getServiceStatus()));
				subServiceStatus.setHostStatus(serviceStatusVo.getHost().getStatus());
				SubServiceDetailsList.add(subServiceStatus);
			}
			subService.setServiceStatus(SubServiceDetailsList);
			subService.setActive(active);
			subService.setDead(dead);
			subServices.add(subService);
		}
		serviceDetail.setSubServices(subServices);
		return serviceDetail;
	}
}
