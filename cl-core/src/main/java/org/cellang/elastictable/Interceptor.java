/**
 *  Nov 28, 2012
 */
package org.cellang.elastictable;

/**
 * @author wuzhen
 * 
 */
public interface Interceptor<T> {

	public void intercept(T opt);

}