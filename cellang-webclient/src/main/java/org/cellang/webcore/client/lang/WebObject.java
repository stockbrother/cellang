/**
 * Jun 13, 2012
 */
package org.cellang.webcore.client.lang;

import java.util.List;
import java.util.Set;

import org.cellang.webcore.client.Container;
import org.cellang.webcore.client.WebClient;
import org.cellang.webcore.client.event.Event;
import org.cellang.webcore.client.event.EventBus;
import org.cellang.webcore.client.event.Event.EventHandlerI;
import org.cellang.webcore.client.event.Event.Type;
import org.cellang.webcore.client.message.MessageDataWrapper;
import org.cellang.webcore.client.message.MessageHandlerI;

/**
 * @author wuzhen
 * 
 */
public interface WebObject extends HasProperties<Object> {
	public WebObject mark(String mark);

	public Set<String> getMarks();

	public boolean hasMark(String mark);

	public WebObject getParent();

	public WebClient getClient(boolean force);

	public WebObject parent(WebObject p);

	public <T> T cast();

	public void clean();

	public <T extends WebObject> void clean(Class<T> cls);

	public WebObject child(WebObject c);

	public boolean contains(WebObject c);

	public boolean contains(WebObject c, boolean offspring);

	public void removeChild(WebObject c);

	public <E extends Event> void addHandler(EventHandlerI<E> l);

	// use EventBusI.addHandler
	public <E extends Event> void addHandler(Event.Type<E> ec, EventHandlerI<E> l);

	public <W extends MessageDataWrapper> void addHandler(Path path, MessageHandlerI<W> mh);

	public <E extends Event> void dispatch(E evt);

	public WebObject getTopObject();

	public void setName(String name);

	public String getName();

	public String getId();

	public <T extends WebObject> T getChild(Class<T> cls, String name, boolean force);

	public <T extends WebObject> T getChild(Class<T> cls, boolean force);

	public <T extends WebObject> T find(Class<T> cls, boolean force);

	public <T extends WebObject> T find(Class<T> cls, String name, boolean force);

	public <T extends WebObject> List<T> findList(Class<T> cls);

	public <T extends WebObject> List<T> findList(Class<T> cls, String name);

	public <T extends WebObject> T find(Callback<WebObject, T> cb);

	public void forEach(Callback<WebObject, Boolean> cb);

	public <T extends WebObject> List<T> getChildList(Class<T> cls);

	public String dump();

	public EventBus getEventBus(boolean force);

	public Container getContainer();

	public List<WebObject> getParentList();

	public boolean isAttached();

	public void attach();

	public void assertAttached();

	public void detach();

	public Path getPath();

	public WebObject attacher(Object obj);

	public <T> T getAttacher(Class<T> cls, boolean force);

	public <T> List<T> getAttacherList(Class<T> cls);

	public <T> void addLazy(String name, LazyI<T> lazy);

	public <T> LazyI<T> getLazy(String name, boolean force);

	public <T> T getLazyObject(String name, boolean force);

	public <T> T getChildById(String id, boolean force);

	public <T> T findById(String id, boolean force);

	public String toDebugString();
}
