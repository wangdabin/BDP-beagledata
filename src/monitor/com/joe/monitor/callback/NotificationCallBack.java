package com.joe.monitor.callback;

import javax.management.Notification;

import com.joe.core.callback.CallBack;
import com.sky.config.ConfigAble;

public interface NotificationCallBack extends ConfigAble,CallBack{

	void call(Notification notification, Object handBack);
}
