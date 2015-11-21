package com.joe.monitor.handle;

import com.joe.monitor.callback.NotificationCallBack;

public interface CallBackHandle extends NotificationCallBack{

	CallBackHandle handle(NotificationCallBack[] callBacks);
}
