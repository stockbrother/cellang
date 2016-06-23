/**
 * Jul 1, 2012
 */
package org.cellang.clwt.core.client;

import org.cellang.clwt.core.client.event.EventBus;
import org.cellang.clwt.core.client.event.EventBusImpl;
import org.cellang.clwt.core.client.widget.WebWidgetFactory;

/**
 * @author wu
 * 
 */
public class DefaultContainer implements Container {

	protected EventBus eventBus;

	protected ClientObject client;

	protected Scheduler scheduler;

	protected WebWidgetFactory widgetFactory;

	public DefaultContainer() {
		this.eventBus = new EventBusImpl(this);
	}

	@Override
	public EventBus getEventBus() {
		return this.eventBus;
	}

	@Override
	public ClientObject getClient(boolean force) {
		return this.client;
	}

	@Override
	public Scheduler getScheduler(boolean force) {
		return this.scheduler;
	}

	@Override
	public WebWidgetFactory getWidgetFactory(boolean force) {
		return this.widgetFactory;
	}

	@Override
	public void setScheduler(Scheduler sc) {
		this.scheduler = sc;
	}

	@Override
	public void setWidgetFactory(WebWidgetFactory wwf) {
		this.widgetFactory = wwf;
	}

	@Override
	public void setClient(ClientObject co) {
		this.client = co;
	}

}
