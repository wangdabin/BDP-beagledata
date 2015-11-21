package com.bdp.rest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.MultivaluedMap;

import com.joe.core.rest.AbstractClient;
import com.sun.jersey.api.client.GenericType;
import com.sun.jersey.core.util.MultivaluedMapImpl;

/**
 *@author computer
 *@version 2015-03-19 17:17:42
 * Automatic generation
 *
*/
@org.springframework.stereotype.Repository("installserviceClient")
public   class  InstallserviceClient extends AbstractClient {

public     InstallserviceClient () throws java.io.IOException{
	super(null);
}
public     InstallserviceClient (org.apache.commons.configuration.Configuration conf) throws java.io.IOException{
super(conf);
}
//GET
public   com.joe.core.vo.Bool  hasNext (java.lang.String param0,java.lang.String param1) {
String resource = getConf().getString("server.ws.resource.services.install.name.version.hasnext");
MultivaluedMap<String, String> queryParams = new MultivaluedMapImpl();
Map<String, String> headerParams = new HashMap<String, String>();
List<Cookie> cookies = new ArrayList<Cookie>();
resource = resource.replaceAll("\\{name\\}",""+param0);
resource = resource.replaceAll("\\{version\\}",""+param1);
return this.doGet(com.joe.core.vo.Bool.class,resource, queryParams, headerParams, cookies);
}
//PUT
public   com.joe.core.vo.ReCode  next (java.lang.String param0,java.lang.String param1) {
String resource = getConf().getString("server.ws.resource.services.install.name.version.next");
MultivaluedMap<String, String> queryParams = new MultivaluedMapImpl();
Map<String, String> headerParams = new HashMap<String, String>();
List<Cookie> cookies = new ArrayList<Cookie>();
resource = resource.replaceAll("\\{name\\}",""+param0);
resource = resource.replaceAll("\\{version\\}",""+param1);
return this.doPut(com.joe.core.vo.ReCode.class,resource, queryParams, headerParams, cookies,null);
}
//PUT
public   com.joe.core.vo.ReCode  reverse (java.lang.String param0,java.lang.String param1) {
String resource = getConf().getString("server.ws.resource.services.install.name.version.reverse");
MultivaluedMap<String, String> queryParams = new MultivaluedMapImpl();
Map<String, String> headerParams = new HashMap<String, String>();
List<Cookie> cookies = new ArrayList<Cookie>();
resource = resource.replaceAll("\\{name\\}",""+param0);
resource = resource.replaceAll("\\{version\\}",""+param1);
return this.doPut(com.joe.core.vo.ReCode.class,resource, queryParams, headerParams, cookies,null);
}
//POST
public   com.joe.core.vo.ReCode  install (java.lang.String param0,java.lang.String param1,java.lang.String param2) {
String resource = getConf().getString("server.ws.resource.services.install.name.version.install");
MultivaluedMap<String, String> queryParams = new MultivaluedMapImpl();
Map<String, String> headerParams = new HashMap<String, String>();
List<Cookie> cookies = new ArrayList<Cookie>();
resource = resource.replaceAll("\\{name\\}",""+param0);
resource = resource.replaceAll("\\{version\\}",""+param1);
queryParams.add("url",""+param2);
return this.doPost(com.joe.core.vo.ReCode.class,resource, queryParams, headerParams, cookies,null);
}
//GET
public   com.joe.core.vo.Bool  hasReverse (java.lang.String param0,java.lang.String param1) {
String resource = getConf().getString("server.ws.resource.services.install.name.version.hasReverse");
MultivaluedMap<String, String> queryParams = new MultivaluedMapImpl();
Map<String, String> headerParams = new HashMap<String, String>();
List<Cookie> cookies = new ArrayList<Cookie>();
resource = resource.replaceAll("\\{name\\}",""+param0);
resource = resource.replaceAll("\\{version\\}",""+param1);
return this.doGet(com.joe.core.vo.Bool.class,resource, queryParams, headerParams, cookies);
}
//GET
public   java.util.List<com.sky.service.define.KeyValue>  supportsBasic (java.lang.String param0,java.lang.String param1) {
String resource = getConf().getString("server.ws.resource.services.install.name.version.supportsbasic");
MultivaluedMap<String, String> queryParams = new MultivaluedMapImpl();
Map<String, String> headerParams = new HashMap<String, String>();
List<Cookie> cookies = new ArrayList<Cookie>();
resource = resource.replaceAll("\\{name\\}",""+param0);
resource = resource.replaceAll("\\{version\\}",""+param1);
return this.doGet(new GenericType<java.util.List<com.sky.service.define.KeyValue>>(){},resource, queryParams, headerParams, cookies);
}
//GET
public   java.util.List<com.sky.service.define.KeyValue>  supportsAdvanced (java.lang.String param0,java.lang.String param1) {
String resource = getConf().getString("server.ws.resource.services.install.name.version.supportsadvanced");
MultivaluedMap<String, String> queryParams = new MultivaluedMapImpl();
Map<String, String> headerParams = new HashMap<String, String>();
List<Cookie> cookies = new ArrayList<Cookie>();
resource = resource.replaceAll("\\{name\\}",""+param0);
resource = resource.replaceAll("\\{version\\}",""+param1);
return this.doGet(new GenericType<java.util.List<com.sky.service.define.KeyValue>>(){},resource, queryParams, headerParams, cookies);
}
//POST
public   com.joe.core.vo.ReCode  addKeyValue (java.lang.String param0,java.lang.String param1,com.sky.service.define.KeyValue param2) {
String resource = getConf().getString("server.ws.resource.services.install.name.version.keyvalues");
MultivaluedMap<String, String> queryParams = new MultivaluedMapImpl();
Map<String, String> headerParams = new HashMap<String, String>();
List<Cookie> cookies = new ArrayList<Cookie>();
resource = resource.replaceAll("\\{name\\}",""+param0);
resource = resource.replaceAll("\\{version\\}",""+param1);
return this.doPost(com.joe.core.vo.ReCode.class,resource, queryParams, headerParams, cookies,param2);
}

}