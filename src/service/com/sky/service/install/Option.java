package com.sky.service.install;

import com.joe.core.xml.ElementParser;

/**
 * 
 * @author qiaolong
 *
 */
public interface Option extends ElementParser{

	String getName();
	
	String getValue();
	
	String getId();
	
	String getOk();
	
	Step getStep();
	
	void setStep(Step step);
}
