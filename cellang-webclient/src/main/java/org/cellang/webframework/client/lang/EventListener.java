/**
 * Jun 13, 2012
 */
package org.cellang.webframework.client.lang;

import org.cellang.webframework.client.event.Event;

/**
 * @author wuzhen
 * 
 */
public interface EventListener<E extends Event> {

	public void onEvent(E e);

}
