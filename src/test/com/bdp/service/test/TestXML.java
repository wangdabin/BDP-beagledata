package com.bdp.service.test;

import java.util.ArrayList;
import java.util.List;

/** 
 *  
 * @author Steven 
 *  
 */  
public class TestXML {  
    public static void main(String[] args) {  
//        // 创建需要转换的对象  
//        User user = new User(1, "Steven", "@sun123", new Date(), 1000.0);  
//        System.out.println("---将对象转换成string类型的xml Start---");  
//        // 将对象转换成string类型的xml  
//        String str = XMLUtil.convertToXml(user);  
//        // 输出  
//        System.out.println(str);  
//        System.out.println("---将对象转换成string类型的xml End---");  
//        System.out.println();  
//        System.out.println("---将String类型的xml转换成对象 Start---");  
//        User userTest = (User) XMLUtil.convertXmlStrToObject(User.class, str);  
//        System.out.println(userTest);  
//        System.out.println("---将String类型的xml转换成对象 End---");  
	
	Node node = new Node();
	node.setName("node");
	
	Node node1 = new Node();
	node1.setName("node1");
	
	List<Node> nodes = new ArrayList<Node>();
	nodes.add(node);
	nodes.add(node1);
	
	Rack rack = new Rack();
	rack.setNodes(nodes);
	
//	 String str = XMLUtil.convertToXml(rack); 
//	 System.out.println(str);
	
	List<Rack> racks = new ArrayList<Rack>();
	racks.add(rack);
	
	RackInfo info = new RackInfo();
	info.setRacks(racks);
	
	String str = XMLUtil.convertToXml(info); 
	System.out.println(str);
	
	
	
    }  
}  