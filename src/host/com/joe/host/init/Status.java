package com.joe.host.init;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @ClassName: Status
 * @Description: 主机在安装agent时的进度状态
 * @author WDB
 * @date 2014-12-31 上午11:03:08
 */
@XmlRootElement
public class Status {
    public static final double FAIL = -1d;
    public static final double INIT = 0d;
    public static final double START = 99d;
    public static final double SUCCESS = 100d;
    
    public Double progress;//保存当前执行的进度

    public Double getProgress() {
        return progress;
    }

    public void setProgress(Double progress) {
        this.progress = progress;
    }

}