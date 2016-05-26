/**
 * Jul 17, 2012
 */
package org.cellang.clwt.core.client.data;

import java.util.ArrayList;
import java.util.List;

import org.cellang.clwt.core.client.util.ObjectUtil;
import org.cellang.clwt.core.client.util.UID;

/**
 * @author wu
 * 
 */
public class ErrorInfoData {

	private String message;

	private String code;// error code?
	
	private String id;

	private List<String> detail;
	public ErrorInfoData(String code, String message) {
		this(code,message,UID.create("err-"));
	}
	public ErrorInfoData(String code, String message, String id) {
		this.id = id;
		this.code = code;
		this.message = message;
		this.detail = new ArrayList<String>();

	}

	public boolean isCode(String code) {
		return this.code.equals(code);
	}

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @return the detail
	 */
	public List<String> getDetail() {
		return detail;
	}

	@Override
	public boolean equals(Object o) {
		if (o == null || !(o instanceof ErrorInfoData)) {
			return false;
		}
		ErrorInfoData ei = (ErrorInfoData) o;
		return ObjectUtil.nullSafeEquals(ei.message, this.message)
				&& ObjectUtil.nullSafeEquals(ei.detail, ei.detail);
	}

	/* */
	@Override
	public String toString() {
		return "code:" + this.code + ",msg:" + this.message + ",detail:" + this.detail;

	}

	/**
	 * @return the source
	 */
	public String getCode() {
		return code;
	}
	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

}
