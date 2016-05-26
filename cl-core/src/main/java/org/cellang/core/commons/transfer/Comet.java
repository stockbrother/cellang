/**
 *  
 */
package org.cellang.core.commons.transfer;

/**
 * @author wu A comet is a instance of client connection.
 */
public interface Comet {

	public String getProtocol();

	public String getId();

	public void sendMessage(String msg);

	public void addListener(CometListener ln);
}
