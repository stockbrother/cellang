/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 3, 2012
 */
package org.cellang.clwt.commons.client.widget;

import org.cellang.clwt.core.client.widget.WebWidget;

/**
 * @author wu
 * 
 */
public interface TabWI extends WebWidget, SelectableI {

	public String getName();
	
	public void setText(boolean i18n, String text);

	public PanelWI getPanel();
	
	public WebWidget getManaged();

	public void close();

}
