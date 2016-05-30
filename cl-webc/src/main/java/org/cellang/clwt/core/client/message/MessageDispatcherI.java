/**
 * All right is from Author of the file,to be explained in comming days.
 * Dec 23, 2012
 */
package org.cellang.clwt.core.client.message;

import org.cellang.clwt.core.client.lang.Path;

/**
 * @author wu
 * 
 */
public interface MessageDispatcherI extends MessageHandlerI<MsgWrapper> {

	public <W extends MsgWrapper> void addHandler(Path path,
			MessageHandlerI<W> mh);

	public <W extends MsgWrapper> void addHandler(Path path, boolean strict,
			MessageHandlerI<W> mh);

	public <W extends MsgWrapper> void addDefaultHandler(MessageHandlerI<W> mh);

	public void addExceptionHandler(MessageExceptionHandlerI eh);

	public void dispatch(MsgWrapper mw);

	public void cleanAllHanlders();
}
