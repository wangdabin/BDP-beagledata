package com.joe.config.init;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.joe.core.init.AbstractInitAble;
import com.joe.core.init.annotation.Init;
import com.sky.config.BDPConfigVO;
import com.sky.config.service.BDPConfigService;

@Init(name = "bdpconfig", type = "bdpconfig")
public class BDPConfigInit extends AbstractInitAble {

	public static final Logger LOG = Logger.getLogger(BDPConfigInit.class);
	private static ApplicationContext context =
	    	new ClassPathXmlApplicationContext("beans.xml");
	
	@Override
	public void doInit() {
		BDPConfigService bdpConfigService = context.getBean(BDPConfigService.class);
		
		List<BDPConfigVO> all = bdpConfigService.getAll();
		if(all.size() == 0){
			LOG.info("BDPConfigInit init start");
			BDPConfigVO configVO = new BDPConfigVO();
			configVO.setKey("aa");
			configVO.setName("bb");
			configVO.setValue("cc");
			configVO.setVisible(false);
			configVO.setDescription("dd");
			configVO.setEditable(true);
			List<BDPConfigVO> bdpConfigVOs = new ArrayList<BDPConfigVO>();
			bdpConfigVOs.add(configVO);
			bdpConfigService.addBatch(bdpConfigVOs);
		}
	}

	@Override
	public void destroy() {
		LOG.info("BDPConfigInit destory");
	}
}
