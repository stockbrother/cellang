/**
 * All right is from Author of the file,to be explained in comming days.
 * May 12, 2013
 */
package org.cellang.clwt.core.client.transfer.ws;

import org.cellang.clwt.core.client.lang.Address;
import org.cellang.clwt.core.client.transfer.DefaultLogicalChannel;
import org.cellang.clwt.core.client.transfer.UnderlyingChannel;
import org.cellang.clwt.core.client.transfer.DefaultLogicalChannel.UnderlyingProtocol;

/**
 * @author wu
 * 
 */
public class WebSocketProtocol implements UnderlyingProtocol {

	/*
	 * May 9, 2013
	 */
	@Override
	public UnderlyingChannel createGomet(Address uri, boolean force) {
		//
		WebSocketTransferProvider rt = new WebSocketTransferProvider(uri);
		
		return rt;

	}

}
