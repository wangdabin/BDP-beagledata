package com.bdp.service.test;

import static org.junit.Assert.fail;

import org.junit.Test;

import com.joe.monitor.vo.BDPMessageModel;
import com.liuzj.pro2config.BdpClient;

public class BDPMessageResourceTest {

	@Test
	public void testList() {
		String resource = "http://localhost:8098/ws/v1/message/list";
		BdpClient.get(resource);
	}

	@Test
	public void testQuery() {
		String resource = "http://localhost:8098/ws/v1/message/query";
		BDPMessageModel model = new BDPMessageModel();
		model.setLevel("INFO");
		BdpClient.post(resource,model);
	}

	@Test
	public void testDelete() {
		fail("Not yet implemented");
	}

	@Test
	public void testRead() {
		String resource = "http://localhost:8098/ws/v1/message/read/2";
		BdpClient.put(resource,null);
	}
}
