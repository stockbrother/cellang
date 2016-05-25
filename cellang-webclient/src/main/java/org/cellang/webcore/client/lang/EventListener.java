/**
 * Jun 13, 2012
 */
package org.cellang.webcore.client.lang;

import org.cellang.webcore.client.event.Event;

/**
 * @author wuzhen
 * 
 */
public interface EventListener<E extends Event> {

	public void onEvent(E e);

}
