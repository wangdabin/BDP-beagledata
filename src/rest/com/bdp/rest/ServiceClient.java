package com.bdp.rest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.MultivaluedMap;

import org.apache.commons.configuration.Configuration;

import com.joe.core.rest.AbstractClient;
import com.joe.core.utils.CoreConfigUtils;
import com.sun.jersey.api.client.GenericType;
import com.sun.jersey.core.util.MultivaluedMapImpl;

/**
 * @author computer
 * @version 2015-03-13 18:14:22 Automatic generation
 * 
 */
@org.springframework.stereotype.Repository("serviceClient")
public class ServiceClient extends AbstractClient {

	public ServiceClient() throws java.io.IOException {
		this(CoreConfigUtils.create());
	}
	
	/**
	 * 
	 * @param conf
	 */
	public ServiceClient(Configuration conf){
		super(conf);
	}

	// DELETE
	public com.joe.core.vo.ReCode remove(java.lang.String param0,
			java.lang.String param1) {
		String resource = getConf().getString(
				"server.ws.resource.services.name.version");
		MultivaluedMap<String, String> queryParams = new MultivaluedMapImpl();
		Map<String, String> headerParams = new HashMap<String, String>();
		List<Cookie> cookies = new ArrayList<Cookie>();
		resource = resource.replaceAll("\\{name\\}", "" + param0);
		resource = resource.replaceAll("\\{version\\}", "" + param1);
		return this.doDelete(com.joe.core.vo.ReCode.class, resource,
				queryParams, headerParams, cookies, null);
	}

	// PUT
	public com.joe.core.vo.ReCode start(java.lang.String param0,
			java.lang.String param1) {
		String resource = getConf().getString(
				"server.ws.resource.services.name.version.start");
		MultivaluedMap<String, String> queryParams = new MultivaluedMapImpl();
		Map<String, String> headerParams = new HashMap<String, String>();
		List<Cookie> cookies = new ArrayList<Cookie>();
		resource = resource.replaceAll("\\{name\\}", "" + param0);
		resource = resource.replaceAll("\\{version\\}", "" + param1);
		return this.doPut(com.joe.core.vo.ReCode.class, resource, queryParams,
				headerParams, cookies, null);
	}

	// PUT
	public com.joe.core.vo.ReCode stop(java.lang.String param0,
			java.lang.String param1) {
		String resource = getConf().getString(
				"server.ws.resource.services.name.version.stop");
		MultivaluedMap<String, String> queryParams = new MultivaluedMapImpl();
		Map<String, String> headerParams = new HashMap<String, String>();
		List<Cookie> cookies = new ArrayList<Cookie>();
		resource = resource.replaceAll("\\{name\\}", "" + param0);
		resource = resource.replaceAll("\\{version\\}", "" + param1);
		return this.doPut(com.joe.core.vo.ReCode.class, resource, queryParams,
				headerParams, cookies, null);
	}

	// GET
	public java.lang.String getOwner(java.lang.String param0,
			java.lang.String param1) {
		String resource = getConf().getString(
				"server.ws.resource.services.name.version.owner");
		MultivaluedMap<String, String> queryParams = new MultivaluedMapImpl();
		Map<String, String> headerParams = new HashMap<String, String>();
		List<Cookie> cookies = new ArrayList<Cookie>();
		resource = resource.replaceAll("\\{name\\}", "" + param0);
		resource = resource.replaceAll("\\{version\\}", "" + param1);
		return this.doGet(java.lang.String.class, resource, queryParams,
				headerParams, cookies);
	}

	// GET
	public java.util.Map<java.lang.String, java.util.List<java.lang.String>> supports() {
		String resource = getConf().getString(
				"server.ws.resource.services.supports");
		MultivaluedMap<String, String> queryParams = new MultivaluedMapImpl();
		Map<String, String> headerParams = new HashMap<String, String>();
		List<Cookie> cookies = new ArrayList<Cookie>();
		return this
				.doGet(new GenericType<java.util.Map<java.lang.String, java.util.List<java.lang.String>>>() {
				}, resource, queryParams, headerParams, cookies);
	}

	// GET
	public java.lang.String getDefInstallDir(java.lang.String param0,
			java.lang.String param1) {
		String resource = getConf().getString(
				"server.ws.resource.services.name.version.default.installDir");
		MultivaluedMap<String, String> queryParams = new MultivaluedMapImpl();
		Map<String, String> headerParams = new HashMap<String, String>();
		List<Cookie> cookies = new ArrayList<Cookie>();
		resource = resource.replaceAll("\\{name\\}", "" + param0);
		resource = resource.replaceAll("\\{version\\}", "" + param1);
		return this.doGet(java.lang.String.class, resource, queryParams,
				headerParams, cookies);
	}

