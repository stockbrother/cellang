/**
 * All right is from Author of the file,to be explained in comming days.
 * Dec 23, 2012
 */
package org.cellang.clwt.core.client.lang;

/**
 * @author wu
 * 
 */
public interface PathBasedDispatcher {

	public <T> void addHandler(Path path, Handler<T> mh);

	public <T> void addHandler(Path path, boolean strict, Handler<T> mh);

	public <T> void addDefaultHandler(Handler<T> mh);

	public void addExceptionHandler(DispatchListener eh);

	/**
	 * Dispatch based on the message's path.
	 * 
	 * @param mw
	 */
	//public void dispatch(MsgWrapper mw);

	/**
	 * Dispatch based on the path specified.
	 * 
	 * @param path
	 * @param mw
	 */
	public void dispatch(Path path, Object mw);

	public void cleanAllHanlders();
}
