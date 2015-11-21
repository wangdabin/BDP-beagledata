package com.sky.service.vo;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.xml.bind.annotation.XmlRootElement;

import com.joe.host.vo.Host;

/**
 * 服务，部署在操作系统之上
 * 
 * @author why
 * 
 */
@XmlRootElement
public class ServiceVo implements Serializable {

	public class ServiceVoStatus {
		public static final int INIT = 0;
		public static final int INSTALLING = 1;
		public static final int RUNNING = 2;
		public static final int INSTALL_SUCCESS = 3;
		public static final int INSTALL_FAILED = 4;
		public static final int STARTING = 5;
		public static final int STOPING = 6;
		public static final int STOP = 7;
	}

	public enum ServiceVoType {
		INSTALL(0), CONFIG(1);
		private int type;

		private ServiceVoType(int type) {
			this.type = type;
		}

		public int getValue() {
			return type;
		}

	}

	private static final long serialVersionUID = 1L;

	// 服务安装主机列表
	private Set<ServiceStatusVo> serviceStatus = new HashSet<ServiceStatusVo>();
	private Set<Host> hosts = new HashSet<Host>();
//	// 服务属性信息 key(String):配置文件名称 value(Map):文件内容
//	private BdpConfigPropertySet props = new BdpConfigPropertySet();
	private ServiceVo parent;
	
	private Set<ServiceVo> children;
	public Set<ServiceVo> getChildren() {
		return children;
	}

	public void setChildren(Set<ServiceVo> children) {
		this.children = children;
	}

	// 服务状态
	private String status = "0";
	// 服务安装目录
	private String installDir;
	private String userName;
	private int type;

	private String name;
	private String version;
	private String desc;

	public ServiceVo() {
	}
	
	public ServiceVo(String name, String version) {
		super();
		this.name = name;
		this.version = version;
	}
	
	public ServiceVo getParent() {
		return parent;
	}

	public void setParent(ServiceVo parent) {
		this.parent = parent;
	}

	public String getInstallDir() {
		return installDir;
	}

	public String getName() {
		return name;
	}

	public String getStatus() {
		return status;
	}

	public int getType() {
		return type;
	}

	public String getUserName() {
		return userName;
	}

	public String getVersion() {
		return version;
	}


	public void setInstallDir(String installDir) {
		this.installDir = installDir;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public void setType(int type) {
		this.type = type;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	@Override
	public String toString() {
		return "ServiceVo [serviceStatus=" + serviceStatus + ", hosts=" + hosts
				+ ", parent=" + parent + ", status=" + status + ", installDir="
				+ installDir + ", userName=" + userName + ", type=" + type
				+ ", name=" + name + ", version=" + version + ", desc=" + desc
				+ "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((version == null) ? 0 : version.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ServiceVo other = (ServiceVo) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (version == null) {
			if (other.version != null)
				return false;
		} else if (!version.equals(other.version))
			return false;
		return true;
	}

	public Set<ServiceStatusVo> getServiceStatus() {
		return serviceStatus;
	}

	public void setServiceStatus(Set<ServiceStatusVo> serviceStatus) {
		this.serviceStatus = serviceStatus;
	}

	public Set<Host> getHosts() {
		return hosts;
	}

	public void setHosts(Set<Host> hosts) {
		this.hosts = hosts;
	}
	
	/**
	 * 得到最顶级的服务的名称
	 */
	public String getTopName(){
		if(this.parent != null){
			return parent.getTopName();
		}else{
			return this.getName();
		}
	}
}
