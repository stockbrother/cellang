/**
 * All right is from Author of the file,to be explained in comming days.
 * Dec 11, 2012
 */
package org.cellang.core.commons.session;

import org.cellang.core.lang.ServerI;
import org.cellang.core.lang.State;

/**
 * @author wu
 * 
 */
public abstract class ServerSupport implements ServerI {

	protected State statue = ServerI.UNKNOW;

	public void start() {
		this.start(false);
	}

	/*
	 * Dec 11, 2012
	 */
	@Override
	public void start(boolean strict) {
		if (this.isState(ServerI.RUNNING)) {

			if (strict) {
				throw new RuntimeException("already started");
			} else {
				return;
			}
		}

		if (this.isState(ServerI.STARTING)) {
			if (strict) {
				throw new RuntimeException("already starting");
			} else {
				return;
			}

		}

		this.setState(STARTING);
		try {
			this.doStart();
			this.setState(RUNNING);
		} finally {
		}
	}

	protected void setState(State s) {
		this.statue = s;
	}

	protected abstract void doStart();

	@Override
	public void shutdown() {

		this.shutdown(false);
	}

	/*
	 * Dec 11, 2012
	 */
	@Override
	public void shutdown(boolean strict) {
		if (!this.isState(ServerI.RUNNING)) {
			if (strict) {
				throw new RuntimeException("not running");
			} else {
				return;
			}
		}

		this.setState(SHUTINGDOWN);
		try {

			this.doShutdown();
			this.setState(ServerI.SHUTDOWN);
		} finally {

		}

	}

	protected abstract void doShutdown();

	/*
	 * Dec 17, 2012
	 */
	@Override
	public State getState() {
		//
		return this.statue;
	}

	/*
	 * Dec 17, 2012
	 */
	@Override
	public boolean isState(State... ss) {
		//
		for (State s : ss) {
			if (this.statue.equals(s)) {
				return true;
			}
		}
		return false;
	}

}
