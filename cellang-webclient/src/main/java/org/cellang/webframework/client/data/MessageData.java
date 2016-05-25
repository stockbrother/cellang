/**
 * All right is from Author of the file,to be explained in comming days.
 * Dec 20, 2012
 */
package org.cellang.webframework.client.data;

import org.cellang.webframework.client.WebException;
import org.cellang.webframework.client.lang.Path;
import org.cellang.webframework.client.util.UID;

/**
 * @author wu
 * 
 */
public class MessageData {

	public static final String HK_PATH = "_path";

	public static final String HK_ID = "_id";

	public static final String HK_SOURCE_ID = "_source_id";

	public static final String PK_SOURCE = "_source";

	public static final String PK_ERROR_INFOS = "_ERROR_INFO_S";

	private StringPropertiesData headers = new StringPropertiesData();

	private ObjectPropertiesData payloads = new ObjectPropertiesData();

	public MessageData() {

	}

	public MessageData(MessageData md) {
		this.headers.setProperties(md.headers);
		this.payloads.setProperties(md.payloads);
	}

	public MessageData(String path) {
		this(path, UID.create("msg-"));
	}

	public MessageData(String path, String id) {
		this.setHeader(HK_PATH, path);
		this.setHeader(HK_ID, id);
	}

	/**
	 * @param path
	 */
	public MessageData(Path path) {
		this(path.toString());
	}

	public StringPropertiesData getHeaders() {
		return headers;
	}

	public String getId() {
		return this.getHeader(HK_ID);
	}

	public String getSourceId() {
		return this.getHeader(HK_SOURCE_ID);
	}

	public String getHeader(String key) {
		String rt = this.headers.getProperty(key, false);
		return rt == null ? null : rt;
	}

	public ErrorInfosData getErrorInfos() {
		ErrorInfosData rt = (ErrorInfosData) this.getPayload(PK_ERROR_INFOS);
		if (rt == null) {
			rt = new ErrorInfosData();
		}
		return rt;
	}

	public String getHeader(String key, boolean force) {
		String rt = this.getHeader(key);

		if (force && rt == null) {
			throw new WebException("no header with key:" + key + ",msg:" + this);
		}

		return rt;
	}

	public void setHeader(String key, String value) {
		this.headers.setProperty(key, value);
	}

	public String getString(String key, boolean force) {
		return this.payloads.getString(key, force);
	}

	public String getString(String key) {
		return this.payloads.getString(key);
	}

	public Boolean getBoolean(String key, Boolean def) {
		return this.payloads.getBoolean(key, def);
	}

	public Object getPayload(String key) {
		Object rt = this.payloads.getProperty(key, false);
		return rt == null ? null : rt;
	}

	public Object getPayload(String key, boolean force) {
		Object rt = this.getPayload(key);

		if (force && rt == null) {
			throw new WebException("no header with key:" + key);
		}

		return rt;
	}

	public ObjectPropertiesData getPayloads() {
		return this.payloads;
	}

	public void setPayloads(PropertiesData<Object> pts) {
		this.payloads.setProperties(pts);
	}

	public void setPayload(String key, boolean value) {
		this.setPayload(key, (value));
	}

	public void setPayload(String key, Object value) {
		this.payloads.setProperty(key, value);
	}

	@Override
	public String toString() {
		return this.getClass().getName() + ",headers:" + this.headers.toString() + ",payloads:"
				+ this.payloads;
	}

	public MessageData getSource() {
		return (MessageData) this.getPayload(PK_SOURCE);
	}

	/**
	 * Jan 1, 2013
	 */
	public Path getPath() {
		//
		String ps = this.getHeader(HK_PATH);
		return Path.valueOf(ps);

	}

}
