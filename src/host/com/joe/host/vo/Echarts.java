package com.joe.host.vo;

import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;
/**
 * 
 * @author lifei
 *
 */
@XmlRootElement
public class Echarts {

	private List<String> xAxisData;
	private List<EchartSeries> seriesData;
	
	public List<String> getxAxisData() {
		return xAxisData;
	}
	public void setxAxisData(List<String> xAxisData) {
		this.xAxisData = xAxisData;
	}
	public List<EchartSeries> getSeriesData() {
		return seriesData;
	}
	public void setSeriesData(List<EchartSeries> seriesData) {
		this.seriesData = seriesData;
	}
	
}
