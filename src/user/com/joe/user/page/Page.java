package com.joe.user.page;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @ClassName: Page
 * @Description: 在client与server端之间传递的分页信息对象
 * @author WDB
 * @date 2015-3-4 上午9:36:58
 *
 */
@XmlRootElement
public class Page<T> implements Serializable{

    private static final long serialVersionUID = 1L;
    
    private Long  totalCount; // 总记录数
    
    private List<T> records; //每页保存的对应的记录集合

    public Long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Long totalCount) {
        this.totalCount = totalCount;
    }

    public List<T> getRecords() {
        return records;
    }

    public void setRecords(List<T> records) {
        this.records = records;
    }

}
