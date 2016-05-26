/**
 * All right is from Author of the file,to be explained in comming days.
 * May 10, 2013
 */
package org.cellang.core.commons.session;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.cellang.core.commons.lang.Handler;
import org.cellang.core.lang.CollectionHandler;

/**
 * @author wu
 * 
 */
public class SessionManagerImpl implements SessionManager {

	private String name;

	private Map<String, Session> sessions = Collections.synchronizedMap(new HashMap<String, Session>());

	private CollectionHandler<Session> timeoutHandlers = new CollectionHandler<Session>();

	private SessionServerImpl server;

	public SessionManagerImpl(String name){
		this.name = name;
	}
	/*
	 * May 10, 2013
	 */
	@Override
	public String getName() {
		//
		return name;
	}

	/*
	 * May 10, 2013
	 */
	@Override
	public Session getSession(String id) {
		//
		Session s = this.sessions.get(id);
		if (s != null && s.isTimeout()) {
			this.doTimeout(s);
			s = null;
		}
		return s;

	}

	/*
	 * May 10, 2013
	 */
	@Override
	public Session touchSession(String id) {
		Session rt = this.getSession(id);
		if (rt == null) {
			return null;
		}
		rt.touch();
		return rt;
	}

	/*
	 * May 10, 2013
	 */
	@Override
	public Session createSession(long timeout) {
		//
		return this.createSession(UUID.randomUUID().toString(), timeout);
	}

	/*
	 * May 10, 2013
	 */
	@Override
	public Session createSession(String id, long timeout) {
		Session rt = new SessionImpl(this.getName(), id, timeout);
		this.sessions.put(rt.getId(), rt);
		return rt;
	}

	/*
	 * May 10, 2013
	 */
	@Override
	public void addCreatedHandler(Handler<Session> hdl) {
		//
		throw new RuntimeException("todo");
	}

	/*
	 * May 10, 2013
	 */
	@Override
	public void addTimeoutHandler(Handler<Session> hdl) {
		//
		this.timeoutHandlers.addHandler(hdl);
	}

	public void checkTimeout() {
		List<Session> sL = new ArrayList<Session>();// copy

		for (Session s : this.sessions.values()) {
			if (s.isTimeout()) {
				sL.add(s);
			}
		}
		//
		for (Session s : sL) {
			this.doTimeout(s);
		}

	}

	private void doTimeout(Session s) {
		this.sessions.remove(s.getId());
		this.timeoutHandlers.handle(s);
	}

	/*
	 * May 10, 2013
	 */
	@Override
	public void addTouchedHandler(Handler<Session> hdl) {
		//
		throw new RuntimeException("todo");
	}

}
