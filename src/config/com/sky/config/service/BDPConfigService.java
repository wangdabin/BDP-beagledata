package com.sky.config.service;

import com.joe.core.service.EntityService;
import com.sky.config.BDPConfigVO;
/**
 * @ClassName: BDPConfigService
 * @Description: BDP全局变量服务
 * @author WDB
 * @date 2015-4-16 上午11:37:05
 *
 */
public interface BDPConfigService extends EntityService<BDPConfigVO>{
	
	public BDPConfigVO findByKey(String key);

	public void updateValueByKey(String key, String value);
	
	
	
}
