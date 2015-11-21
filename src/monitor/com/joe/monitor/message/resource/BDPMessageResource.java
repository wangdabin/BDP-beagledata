package com.joe.monitor.message.resource;

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
import com.joe.monitor.message.service.BDPMessageService;
import com.joe.monitor.message.utils.Constants;
import com.joe.monitor.vo.BDPMessage;
import com.joe.monitor.vo.BDPMessageModel;
import com.joe.monitor.vo.BDPMessageResult;

@RestResource(name = Constants.NAME)
@Path(BDPVersion.BASE_PATH + BDPMessageResource.PATH)
@Produces(value = {MediaType.APPLICATION_JSON,MediaType.TEXT_XML,MediaType.APPLICATION_XML})
@Consumes(value = {MediaType.APPLICATION_JSON,MediaType.TEXT_XML,MediaType.APPLICATION_XML})
@Controller
public class BDPMessageResource {

	public static final String PATH = "/message";
	
	
	@Resource(name = "bDPMessageService")
	private BDPMessageService bDPMessageService;
	@GET
	@Path("/list")
	public List<BDPMessage> list() {
		List<BDPMessage> result = bDPMessageService.getAll();
		return result;
	}
	
	@POST
	@Path("/query")
	public BDPMessageResult query(BDPMessageModel model) {
		List<BDPMessage> bdpMessages = bDPMessageService.query(model);
		BDPMessageResult result = new BDPMessageResult();
		result.setResult(bdpMessages);
		return result;
	}
	@DELETE
	@Path("/delete/{id}")
	public ReCode delete(@PathParam("id") int id) {
		bDPMessageService.removeById(id);
		ReCode reCode = new ReCode();
		reCode.setData(new Data(id));
		reCode.setErrcode(Constants.NOT_ERROR);
		reCode.setRet(Constants.RET_SUCCESS);
		return reCode;
	}

	@PUT
	@Path("/read/{id}")
	public ReCode read(@PathParam("id") int id) {
		bDPMessageService.read(id);
		ReCode reCode = new ReCode();
		reCode.setData(new Data(id));
		reCode.setErrcode(Constants.NOT_ERROR);
		reCode.setRet(Constants.RET_SUCCESS);
		return reCode;
	}
}
