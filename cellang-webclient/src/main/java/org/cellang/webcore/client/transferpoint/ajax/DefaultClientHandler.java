/**
 *  
 */
package org.cellang.webcore.client.transferpoint.ajax;


import org.cellang.webcore.client.logger.WebLogger;
import org.cellang.webcore.client.logger.WebLoggerFactory;


/**
 * @author wu
 * 
 */
public class DefaultClientHandler extends ClientAjaxHandler {

	public static final WebLogger LOG = WebLoggerFactory.getLogger(DefaultClientHandler.class);

	/**
	 * @param client
	 */
	public DefaultClientHandler(AjaxTransferProvider client) {
		super(client);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.fs.webcomet.impl.mock.ClientAjaxHandler#handle(com.fs.webcomet.impl
	 * .mock.ClientAjaxMsgContext)
	 */
	@Override
	public void handle(ClientAjaxMsgContext amc) {
		LOG.info("amc:" + amc);
	}

}
