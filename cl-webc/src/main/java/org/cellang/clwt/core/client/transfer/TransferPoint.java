/**
 * All right is from Author of the file,to be explained in comming days.
 * Dec 20, 2012
 */
package org.cellang.clwt.core.client.transfer;

import org.cellang.clwt.core.client.data.MessageData;
import org.cellang.clwt.core.client.data.ObjectPropertiesData;
import org.cellang.clwt.core.client.data.PropertiesData;
import org.cellang.clwt.core.client.lang.Address;
import org.cellang.clwt.core.client.lang.WebObject;
import org.cellang.clwt.core.client.message.MsgWrapper;

/**
 * @author wu
 * 
 */
public interface TransferPoint extends WebObject {

	public static final String D_NAME = "endpoint";

	public void sendMessage(MessageData req);

	public void sendMessage(MsgWrapper req);

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
