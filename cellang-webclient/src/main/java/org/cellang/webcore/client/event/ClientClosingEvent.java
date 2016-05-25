/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 5, 2012
 */
package org.cellang.webcore.client.event;

import org.cellang.webcore.client.WebClient;

/**
 * @author wu
 * 
 */
public class ClientClosingEvent extends StateChangeEvent {

	public static final Type<ClientClosingEvent> TYPE = new Type<ClientClosingEvent>("client-closing");

	/**
	 * @param type
	 */
	public ClientClosingEvent(WebClient client) {
		super(TYPE, client);
	}

}
