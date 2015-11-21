package com.bdp.service.test;

import org.junit.Test;

import com.liuzj.pro2config.BdpClient;
import com.sky.config.BDPConfigVO;

public class BDPConfigResourceTest {

	@Test
	public void testGetAll() {
		String resource = "http://localhost:8098/ws/v1/bdpconfig/list";
		BdpClient.get(resource);
	}

	@Test
	public void testGet() {
		String resource = "http://localhost:8098/ws/v1/bdpconfig/get/aa";
		BdpClient.get(resource);
	}

	@Test
	public void testAddBDPConfigVO() {
		
		String resource = "http://localhost:8098/ws/v1/bdpconfig/add";
		BDPConfigVO configVO = new BDPConfigVO();
		configVO.setKey("aaaa1111");
		configVO.setName("bb");
		configVO.setValue("cc");
		configVO.setVisible(false);
		configVO.setDescription("dd");
		configVO.setEditable(true);
		BdpClient.post(resource,configVO);
		
	}

	@Test
	public void testAddListOfBDPConfigVO() {
		String resource = "http://localhost:8098/ws/v1/bdpconfig/add/batch";
		BDPConfigVO configVO = new BDPConfigVO();
		configVO.setKey("aaaa");
		configVO.setName("bb");
		configVO.setValue("cc");
		configVO.setVisible(false);
		configVO.setDescription("dd");
		configVO.setEditable(true);
		BDPConfigVO[]  bdpConfigVOs = {configVO};
		BdpClient.post(resource,bdpConfigVOs);
	}

	@Test
	public void testUpdate() {
		String resource = "http://localhost:8098/ws/v1/bdpconfig/update/aa/aaaaa";
		BdpClient.put(resource,null);
		
	}

	
	

}
