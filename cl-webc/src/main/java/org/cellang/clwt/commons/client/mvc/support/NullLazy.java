/**
 * All right is from Author of the file,to be explained in comming days.
 * Nov 23, 2012
 */
package org.cellang.clwt.commons.client.mvc.support;

import org.cellang.clwt.core.client.lang.LazyI;

/**
 * @author wu
 * 
 */
public class NullLazy<T> implements LazyI<T> {

	/*
	 * Nov 23, 2012
	 */
	@Override
	public T get() {
		//
		return null;
	}

}
