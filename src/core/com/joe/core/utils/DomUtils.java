package com.joe.core.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class DomUtils {

	/**
	 * 初始化
	 * @param resource
	 * @throws SAXException
	 * @throws IOException
	 * @throws ParserConfigurationException
	 */
	public static final Document parse(String resource) throws SAXException,
			IOException, ParserConfigurationException {
		InputStream in = DomUtils.class.getResourceAsStream(resource);
		return parse(new InputStreamReader(in,"utf-8"));
	}
	
	/**
	 * 
	 * @param reader
	 * @return
	 * @throws SAXException
	 * @throws IOException
	 * @throws ParserConfigurationException
	 */
	public static final Document parse(Reader reader) throws SAXException,
	IOException, ParserConfigurationException {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		factory.setIgnoringComments(true);
		factory.setNamespaceAware(true);
		try {
			factory.setXIncludeAware(true);
		} catch (UnsupportedOperationException e) {
			e.printStackTrace();
		}
		InputSource inputSource = new InputSource(reader);
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document doc = builder.parse(inputSource);
		return doc;
	}
}
