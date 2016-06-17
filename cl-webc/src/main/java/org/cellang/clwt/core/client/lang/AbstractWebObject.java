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

import org.cellang.clwt.core.client.Container;
import org.cellang.clwt.core.client.ContainerAware;
import org.cellang.clwt.core.client.Scheduler;
import org.cellang.clwt.core.client.ClientObject;
import org.cellang.clwt.core.client.UiException;
import org.cellang.clwt.core.client.event.AttachedEvent;
import org.cellang.clwt.core.client.event.Event;
import org.cellang.clwt.core.client.event.EventBus;
import org.cellang.clwt.core.client.event.Event.EventHandlerI;
import org.cellang.clwt.core.client.event.Event.Type;
import org.cellang.clwt.core.client.logger.WebLogger;
import org.cellang.clwt.core.client.logger.WebLoggerFactory;
import org.cellang.clwt.core.client.message.MsgWrapper;
import org.cellang.clwt.core.client.message.MessageHandlerI;
import org.cellang.clwt.core.client.util.OID;

/**
 * @author wuzhen
 * 
 */
public class AbstractWebObject extends AbstractHasProperties<Object>implements WebObject {

	private static final WebLogger LOG = WebLoggerFactory.getLogger(AbstractWebObject.class);

	protected Map<String, LazyI> lazyMap;

	protected WebObject parent;

	protected Set<String> marks = new HashSet<String>();

	protected List<WebObject> childList;

	protected List<Object> attacherList;// other any object attach to this
										// object

	protected boolean isInAddChild = false;

	protected boolean attached;

	protected PathBasedDispatcher eventDispatcher;

	protected String name;

	protected WebLogger logger;

	protected String id;

	protected Container container;

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
		this.childList = new ArrayList<WebObject>();
		this.attacherList = new ArrayList<Object>();
		this.logger = log != null ? log : WebLoggerFactory.getLogger(this.getClass());//

