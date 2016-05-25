/**
 *  
 */
package org.cellang.webframework.client.transferpoint.ajax;

/**
 * @author wu
 *
 */
public class MessageHandler extends ClientAjaxHandler{

	/**
	 * @param client
	 */
	public MessageHandler(AjaxTransferProvider client) {
		super(client);
	}

	/* (non-Javadoc)
	 * @see com.fs.webcomet.impl.mock.ClientAjaxHandler#handle(com.fs.webcomet.impl.mock.ClientAjaxMsgContext)
	 */
	@Override
	public void handle(ClientAjaxMsgContext amc) {
		String msg = amc.am.getProperty(AjaxMsgWrapper.PK_TEXTMESSAGE,true);
		this.client.messageFromServer(msg);
		
	}

}
