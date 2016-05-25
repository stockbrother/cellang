/**
 * All right is from Author of the file,to be explained in comming days.
 * May 12, 2013
 */
package org.cellang.webframework.client.transferpoint;

import org.cellang.webframework.client.Container;
import org.cellang.webframework.client.lang.Address;
import org.cellang.webframework.client.transferpoint.EndpointImpl.UnderlyingProtocol;
import org.cellang.webframework.client.transferpoint.ajax.AjaxTransferProvider;

/**
 * @author wu
 * 
 */
public class AjaxProtocol implements UnderlyingProtocol {

	private Container c;

	public AjaxProtocol(Container c) {
		this.c = c;
	}

	/*
	 * May 9, 2013
	 */
	@Override
	public TransferProvider createGomet(Address uri, boolean force) {
		//

		AjaxTransferProvider rt = new AjaxTransferProvider(this.c, uri);
		return rt;

	}

}
