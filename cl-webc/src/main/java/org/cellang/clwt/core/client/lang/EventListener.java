/**
 * Jun 13, 2012
 */
package org.cellang.clwt.core.client.lang;

import org.cellang.clwt.core.client.event.Event;

/**
 * @author wuzhen
 * 
 */
public interface EventListener<E extends Event> {

	public void onEvent(E e);

}
