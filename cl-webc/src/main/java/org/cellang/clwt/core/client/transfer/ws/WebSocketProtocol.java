/**
 * All right is from Author of the file,to be explained in comming days.
 * May 12, 2013
 */
package org.cellang.clwt.core.client.transfer.ws;

import org.cellang.clwt.core.client.lang.Address;
import org.cellang.clwt.core.client.transfer.EndpointImpl;
import org.cellang.clwt.core.client.transfer.UnderlyingTransfer;
import org.cellang.clwt.core.client.transfer.EndpointImpl.UnderlyingProtocol;

/**
 * @author wu
 * 
 */
public class WebSocketProtocol implements UnderlyingProtocol {

	/*
	 * May 9, 2013
	 */
	@Override
	public UnderlyingTransfer createGomet(Address uri, boolean force) {
		//
		WebSocketTransferProvider rt = new WebSocketTransferProvider(uri);
		
		return rt;

	}

}
