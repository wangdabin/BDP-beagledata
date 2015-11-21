package com.joe.user.resource;

import java.util.ArrayList;
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
import com.joe.user.service.PermissionService;
import com.joe.user.utils.PermissionUtils;
import com.joe.user.vo.Permission;

/**
*
* @description
*
* @function
*
* @author ZhouZH
*
*/
@RestResource(name = PermissionUtils.NAME)
@Path(BDPVersion.BASE_PATH + PermissionResource.PATH)
@Controller
public class PermissionResource {
	
public static final String PATH = "/permissions";
	
	@Resource(name = "permissionService")
	private PermissionService permissionService;
	
	@Resource(name = "userI18n")
	private UserI18nMessage userI18nMessage;
	
	
	/**
     * 
     * @return
     */
    @GET
    @Produces(value = {MediaType.TEXT_XML,MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
    @Path("/list")
    public List<Permission> list(){
    	return permissionService.GetPermissionsByWhere("", "");
    }
    
    @GET
    @Produces(value = {MediaType.TEXT_XML,MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
    public List<Permission> getAll(){
	List<Permission> permissions = permissionService.GetPermissionsByWhere("", "");
	List<Permission> permissionVos = new ArrayList<Permission>();
	for(Permission permission : permissions) {
	    Permission permissionVo = new Permission();
	    permissionVo.setUrl(permission.getUrl());
	    permissionVo.setDescription(permission.getDescription());
	    permissionVo.setName(permission.getName());
	}
	return permissionVos;
    }
    
    @GET
    @Produces(value = {MediaType.TEXT_XML,MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
    @Path("/{url}")
    public Permission getRole(@PathParam("id") String url){
    	return permissionService.GetPermissionByURL(url);
    }
    
    /**
     * 添加User节点
     * @param User
     * @return
     */
    @POST
    @Produces(value = {MediaType.TEXT_XML,MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
    @Path("/add")
    public ReCode add(Permission permission){
    	permission.setCreateDate(System.currentTimeMillis());//设置创建时间
    	permission.setLastUpdateDate(System.currentTimeMillis());
    	permissionService.NewPermission(permission);
    	ReCode reCode = new ReCode();
    	reCode.setData(new Data(permission.getUrl()));
    	reCode.setMsg(userI18nMessage.getMessage(Constants.SUCCESS_ADD));
    	reCode.setErrcode(Constants.NOT_ERROR);
    	reCode.setRet(Constants.RET_SUCCESS);
    	return reCode;
    }
    
    @PUT
    @Produces(value = {MediaType.TEXT_XML,MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
    @Path("/{url}")
    public ReCode update(@PathParam("url") String url,Permission permission){
    	permission.setLastUpdateDate(System.currentTimeMillis());//设置更新时间
    	permissionService.ModifyPermission(permission);
    	ReCode reCode = new ReCode();
    	reCode.setData(new Data(permission.getUrl()));
    	reCode.setMsg(userI18nMessage.getMessage(Constants.SUCCESS_ADD));
    	reCode.setErrcode(Constants.NOT_ERROR);
    	reCode.setRet(Constants.RET_SUCCESS);
    	return reCode;
    }
}
