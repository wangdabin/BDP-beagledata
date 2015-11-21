package com.bdp.service.test;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @ClassName: RackInfo
 * @Description: 保存机架和主机之间的关系
 * @author WDB
 * @date 2015-4-1 上午10:49:03
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "rackList")
public class RackInfo implements Serializable {
    private static final long serialVersionUID = 1L;
    private List<Rack> racks;
    public List<Rack> getRacks() {
        return racks;
    }
    public void setRacks(List<Rack> racks) {
        this.racks = racks;
    }
}
