/**
 * Jun 23, 2012
 */
package org.cellang.clwt.core.client.lang;

/**
 * @author wu
 * 
 */
public class Holder<T> {

	private T target;

	public Holder() {

	}

	public Holder(T t) {
		this.target = t;
	}

	public T getTarget() {
		return this.target;
	}

	public void setTarget(T t) {
		this.target = t;
	}
}
