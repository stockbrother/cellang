/**
 * All right is from Author of the file,to be explained in comming days.
 * Dec 20, 2012
 */
package org.cellang.clwt.core.client.transfer;

import org.cellang.clwt.core.client.data.MessageData;
import org.cellang.clwt.core.client.lang.Address;
import org.cellang.clwt.core.client.lang.Path;
import org.cellang.clwt.core.client.lang.WebObject;
import org.cellang.clwt.core.client.message.MessageHandlerI;
import org.cellang.clwt.core.client.message.MsgWrapper;

/**
 * @author wu
 * 
 */
public interface LogicalChannel extends WebObject {
	public static final Path MSG_CLIENT_IS_READY = Path.valueOf("control.status.clientIsReady");
	
	public static final Path MSG_SERVER_IS_READY = Path.valueOf("control.status.serverIsReady");

	public static final String D_NAME = "endpoint";

	public void sendMessage(MessageData req);

	public void sendMessage(MsgWrapper req);

	public Address getUri();

	public void open();

	public void close();

	public boolean isOpen();

	public void destroy();

	public void addHandler(String topic, Path path, MessageHandlerI<MsgWrapper> mh);

}
