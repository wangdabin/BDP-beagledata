package com.joe.host.resource;

import java.io.IOException;
import java.util.List;

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

import org.springframework.stereotype.Controller;

import com.joe.core.annotation.RestResource;
import com.joe.core.i18n.I18nMessage;
import com.joe.core.version.BDPVersion;
import com.joe.core.vo.ReCode;
import com.joe.core.vo.ReCode.Data;
import com.joe.core.vo.Total;
import com.joe.host.init.Status;
import com.joe.host.service.HostService;
import com.joe.host.utils.Constants;
import com.joe.host.utils.HostUtils;
import com.joe.host.vo.AllHostSummary;
import com.joe.host.vo.Echarts;
import com.joe.host.vo.FindResult;
import com.joe.host.vo.Host;
import com.joe.host.vo.HostRange;
import com.joe.host.vo.HostSummary;
import com.sky.service.service.ServiceHostService;
import com.sky.service.vo.ServiceHost;

/**
 * 
 * 主机资源
 * 
 * @author Joe
 * 
 */
@RestResource(name = HostUtils.NAME)
@Path(BDPVersion.BASE_PATH + HostResource.PATH)
@Produces(value = {MediaType.APPLICATION_JSON,MediaType.TEXT_XML,MediaType.APPLICATION_XML})
@Consumes(value = {MediaType.APPLICATION_JSON,MediaType.TEXT_XML,MediaType.APPLICATION_XML})
@Controller
public class HostResource {

    public static final String PATH = "/hosts"; // 资源路径

    @Resource(name = "hostService")
    private HostService hostService;
    
    @Resource(name = "serviceHostService")
    private ServiceHostService serviceHostService;

    @Resource(name = "hostI18n")
    private I18nMessage hostI18n;

    /**
     * @return
     */
    @GET
    @Path("/list")
    public List<Host> list() {
	return hostService.getAll();
    }

    /**
     * 
     * @return
     */
    @GET
    public List<Host> getAll() {
	return hostService.getAll();
    }

    /**
     * 得到主机总数
     * 
     * @return
     */
    @GET
    @Path("/total")
    public Total getTotal() {
	return hostService.getTotal();
    }

    @GET
    @Path("/{hostname}")
    public Host getHost(@PathParam("hostname") String hostName) {
	return hostService.findByHostName(hostName);
    }

    @POST
    @Path("/find")
    public FindResult find(HostRange hostRange) {
	return hostService.find(hostRange);
    }

    /**
     * @Title: find
     * @Description: 根据一个host集合查找hosts
     * @param hosts
     * @return FindResult 存在和不存在的host集合
     */
    @POST
    @Path("/findbatch")
    public FindResult find(Host[] hosts) {// TODO
					  // 由于传递List类型的Host失败,未找到方法,先暂时使用Host类型的数组进行传递
	return hostService.find(hosts);
    }

    /**
     * 添加host节点
     * 
     * @param host
     * @return
     * @throws IOException
     */
    @POST
    @Path("/add")
    public ReCode add(Host host) throws IOException {
	host.setCreateDate(System.currentTimeMillis());// 设置创建时间
	hostService.add(host);
	ReCode reCode = new ReCode();
	reCode.setData(new Data(host.getId()));
	reCode.setMsg(hostI18n.getMessage(Constants.SUCCESS_ADD));
	reCode.setErrcode(Constants.NOT_ERROR);
	reCode.setRet(Constants.RET_SUCCESS);
	return reCode;
    }

    /**
     * 检查节点状态
     * 
     * @param host
     * @return
     */
    @POST
    @Path("/monitor")
    public Status monitor(Host host) {
	return hostService.monitor(host);
    }

    @PUT
    @Path("/{hid}")
    public ReCode update(@PathParam("hid") int id, Host host) {
	host.setId(id);
	host.setLastUpdateDate(System.currentTimeMillis());// 设置更新时间
	hostService.update(host);
	ReCode reCode = new ReCode();
	reCode.setData(new Data(host.getId()));
	reCode.setMsg(hostI18n.getMessage(Constants.SUCCESS_ADD));
	reCode.setErrcode(Constants.NOT_ERROR);
	reCode.setRet(Constants.RET_SUCCESS);
	return reCode;
    }

