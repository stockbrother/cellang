/**
 * All right is from Author of the file,to be explained in comming days.
 * Nov 17, 2012
 */
package org.cellang.clwt.core.client.gwtbridge;

import org.cellang.clwt.core.client.logger.WebLogger;
import org.cellang.clwt.core.client.logger.WebLoggerFactory;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;

/**
 * @author wu
 * 
 */
public abstract class AbstractGwtHandler<E extends GwtEvent<H>, H extends EventHandler> 
		implements EventHandler,GwtHandlerI<E,H> {

	protected WebLogger LOG = WebLoggerFactory
			.getLogger(AbstractGwtHandler.class);

	protected void handle(E evt) {
		try {
			this.handleInternal(evt);
		} catch (Throwable t) {
			LOG.error("", t);
		}
	}

	public H cast() {
		return (H) this;
	}

	protected abstract void handleInternal(E evt);

}
