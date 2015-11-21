package com.joe.core.support;

import java.util.List;

import com.joe.core.version.Name;
import com.joe.host.vo.Host;

/**
 * 被发布者
 * 
 * @author qiaolong
 * 
 */
public interface Deployed extends Name{

	/**
	 * 所有数据源
	 * 
	 * @return
	 */
	List<Pair> getPair();

	/**
	 * 目标主机
	 * 
	 * @return
	 */
	List<Host> getAllHosts();

	public static class Pair {
		private String source;
		private String target;
		private String type;
		private boolean uncompress;

		public String getSource() {
			return source;
		}

		public void setSource(String source) {
			this.source = source;
		}

		public String getTarget() {
			return target;
		}

		public void setTarget(String target) {
			this.target = target;
		}

		public boolean isUncompress() {
			return uncompress;
		}

		public void setUncompress(boolean uncompress) {
			this.uncompress = uncompress;
		}

		public String getType() {
			return type;
		}

		public void setType(String type) {
			this.type = type;
		}
	}
}
