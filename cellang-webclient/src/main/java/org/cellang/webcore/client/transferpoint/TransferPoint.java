/**
 * All right is from Author of the file,to be explained in comming days.
 * Dec 20, 2012
 */
package org.cellang.webcore.client.transferpoint;

import org.cellang.webcore.client.data.MessageData;
import org.cellang.webcore.client.data.ObjectPropertiesData;
import org.cellang.webcore.client.data.PropertiesData;
import org.cellang.webcore.client.lang.Address;
import org.cellang.webcore.client.lang.WebObject;
import org.cellang.webcore.client.message.MessageDataWrapper;

/**
 * @author wu
 * 
 */
public interface TransferPoint extends WebObject {

	public static final String D_NAME = "endpoint";

	public void sendMessage(MessageData req);

	public void sendMessage(MessageDataWrapper req);

	public Address getUri();
	
	public void open();

	public void close();

	public boolean isOpen();

	public void auth(PropertiesData<Object> pts);

	public void logout();

	public boolean isBond();

	public String getSessionId();

	public ObjectPropertiesData getUserInfo();

	public void destroy();

}