    /**
     * 
     * @param id
     * @return
     */
    @DELETE
    @Path("/{hid}")
    public ReCode delete(@PathParam("hid") int id) {
	hostService.removeById(id);
	ReCode reCode = new ReCode();
	reCode.setData(new Data(id));
	reCode.setMsg(hostI18n.getMessage(Constants.SUCCESS_DELETE));
	reCode.setErrcode(Constants.NOT_ERROR);
	reCode.setRet(Constants.RET_SUCCESS);
	return reCode;
    }
    
    
    /**
     * @Title: monitor
     * @Description: 首页主机概要
     * @param hosts
     * @return 
     */
    @GET
    @Produces(value = { MediaType.TEXT_XML, MediaType.APPLICATION_XML,
	    MediaType.APPLICATION_JSON })
    @Path("/summary")
    public AllHostSummary findAllHostSummary() {
	return hostService.findAllHostSummary();
    }
    
    /**
     * @Title: monitor
     * @Description: 主机详情概要
     * @param hosts
     * @return 
     */
    @GET
    @Produces(value = { MediaType.TEXT_XML, MediaType.APPLICATION_XML,
	    MediaType.APPLICATION_JSON })
    @Path("/{hostName}/summary")
    public HostSummary findHostSummary(@PathParam("hostName") String hostName) {
    return hostService.findHostSummary(hostName);
    }
    
    /**
     * @Title: monitor
     * @Description: 主机服务列表
     * @param hosts
     * @return 
     */
    @GET
    @Produces(value = { MediaType.TEXT_XML, MediaType.APPLICATION_XML,
	    MediaType.APPLICATION_JSON })
    @Path("/{hostName}/service")
    public List<ServiceHost> findHostService(@PathParam("hostName") String hostName) {
    return serviceHostService.findServiceByHost(hostName);
    }
    
    /**
     * @Title: monitor
     * @Description: 查询某段时间内指定类型的历史数据
     * @param
     * @return 
     */
    @GET
    @Produces(value = { MediaType.TEXT_XML, MediaType.APPLICATION_XML,
	    MediaType.APPLICATION_JSON })
    @Path("/historyecharts")
    public Echarts findHistoryEcharts(@QueryParam("typeName") String typeName,@QueryParam("hostName") String hostName,@QueryParam("stime") long stime,@QueryParam("etime") long etime) {
        if ("memory".equalsIgnoreCase(typeName)) {
        	return hostService.findMemoryEcharts(hostName, stime, etime);
		} else if ("net".equalsIgnoreCase(typeName)) {
			return hostService.findNetEcharts(hostName, stime, etime);
		} else if ("disk".equalsIgnoreCase(typeName)) {
			return hostService.findDiskEcharts(hostName, stime, etime);
		} else if ("cpu".equalsIgnoreCase(typeName)) {
			return hostService.findCpuEcharts(hostName, stime, etime);
		}
        return new Echarts();
    }
    @POST
	@Path("/sshkeygen")
	public ReCode sshKeygen(){
		hostService.sshKeygen();
		ReCode reCode = new ReCode();
		reCode.setData(new Data("sshkeygen"));
		reCode.setErrcode(Constants.NOT_ERROR);
		reCode.setRet(Constants.RET_SUCCESS);
		return reCode;
	}
    @POST
   	@Path("/clustertrust")
   	public ReCode clusterTrust() throws Exception{
   		hostService.clusterTrust();
   		ReCode reCode = new ReCode();
   		reCode.setData(new Data("clustertrust"));
   		reCode.setErrcode(Constants.NOT_ERROR);
   		reCode.setRet(Constants.RET_SUCCESS);
   		return reCode;
   	}
    
    
}