/**
 *  
 */
package org.cellang.clwt.core.client.transfer.ajax;


/**
 * @author wu
 * 
 */
public abstract class ClientAjaxHandler {
	protected AjaxUnderlyingTransfer client;

	public ClientAjaxHandler(AjaxUnderlyingTransfer client) {
		this.client = client;
	}

	public abstract void handle(ClientAjaxMsgContext amc);
}
