/**
 *  
 */
package org.cellang.commons.transfer;

import java.util.HashMap;
import java.util.Map;

/**
 * @author wu
 * 
 */
public abstract class CometSupport extends CollectionCometListener implements Comet {

	private String id;

	private String protocol;

	private Map<String, Object> attributes = new HashMap<String, Object>();

	public CometSupport(String pro, String tid) {
		this.id = tid;
		this.protocol = pro;
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.fs.websocket.api.WebSocketI#addListener(com.fs.websocket.api.
	 * WsListenerI )
	 */
	@Override
	public void addListener(CometListener ln) {
		this.addListener(ln);
	}

	@Override
	public String getProtocol() {
		return this.protocol;
	}

	public void setAttribute(String key, Object value) {
		this.attributes.put(key, value);
	}

	public Object getAttribute(String key) {
		return this.attributes.get(key);
	}
}
