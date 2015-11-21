package com.joe.core.utils;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Map;

import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

/**
 * 
 * @author qiaolong
 *
 */
public class JsonUtils {

	private static final ObjectMapper objectMapper = new ObjectMapper();
	private static final JsonFactory jsonFactory ;
	
	static{
		//意思是反序列化的作用是确定是否强制让非数组模式的json字符串与java集合类型相匹配。
		objectMapper.configure(DeserializationConfig.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
		jsonFactory = objectMapper.getJsonFactory();
	}
	
	public static void main(String[] args) throws JsonParseException, JsonMappingException, IOException {
		String json = "{\"user\":\"qiaolong\",\"age\":19,\"array\":[{\"t\":\"t1\"},{\"t\":\"t2\"}]}";
		String jsonTest = "[{\"t\":\"t1\"},{\"t\":\"t2\"}]";
		Map obj =  (Map) objectMapper.readValue(json, Object.class);
		System.out.println(obj.get("array").getClass());
	}
	
	/**
	 * 
	 * 把json 转成 对象
	 * @param valueType
	 * @param json
	 * @return
	 * @throws IOException
	 * @throws JsonProcessingException
	 */
	public static final <T> T jsonToObject(Class<T> valueType,JSONObject json){
		return jsonToObject(valueType,json.toString());
	}

	/**
	 * 
	 * 把json 转成 对象
	 * @param valueType
	 * @param json
	 * @return
	 * @throws IOException
	 * @throws JsonProcessingException
	 */
	public static final <T> T jsonToObject(Class<T> valueType,String json){
		try{
			JsonParser jp = jsonFactory.createJsonParser(json);
			T t = jp.readValueAs(valueType);
			return t;
		}catch(Exception e){
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * 
	 * @param obj
	 * @return
	 * @throws IOException 
	 * @throws JSONException 
	 */
	public static final JSONObject objectToJson(Object obj) {
		try{
			JSONObject json = new JSONObject(objectToJsonString(obj));
			return json;
		}catch(Exception e){
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * 
	 * @param obj
	 * @return
	 * @throws IOException 
	 * @throws JSONException 
	 */
	public static final JSONArray objectToJsonArray(Object obj) {
		try{
			JSONArray jsonArray = new JSONArray(objectToJsonString(obj));
			return jsonArray;
		}catch(Exception e){
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * 
	 * @param obj
	 * @return
	 */
	public static final String objectToJsonString(Object obj){
		try{
			StringWriter writer = new StringWriter();
			JsonGenerator jsonGenerator = jsonFactory.createJsonGenerator(writer);
			jsonGenerator.useDefaultPrettyPrinter();
			
			jsonGenerator.writeObject(obj);
			jsonGenerator.close();
			return writer.toString();
		}catch(Exception e){
			throw new RuntimeException(e);
		}
	}
}
