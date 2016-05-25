/**
 * All right is from Author of the file,to be explained in comming days.
 * May 12, 2013
 */
package org.cellang.webframework.client.transferpoint.ws;

import org.cellang.webframework.client.lang.Address;
import org.cellang.webframework.client.transferpoint.EndpointImpl;
import org.cellang.webframework.client.transferpoint.TransferProvider;
import org.cellang.webframework.client.transferpoint.EndpointImpl.UnderlyingProtocol;

/**
 * @author wu
 * 
 */
public class WebSocketProtocol implements UnderlyingProtocol {

	/*
	 * May 9, 2013
	 */
	@Override
	public TransferProvider createGomet(Address uri, boolean force) {
		//
		WebSocketTransferProvider rt = new WebSocketTransferProvider(uri);
		
		return rt;

	}

}
