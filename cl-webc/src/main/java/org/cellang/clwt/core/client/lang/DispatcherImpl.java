/**
 * All right is from Author of the file,to be explained in comming days.
 * Dec 23, 2012
 */
package org.cellang.clwt.core.client.lang;

import java.util.ArrayList;
import java.util.List;

import org.cellang.clwt.core.client.logger.WebLogger;
import org.cellang.clwt.core.client.logger.WebLoggerFactory;

/**
 * @author wu
 * 
 */
public class DispatcherImpl implements PathBasedDispatcher {

	private static final WebLogger logger = WebLoggerFactory.getLogger(DispatcherImpl.class);//

	protected List<HandlerEntry> handlers;

	protected CollectionHandler defaultHandlers;

	protected CollectionHandler<DispatchingException> exceptionHandlers;

	protected String name;

	public DispatcherImpl(String name) {
		this.name = name;
		this.exceptionHandlers = new CollectionHandler<DispatchingException>();
		this.handlers = new ArrayList<HandlerEntry>();
		this.defaultHandlers = new CollectionHandler<Object>();

	}

	@Override
	public void dispatch(Path path, Object msg) {
		try {
			this.doDispatch(path, msg);
		} catch (Throwable t) {

			if (this.exceptionHandlers.size() == 0) {
				logger.error("exception got when dispatch message:" + msg, t);
			} else {
				try {

					this.exceptionHandlers.handle(new DispatchingException(t, msg));

				} catch (Throwable e2) {
					logger.error("exception's exception", e2);
				} finally {

				}
			}
		}
	}

	protected void doDispatch(Path p, Object t) {
		// logger.debug("dispatcher:" + ",handle msg:" + t);

		List<HandlerEntry> hls = new ArrayList<HandlerEntry>(this.handlers);
		int matches = 0;
		for (HandlerEntry he : hls) {
			boolean match = he.tryHandle(true, p, t);
			if (match) {
				matches++;
			}
		}

		if (matches == 0) {

			this.defaultHandlers.handle(t);
			if (this.defaultHandlers.size() == 0) {
				logger.debug("path:" + p + " with msg:" + t + " has no handler match it in dispatcher:" + this.name
						+ ",all handlers:" + hls);
			}
		}

	}

	/*
	 * Dec 23, 2012
	 */
	@Override
	public <W extends Object> void addHandler(Path path, Handler<W> mh) {
		this.addHandler(path, false, mh);
	}

	@Override
	public void addHandler(Path path, boolean strict, Handler mh) {

		HandlerEntry he = new HandlerEntry(this, path, strict, mh);

		this.handlers.add(he);

	}

	/*
	 * Dec 23, 2012
	 */
	@Override
	public void addDefaultHandler(Handler mh) {
		this.defaultHandlers.addHandler(mh);
	}

	@Override
	public void addExceptionHandler(DispatchListener eh) {
		this.exceptionHandlers.addHandler(eh);
	}

	/*
	 * May 12, 2013
	 */
	@Override
	public void cleanAllHanlders() {
		this.exceptionHandlers.cleanAll();
	}

}
