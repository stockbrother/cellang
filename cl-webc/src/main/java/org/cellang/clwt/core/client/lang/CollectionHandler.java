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
public class CollectionHandler<T> implements Handler<T> {

	private static final WebLogger LOG = WebLoggerFactory.getLogger(CollectionHandler.class);

	protected List<Handler> handlers = new ArrayList<Handler>();

	public void addHandler(Handler<? extends T> h) {
		this.handlers.add(h);
	}

	public int size() {
		return this.handlers.size();
	}

	public int cleanAll() {
		int rt = this.handlers.size();
		this.handlers.clear();
		return rt;
	}

	/*
	 * Dec 23, 2012
	 */
	@Override
	public void handle(T t) {
		if (LOG.isTraceEnabled()) {
			LOG.trace("child handlers:" + this.handlers);
		}
		for (Handler h : this.handlers) {
			if (LOG.isTraceEnabled()) {
				LOG.trace("handle by child:" + h);
			}

			h.handle(t);
		}
	}

}
