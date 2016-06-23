/**
 * Jun 25, 2012
 */
package org.cellang.clwt.core.client.lang;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.cellang.clwt.core.client.ClientObject;
import org.cellang.clwt.core.client.Container;
import org.cellang.clwt.core.client.ContainerAware;
import org.cellang.clwt.core.client.Scheduler;
import org.cellang.clwt.core.client.UiException;
import org.cellang.clwt.core.client.event.Event;
import org.cellang.clwt.core.client.event.Event.EventHandlerI;
import org.cellang.clwt.core.client.event.Event.Type;
import org.cellang.clwt.core.client.event.EventBus;
import org.cellang.clwt.core.client.logger.WebLogger;
import org.cellang.clwt.core.client.logger.WebLoggerFactory;
import org.cellang.clwt.core.client.message.MessageHandlerI;
import org.cellang.clwt.core.client.message.MsgWrapper;
import org.cellang.clwt.core.client.util.OID;

/**
 * @author wuzhen
 * 
 */
public class AbstractWebObject extends AbstractHasProperties<Object> implements WebObject {

	private static final WebLogger LOG = WebLoggerFactory.getLogger(AbstractWebObject.class);

	protected Map<String, LazyI> lazyMap;

	protected WebObject parent;

	protected Set<String> marks = new HashSet<String>();

	// protected List<WebObject> childList;

	protected List<Object> attacherList;// other any object attach to this
										// object

	protected boolean isInAddChild = false;
//
//	protected boolean attached;

	protected PathBasedDispatcher eventDispatcher;

	protected String name;

	protected WebLogger logger;

	protected String id;

	protected Container container;

	protected Set<Path> globalEventSet;

	protected Map<Class, Map<String, List>> getChildList_cache = new HashMap<Class, Map<String, List>>();

	public AbstractWebObject(Container c) {
		this(c, null);
	}

	public AbstractWebObject(Container c, String name) {
		this.init(c, name, null, null, null);
	}

	public AbstractWebObject(Container c, String name, String id) {
		this.init(c, name, id, null, null);
	}

	public AbstractWebObject(Container c, String name, HasProperties<Object> pts) {
		this.init(c, name, null, null, pts);
	}

	public AbstractWebObject(Container c, String name, String id, WebLogger log) {
		this.init(c, name, id, log, null);
	}

	protected void init(Container c, String name, String id, WebLogger log, HasProperties<Object> pts) {
		this.container = c;
		this.name = name == null ? "unkown" : name;
		this.id = id == null ? OID.next("oid-") : id;
		this.attacherList = new ArrayList<Object>();
		this.logger = log != null ? log : WebLoggerFactory.getLogger(this.getClass());//

		this.eventDispatcher = new DispatcherImpl(name + "-dispatcher");//
		this.lazyMap = new HashMap<String, LazyI>();
		if (pts != null) {
			this.setProperties(pts);
		}
	}

	protected Scheduler getScheduler() {
		return this.container.getScheduler(true);
	}

	/*
	 * Feb 6, 2013
	 */
	@Override
	public boolean equals(Object obj) {

		if (obj == this) {
			return true;
		}
		if (obj == null || !(obj instanceof WebObject)) {
			return false;
		}

		WebObject o2 = (WebObject) obj;
		return this.id.equals(o2.getId());
	}

	@Override
	public <T> T cast() {
		return (T) this;
	}


	@Override
	public ClientObject getClient(boolean force) {
		return this.container.getClient(force);
	}

	/* */
	@Override
	public <E extends Event> void addHandler(Type<E> ec, EventHandlerI<E> l) {
		this.eventDispatcher.addHandler(ec.getAsPath(), l);
	}

	@Override
	public <E extends Event> void addHandler(EventHandlerI<E> l) {
		this.eventDispatcher.addHandler(Path.ROOT, l);
	}

	@Override
	public <W extends MsgWrapper> void addHandler(Path path, MessageHandlerI<W> mh) {
		this.eventDispatcher.addHandler(path, mh);
	}

	@Override
	public <T extends Event> void addGlobalEvent(Event.Type<T> type) {
		if (this.globalEventSet == null) {
			this.globalEventSet = new HashSet<Path>();
		}
		this.globalEventSet.add(type.getAsPath());
	}

	@Override
	public <E extends Event> void dispatch(E evt) {
		if (LOG.isTraceEnabled()) {
			LOG.trace("dispatch event:" + evt);//
		}
		this.eventDispatcher.dispatch(evt.getPath(), evt);

		if (this.globalEventSet == null || (this.globalEventSet.contains(evt.getPath()))) {

			EventBus eb = this.getEventBus(false);
			if (eb == null) {
				if (LOG.isTraceEnabled()) {
					LOG.trace("event bus is null");
				}
				return;
			} else if (this == eb) {
				if (LOG.isTraceEnabled()) {
					LOG.trace("event bus already dispatched this event.");
				}
				return;
			}
			if (LOG.isTraceEnabled()) {
				LOG.trace("dispatch global event:" + evt + " to event bus.");//
			}
			eb.dispatch(evt);
		}
	}

	/* */
	@Override
	public WebObject mark(String mark) {
		this.marks.add(mark);
		return this;

	}

	/* */
	@Override
	public Set<String> getMarks() {

		return this.marks;

	}

	/* */
	@Override
	public boolean hasMark(String mark) {

		return this.marks.contains(mark);

	}

	@Override
	public EventBus getEventBus(boolean force) {
		Container c = this.getContainer();
		if (c == null) {
			if (force) {
				throw new UiException("force,container not found for object:" + this.toDebugString());
			}
			return null;
		}
		return c.getEventBus();//

	}

	@Override
	public Container getContainer() {
		return this.container;
	}

	@Override
	public Path getPath() {
		if (this.parent == null) {
			return Path.valueOf(this.name);
		}
		return Path.valueOf(this.parent.getPath(), this.name);
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		//
		return "class:" + this.getClass().getName() + ",path:" + this.getPath() + "";
	}

	@Override
	public String toDebugString() {
		return "todo";
	}

	@Override
	public <T> LazyI<T> getLazy(String name, boolean force) {
		LazyI rt = this.lazyMap.get(name);

		if (rt == null && force) {
			throw new UiException("no lazy:" + name);
		}

		return rt;

	}

	/*
	 * Nov 23, 2012
	 */
	@Override
	public <T> void addLazy(String name, LazyI<T> lz) {
		if (null != this.getLazy(name, false)) {
			throw new UiException("lazy exist:" + name);
		}
		this.lazyMap.put(name, lz);
	}

	/*
	 * Nov 23, 2012
	 */
	@Override
	public <T> T getLazyObject(String name, boolean force) {
		//
		LazyI<T> lz = this.getLazy(name, force);
		if (lz == null) {
			return null;
		}
		return lz.get();
	}

	/*
	 * Nov 24, 2012
	 */
	@Override
	public String getId() {
		//
		return this.id;
	}

}
