/**
 *  Jan 31, 2013
 */
package org.cellang.clwt.commons.client.frwk;

import org.cellang.clwt.commons.client.mvc.ViewI;
import org.cellang.clwt.core.client.event.Event.EventHandlerI;
import org.cellang.clwt.core.client.lang.Path;

/**
 * @author wuzhen
 * 
 */
public interface HeaderViewI extends ViewI {

	public void addItem(Path path, EventHandlerI<HeaderItemEvent> hdl);

	public void addItem(Path path, boolean left, EventHandlerI<HeaderItemEvent> hdl);

	public void addItemIfNotExist(Path path);

	public void setItemDisplayText(Path path, boolean toloc, String text);

	public void tryRemoveItem(Path path);
	
	public void _clickItem(Path path);

}
