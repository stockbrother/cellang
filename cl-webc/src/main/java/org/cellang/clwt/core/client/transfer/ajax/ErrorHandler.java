/**
 *  
 */
package org.cellang.clwt.core.client.transfer.ajax;

/**
 * @author wu
 * 
 */
public class ErrorHandler extends ClientAjaxHandler {

	/**
	 * @param client
	 */
	public ErrorHandler(AjaxUnderlyingTransfer client) {
		super(client);
	}

	@Override
	public void handle(ClientAjaxMsgContext amc) {
		
		
		String errorCode = amc.am.getProperty(AjaxMsgWrapper.PK_ERROR_CODE,true);
		String errorMsg = amc.am.getProperty(AjaxMsgWrapper.PK_ERROR_MSG);
		
		this.client.errorFromServer(errorCode,errorMsg);
	}

}
