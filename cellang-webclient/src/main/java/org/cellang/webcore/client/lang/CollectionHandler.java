/**
 * All right is from Author of the file,to be explained in comming days.
 * Dec 23, 2012
 */
package org.cellang.webcore.client.lang;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wu
 * 
 */
public class CollectionHandler<T> implements Handler<T> {

	protected List<Handler> handlers = new ArrayList<Handler>();

	public void addHandler(Handler<? extends T> h) {
		this.handlers.add(h);
	}

	public int size() {
		return this.handlers.size();
	}
	
	public int cleanAll(){
		int rt = this.handlers.size();
		this.handlers.clear();
		return rt;
	}

	/*
	 * Dec 23, 2012
	 */
	@Override
	public void handle(T t) {
		for (Handler h : this.handlers) {
			h.handle(t);
		}
	}

}