		this.eventDispatcher = new DispatcherImpl(name+"-dispatcher");//
		this.lazyMap = new HashMap<String, LazyI>();
		if (pts != null) {
			this.setProperties(pts);
		}
	}

	protected Scheduler getScheduler() {
		return this.container.get(Scheduler.class, true);
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
	public boolean contains(WebObject c) {
		return this.contains(c, false);
	}

	@Override
	public boolean contains(WebObject c, boolean offspring) {
		WebObject p = c.getParent();
		while (p != null) {
			if (p == this) {
				return true;
			}

			if (!offspring) {
				break;
			}
			p = p.getParent();
		}

		return false;
	}

	@Override
	public WebObject parent(WebObject newParent) {
		
		if (newParent != null && !(newParent instanceof AbstractWebObject)) {
			throw new UiException("parent type not supported,parent:"+newParent);
		}

		if (this.parent != null) {
			this.parent.removeChild(this);
			if (this.parent.isAttached()) {// detach?
				this.detach();//
			}
			if (this instanceof ContainerAware) {
				Container c = newParent.getContainer();
				((ContainerAware) this).setContainer(null);// ?
			}
			this.parent = null;

		}
		// new parent
		if (newParent != null) {
			if (newParent.contains(this)) {
				throw new UiException("already parent:" + newParent + ",child:" + this.toDebugString());
			}

			if (this instanceof ContainerAware) {
				Container c = newParent.getContainer();
				((ContainerAware) this).setContainer(c);//
			}
			((AbstractWebObject) newParent).addChild(this);
			this.parent = newParent;
			if (newParent.isAttached()) {
				this.attach();//
			}
		} else {
			this.parent = newParent;
		}

		return this;
	}

	public void addChild(WebObject c) {
		this.childList.add(c);
	}

	public void removeChild(WebObject c) {
		this.childList.remove(c);
	}

	@Override
	public <T extends WebObject> T getChild(Class<T> cls, String name, boolean force) {
		List<T> rt = this.getChildList(cls, name);

		if (rt.isEmpty()) {
			if (force) {
				throw new UiException("force:" + cls + "/" + name + " in " + this.toDebugString());
			}
			return null;
		} else if (rt.size() > 1) {
			throw new UiException(
					"too many,there are " + rt.size() + " " + cls + "/" + name + " in " + this.toDebugString());

		}
		return rt.get(0);

	}

	@Override
	public <T extends WebObject> T getChild(Class<T> cls, boolean force) {
		return this.getChild(cls, null, force);
	}

	@Override
	public <T extends WebObject> List<T> getChildList(Class<T> cls) {
		return getChildList(cls, null);
	}

	public <T extends WebObject> List<T> getChildList(Class<T> cls, String name) {

		List<T> rt = new ArrayList<T>();
		for (WebObject oi : this.childList) {
			if ((cls == null || InstanceOf.isInstance(cls, oi)) && (name == null || name.equals(oi.getName()))) {
				rt.add((T) oi);
			}
		}
		return rt;
	}

	public List<WebObject> getCopiedChildList() {
		return new ArrayList<WebObject>(this.childList);
	}

	@Override
	public void clean() {
		List<WebObject> cL = this.copyChildList();
		for (WebObject c : cL) {
			c.parent(null);
		}

	}

	@Override
	public <T extends WebObject> void clean(Class<T> cls) {
		List<T> cL = this.getChildList(cls);
		for (WebObject c : cL) {
			c.parent(null);
		}
	}

	protected List<WebObject> copyChildList() {
		List<WebObject> rt = new ArrayList<WebObject>();
		rt.addAll(this.childList);

		return rt;
	}

	@Override
	public ClientObject getClient(boolean force) {

		return this.container.get(ClientObject.class, force);

	}

	protected <T extends WebObject> T getTopObject(Class<T> cls) {
		return (T) this.getTopObject();
	}

	protected <T extends WebObject> T findParent(Class<T> cls, boolean force) {
		WebObject pre = this;

		WebObject next = pre.getParent();
		while (next != null) {
			if (InstanceOf.isInstance(cls, next)) {//
				return (T) next;
			}
			pre = next;
			next = next.getParent();
		}
		if (force) {
			throw new UiException("force:" + cls);
		}
		return null;

	}

	@Override
	public WebObject getTopObject() {
		WebObject pre = this;

		WebObject next = pre.getParent();
		while (next != null) {
			pre = next;
			next = next.getParent();
		}
		return pre;
	}

	@Override
	public WebObject getParent() {
		return parent;
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
	public <E extends Event> void dispatch(E evt) {
		if(LOG.isTraceEnabled()){
			LOG.trace("dispatch event:" + evt);//
		}
		this.eventDispatcher.dispatch(evt.getPath(), evt);

		if (evt.isGlobal()) {
			EventBus eb = this.getEventBus(false);
			if (eb == null) {
				if(LOG.isTraceEnabled()){
					LOG.trace("event bus is null");
				}
				return;
			} else if (this == eb) {
				if(LOG.isTraceEnabled()){
					LOG.trace("event bus already dispatched this event.");
				}	
				return;
			}
			if(LOG.isTraceEnabled()){
				LOG.trace("dispatch global event:" + evt + " to event bus.");//				
			}
			eb.dispatch(evt);
		}
	}

	/* */
	@Override
	public <T extends WebObject> T find(Class<T> cls, String name, boolean force) {
		List<T> rt = this.findList(cls, name);

		if (rt.isEmpty()) {
			if (force) {
				throw new UiException("force:" + cls + "/" + name + " in:" + this.toDebugString());
			}
			return null;
		} else if (rt.size() > 1) {
			throw new UiException("too many:" + cls + "/" + name + " in:" + this.toDebugString());

		}
		return rt.get(0);

	}

	/* */
	@Override
	public <T extends WebObject> List<T> findList(Class<T> cls, String name) {
		List<T> rt = new ArrayList<T>();
		List<T> l = this.getChildList(cls, name);
		rt.addAll(l);
		for (WebObject c : this.childList) {
			List<T> cL = c.findList(cls, name);
			rt.addAll(cL);
		}

		return rt;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see core.UiObjectI#find(java.lang.Class, java.lang.String, boolean)
	 */
	@Override
	public <T extends WebObject> T find(Class<T> cls, boolean force) {

		return find(cls, (String) null, force);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see core.UiObjectI#findList(java.lang.Class, java.lang.String)
	 */
	@Override
	public <T extends WebObject> List<T> findList(Class<T> cls) {
		// TODO Auto-generated method stub
		return findList(cls, null);
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

	/* */
	@Override
	public boolean isAttached() {

		return this.attached;

	}

	@Override
	public void attach() {
		if (!this.attached) {

			this.doAttach();
			this.attached = true;// attached.
			new AttachedEvent(this).dispatch();// TODO sync event?

			//
			List<Attacher> aL = this.getAttacherList(Attacher.class);
			for (Attacher a : aL) {
				a.ownerAttached();
			}

		}
		//
		//
		List<WebObject> copied = new ArrayList<WebObject>(this.childList);
		// NOTE when calling the child attach(),there may create new child for
		// this object and the child list will changed.
		// and the new added child will regard the parent isAttached and the new
		// add child is attached too.
		for (WebObject c : copied) {
			c.attach();
		}
	}

	protected void doAttach() {
		// this.eventHandlers.onOwnerAttach();
	}

	protected void doDetach() {
		// this.eventHandlers.onOwnerDettach();
	}

	@Override
	public void detach() {
		if (this.attached) {
			this.doDetach();
			this.attached = false;

			//
			List<Attacher> aL = this.getAttacherList(Attacher.class);
			for (Attacher a : aL) {
				a.ownerAttached();
			}

		}

		for (WebObject c : this.childList) {
			c.detach();
		}
	}

	@Override
	public String dump() {
		return this.dump(0, this);
	}

	public String dump(int depth, WebObject o) {
		String line = "";
		for (int i = 0; i < depth; i++) {
			line += " ";
		}
		line += ">" + o.getClass().getName() + "/" + o.getName();
		String rt = line + "\n";//
		List<WebObject> cL = o.getChildList(WebObject.class);
		for (WebObject c : cL) {
			rt += this.dump(depth + 1, c);

		}
		return rt;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see core.UiObjectI#getEventBus()
	 */
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
	public void assertAttached() {
		if (this.attached) {
			return;
		}
		throw new UiException("not attached:" + this.toDebugString());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see core.UiObjectI#getParentList()
	 */
	@Override
	public List<WebObject> getParentList() {
		List<WebObject> rt = new ArrayList<WebObject>();
		WebObject p = this.parent;
		while (p != null) {
			rt.add(p);//
			p = p.getParent();
		}
		return rt;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see core.UiObjectI#getPath()
	 */
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see core.UiObjectI#child(com.fs.uicore.api.gwt .client.core.UiObjectI)
	 */
	@Override
	public WebObject child(WebObject c) {
		c.parent(this);
		return this;
	}

	/*
	 * Nov 8, 2012
	 */
	@Override
	public String toString() {
		//
		return "class:" + this.getClass().getName() + ",path:" + this.getPath() + "";
	}

	@Override
	public String toDebugString() {
		return this.dump();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see core.UiObjectI#attacher(java.lang.Object)
	 */
	@Override
	public WebObject attacher(Object obj) {
		this.attacherList.add(obj);
		if (obj instanceof Attacher) {
			((Attacher) obj).owner(this);//
		}
		return this;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see core.UiObjectI#getAttacher(java.lang.Class, boolean)
	 */
	@Override
	public <T> T getAttacher(Class<T> cls, boolean force) {
		List<T> l = this.getAttacherList(cls);
		if (l.isEmpty()) {
			if (force) {
				throw new UiException("no attacher:" + cls + " in uio:" + this.toDebugString());
			}
			return null;
		} else if (l.size() == 1) {
			return l.get(0);
		} else {
			throw new UiException("to many attacher:" + cls + " in uio:" + this.toDebugString());

		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see core.UiObjectI#getAttacherList(java.lang .Class)
	 */
	@Override
	public <T> List<T> getAttacherList(Class<T> cls) {
		List<T> rt = new ArrayList<T>();
		for (Object o : this.attacherList) {
			if (InstanceOf.isInstance(cls, o)) {
				rt.add((T) o);
			}
		}
		return rt;
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

	/*
	 * Nov 24, 2012
	 */
	@Override
	public <T> T getChildById(String id, boolean force) {
		//
		for (WebObject c : this.childList) {
			if (id.equals(c.getId())) {
				return (T) c;
			}
		}
		if (force) {
			throw new UiException("no id:" + id + " in:" + this.toDebugString());
		}
		return null;
	}

	/*
	 * Nov 24, 2012
	 */
	@Override
	public <T> T findById(String id, boolean force) {
		//
		for (WebObject c : this.childList) {

			if (id.equals(c.getId())) {
				return (T) c;
			}
			WebObject o = c.findById(id, false);
			if (o != null) {
				return (T) o;
			}
		}

		if (force) {
			throw new UiException("not found id:" + id + " in:" + this.toDebugString());
		}

		return null;
	}

	/*
	 * Jan 13, 2013
	 */
	@Override
	public <T extends WebObject> T find(final Callback<WebObject, T> cb) {
		//
		final Holder<T> rtH = new Holder<T>();
		this.forEach(new Callback<WebObject, Boolean>() {

			@Override
			public Boolean execute(WebObject t) {
				//
				T rt = cb.execute(t);
				if (rt != null) {
					rtH.setTarget(rt);
					return true;
				}
				return null;
			}
		});
		return rtH.getTarget();
	}

	/*
	 * Jan 13, 2013
	 */
	@Override
	public void forEach(Callback<WebObject, Boolean> cb) {
		Holder<Boolean> bh = new Holder<Boolean>(false);
		AbstractWebObject.forEach(this, cb, bh);

	}

	private static void forEach(WebObject obj, Callback<WebObject, Boolean> cb, Holder<Boolean> bh) {
		//
		List<WebObject> cl = obj.getChildList(WebObject.class);
		for (WebObject uo : cl) {

			Boolean stop = cb.execute(uo);
			if (stop != null && stop) {
				bh.setTarget(true);
				return;
			}
		}
		for (WebObject uo : cl) {
			AbstractWebObject.forEach(uo, cb, bh);
			Boolean stop = bh.getTarget();
			if (stop != null && stop) {
				return;
			}

		}
	}

}
