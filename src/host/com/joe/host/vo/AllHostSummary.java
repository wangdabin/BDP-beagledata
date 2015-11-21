package com.joe.host.vo;

import java.util.LinkedList;
import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;
/**
 * 
 * @author lifei
 *
 */
@XmlRootElement
public class AllHostSummary {
	private int active = 0;
	private int dead = 0;
	private List<Host> hostList = new LinkedList<Host>();
	
	public int getActive() {
		return active;
	}
	public void setActive(int active) {
		this.active = active;
	}
	public int getDead() {
		return dead;
	}
	public void setDead(int dead) {
		this.dead = dead;
	}
	public List<Host> getHostList() {
		return (hostList == null ? new LinkedList<Host>() : hostList);
	}
	public void setHostList(List<Host> hostList) {
		this.hostList = hostList;
	}
	
}
