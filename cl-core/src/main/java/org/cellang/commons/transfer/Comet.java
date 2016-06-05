/**
 *  
 */
package org.cellang.commons.transfer;

/**
 * @author wu A comet is a instance of client connection.
 */
public interface Comet {

	public String getProtocol();

	public String getId();

	public void sendMessage(String msg);

	public void addListener(CometListener ln);

	public void setAttribute(String string, Object value);

	public Object getAttribute(String string);
	
	
}
