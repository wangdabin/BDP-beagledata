package com.sky.service.define;

import org.w3c.dom.Element;

public class DirDefile extends AbstractDefine {

	private String dir;

	public String getDir() {
		return dir;
	}

	private void setDir(String dir) {
		this.dir = dir;
	}

	@Override
	public void parse(Element dirElment) {
		this.parseName(dirElment);
		String dir = dirElment.getTextContent();
		this.setDir(dir);
	}

}
