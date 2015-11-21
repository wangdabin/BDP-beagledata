package com.sky.jetty;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.log4j.Logger;
import org.eclipse.jetty.http.MimeTypes;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class MimeUtil {
    private static final Logger LOG = Logger.getLogger(MimeUtil.class);

    public static final MimeTypes loadResouse(String file) throws Exception{
	return loadResouse(new FileInputStream(file));
    }
    
    public static final MimeTypes loadResouse(File file) throws Exception{
	return loadResouse(new FileInputStream(file));
    }
    
    public static final MimeTypes loadResouse(InputStream in) throws SAXException, IOException, ParserConfigurationException{
	MimeTypes mime = new MimeTypes();
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	factory.setIgnoringComments(true);
	factory.setNamespaceAware(true);
	try {
	    factory.setXIncludeAware(true);
	} catch (UnsupportedOperationException e) {
	    e.printStackTrace();
	}
	DocumentBuilder builder = factory.newDocumentBuilder();
	Document doc = builder.parse(in);
	Element root = doc.getDocumentElement();
	if (!"mimes".equals(root.getTagName())) {
	    LOG.error("bad conf file : top-level element not <datas>");
	}
	NodeList nodes = root.getChildNodes();
	for (int i = 0; i < nodes.getLength(); i++) {
	    Node node = nodes.item(i);
	    if (!(node instanceof Element)) {
		continue;
	    }
	    Element element = (Element) node;
	    if ("mime-mapping".equalsIgnoreCase(element.getTagName())) {
		NodeList cNodes = element.getChildNodes();
		for(int j = 0; j< cNodes.getLength();j ++){
		    Node cNode = cNodes.item(i);
		    if (!(cNode instanceof Element)) {
			continue;
		    }
		    String extension = "";
		    String mimeType = "";
		    if("extension".equalsIgnoreCase(cNode.getNodeName())){
			extension = cNode.getTextContent();
		    }else if("mime-type".equalsIgnoreCase(cNode.getNodeName())){
			mimeType = cNode.getTextContent();
		    }
		    mime.addMimeMapping(extension, mimeType);
		}
	    }
	}
	return mime;
    }
}
