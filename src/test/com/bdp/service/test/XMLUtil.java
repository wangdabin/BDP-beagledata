package com.bdp.service.test;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

/**
 * @ClassName: XMLUtil
 * @Description: Object和XML对象相互转化
 * @author WDB
 * @date 2015-4-1 上午11:09:26
 * 
 */
public class XMLUtil {
    /**
     * 将对象直接转换成String类型的 XML输出
     * 
     * @param obj
     * @return
     */
    public static String convertToXml(Object obj) {
	// 创建输出流
	StringWriter sw = new StringWriter();
	try {
	    // 利用jdk中自带的转换类实现
	    JAXBContext context = JAXBContext.newInstance(obj.getClass());

	    Marshaller marshaller = context.createMarshaller();
	    // 格式化xml输出的格式
	    marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT,
		    Boolean.TRUE);
	    // 将对象转换成输出流形式的xml
	    marshaller.marshal(obj, sw);
	} catch (JAXBException e) {
	    e.printStackTrace();
	}
	return sw.toString();
    }

    /**
     * 将对象根据路径转换成xml文件
     * 
     * @param obj
     * @param path
     * @return
     */
    public static void convertToXml(Object obj, String path) {
	try {
	    // 利用jdk中自带的转换类实现
	    JAXBContext context = JAXBContext.newInstance(obj.getClass());

	    Marshaller marshaller = context.createMarshaller();
	    // 格式化xml输出的格式
	    marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT,
		    Boolean.TRUE);
	    // 将对象转换成输出流形式的xml
	    // 创建输出流
	    FileWriter fw = null;
	    try {
		fw = new FileWriter(path);
	    } catch (IOException e) {
		e.printStackTrace();
	    }
	    marshaller.marshal(obj, fw);
	} catch (JAXBException e) {
	    e.printStackTrace();
	}
    }

    /** 
     * 将String类型的xml转换成对象 
     */
    public static <T> Object convertXmlStrToObject(Class<T> clazz, String xmlStr) {
	Object xmlObject = null;
	try {
	    JAXBContext context = JAXBContext.newInstance(clazz);
	    // 进行将Xml转成对象的核心接口
	    Unmarshaller unmarshaller = context.createUnmarshaller();
	    StringReader sr = new StringReader(xmlStr);
	    xmlObject = unmarshaller.unmarshal(sr);
	} catch (JAXBException e) {
	    e.printStackTrace();
	}
	return xmlObject;
    }

    /** 
     * 将file类型的xml转换成对象 
     */
    public static <T> Object convertXmlFileToObject(Class<T> clazz, String xmlPath) {
	Object xmlObject = null;
	try {
	    JAXBContext context = JAXBContext.newInstance(clazz);
	    Unmarshaller unmarshaller = context.createUnmarshaller();
	    FileReader fr = null;
	    try {
		fr = new FileReader(xmlPath);
	    } catch (FileNotFoundException e) {
		e.printStackTrace();
	    }
	    xmlObject = unmarshaller.unmarshal(fr);
	} catch (JAXBException e) {
	    e.printStackTrace();
	}
	return xmlObject;
    }

    public static void writeFile(String xmlString, File file) {
	DataOutputStream outputStream = null;
	try {
	    outputStream = new DataOutputStream(new FileOutputStream(file));
	    outputStream.write(xmlString.getBytes());
	} catch (IOException e) {
	    e.printStackTrace();
	} finally {
	    try {
		outputStream.close();
	    } catch (IOException e) {
		e.printStackTrace();
	    }
	}
    }

}