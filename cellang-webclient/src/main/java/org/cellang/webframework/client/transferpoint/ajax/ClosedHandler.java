/**
 *  
 */
package org.cellang.webframework.client.transferpoint.ajax;

/**
 * @author wu
 *
 */
public class ClosedHandler extends ClientAjaxHandler{

	/**
	 * @param client
	 */
	public ClosedHandler(AjaxTransferProvider client) {
		super(client);
	}

	/* (non-Javadoc)
	 * @see com.fs.webcomet.impl.mock.ClientAjaxHandler#handle(com.fs.webcomet.impl.mock.ClientAjaxMsgContext)
	 */
	@Override
	public void handle(ClientAjaxMsgContext amc) {
		this.client.tryClosedByServer();
	}

}
