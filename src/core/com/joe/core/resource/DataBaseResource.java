package com.joe.core.resource;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.apache.commons.configuration.Configuration;

import com.joe.core.annotation.InitResource;
import com.joe.core.database.DataBase;
import com.joe.core.database.DatabaseInitHandler;
import com.joe.core.database.DatabaseUtils;
import com.joe.core.database.Dialect;
import com.joe.core.database.DialectUtils;
import com.joe.core.i18n.CoreI18nMessage;
import com.joe.core.i18n.I18nMessage;
import com.joe.core.utils.Constants;
import com.joe.core.utils.CoreConfigUtils;
import com.joe.core.version.BDPVersion;
import com.joe.core.vo.ReCode;
import com.joe.core.vo.ReCode.Data;
import com.sky.config.ConfigAble;
import com.sky.config.Configed;

/**
 * 
 * 初始化的第一步
 * 配置数据库
 * @author Joe
 *
 */
@InitResource(name = DataBaseResource.NAME)
@Path(BDPVersion.BASE_PATH + DataBaseResource.PATH)
public class DataBaseResource extends Configed implements ConfigAble{
	
	public static final String NAME = "database";
	public static final String PATH = "/database";
	
	private I18nMessage i18nMsg;
	
	@Context
	private ServletContext servletContext;
	
	/**
	 * 
	 * @throws IOException
	 */
	public DataBaseResource() throws IOException{
		super(CoreConfigUtils.create());
		this.i18nMsg = new CoreI18nMessage();
	}
	
	/**
	 * 得到当前database的配置。
	 * @return
	 * @throws IOException 
	 */
	@GET
	@Produces(value = {MediaType.TEXT_XML,MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
	public DataBase getDataBase(){
		try{
			Configuration config = getConf();
			DataBase database = new DataBase();
			database.setName(DialectUtils.getDialectName(config.getString("jdbc.dialect")));
			database.setUrl(config.getString("jdbc.url"));
			database.setUsername(config.getString("jdbc.username"));
			database.setPassword(config.getString("jdbc.password"));
			return database;
		}catch(Exception e){
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * 当前系统支持的database
	 * @return
	 */
	@GET
	@Produces(value = {MediaType.TEXT_XML,MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
	@Path("/support")
	public List<Dialect> getSupperts(){
		return new ArrayList<Dialect>(DialectUtils.getDialects());
	}
	
	@POST
	@Produces(value = {MediaType.TEXT_XML,MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
	public ReCode setDatabase(DataBase database){
		try{
			Configuration config = getConf();
			ReCode reCode = new ReCode();
			Dialect dialect = DialectUtils.getDialect(database.getName());
			try{
				DatabaseUtils.getConnection(database.getUrl(), database.getUsername(), database.getPassword(), dialect.getDriver());
				config.setProperty("jdbc.dialect", dialect.getDialect());
				config.setProperty("jdbc.driver", dialect.getDriver());
				config.setProperty("jdbc.url", database.getUrl());
				config.setProperty("jdbc.username", database.getUsername());
				config.setProperty("jdbc.password", database.getPassword());
				config.setProperty(DatabaseInitHandler.JDBC_INITED, true);
				//更新配置项
				CoreConfigUtils.updateConfig();
				DatabaseInitHandler initHandle = (DatabaseInitHandler)servletContext.getAttribute(DatabaseInitHandler.DATABASE_INIT_HANDLE);
				initHandle.doInit(); //重新加载配置项。。。
				
		    	reCode.setData(new Data(database.getName()));
		    	reCode.setMsg(i18nMsg.getMessage(Constants.CORE_DATABASE_SETUP_SUCCESS));
		    	reCode.setErrcode(Constants.NOT_ERROR);
		    	reCode.setRet(Constants.RET_SUCCESS);
		    	return reCode;
			}catch(Exception e){
				if(e instanceof SQLException){
					reCode.setRet(((SQLException)e).getErrorCode());
					reCode.setErrcode(Constants.CORE_DATABASE_CONN_ERROR);
				}else if(e instanceof ClassNotFoundException){
					reCode.setErrcode(Constants.CORE_DATABASE_DRIVER_NOT_FOUND);
					reCode.setRet(Constants.CORE_APPLICATION_ERROR);
				}else{
					reCode.setErrcode(Constants.CORE_UNKNOW_ERROR_CODE);
					reCode.setRet(Constants.CORE_UNKNOW_ERROR);
				}
				reCode.setMsg(e.getMessage());
				return reCode;
			}
		}catch(Exception e){
			throw new RuntimeException(e);
		}
	}
}
