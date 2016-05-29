/**
 *  Dec 27, 2012
 */
package org.cellang.clwt.commons.client.widget;

import org.cellang.clwt.core.client.event.Event.EventHandlerI;

/**
 * @author wuzhen
 * 
 */
public interface SelectableI {

	public boolean isSelected();

	public void select();

	public void addSelectEventHandler(EventHandlerI<SelectEvent> eh);
}
