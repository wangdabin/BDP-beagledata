package com.sky.service.install.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.sky.service.install.Entry;
import com.sky.service.install.Group;
import com.sky.service.install.Step;

/**
 * 一个步骤组
 * @author qiaolong
 *
 */
public class GroupImpl extends AbstractEntry implements Group {

	
	private List<Group> groups;
	private List<Step> steps;
	private Group parent;

	public List<Group> getGroups() {
		return groups;
	}

	public void setGroups(List<Group> groups) {
		this.groups = groups;
	}

	public List<Step> getSteps() {
		return steps;
	}

	public void setSteps(List<Step> steps) {
		this.steps = steps;
	}

	@Override
	public boolean isStep() {
		return false;
	}

	@Override
	public boolean isGroup() {
		return true;
	}
	
	public Group getGroup() {
		return parent;
	}

	public void setParent(Group parent) {
		this.parent = parent;
	}
	
	@Override
	public Group getTopGroup() {
		if(this.parent == null){
			return this;
		}else{
			return this.parent.getTopGroup();
		}
	}
	
//	@Override
//	public String getOK() {
//		String ok = super.getOK();
//		if(StringUtils.isBlank(ok)){
//			if(parent != null){
//				ok = parent.getOK();
//			}
//		}
//		return ok;
//	}

	@Override
	public void parse(Map<String, Entry> entrys, Element element) {
		super.parse(entrys,element);
		NodeList groupNodes = element.getChildNodes();
    	if(groupNodes != null){
    		List<Group> groups = new ArrayList<Group>();
    		List<Step> steps = new ArrayList<Step>();
    		for (int j = 0; j < groupNodes.getLength(); j++) {
    		    Node groupNode = groupNodes.item(j);
    		    if (!(groupNode instanceof Element)) {
    		    	continue;
    		    }
    		    Element groupElement = (Element) groupNode;
    		    String tagName = groupElement.getTagName();
    		    if("group".equalsIgnoreCase(tagName)){ // 引入其他配置项
    		    	Group group = GroupImpl.build(entrys,groupElement);
    		    	group.setParent(this);
    		    	groups.add(group);
    		    }else if("step".equalsIgnoreCase(tagName)){
    		    	Step step = StepImpl.build(entrys,groupElement);
    		    	step.setGroup(this);
    		    	steps.add(step);
    		    }
    		}
    		this.groups = groups;
    		this.steps = steps;
    	}
	}
	
	public static final Group build(Map<String, Entry> entrys,Element node){
		Group group = new GroupImpl();
		group.parse(entrys,node);
		return group;
	}

	@Override
	public boolean hasGroup() {
		return this.parent != null;
	}

}
