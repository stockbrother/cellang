/**
 *  Jan 31, 2013
 */
package org.cellang.clwt.commons.client;

import org.cellang.clwt.core.client.Container;

/**
 * @author wuzhen
 * 
 */
public interface CreaterI<T> {

	public T create(Container ct);

}
