/**
 * Jul 17, 2012
 */
package org.cellang.core.lang;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.cellang.commons.ObjectUtil;
import org.cellang.commons.lang.NameSpace;

/**
 * @author wu
 * 
 */
public class ErrorInfo implements ValueI {

	private String id;

	private String code;

	private String message;

	private List<String> detail;

	public ErrorInfo(NameSpace ecode) {
		this(ecode == null ? null : ecode.toString());
	}

	public ErrorInfo(String code) {
		this(code, (String) null);
	}

	public ErrorInfo(String code, String msg) {
		this(code, msg, null);
	}

	public ErrorInfo(Throwable t) {
		this(null, t);
	}

	public ErrorInfo(String source, Throwable t) {
		this(source, null, t);
	}

	public ErrorInfo(String code, String msg, Throwable t) {
		this(code, msg, t, UUID.randomUUID().toString());
	}

	public ErrorInfo(String code, String msg, Throwable t, String id) {
		this.id = id;
		this.code = code == null ? "/error/unknow" : code;
		this.message = msg;
		this.detail = new ArrayList<String>();
		if (t != null) {
			ByteArrayOutputStream os = new ByteArrayOutputStream();
			PrintStream ps = new PrintStream(os);
			t.printStackTrace(ps);
			String[] ss = os.toString().split("\n");
			this.detail.addAll(Arrays.asList(ss));
		}
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
		if (o == null || !(o instanceof ErrorInfo)) {
			return false;
		}
		ErrorInfo ei = (ErrorInfo) o;
		return ObjectUtil.isNullSafeEquals(ei.code, this.code) && ObjectUtil.isNullSafeEquals(ei.message, this.message)
				&& ObjectUtil.isNullSafeEquals(ei.detail, ei.detail);
	}

	/* */
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();

		sb.append("code:");
		sb.append(this.code);
		sb.append(",message:");
		sb.append(this.message);
		sb.append(",detail:");
		sb.append("");
		for (String line : this.detail) {
			sb.append(line);
			sb.append("\n");

		}
		return sb.toString();

	}

	/**
	 * @return the source
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @param message
	 *            the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	public static ErrorInfo valueOf(String code, String message) {
		//
		return new ErrorInfo(code, message);
	}

}