	// GET
	public java.util.List<java.lang.String> supportModels(
			java.lang.String param0, java.lang.String param1) {
		String resource = getConf().getString(
				"server.ws.resource.services.name.version.support.models");
		MultivaluedMap<String, String> queryParams = new MultivaluedMapImpl();
		Map<String, String> headerParams = new HashMap<String, String>();
		List<Cookie> cookies = new ArrayList<Cookie>();
		resource = resource.replaceAll("\\{name\\}", "" + param0);
		resource = resource.replaceAll("\\{version\\}", "" + param1);
		return this.doGet(new GenericType<java.util.List<java.lang.String>>() {
		}, resource, queryParams, headerParams, cookies);
	}

	// GET
	public java.util.List<java.lang.String> supportSubService(
			java.lang.String param0, java.lang.String param1) {
		String resource = getConf().getString(
				"server.ws.resource.services.name.version.support.subservices");
		MultivaluedMap<String, String> queryParams = new MultivaluedMapImpl();
		Map<String, String> headerParams = new HashMap<String, String>();
		List<Cookie> cookies = new ArrayList<Cookie>();
		resource = resource.replaceAll("\\{name\\}", "" + param0);
		resource = resource.replaceAll("\\{version\\}", "" + param1);
		return this.doGet(new GenericType<java.util.List<java.lang.String>>() {
		}, resource, queryParams, headerParams, cookies);
	}

	// GET
	public java.util.List<com.sky.service.define.KeyValue> supportBasicEnvironment(
			java.lang.String param0, java.lang.String param1) {
		String resource = getConf()
				.getString(
						"server.ws.resource.services.name.version.support.basicenvironments");
		MultivaluedMap<String, String> queryParams = new MultivaluedMapImpl();
		Map<String, String> headerParams = new HashMap<String, String>();
		List<Cookie> cookies = new ArrayList<Cookie>();
		resource = resource.replaceAll("\\{name\\}", "" + param0);
		resource = resource.replaceAll("\\{version\\}", "" + param1);
		return this
				.doGet(new GenericType<java.util.List<com.sky.service.define.KeyValue>>() {
				}, resource, queryParams, headerParams, cookies);
	}

	// GET
	public java.util.List<com.sky.service.define.KeyValue> supportAdvancedEnvironment(
			java.lang.String param0, java.lang.String param1) {
		String resource = getConf()
				.getString(
						"server.ws.resource.services.name.version.support.advancedenvironments");
		MultivaluedMap<String, String> queryParams = new MultivaluedMapImpl();
		Map<String, String> headerParams = new HashMap<String, String>();
		List<Cookie> cookies = new ArrayList<Cookie>();
		resource = resource.replaceAll("\\{name\\}", "" + param0);
		resource = resource.replaceAll("\\{version\\}", "" + param1);
		return this
				.doGet(new GenericType<java.util.List<com.sky.service.define.KeyValue>>() {
				}, resource, queryParams, headerParams, cookies);
	}

	// GET
	public java.util.List<com.sky.service.define.KeyValue> supportBasicConfig(
			java.lang.String param0, java.lang.String param1) {
		String resource = getConf()
				.getString(
						"server.ws.resource.services.name.version.support.basicconfigs");
		MultivaluedMap<String, String> queryParams = new MultivaluedMapImpl();
		Map<String, String> headerParams = new HashMap<String, String>();
		List<Cookie> cookies = new ArrayList<Cookie>();
		resource = resource.replaceAll("\\{name\\}", "" + param0);
		resource = resource.replaceAll("\\{version\\}", "" + param1);
		return this
				.doGet(new GenericType<java.util.List<com.sky.service.define.KeyValue>>() {
				}, resource, queryParams, headerParams, cookies);
	}

	// GET
	public java.util.List<com.sky.service.define.KeyValue> supportAdvancedConfig(
			java.lang.String param0, java.lang.String param1) {
		String resource = getConf()
				.getString(
						"server.ws.resource.services.name.version.support.advancedconfigs");
		MultivaluedMap<String, String> queryParams = new MultivaluedMapImpl();
		Map<String, String> headerParams = new HashMap<String, String>();
		List<Cookie> cookies = new ArrayList<Cookie>();
		resource = resource.replaceAll("\\{name\\}", "" + param0);
		resource = resource.replaceAll("\\{version\\}", "" + param1);
		return this
				.doGet(new GenericType<java.util.List<com.sky.service.define.KeyValue>>() {
				}, resource, queryParams, headerParams, cookies);
	}

