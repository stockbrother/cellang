/**
 * Jun 13, 2012
 */
package org.cellang.clwt.core.client.event;

import org.cellang.clwt.core.client.UiException;
import org.cellang.clwt.core.client.data.MessageData;
import org.cellang.clwt.core.client.lang.Path;
import org.cellang.clwt.core.client.lang.SynchronizedI;
import org.cellang.clwt.core.client.lang.UiType;
import org.cellang.clwt.core.client.lang.WebObject;
import org.cellang.clwt.core.client.message.MsgWrapper;
import org.cellang.clwt.core.client.message.MessageHandlerI;

/**
 * @author wuzhen <br>
 *         Event is Message, it is dispatch-able by message dispatcher.
 */
public class Event extends MsgWrapper {

	public static class Type<E extends Event> extends UiType<E> {

		private String name;

		public Type(String name) {
			this(null, name);
		}

		public Type(Type<? extends Event> p, String name) {
			super(p);
			this.name = name;
		}

		public String getName() {
			return name;
		}

		public Path getAsPath() {
			Type<?> pt = (Type<?>) this.getParent();
			if (pt == null) {
				return Path.valueOf(this.name);
			}
			return pt.getAsPath().getSubPath(this.name);
		}
	}
//
//	@Deprecated
//	public static interface FilterI {
//
//		public <T extends Event> T filter(Event e);
//
//	}

	public static interface SyncHandlerI<E extends Event> extends EventHandlerI<E>, SynchronizedI {

	}

	public static interface AsyncHandlerI<E extends Event> extends EventHandlerI<E> {

	}

	public static interface EventHandlerI<E extends Event> extends MessageHandlerI<E> {

	}

	protected WebObject source;

	protected boolean isGlobal = true;

	public Event(Type<? extends Event> type) {
		this(type, null);
	}

	public Event(Type<? extends Event> type, WebObject src) {
		this(type, src, type.getAsPath());
	}

	public Event(Type<? extends Event> type, WebObject src, Path path) {
		this(type, src, new MessageData(path));
	}

	private Event(Type<? extends Event> type, WebObject src, MessageData msg) {
		this(type.getAsPath(), src, msg);
	}

	private Event(Path path, WebObject src, MessageData msg) {
		super(msg);
		Path mpath = msg.getPath();
		if (!path.isSubPath(mpath, true)) {
			throw new UiException("event type path:" + path + " is not the super type of message path:" + mpath);
		}
		this.source = src;

	}

	public boolean isGlobal() {
		return this.isGlobal;
	}

	/**
	 * @return the source
	 */
	public WebObject getSource() {
		return source;
	}

	public <E extends Event> E source(WebObject s) {
		this.source = s;

		return (E) this;
	}

	public <T extends WebObject> T getSource(Class<T> cls) {
		return (T) this.source;
	}

	public <T extends Event> T property(String name, Object value) {
		this.setProperty(name, value);
		return (T) this;
	}

	public <E extends Event> E dispatch() {
		this.source.dispatch(this);
		return (E) this;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Event,class:" + this.getClass() + "," + super.toString() + ",source:" + this.source + ",isClobal:"
				+ this.isGlobal;
	}

}
