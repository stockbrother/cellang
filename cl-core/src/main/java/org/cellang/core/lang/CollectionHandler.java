/**
 *  Dec 28, 2012
 */
package org.cellang.core.lang;

import java.util.ArrayList;
import java.util.List;

import org.cellang.commons.lang.Handler;

/**
 * @author wuzhen
 * 
 */
public class CollectionHandler<T> implements Handler<T> {

	private List<Handler<T>> handlers;

	public CollectionHandler() {
		this.handlers = new ArrayList<Handler<T>>();
	}

	public int size() {
		return this.handlers.size();
	}

	public void addHandler(Handler<T> h) {
		this.handlers.add(h);
	}

	@Override
	public void handle(T sc) {
		for (Handler<T> h : this.handlers) {
			h.handle(sc);
		}
	}

}
