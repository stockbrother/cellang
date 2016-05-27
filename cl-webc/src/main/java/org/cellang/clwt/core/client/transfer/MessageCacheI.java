/**
 * All right is from Author of the file,to be explained in comming days.
 * Mar 28, 2013
 */
package org.cellang.clwt.core.client.transfer;

import org.cellang.clwt.core.client.data.MessageData;
import org.cellang.clwt.core.client.lang.WebObject;
import org.cellang.clwt.core.client.message.MessageDataWrapper;

/**
 * @author wu
 * 
 */
public interface MessageCacheI extends WebObject {

	public void addMessage(MessageData md);

	public void addMessage(MessageDataWrapper mw);

	public MessageData getMessage(String id);
	
	public MessageData removeMessage(String id);
	
	public int size();

	public void start();

	public void stop();

}
