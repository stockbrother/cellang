/**
 * Jun 16, 2012
 */
package org.cellang.core.lang;

/**
 * @author wu
 * 
 */
public interface ServerI {
	public static final State UNKNOW = new State("unknown");

	public static final State RUNNING = new State("running");

	public static final State STARTING = new State("starting");

	public static final State SHUTINGDOWN = new State("shutingdown");

	public static final State SHUTDOWN = new State("shutdown");

	public State getState();

	public boolean isState(State... s);

	public void start();

	public void start(boolean strict);

	public void shutdown();

	public void shutdown(boolean strict);

}
