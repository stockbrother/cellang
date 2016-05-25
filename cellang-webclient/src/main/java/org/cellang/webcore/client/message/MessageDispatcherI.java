/**
 * All right is from Author of the file,to be explained in comming days.
 * Dec 23, 2012
 */
package org.cellang.webcore.client.message;

import org.cellang.webcore.client.lang.Path;

/**
 * @author wu
 * 
 */
public interface MessageDispatcherI extends MessageHandlerI<MessageDataWrapper> {

	public <W extends MessageDataWrapper> void addHandler(Path path,
			MessageHandlerI<W> mh);

	public <W extends MessageDataWrapper> void addHandler(Path path, boolean strict,
			MessageHandlerI<W> mh);

	public <W extends MessageDataWrapper> void addDefaultHandler(MessageHandlerI<W> mh);

	public void addExceptionHandler(MessageExceptionHandlerI eh);

	public void dispatch(MessageDataWrapper mw);

	public void cleanAllHanlders();
}
