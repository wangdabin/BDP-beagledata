package com.sky.service.define;

import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * 
 * 定义命令
 * @author qiaolong
 * 
 */
public class CommandDefine extends AbstractDefine {

	private String basePath; //命令的基础路径
	private String shell;
	private List<String> params = new ArrayList<String>();

	public String getShell() {
		return shell;
	}
	
	public void setShell(String shell){
		this.shell = shell;
	}

	public List<String> getParams() {
		return params;
	}
	
	public void addParam(String param){
		this.params.add(param);
	}
	
	/**
	 * 得到命令的基础路径
	 * @return
	 */
	public String getBasePath() {
		return basePath;
	}

	/**
	 * 设置命令的基础路径
	 * @param basePath
	 */
	public void setBasePath(String basePath) {
		this.basePath = basePath;
	}

	@Override
	public void parse(Element commandNode) {
		this.parseName(commandNode);
		String shell = commandNode.getAttribute("shell");
		this.setShell(shell);
		NodeList nodes = commandNode.getChildNodes();
		for (int i = 0; i < nodes.getLength(); i++) {
			Node node = nodes.item(i);
			if (!(node instanceof Element)) {
				continue;
			}
			Element element = (Element) node;
			String tagName = element.getTagName();
			String value = element.getTextContent();
			if ("param".equalsIgnoreCase(tagName)) {
				this.params.add(value);
			} else {
				throwException(tagName);
			}
		}
	}
}
