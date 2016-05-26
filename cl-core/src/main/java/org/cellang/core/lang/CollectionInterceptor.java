/**
 *  Dec 17, 2012
 */
package org.cellang.core.lang;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wuzhen
 * 
 */
public class CollectionInterceptor implements InterceptorI {

	private List<InterceptorI> interceptors = new ArrayList<InterceptorI>();;

	public void addInterceptor(InterceptorI i) {
		this.interceptors.add(i);
	}

}
