package com.joe.user.init;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;

import com.joe.core.annotation.RestResource;
import com.joe.core.annotation.SecurityResource;
import com.joe.core.factory.AbstractFactory;
import com.joe.core.resource.utils.PathInfo;
import com.joe.core.resource.utils.ResourceBean;
import com.joe.core.resource.utils.ResourceManager;
import com.joe.core.resource.utils.RestResourceCallback;
import com.joe.core.utils.CoreConfigUtils;
import com.joe.core.version.BDPVersion;
import com.joe.core.vo.ReCode;
import com.joe.core.vo.ReCode.Data;
import com.joe.host.utils.Constants;
import com.joe.user.service.PermissionService;
import com.joe.user.service.RoleService;
import com.joe.user.service.UserService;
import com.joe.user.vo.Operation;
import com.joe.user.vo.Permission;
import com.joe.user.vo.Role;
import com.joe.user.vo.RoleRelation;
import com.joe.user.vo.User;

/**
 * 
 * 用户初始化， 加载所有的资源， 并创建管理员账号
 * 
 * @author Joe
 * 
 */
@SecurityResource(name = UserInitResource.NAME)
@Path(BDPVersion.BASE_PATH + UserInitResource.PATH)
@Controller
public class UserInitResource extends AbstractFactory<RestResource, Object>{

	private static final Logger LOG = Logger.getLogger(UserInitResource.class);
	
	@Resource(name = "permissionService")
	private PermissionService permissionService;
	
	@Resource(name = "userService")
	private UserService userService;
	
	@Resource(name = "roleService")
	private RoleService roleService;
	
	public UserInitResource() throws IOException {
		super(CoreConfigUtils.create(), LOG, RestResource.class);
	}
	public static final String NAME = "userInit";
	public static final String PATH = "/user";
	
	@Override
	protected String getName(RestResource ann) {
		return ann.name();
	}
	@Override
	protected String getType(RestResource ann) {
		return ann.type();
	}
	@Override
	protected boolean isEnable(RestResource ann) {
		return ann.enable();
	}

	/**
	 * 
	 */
	private void init(long createDate){
		Collection<Class<Object>> classes = this.getAll();
		List<ResourceBean> resourceBeans = ResourceManager.parse(classes, new RestResourceCallback());
		for(ResourceBean rb : resourceBeans){
			Map<String,Permission> pers = new HashMap<String,Permission>();
			for(PathInfo pi : rb.getPathInfos()){
				String url = pi.getPath();
				Permission permission = pers.get(url);
				if(permission == null){
					permission = new Permission();
					permission.setCreateDate(createDate);
					permission.setName(rb.getName() + "_" + pi.getMethod() + "_" + pi.getPath());
					permission.setLastUpdateDate(createDate);
					permission.setDescription(rb.getName());
					permission.setUrl(url);
					pers.put(url, permission);
				}
				if(pi.getMethod().equalsIgnoreCase("get")){
					permission.addOperation(Operation.GET);
				}else if(pi.getMethod().equalsIgnoreCase("put")){
					permission.addOperation(Operation.PUT);
				}else if(pi.getMethod().equalsIgnoreCase("post")){
					permission.addOperation(Operation.POST);
				}else{
					permission.addOperation(Operation.DELETE);
				}
			}
			for(Permission per : pers.values()){
				boolean create = permissionService.NewPermission(per);
				System.out.println(create);
//				if(!create){
//					throw new RuntimeException("Create permission " + rb.getBasePath() + " class name " + rb.getClassName() + " error ..");
//				}
			}
		}
	}
	
	/**
	 * 
	 * @return
	 */
	private Role createAdminRole(long createDate){
		Role role = new Role();
		role.setName("admin");
		role.setDescription("超级管理员");
		role.setCreateDate(createDate);
		role.setLastUpdateDate(createDate);
		roleService.add(role);
		role.setRolerelation(createAdminRoleRelations(createDate,role.getId()));
		roleService.update(role);
		return role;
	}
	
	private Set<RoleRelation> createAdminRoleRelations(long createDate,int roleId){
		Set<RoleRelation> rrs = new HashSet<RoleRelation>();
		Permission permission = createAdminPermission(createDate);
		RoleRelation roleRelation = new RoleRelation();
		roleRelation.setPermission(permission);
		roleRelation.setCreateDate(createDate);
		roleRelation.setLastUpdateDate(createDate);
		roleRelation.setOperation(Operation.GET);
		rrs.add(roleRelation);
		
		roleRelation = new RoleRelation();
		roleRelation.setPermission(permission);
		roleRelation.setCreateDate(createDate);
		roleRelation.setLastUpdateDate(createDate);
		roleRelation.setOperation(Operation.PUT);
		rrs.add(roleRelation);
		
		roleRelation = new RoleRelation();
		roleRelation.setPermission(permission);
		roleRelation.setCreateDate(createDate);
		roleRelation.setLastUpdateDate(createDate);
		roleRelation.setOperation(Operation.POST);
		rrs.add(roleRelation);
		
		roleRelation = new RoleRelation();
		roleRelation.setPermission(permission);
		roleRelation.setCreateDate(createDate);
		roleRelation.setLastUpdateDate(createDate);
		roleRelation.setOperation(Operation.DELETE);
		rrs.add(roleRelation);
		return rrs;
	}
	
	/**
	 * 
	 * @param createDate
	 * @return
	 */
	private Permission createAdminPermission(long createDate){
		Permission permission = new Permission();
		permission.setCreateDate(createDate);
		permission.setName("管理员权限");
		permission.setLastUpdateDate(createDate);
		permission.setDescription("管理员权限");
		permission.setUrl("/**");
		
		permission.addOperation(Operation.GET);
		permission.addOperation(Operation.PUT);
		permission.addOperation(Operation.POST);
		permission.addOperation(Operation.DELETE);
		permissionService.NewPermission(permission);
		return permission;
	}
	
	/**
	 * 设置已经初始化
	 */
	private void setInited(){
		getConf().setProperty(SecurityResource.USER_ADMIN_INITED, true);
		try{
			CoreConfigUtils.updateConfig();
		}catch(Exception e){
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * 判断是否已经初始化
	 * @return
	 */
	private boolean isInited(){
		return getConf().getBoolean(SecurityResource.USER_ADMIN_INITED);
	}
	
	/**
	 * 
	 * @return
	 */
	@POST
	@Produces(value = {MediaType.APPLICATION_JSON, MediaType.TEXT_XML,MediaType.APPLICATION_XML})
	@Path("/createAdmin")
	public ReCode createAdmin(AdminUser managerUser) {
		
		if(managerUser.getPassword().equals(managerUser.getRepassword())){
			long createDate = System.currentTimeMillis();
			this.init(createDate); // 初始化permission 。。
			User user = new User();
			user.setAccount(managerUser.getUsername());
			user.setPassword(managerUser.getPassword());
			user.setCreateDate(createDate);
			Role role = createAdminRole(createDate);
			Set<User> users = role.getUsers();
			users.add(user);
			role.setUsers(users);
			roleService.update(role);
			userService.add(user);
			setInited(); // 设置已经初始化
			ReCode reCode = new ReCode();
			reCode.setData(new Data(user.getId()));
			reCode.setMsg("Admin user create success.");
			reCode.setErrcode(Constants.NOT_ERROR);
			reCode.setRet(Constants.RET_SUCCESS);
			return reCode;
		}else{
			throw new RuntimeException("Password not equals");
		}
	}
}
