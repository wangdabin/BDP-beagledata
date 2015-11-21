package com.joe.monitor.message.dao.impl;

import org.springframework.stereotype.Repository;

import com.joe.monitor.message.dao.BDPMessageDAO;
import com.joe.monitor.vo.BDPMessage;
import com.joe.user.dao.impl.EntityDAOImpl;
@Repository(value = "bdpMessageDAO")
public class BDPMessageDAOImpl extends EntityDAOImpl<BDPMessage> implements BDPMessageDAO {

}
