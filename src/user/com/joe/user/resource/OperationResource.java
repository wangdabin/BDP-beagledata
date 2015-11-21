package com.joe.user.resource;

import java.util.List;

import javax.annotation.Resource;
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
import com.joe.user.i18n.UserI18nMessage;
import com.joe.user.service.OperationService;
import com.joe.user.utils.OperationUtils;
import com.joe.user.vo.Operation;

/**
*
* @description
*
* @function
*
* @author ZhouZH
*
*/
@RestResource(name = OperationUtils.NAME)
@Path(BDPVersion.BASE_PATH + OperationResource.PATH)
@Controller
public class OperationResource {
	
public static final String PATH = "/operations";
	
	@Resource(name = "operationService")
	private OperationService operationService;
	
	@Resource(name = "userI18n")
	private UserI18nMessage userI18nMessage;
	
	
	/**
     * 
     * @return
     */
    @GET
    @Produces(value = {MediaType.TEXT_XML,MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
    @Path("/list")
    public List<Operation> list(){
    	return operationService.GetAll();
    }
    
    /**
     * 
     * @return
     */
    @GET
    @Produces(value = {MediaType.TEXT_XML,MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
    public List<Operation> getAll(){
    	return operationService.GetAll();
    }
    
    
    @GET
    @Produces(value = {MediaType.TEXT_XML,MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
    @Path("/{name}")
    public Operation getRole(@PathParam("name") String name){
    	return operationService.GetOperationByName(name);
    }
    
    /**
     * 添加User节点
     * @param User
     * @return
     */
    @POST
    @Produces(value = {MediaType.TEXT_XML,MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
    @Path("/add")
    public ReCode add(Operation operation){
    	operation.setCreateDate(System.currentTimeMillis());//设置创建时间
    	operation.setLastUpdateDate(System.currentTimeMillis());
    	operationService.NewOperation(operation);
    	ReCode reCode = new ReCode();
    	reCode.setData(new Data(operation.getName()));
    	reCode.setMsg(userI18nMessage.getMessage(Constants.SUCCESS_ADD));
    	reCode.setErrcode(Constants.NOT_ERROR);
    	reCode.setRet(Constants.RET_SUCCESS);
    	return reCode;
    }
    
    @PUT
    @Produces(value = {MediaType.TEXT_XML,MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
    @Path("/{name}")
    public ReCode update(@PathParam("name") String name,Operation operation){
    	operation.setName(name);
    	operation.setLastUpdateDate(System.currentTimeMillis());//设置更新时间
    	operationService.ModifyOperation(operation);
    	ReCode reCode = new ReCode();
    	reCode.setData(new Data(operation.getName()));
    	reCode.setMsg(userI18nMessage.getMessage(Constants.SUCCESS_ADD));
    	reCode.setErrcode(Constants.NOT_ERROR);
    	reCode.setRet(Constants.RET_SUCCESS);
    	return reCode;
    }
}