	// POST
	public com.joe.core.vo.ReCode addConfig(java.lang.String param0,
			java.lang.String param1, com.sky.service.define.KeyValue param2) {
		String resource = getConf().getString(
				"server.ws.resource.services.name.version.configs");
		MultivaluedMap<String, String> queryParams = new MultivaluedMapImpl();
		Map<String, String> headerParams = new HashMap<String, String>();
		List<Cookie> cookies = new ArrayList<Cookie>();
		resource = resource.replaceAll("\\{name\\}", "" + param0);
		resource = resource.replaceAll("\\{version\\}", "" + param1);
		return this.doPost(com.joe.core.vo.ReCode.class, resource, queryParams,
				headerParams, cookies, param2);
	}

	// DELETE
	public com.joe.core.vo.ReCode removeConfig(java.lang.String param0,
			java.lang.String param1, java.lang.String param2) {
		String resource = getConf().getString(
				"server.ws.resource.services.name.version.configs.key");
		MultivaluedMap<String, String> queryParams = new MultivaluedMapImpl();
		Map<String, String> headerParams = new HashMap<String, String>();
		List<Cookie> cookies = new ArrayList<Cookie>();
		resource = resource.replaceAll("\\{name\\}", "" + param0);
		resource = resource.replaceAll("\\{version\\}", "" + param1);
		resource = resource.replaceAll("\\{key\\}", "" + param2);
		return this.doDelete(com.joe.core.vo.ReCode.class, resource,
				queryParams, headerParams, cookies, null);
	}

	// GET
	public java.lang.String getModel(java.lang.String param0,
			java.lang.String param1) {
		String resource = getConf().getString(
				"server.ws.resource.services.name.version.model");
		MultivaluedMap<String, String> queryParams = new MultivaluedMapImpl();
		Map<String, String> headerParams = new HashMap<String, String>();
		List<Cookie> cookies = new ArrayList<Cookie>();
		resource = resource.replaceAll("\\{name\\}", "" + param0);
		resource = resource.replaceAll("\\{version\\}", "" + param1);
		return this.doGet(java.lang.String.class, resource, queryParams,
				headerParams, cookies);
	}

	// GET
	public java.util.List<com.joe.host.vo.Host> getHosts(
			java.lang.String param0, java.lang.String param1) {
		String resource = getConf().getString(
				"server.ws.resource.services.name.version.hosts");
		MultivaluedMap<String, String> queryParams = new MultivaluedMapImpl();
		Map<String, String> headerParams = new HashMap<String, String>();
		List<Cookie> cookies = new ArrayList<Cookie>();
		resource = resource.replaceAll("\\{name\\}", "" + param0);
		resource = resource.replaceAll("\\{version\\}", "" + param1);
		return this.doGet(
				new GenericType<java.util.List<com.joe.host.vo.Host>>() {
				}, resource, queryParams, headerParams, cookies);
	}

	// POST
	public com.joe.core.vo.ReCode addHost(java.lang.String param0,
			java.lang.String param1, com.joe.host.vo.Host param2) {
		String resource = getConf().getString(
				"server.ws.resource.services.name.version.hosts");
		MultivaluedMap<String, String> queryParams = new MultivaluedMapImpl();
		Map<String, String> headerParams = new HashMap<String, String>();
		List<Cookie> cookies = new ArrayList<Cookie>();
		resource = resource.replaceAll("\\{name\\}", "" + param0);
		resource = resource.replaceAll("\\{version\\}", "" + param1);
		return this.doPost(com.joe.core.vo.ReCode.class, resource, queryParams,
				headerParams, cookies, param2);
	}

	// DELETE
	public com.joe.core.vo.ReCode removeHost(java.lang.String param0,
			java.lang.String param1, com.joe.host.vo.Host param2) {
		String resource = getConf().getString(
				"server.ws.resource.services.name.version.hosts");
		MultivaluedMap<String, String> queryParams = new MultivaluedMapImpl();
		Map<String, String> headerParams = new HashMap<String, String>();
		List<Cookie> cookies = new ArrayList<Cookie>();
		resource = resource.replaceAll("\\{name\\}", "" + param0);
		resource = resource.replaceAll("\\{version\\}", "" + param1);
		return this.doDelete(com.joe.core.vo.ReCode.class, resource,
				queryParams, headerParams, cookies, param2);
	}

