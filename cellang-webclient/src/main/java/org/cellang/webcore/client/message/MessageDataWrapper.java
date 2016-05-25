/**
 * Jun 13, 2012
 */
package org.cellang.webcore.client.message;

import org.cellang.webcore.client.WebException;
import org.cellang.webcore.client.data.MessageData;
import org.cellang.webcore.client.data.ObjectPropertiesData;
import org.cellang.webcore.client.lang.AbstractHasProperties;
import org.cellang.webcore.client.lang.Path;

/**
 * @author wuzhen
 * 
 */
public class MessageDataWrapper extends AbstractHasProperties<Object> {

	public static final String HK_REQUEST_ID = "_requestId";
	
	protected MessageData target;

	public MessageDataWrapper(String path) {
		this(Path.valueOf(path));
	}

	public MessageDataWrapper(Path path) {
		this(new MessageData(path));
	}

	public MessageDataWrapper(MessageData md) {
		this.target = md;
	}

	public String getHeader(String key, boolean force) {
		return this.target.getHeader(key, force);
	}

	public String getHeader(String key) {
		return this.target.getHeader(key);
	}

	/**
	 * @param headers
	 *            the header to set
	 */
	public void setHeader(String key, String value) {
		this.target.setHeader(key, value);
	}

	/**
	 * @return the payload
	 */
	public ObjectPropertiesData getPayloads() {
		return this.target.getPayloads();
	}

	/**
	 * @param payload
	 *            the payload to set
	 */
	public void setPayloads(ObjectPropertiesData pts) {
		this.target.setPayloads(pts);
	}

	public <T> T getPayload(String key, boolean force) {
		Object rt = this.getPayloads().getProperty(key, force);

		return (T) rt;
	}

	public void setPayload(String key, boolean value) {
		this.target.setPayload(key, value);
	}

	public void setPayload(String key, String value) {

		this.target.setPayload(key, (value));

	}

	public void setPayload(String key, Object value) {
		this.target.setPayload(key, value);
	}

	public String getPayLoadAsString(String key, boolean force) {
		String sd = this.getPayload(key, force);
		String rt = sd == null ? null : sd;
		if (rt == null && force) {
			throw new WebException("no payload:" + key);
		}
		return rt;

	}

	public Boolean getPayLoadAsBoolean(String key, Boolean def) {
		Boolean rt = this.getPayLoadAsBoolean(key, false);
		return rt == null ? def : rt;
	}

	public Boolean getPayLoadAsBoolean(String key, boolean force) {
		Boolean sd = this.getPayload(key, false);
		Boolean rt = sd == null ? null : sd;

		if (rt == null && force) {
			throw new WebException("no payload:" + key);
		}

		return rt;

	}

	public Path getPath() {
		return this.target.getPath();
	}

	/**
	 * @return the target
	 */
	public MessageData getTarget() {
		return target;
	}

	/**
	 * @return
	 */
	public MessageData getMessage() {
		return this.getTarget();
	}

	@Override
	public String toString() {
		return "MsgWarpper,target:" + this.target.toString();
	}
}
