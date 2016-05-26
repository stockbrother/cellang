/**
 *  
 */
package org.cellang.core.commons.transfer;

/**
 * @author wu
 * 
 */
public abstract class CometSupport extends CollectionCometListener implements Comet {

	private String id;

	private String protocol;

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
	 * @see
	 * com.fs.websocket.api.WebSocketI#addListener(com.fs.websocket.api.WsListenerI
	 * )
	 */
	@Override
	public void addListener(CometListener ln) {
		this.addListener(ln);
	}

	@Override
	public String getProtocol() {
		return this.protocol;
	}

}
