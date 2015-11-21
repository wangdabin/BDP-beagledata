package com.joe.core.vo;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * { errcode : 返回错误码, msg : 错误信息, ret : 返回值，0-成功，非0-失败, data : { id : 微博的id },
 * seqid : 序列号 }
 * 
 * @author Joe
 * 
 */
@XmlRootElement
public class ReCode implements Serializable {

	public static final int RET_SUCCESS = 0;
	public static final int RET_FAIL = -1;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String errcode;
	private String msg;
	private int ret;
	private Data data;

	/**
	 * @return the errcode
	 */
	public String getErrcode() {
		return errcode;
	}

	/**
	 * @param errcode
	 *            the errcode to set
	 */
	public void setErrcode(String errcode) {
		this.errcode = errcode;
	}

	/**
	 * @return the msg
	 */
	public String getMsg() {
		return msg;
	}

	/**
	 * @param msg
	 *            the msg to set
	 */
	public void setMsg(String msg) {
		this.msg = msg;
	}

	/**
	 * @return the ret
	 */
	public int getRet() {
		return ret;
	}

	/**
	 * @param ret
	 *            the ret to set
	 */
	public void setRet(int ret) {
		this.ret = ret;
	}

	/**
	 * @return the data
	 */
	public Data getData() {
		return data;
	}

	/**
	 * @param data
	 *            the data to set
	 */
	public void setData(Data data) {
		this.data = data;
	}

	/**
	 * 
	 * @author Joe
	 */
	@XmlRootElement
	public static class Data {
		private long id;
		private String sId;

		public Data() {
		}

		public Data(long id) {
			this.id = id;
		}

		public Data(String sId) {
			this.sId = sId;
		}

		/**
		 * @return the id
		 */
		public long getId() {
			return id;
		}

		/**
		 * @param id
		 *            the id to set
		 */
		public void setId(long id) {
			this.id = id;
		}

		/**
		 * @return the sId
		 */
		public String getsId() {
			return sId;
		}

		/**
		 * @param sId
		 *            the sId to set
		 */
		public void setsId(String sId) {
			this.sId = sId;
		}
	}

}
