/**
 * All right is from Author of the file,to be explained in comming days.
 * Sep 23, 2012
 */
package org.cellang.clwt.core.client;

import org.cellang.clwt.core.client.event.StateChangeEvent;
import org.cellang.clwt.core.client.lang.AbstractWebObject;
import org.cellang.clwt.core.client.lang.HasState;
import org.cellang.clwt.core.client.lang.State;

/**
 * @author wu
 * 
 */
public class HasStateWebObject extends AbstractWebObject implements HasState {

	/**
	 * @param name
	 */
	public HasStateWebObject(Container c) {
		this(c, null);
	}

	public HasStateWebObject(Container c, String name) {
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
	 * state.StatefulI#isState(com.fs.uicore.api
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
