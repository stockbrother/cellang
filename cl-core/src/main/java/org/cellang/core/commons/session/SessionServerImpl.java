/**
 * All right is from Author of the file,to be explained in comming days.
 * May 10, 2013
 */
package org.cellang.core.commons.session;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.cellang.core.lang.ServerI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author wu
 * 
 */
public class SessionServerImpl extends ServerSupport implements SessionServer {

	private static final Logger LOG = LoggerFactory.getLogger(SessionServerImpl.class);

	private Map<String, SessionManagerImpl> managers = new HashMap<String, SessionManagerImpl>();

	private ExecutorService executor;

	/*
	 * May 10, 2013
	 */
	@Override
	protected void doStart() {
		//
		this.executor = Executors.newSingleThreadExecutor();

		this.executor.submit(new Callable() {

			@Override
			public Object call() throws Exception {
				//
				SessionServerImpl.this.run();
				return null;
			}
		});

	}

	/**
	 * May 10, 2013
	 */
	protected void run() {
		try {

			while (this.isState(ServerI.RUNNING, ServerI.STARTING)) {
				try {
					Thread.sleep(60 * 1000);
				} catch (InterruptedException e) {

				}
			}
		} catch (Throwable t) {
			LOG.error("excetion when monitoring the sessions,sessions may leak", t);
		}
	}

	/*
	 * May 10, 2013
	 */
	@Override
	protected void doShutdown() {
		//
		this.executor.shutdown();
		try {
			boolean rt = this.executor.awaitTermination(10, TimeUnit.SECONDS);
			if (!rt) {
				// warning?
			}
		} catch (InterruptedException e) {

		}

	}

	/*
	 * May 10, 2013
	 */
	@Override
	public SessionManager createManager(String name) {
		//
		if (null != this.getManager(name)) {
			throw new RuntimeException("duplicated:" + name);
		}
		SessionManagerImpl rt = new SessionManagerImpl(name);
		this.managers.put(name, rt);
		return rt;
	}

	/*
	 * May 10, 2013
	 */
	@Override
	public SessionManager getManager(String name) {
		//

		return this.managers.get(name);

	}

}
