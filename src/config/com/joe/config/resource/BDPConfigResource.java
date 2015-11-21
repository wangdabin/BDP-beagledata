package com.joe.config.resource;

import java.util.Arrays;
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
import javax.ws.rs.core.MediaType;

import org.springframework.stereotype.Controller;

import com.joe.core.annotation.RestResource;
import com.joe.core.version.BDPVersion;
import com.joe.core.vo.ReCode;
import com.joe.core.vo.ReCode.Data;
import com.joe.host.utils.Constants;
import com.sky.config.BDPConfigVO;
import com.sky.config.service.BDPConfigService;
import com.sky.config.utils.BDPConfigConstants;

/**
 * @ClassName: BDPConfigResource
 * @Description: BDP全局属性
 * @author WDB
 * @date 2015-4-29 下午2:46:51
 */
@RestResource(name = BDPConfigConstants.NAME)
@Path(BDPVersion.BASE_PATH + BDPConfigResource.PATH)
@Produces(value = { MediaType.APPLICATION_JSON, MediaType.TEXT_XML,
		MediaType.APPLICATION_XML })
@Consumes(value = { MediaType.APPLICATION_JSON, MediaType.TEXT_XML,
		MediaType.APPLICATION_XML })
@Controller
public class BDPConfigResource {

	public static final String PATH = "/bdpconfig"; // 资源路径

	@Resource(name = "bDPConfigService")
	private BDPConfigService bDPConfigService;

	@GET
	@Path("/list")
	public List<BDPConfigVO> getAll() {
		return bDPConfigService.getAll();
	}

	// monitory=cpu,disk,memory,net
	// monitory_cpu=0 15 10 * * ? *
	// monitory_disk=0 15 10 * * ? *
	// monitory_memory=0 15 10 * * ? *
	// monitory_net=0 15 10 * * ? *

	@GET
	@Path("/get/{key}")
	public BDPConfigVO get(@PathParam("key") String key) {
		return bDPConfigService.findByKey(key);
	}

	@POST
	@Path("/add")
	public ReCode add(BDPConfigVO bdpConfigVO) {
		bDPConfigService.add(bdpConfigVO);
		ReCode reCode = new ReCode();
		reCode.setData(new Data(bdpConfigVO.getName()));
		reCode.setErrcode(Constants.NOT_ERROR);
		reCode.setRet(Constants.RET_SUCCESS);
		return reCode;
	}

	@POST
	@Path("/add/batch")
	public ReCode add(BDPConfigVO[] bdpConfigVOs) {
		List<BDPConfigVO> asList = Arrays.asList(bdpConfigVOs);
		bDPConfigService.addBatch(asList);
		ReCode reCode = new ReCode();
		reCode.setData(new Data(asList.size()));
		reCode.setErrcode(Constants.NOT_ERROR);
		reCode.setRet(Constants.RET_SUCCESS);
		return reCode;
	}

	@PUT
	@Path("/update/{key}/{value}")
	public ReCode update(@PathParam("key") String key,
			@PathParam("value") String value) {
		bDPConfigService.updateValueByKey(key, value);
		ReCode reCode = new ReCode();
		reCode.setData(new Data(key));
		reCode.setErrcode(Constants.NOT_ERROR);
		reCode.setRet(Constants.RET_SUCCESS);
		return reCode;
	}

	@DELETE
	@Path("/delete/{key}")
	public ReCode delete(@PathParam("key") String key) {
		bDPConfigService.removeById(key);
		ReCode reCode = new ReCode();
		reCode.setData(new Data(key));
		reCode.setErrcode(Constants.NOT_ERROR);
		reCode.setRet(Constants.RET_SUCCESS);
		return reCode;
	}
}