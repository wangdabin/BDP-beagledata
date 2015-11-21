package com.sky.service.install;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.joe.core.utils.DomUtils;
import com.sky.service.define.ConfigDefine;
import com.sky.service.define.KeyValue;

/**
 * 基本配置
 * @author qiaolong
 *
 */
public class BasicKeyValues {
	public static final String BASIC_RESOURCE = "/conf/service-basic.xml";
	private static final Logger LOG = Logger.getLogger(BasicKeyValues.class);
	public static final String CONF_CONFIGS = "configs";
	public static final String CONF_CONFIG = "config";
	/**
	 * 基本配置
	 * @param resource
	 * @return
	 * @throws ParserConfigurationException 
	 * @throws IOException 
	 * @throws SAXException 
	 */
	public static List<KeyValue> getBasickeyvalues(String resource) throws SAXException, IOException, ParserConfigurationException {
		Document doc = DomUtils.parse(resource);
		Element root = doc.getDocumentElement();
		if (!CONF_CONFIGS.equals(root.getTagName())) {
			LOG.error("bad conf file : top-level element not <configs>");
		}
		NodeList nodes = root.getChildNodes();
		for (int i = 0; i < nodes.getLength(); i++) {
			Node node = nodes.item(i);
			if (!(node instanceof Element)) {
				continue;
			}
			Element element = (Element) node;
			if (CONF_CONFIG.equals(element.getTagName())) {
				ConfigDefine configDefine = ConfigDefine.build(element);
				if(configDefine != null){
					return configDefine.getProps();
				}
			}
		}
		List<KeyValue> basicKeyValues = new ArrayList<KeyValue>();
		return basicKeyValues;
	}
	
	/**
	 * 
	 * @return
	 * @throws SAXException
	 * @throws IOException
	 * @throws ParserConfigurationException
	 */
	public static final List<KeyValue> getDefaultkeyvalues() throws SAXException, IOException, ParserConfigurationException {
		return getBasickeyvalues(BASIC_RESOURCE);
	}
}
