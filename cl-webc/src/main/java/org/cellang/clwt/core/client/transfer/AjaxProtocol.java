/**
 * All right is from Author of the file,to be explained in comming days.
 * May 12, 2013
 */
package org.cellang.clwt.core.client.transfer;

import org.cellang.clwt.core.client.Container;
import org.cellang.clwt.core.client.lang.Address;
import org.cellang.clwt.core.client.transfer.EndpointImpl.UnderlyingProtocol;
import org.cellang.clwt.core.client.transfer.ajax.AjaxUnderlyingTransfer;

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
	public UnderlyingTransfer createGomet(Address uri, boolean force) {
		//

		AjaxUnderlyingTransfer rt = new AjaxUnderlyingTransfer(this.c, uri);
		return rt;

	}

}