package com.sky.service.install;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.joe.core.exception.Exceptions;
import com.joe.core.utils.DomUtils;
import com.sky.service.define.KeyValue;
import com.sky.service.install.impl.GroupImpl;
import com.sky.service.install.impl.StepImpl;

/**
 * 
 * 步骤加载者
 * @author qiaolong
 *
 */
public abstract class StepsLoader {
	private static final Logger LOG = Logger.getLogger(StepsLoader.class);
	public static final String CONF_STEPS = "steps";
	public static final String CONF_STEP = "step";
	public static final String CONF_GROUP = "group";
	
	/**
	 * 所有的步骤
	 */
	public final Map<String,Entry> steps = new HashMap<String,Entry>();
	
	public StepsLoader(){
		try {
			this.parse(resource());
		} catch (Exception e) {
			throw Exceptions.create(e);
		} 
	}
	
	/**
	 * 
	 * @param service
	 * @param version
	 * @return
	 */
	public final Entry get(String id){
		return steps.get(id);
	}
	
	/**
	 * 得到启动步骤
	 * @param name
	 * @param version
	 * @return
	 */
	public Step getStart(){
		return getById(Entry.START_ID);
	}
	
	/**
	 * 
	 * @param id
	 * @return
	 */
	private Step getById(String id){
		Entry entry = steps.get(id);
		return (Step)entry;
	}
	
	/**
	 * 是否有下一步
	 * @param step
	 * @return
	 */
	public boolean hasNext(Step step){
		return step.hasNext();
	}
	
	public boolean hasReverse(Step step){
		return step.hasReverse();
	}
	
	public Step reverse(Step step){
		return getWhichOkTo(step);
	}
	
	/**
	 * 
	 * @param step
	 * @return
	 */
	public Step next(Step step){
		String id = step.getOK();
		return getById(id);
	}
	
	/**
	 * 
	 * @param step
	 * @param kv
	 * @return
	 */
	public Step next(Step step,KeyValue kv){
		List<Option> options = step.options();
		String value = kv.getValues().get(0);
		for(Option option : options){
			if(option.getValue().equals(value)){
				return getById(option.getOk());
			}
		}
		return null;
	}
	
	private Step getWhichOkTo(Step step){
		for(Entry entry : steps.values()){
			if(entry.isStep()){
				Step s = (Step) entry;
				if(s.isSelect()){
					List<Option> options = s.options();
					if(options != null){
						for(Option option : options){
							if(option.getOk().equalsIgnoreCase(step.getId())){
								return s;
							}
						}
					}
				}else{
					if(s.getOK().equalsIgnoreCase(step.getId())){
						return s;
					}
				}
			}
		}
		return null;
	}
	
	/**
	 * 指定类型得到步骤
	 * @param name
	 * @param version
	 * @param type
	 * @return
	 */
	public final List<Entry> getByType(String type){
		List<Entry> results = new ArrayList<Entry>();
		if(steps != null){
			for(Entry step : steps.values()){
				if(type.equalsIgnoreCase(step.getType())){
					results.add(step);
				}
			}
		}
		return results;
	}
	
	/**
	 * 初始化
	 * @param resource
	 * @throws SAXException
	 * @throws IOException
	 * @throws ParserConfigurationException
	 */
	private final void parse(String resource) throws SAXException,
			IOException, ParserConfigurationException {
		Document doc = DomUtils.parse(resource);
		Element root = doc.getDocumentElement();
		if (CONF_STEPS.equals(root.getTagName())) {
			LOG.warn("bad conf file : top-level element not <steps>");
			NodeList nodes = root.getChildNodes();
			for (int i = 0; i < nodes.getLength(); i++) {
				Node node = nodes.item(i);
				if (!(node instanceof Element)) {
					continue;
				}
				Element element = (Element) node;
				Entry entry = parseEntry(element);
			}
		}else{
			parseEntry(root);
		}
	}
	
	private final Entry parseEntry(Element entryNode){
		if (CONF_STEP.equalsIgnoreCase(entryNode.getTagName())) {
			return StepImpl.build(steps,entryNode);
		}else if(CONF_GROUP.equalsIgnoreCase(entryNode.getTagName())){
			return GroupImpl.build(steps,entryNode);
		}
		return null;
	}
	
	protected abstract String resource();
}
