/**
 *  Jan 31, 2013
 */
package org.cellang.clwt.commons.client.frwk;

import org.cellang.clwt.commons.client.mvc.ViewI;
import org.cellang.clwt.core.client.lang.Path;
import org.cellang.clwt.core.client.widget.WebWidget;

/**
 * @author wuzhen
 * 
 */
public interface BodyViewI extends ViewI {

	public <T extends WebWidget> T getItem(Path path, boolean force);

	public <T extends WebWidget> T addItem(Path path, T w);
	
	public void select(Path path);
	
	public void tryCloseItem(Path path);
	
	//TODO provide new interface.
	public void setTitleOfItem(Path path, String title, boolean force);
	
	public <T extends WebWidget> T getOrCreateItem(Path path, org.cellang.clwt.commons.client.CreaterI<T> crt);

	public <T extends WebWidget> T getOrCreateItem(Path path, org.cellang.clwt.commons.client.CreaterI<T> crt,boolean select);
	
	public void closeAllItems();

}
