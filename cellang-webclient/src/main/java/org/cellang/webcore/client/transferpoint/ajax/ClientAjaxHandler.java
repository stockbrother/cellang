/**
 *  
 */
package org.cellang.webcore.client.transferpoint.ajax;


/**
 * @author wu
 * 
 */
public abstract class ClientAjaxHandler {
	protected AjaxTransferProvider client;

	public ClientAjaxHandler(AjaxTransferProvider client) {
		this.client = client;
	}

	public abstract void handle(ClientAjaxMsgContext amc);
}
