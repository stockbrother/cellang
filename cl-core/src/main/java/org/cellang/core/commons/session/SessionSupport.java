/**
 * All right is from Author of the file,to be explained in comming days.
 * May 10, 2013
 */
package org.cellang.core.commons.session;

import java.util.UUID;

import org.cellang.core.lang.MapProperties;

/**
 * @author wu
 * 
 */
public abstract class SessionSupport extends MapProperties<Object> implements Session {

	protected long created;

	protected long touched;

	protected long timeout;

	protected String group;

	protected String id;

	public SessionSupport(String group, long timeout) {
		this(group, UUID.randomUUID().toString(), timeout);
	}

	public SessionSupport(String group, String id, long timeout) {
		this.group = group;
		this.timeout = timeout;
		this.id = id == null ? UUID.randomUUID().toString() : id;
		this.created = System.currentTimeMillis();
		this.touch();

	}

	@Override
	public String getId() {
		return this.id;
	}

	/*
	 * May 10, 2013
	 */
	@Override
	public boolean isTimeout() {
		//
		long now = System.currentTimeMillis();
		return now > this.touched + this.timeout;
	}

	/*
	 * May 10, 2013
	 */
	@Override
	public long getIdleTimeoutMs() {
		//
		return this.timeout;
	}

	/*
	 * May 10, 2013
	 */
	@Override
	public String getGroup() {
		//
		return this.group;
	}

	/*
	 * May 10, 2013
	 */
	@Override
	public long getCreated() {
		//
		return this.created;
	}

	/*
	 * May 10, 2013
	 */
	@Override
	public long getTouched() {
		//
		return this.touched;
	}

	/*
	 * May 10, 2013
	 */
	@Override
	public void touch() {
		//
		this.touched = System.currentTimeMillis();
	}

}
