package com.joe.user.resource;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.ws.rs.Consumes;
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
import com.joe.user.i18n.UserI18nMessage;
import com.joe.user.service.RoleService;
import com.joe.user.utils.Constants;
import com.joe.user.utils.RoleUtils;
import com.joe.user.vo.Permission;
import com.joe.user.vo.Role;
import com.joe.user.vo.RoleRelation;
import com.joe.user.vo.User;

/**
 * @ClassName: RoleResource
 * @Description: 角色资源
 * @author WDB
 * @date 2015-4-28 下午2:54:08 PUT:修改,更新 POST:添加,创建 GET:请求用于获取一个或多个资源
 *       DELETE:删除一个资源
 */
@RestResource(name = RoleUtils.NAME)
@Path(BDPVersion.BASE_PATH + RoleResource.PATH)
@Produces(value = { MediaType.APPLICATION_JSON, MediaType.TEXT_XML,
		MediaType.APPLICATION_XML })
@Consumes(value = { MediaType.APPLICATION_JSON, MediaType.TEXT_XML,
		MediaType.APPLICATION_XML })
@Controller
public class RoleResource {

	public static final String PATH = "/roles";

	@Resource(name = "roleService")
	private RoleService roleService;

	@Resource(name = "userI18n")
	private UserI18nMessage userI18nMessage;

	@GET
	@Path("/list")
	public List<Role> list() {
		List<Role> roles = roleService.getAll();
		for (Role role : roles) {
			Set<User> users = role.getUsers();
			for (User user : users) {
				user.setRoles(null);
			}
			Set<RoleRelation> rolerelations = role.getRolerelation();
			for(RoleRelation relation : rolerelations){
				relation.setRole(null);
			}
		}
		return roles;
	}

	@GET
	@Path("/{id}")
	public Role getRole(@PathParam("id") int id) {
		Role role = roleService.get(id);
		Role roleVO = new Role();
		roleVO.setId(role.getId());
		roleVO.setName(role.getName());
		roleVO.setDescription(role.getDescription());
		return roleVO;
	}

	@POST
	@Path("/add")
	public ReCode add(Role role) {
		role.setCreateDate(System.currentTimeMillis());// 设置创建时间
		role.setLastUpdateDate(System.currentTimeMillis());
		roleService.add(role);
		ReCode reCode = new ReCode();
		reCode.setData(new Data(role.getId()));
		reCode.setMsg(userI18nMessage.getMessage(Constants.SUCCESS_ADD));
		reCode.setErrcode(Constants.NOT_ERROR);
		reCode.setRet(Constants.RET_SUCCESS);
		return reCode;
	}

	@PUT
	@Path("/{hid}")
	public ReCode update(@PathParam("hid") int id, Role role) {
		role.setId(id);
		role.setLastUpdateDate(System.currentTimeMillis());// 设置更新时间
		Role roleDB = roleService.get(id);
		roleDB.setName(role.getName());
		roleDB.setDescription(role.getDescription());
		roleService.update(roleDB);
		ReCode reCode = new ReCode();
		reCode.setData(new Data(role.getId()));
		reCode.setMsg(userI18nMessage.getMessage(Constants.SUCCESS_ADD));
		reCode.setErrcode(Constants.NOT_ERROR);
		reCode.setRet(Constants.RET_SUCCESS);
		return reCode;
	}
	
	@PUT
	@Path("/delete/{id}")
	public ReCode delete(@PathParam("id") int id) {
		Role role = roleService.get(id);
		roleService.remove(role);
		ReCode reCode = new ReCode();
		reCode.setData(new Data(role.getId()));
		reCode.setMsg(userI18nMessage.getMessage(Constants.SUCCESS_ADD));
		reCode.setErrcode(Constants.NOT_ERROR);
		reCode.setRet(Constants.RET_SUCCESS);
		return reCode;
	}
	
	

	/**
	 * @Title: permissionList
	 * @Description: 根据对应的Role查询对应的角色的权限信息
	 * @param role
	 * @return Permissions 该Role已绑定的权限和未绑定的权限
	 * @throws
	 */
	@GET
	@Produces(value = { MediaType.APPLICATION_JSON })
	@Path("/{roleId}/permissions")
	public List<Permission> getPermissionsByRoleId(
			@PathParam("roleId") int roleId) {
		return roleService.getPermissionsByRoleId(roleId);
	}

	/**
	 * @Title: update
	 * @Description: 更新角色与用户之间的关系
	 * @param @param id
	 * @param @param users
	 * @param @return 设定文件
	 * @return ReCode 返回类型
	 * @throws
	 */
	@PUT
	@Produces(value = { MediaType.APPLICATION_JSON })
	@Path("/update/users")
	public ReCode updateUsers(Role role) {
		roleService.updateUsers(role);
		ReCode reCode = new ReCode();
		reCode.setData(new Data(role.getId()));
		reCode.setMsg(userI18nMessage.getMessage(Constants.SUCCESS_ADD));
		reCode.setErrcode(Constants.NOT_ERROR);
		reCode.setRet(Constants.RET_SUCCESS);
		return reCode;
	}

	/**
	 * @Title: getUsersByRoleId
	 * @Description: 根据对应的角色得到对应的用户信息
	 * @param @param roleId
	 * @return List<UserWeb> 返回对应的已绑定的用户和未绑定的用户
	 * @throws
	 */
	@GET
	@Produces(value = { MediaType.APPLICATION_JSON })
	@Path("/list/users/{rid}")
	public Map<String, Set<User>> getUsersByRoleId(@PathParam("rid") int roleId) {
		Set<User> existUsers = roleService.getUsersByRoleId(roleId, true);
		Set<User> noExistUsers = roleService.getUsersByRoleId(roleId, false);
		Map<String, Set<User>> result = new HashMap<String, Set<User>>();
		result.put("existUsers", existUsers);
		result.put("noexistUsers", noExistUsers);
		return result;
	}

	/**
	 * @Title: updateRolerelations
	 * @Description: 更新角色与权限之间的关系
	 * @param @return 设定文件
	 * @return ReCode 返回类型
	 * @throws
	 */
	@PUT
	@Produces(value = { MediaType.APPLICATION_JSON })
	@Path("/update/rolerelations")
	public ReCode updateRolerelations(Role role) {
		roleService.updateRolerelations(role);
		ReCode reCode = new ReCode();
		reCode.setData(new Data(role.getId()));
		reCode.setMsg(userI18nMessage.getMessage(Constants.SUCCESS_ADD));
		reCode.setErrcode(Constants.NOT_ERROR);
		reCode.setRet(Constants.RET_SUCCESS);
		return reCode;
	}
}
