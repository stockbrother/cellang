/**
 * Jul 1, 2012
 */
package org.cellang.clwt.core.client;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
	
	protected Map<Class,List> getList_cache = new HashMap<Class,List>();
	
	protected EventBus eventBus;
	public DefaultContainer() {
		this.eventBus = new EventBusImpl(this);
	}

	/* */
	@Override
	public <T> List<T> getList(Class<T> cls) {
		
		List<T> rt = getList_cache.get(cls);
		
		if(rt != null){
			return rt;
		}
		rt = new ArrayList<T>();
		for (Object o : objects) {
			if (InstanceOf.isInstance(cls, o)) {
				rt.add((T) o);
			}
		}
		getList_cache.put(cls, rt);
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
		this.getList_cache.clear();//
		if (obj instanceof ContainerAware) {
			((ContainerAware) obj).setContainer(this);
		}
		
	}

	@Override
	public EventBus getEventBus() {
		return this.eventBus;
	}

}
