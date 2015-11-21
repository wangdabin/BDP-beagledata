package com.sky.service.install.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.sky.service.install.Entry;
import com.sky.service.install.Group;
import com.sky.service.install.Option;
import com.sky.service.install.Step;

/**
 * 步骤的实体类
 * @author qiaolong
 *
 */
public class StepImpl extends AbstractEntry implements Step{

	private List<Option> options;
	private Group group;

	@Override
	public boolean hasNext() {
		return !this.isEnd();
	}

	@Override
	public boolean hasReverse() {
		return !isStart();
	}

	@Override
	public List<Option> options() {
		return options;
	}
	
	@Override
	public boolean isStart() {
		return getId().equalsIgnoreCase(START_ID);
	}

	@Override
	public boolean isBasic() {
		return isType(Entry.TYPE_BASIC);
	}


	@Override
	public boolean isSelect() {
		return isType(Entry.TYPE_SELECT);
	}
	
	/**
	 * 是某种类型
	 * @param type
	 * @return
	 */
	private boolean isType(String type){
		return type.equalsIgnoreCase(getType());
	}
	
	public Group getGroup(){
		return group;
	}
	
	public void setGroup(Group group) {
		this.group = group;
	}

	@Override
	public boolean isStep() {
		return true;
	}


	@Override
	public boolean isGroup() {
		return false;
	}
	
	@Override
	public void parse(Map<String, Entry> entrys, Element element) {
		super.parse(entrys, element);
		this.setParentService(element.getAttribute("parent"));
		if(isSelect()){
			NodeList optionNodes = element.getChildNodes();
	    	if(optionNodes != null){
	    		List<Option> options = new ArrayList<Option>();
	    		for (int j = 0; j < optionNodes.getLength(); j++) {
	    		    Node optionNode = optionNodes.item(j);
	    		    if (!(optionNode instanceof Element)) {
	    		    	continue;
	    		    }
	    		    Element optionElement = (Element) optionNode;
	    		    String tagName = optionElement.getTagName();
	    		    if("option".equalsIgnoreCase(tagName)){ // 引入其他配置项
	    		    	Option option = OptionImpl.build(optionElement);
	    		    	option.setStep(this);
	    		    	options.add(option);
	    		    }
	    		}
	    		this.options = options;
	    	}
		}
	}
	
	/**
	 * 
	 * @param entrys
	 * @param node
	 * @return
	 */
	public static final Step build(Map<String, Entry> entrys,Element node){
		Step step = new StepImpl();
		step.parse(entrys,node);
		return step;
	}

	@Override
	public Group getTopGroup() {
		if(this.group == null){
			return null;
		}else{
			return group.getTopGroup();
		}
	}

	@Override
	public boolean hasGroup() {
		return this.group != null;
	}

	@Override
	public boolean isEnd() {
		return Entry.OK_END.equalsIgnoreCase(this.getOK());
	}

	@Override
	public boolean isHost() {
		return isType(TYPE_HOST);
	}
	
	private String parentService;
	@Override
	public String getParentService() {
		// TODO Auto-generated method stub
		return parentService;
	}

	public void setParentService(String parentService) {
		this.parentService = parentService;
	}
}
