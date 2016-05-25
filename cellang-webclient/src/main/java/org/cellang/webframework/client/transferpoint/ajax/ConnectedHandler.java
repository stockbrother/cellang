/**
 *  
 */
package org.cellang.webframework.client.transferpoint.ajax;

/**
 * @author wu
 *
 */
public class ConnectedHandler extends ClientAjaxHandler{

	/**
	 * @param client
	 */
	public ConnectedHandler(AjaxTransferProvider client) {
		super(client);
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see com.fs.webcomet.impl.mock.ClientAjaxHandler#handle(com.fs.webcomet.impl.mock.ClientAjaxMsgContext)
	 */
	@Override
	public void handle(ClientAjaxMsgContext amc) {
		String sid = amc.am.getSessionId(true);
		this.client.conected(sid);
		
	}

}
