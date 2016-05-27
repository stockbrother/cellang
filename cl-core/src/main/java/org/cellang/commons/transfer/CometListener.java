/**
 * All right is from Author of the file,to be explained in comming days.
 * Dec 11, 2012
 */
package org.cellang.commons.transfer;

import java.io.Reader;


/**
 * @author wu
 * 
 */
public interface CometListener {


	public void onConnect(Comet ws);

	public void onMessage(Comet ws, Reader reader);
	
	public void onMessage(Comet ws, String ms);
	
	public void onException(Comet ws, Throwable t);

	public void onClose(Comet ws, int statusCode, String reason);

}
