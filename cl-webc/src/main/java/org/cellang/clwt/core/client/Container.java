/**
 * Jul 1, 2012
 */
package org.cellang.clwt.core.client;

import org.cellang.clwt.core.client.event.EventBus;
import org.cellang.clwt.core.client.widget.WebWidgetFactory;

/**
 * @author wu
 * 
 */
public interface Container {

	//public <T> List<T> getList(Class<T> cls);

	//public <T> T get(Class<T> cls, boolean force);

	//public void add(Object obj);

	public EventBus getEventBus();
	
	public ClientObject getClient(boolean force);

	public Scheduler getScheduler(boolean force);
	
	public WebWidgetFactory getWidgetFactory(boolean force);
	
	public void setScheduler(Scheduler sc);
	
	public void setWidgetFactory(WebWidgetFactory wwf);
	
	public void setClient(ClientObject co);
	
}
