package com.joe.user.resource;

import java.util.List;
import java.util.Set;

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
import com.joe.user.i18n.UserI18nMessage;
import com.joe.user.service.UserService;
import com.joe.user.utils.Constants;
import com.joe.user.utils.UserUtils;
import com.joe.user.vo.Role;
import com.joe.user.vo.User;

/**
 * @ClassName: UserResource
 * @Description: 用户管理
 * @author WDB
 * @date 2015-4-16 上午11:29:03 PUT:修改,更新 POST:添加,创建 GET:请求用于获取一个或多个资源
 *       DELETE:删除一个资源
 */
@RestResource(name = UserUtils.NAME)
@Path(BDPVersion.BASE_PATH + UserResource.PATH)
@Produces(value = { MediaType.APPLICATION_JSON, MediaType.TEXT_XML,
		MediaType.APPLICATION_XML })
@Consumes(value = { MediaType.APPLICATION_JSON, MediaType.TEXT_XML,
		MediaType.APPLICATION_XML })
@Controller
public class UserResource {

	public static final String PATH = "/users";
	@Resource(name = "userService")
	private UserService userService;

	@Resource(name = "userI18n")
	private UserI18nMessage userI18nMessage;

	@GET
	@Path("/list")
	public List<User> list() {
		List<User> users = userService.getAll();
		for (User user : users) {
			Set<Role> roles = user.getRoles();
			for (Role role : roles) {
				role.setUsers(null);
				role.setRolerelation(null);
			}
		}
		return users;
	}

	@GET
	@Path("/{id}")
	public User getUser(@PathParam("id") int id) {
		User user = userService.get(id);
		Set<Role> roles = user.getRoles();
		for (Role role : roles) {
			role.setUsers(null);
			role.setRolerelation(null);
		}
		return user;
	}

	@POST
	@Path("/add")
	public ReCode add(User user) {
		userService.add(user);
		ReCode reCode = new ReCode();
		reCode.setData(new Data(user.getId()));
		reCode.setMsg(userI18nMessage.getMessage(Constants.SUCCESS_ADD));
		reCode.setErrcode(Constants.NOT_ERROR);
		reCode.setRet(Constants.RET_SUCCESS);
		return reCode;
	}

	@PUT
	@Path("/update")
	public ReCode update(User user) {
		User userDb = userService.get(user.getId());
		userDb.setLastUpdateDate(System.currentTimeMillis());
		userDb.setAccount(user.getAccount());
		userDb.setNickName(user.getNickName());
		userDb.setRoles(user.getRoles());
		userService.update(userDb);
		ReCode reCode = new ReCode();
		reCode.setData(new Data(user.getId()));
		reCode.setMsg(userI18nMessage.getMessage(Constants.SUCCESS_UPDATE));
		reCode.setErrcode(Constants.NOT_ERROR);
		reCode.setRet(Constants.RET_SUCCESS);
		return reCode;
	}

	@DELETE
	@Path("/delete/{id}")
	public ReCode delete(@PathParam("id") int id) {
		User user = userService.get(id);
		userService.remove(user);
		ReCode reCode = new ReCode();
		reCode.setData(new Data(user.getId()));
		reCode.setMsg(userI18nMessage.getMessage(Constants.SUCCESS_DELETE));
		reCode.setErrcode(Constants.NOT_ERROR);
		reCode.setRet(Constants.RET_SUCCESS);
		return reCode;
	}
}
