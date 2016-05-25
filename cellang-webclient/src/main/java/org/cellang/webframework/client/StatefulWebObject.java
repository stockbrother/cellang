/**
 * All right is from Author of the file,to be explained in comming days.
 * Sep 23, 2012
 */
package org.cellang.webframework.client;

import org.cellang.webframework.client.event.StateChangeEvent;
import org.cellang.webframework.client.lang.AbstractWebObject;
import org.cellang.webframework.client.lang.State;
import org.cellang.webframework.client.lang.StatefulI;

/**
 * @author wu
 * 
 */
public class StatefulWebObject extends AbstractWebObject implements StatefulI {

	/**
	 * @param name
	 */
	public StatefulWebObject(Container c) {
		this(c, null);
	}

	public StatefulWebObject(Container c, String name) {
		super(c, name);

	}

	protected State state;

	@Override
	public State getState() {
		return this.state;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.fs.uicore.api.gwt.client.state.StatefulI#isState(com.fs.uicore.api
	 * .gwt.client.state.State)
	 */
	@Override
	public boolean isState(State s) {
		return s.equals(this.state);
	}

	protected void setState(State news) {
		State old = this.state;
		this.state = news;
		
		new StateChangeEvent(this).dispatch();
	}

}
