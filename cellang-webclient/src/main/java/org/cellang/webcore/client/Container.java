/**
 * Jul 1, 2012
 */
package org.cellang.webcore.client;

import java.util.List;

import org.cellang.webcore.client.event.EventBus;

/**
 * @author wu
 * 
 */
public interface Container {

	public <T> List<T> getList(Class<T> cls);

	public <T> T get(Class<T> cls, boolean force);

	public void add(Object obj);

	public EventBus getEventBus();
}
