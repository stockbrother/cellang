/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 3, 2012
 */
package org.cellang.clwt.commons.client.widget;

import org.cellang.clwt.commons.client.mvc.CompositeI;
import org.cellang.clwt.core.client.widget.WebWidget;

/**
 * @author wu
 *         <p>
 *         Context menu,pop up view.
 */
public interface MenuWI extends CompositeI {

	public MenuItemWI addItem(String name);

	public MenuItemWI addItem(String name, MenuWI subm);

	public MenuItemWI getItem(String name);
	
	public int size();

	public void openBy(WebWidget src);

	public void close();

}
