/**
 * Jul 1, 2012
 */
package org.cellang.clwt.core.client.impl;

import java.util.ArrayList;
import java.util.List;

import org.cellang.clwt.core.client.Container;
import org.cellang.clwt.core.client.ContainerAware;
import org.cellang.clwt.core.client.event.EventBus;
import org.cellang.clwt.core.client.event.EventBusImpl;
import org.cellang.clwt.core.client.lang.InstanceOf;
import org.cellang.clwt.core.client.util.CollectionUtil;

/**
 * @author wu
 * 
 */
public class DefaultContainer implements Container {

	protected List<Object> objects = new ArrayList<Object>();

	protected EventBus eventBus;
	public DefaultContainer() {
		this.eventBus = new EventBusImpl(this);
	}

	/* */
	@Override
	public <T> List<T> getList(Class<T> cls) {
		List<T> rt = new ArrayList<T>();
		for (Object o : objects) {
			if (InstanceOf.isInstance(cls, o)) {
				rt.add((T) o);
			}
		}
		return rt;

	}

	/* */
	@Override
	public <T> T get(Class<T> cls, boolean force) {

		return CollectionUtil.single(this.getList(cls), force);

	}

	/* */
	@Override
	public void add(Object obj) {
		this.objects.add(obj);
		if (obj instanceof ContainerAware) {
			((ContainerAware) obj).setContainer(this);
		}
	}

	@Override
	public EventBus getEventBus() {
		return this.eventBus;
	}

}
