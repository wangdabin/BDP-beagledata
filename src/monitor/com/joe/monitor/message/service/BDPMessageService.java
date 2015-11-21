package com.joe.monitor.message.service;

import java.io.Serializable;
import java.util.List;

import com.joe.core.service.EntityService;
import com.joe.monitor.vo.BDPMessage;
import com.joe.monitor.vo.BDPMessageModel;

/**
 * @ClassName: BDPMessageService
 * @Description: 监控消息管理服务
 * @author WDB
 * @date 2015-5-4 上午11:17:07
 */
public interface BDPMessageService extends EntityService<BDPMessage> {

	List<BDPMessage> query(BDPMessageModel model);

	void read(Serializable id);

}
