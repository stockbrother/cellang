/**
 * All right is from Author of the file,to be explained in comming days.
 * May 9, 2013
 */
package org.cellang.clwt.core.client.transferpoint;

import org.cellang.clwt.core.client.lang.Handler;

/**
 * @author wu
 * 
 */
public interface TransferProvider {

	public String getProtocol();

	public void open(long timeoutMs);

	public void close();

	public void send(String jsS, Handler<String> onfailure);

	// regist listeners
	public void addOpenHandler(Handler<TransferProvider> handler);

	public void addCloseHandler(Handler<String> handler);

	public void addErrorHandler(Handler<String> handler);

	public void addMessageHandler(Handler<String> handler);

	public boolean isOpen();

}
