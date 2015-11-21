package com.joe.agent.i18n;

import org.springframework.stereotype.Service;

import com.joe.agent.utils.AgentUtils;
import com.joe.core.annotation.I18n;
import com.joe.core.i18n.I18nMessage;

@I18n(name = AgentUtils.NAME,type = AgentUtils.TYPE)
@Service("agentI18n")
public class AgentI18nMessage extends I18nMessage {

	public static final String BASE_NAME = "i18n.agent.message";
	
	public AgentI18nMessage() {
		super(BASE_NAME);
	}
}
