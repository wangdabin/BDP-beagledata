package com.joe.host.ssh;

import com.joe.core.utils.CoreConfigUtils;

/**
 * @ClassName: Src2Dest
 * @Description: 保存不同类型的*tar.gz在bdp平台的存放目录和资源在远程主机存放的目录
 * @author WDB
 * @date 2014-12-27 下午4:46:04
 * 
 */
public enum Src2Dest {
    BASIC("host.basic.component.src", "host.basic.component.dst");

    private String src;// 资源在bdp平台的存放目录
    private String desc;// 资源在远程主机存放的目录

    private Src2Dest(String src, String desc) {
	try {
	    this.src = CoreConfigUtils.create().getString(src);
	    this.desc = CoreConfigUtils.create().getString(desc);
	} catch (Exception e) {
	    throw new RuntimeException(e);
	}

    }

    public String getSrc() {
	if (!src.endsWith("/")) {
	    src += "/";
	}
	return src;
    }

    public String getDesc() {
	if (!desc.endsWith("/")) {
	    desc += "/";
	}
	return desc;
    }

    public static void main(String[] args) {
	System.out.println(Src2Dest.BASIC.getSrc());
    }
}