	// POST
	public com.joe.core.vo.ReCode addEnvironment(java.lang.String param0,
			java.lang.String param1, com.sky.service.define.KeyValue param2) {
		String resource = getConf().getString(
				"server.ws.resource.services.name.version.environments");
		MultivaluedMap<String, String> queryParams = new MultivaluedMapImpl();
		Map<String, String> headerParams = new HashMap<String, String>();
		List<Cookie> cookies = new ArrayList<Cookie>();
		resource = resource.replaceAll("\\{name\\}", "" + param0);
		resource = resource.replaceAll("\\{version\\}", "" + param1);
		return this.doPost(com.joe.core.vo.ReCode.class, resource, queryParams,
				headerParams, cookies, param2);
	}

	// DELETE
	public com.joe.core.vo.ReCode removeEnvironment(java.lang.String param0,
			java.lang.String param1, java.lang.String param2) {
		String resource = getConf().getString(
				"server.ws.resource.services.name.version.environments.key");
		MultivaluedMap<String, String> queryParams = new MultivaluedMapImpl();
		Map<String, String> headerParams = new HashMap<String, String>();
		List<Cookie> cookies = new ArrayList<Cookie>();
		resource = resource.replaceAll("\\{name\\}", "" + param0);
		resource = resource.replaceAll("\\{version\\}", "" + param1);
		resource = resource.replaceAll("\\{key\\}", "" + param2);
		return this.doDelete(com.joe.core.vo.ReCode.class, resource,
				queryParams, headerParams, cookies, null);
	}

	// POST
	public com.joe.core.vo.ReCode setInstallDir(java.lang.String param0,
			java.lang.String param1, java.lang.String param2) {
		String resource = getConf().getString(
				"server.ws.resource.services.name.version.installdir");
		MultivaluedMap<String, String> queryParams = new MultivaluedMapImpl();
		Map<String, String> headerParams = new HashMap<String, String>();
		List<Cookie> cookies = new ArrayList<Cookie>();
		resource = resource.replaceAll("\\{name\\}", "" + param0);
		resource = resource.replaceAll("\\{version\\}", "" + param1);
		queryParams.add("installdir", "" + param2);
		return this.doPost(com.joe.core.vo.ReCode.class, resource, queryParams,
				headerParams, cookies, null);
	}

	// GET
	public com.joe.core.vo.ReCode install(java.lang.String param0,
			java.lang.String param1, java.lang.String param2) {
		String resource = getConf().getString(
				"server.ws.resource.services.name.version.install");
		MultivaluedMap<String, String> queryParams = new MultivaluedMapImpl();
		Map<String, String> headerParams = new HashMap<String, String>();
		List<Cookie> cookies = new ArrayList<Cookie>();
		resource = resource.replaceAll("\\{name\\}", "" + param0);
		resource = resource.replaceAll("\\{version\\}", "" + param1);
		return this.doGet(com.joe.core.vo.ReCode.class, resource, queryParams,
				headerParams, cookies);
	}

	// POST
	public com.joe.core.vo.ReCode setOwner(java.lang.String param0,
			java.lang.String param1, java.lang.String param2,
			java.lang.String param3) {
		String resource = getConf().getString(
				"server.ws.resource.services.name.version.owner");
		MultivaluedMap<String, String> queryParams = new MultivaluedMapImpl();
		Map<String, String> headerParams = new HashMap<String, String>();
		List<Cookie> cookies = new ArrayList<Cookie>();
		resource = resource.replaceAll("\\{name\\}", "" + param0);
		resource = resource.replaceAll("\\{version\\}", "" + param1);
		queryParams.add("username", "" + param2);
		queryParams.add("password", "" + param3);
		return this.doPost(com.joe.core.vo.ReCode.class, resource, queryParams,
				headerParams, cookies, null);
	}

	// GET
	public java.util.List<java.lang.String> versions(java.lang.String param0) {
		String resource = getConf().getString(
				"server.ws.resource.services.name.versions");
		MultivaluedMap<String, String> queryParams = new MultivaluedMapImpl();
		Map<String, String> headerParams = new HashMap<String, String>();
		List<Cookie> cookies = new ArrayList<Cookie>();
		resource = resource.replaceAll("\\{name\\}", "" + param0);
		return this.doGet(new GenericType<java.util.List<java.lang.String>>() {
		}, resource, queryParams, headerParams, cookies);
	}

}