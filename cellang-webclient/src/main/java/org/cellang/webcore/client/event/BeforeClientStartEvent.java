/**
 * Jul 20, 2012
 */
package org.cellang.webcore.client.event;

import org.cellang.webcore.client.WebClient;

/**
 * @author wu
 * 
 */
public class BeforeClientStartEvent extends ClientEvent {
	public static Type<BeforeClientStartEvent> TYPE = new Type<BeforeClientStartEvent>(ClientEvent.TYPE, "before-start");

	/** */
	public BeforeClientStartEvent(WebClient client) {
		super(TYPE, client);
	}

}