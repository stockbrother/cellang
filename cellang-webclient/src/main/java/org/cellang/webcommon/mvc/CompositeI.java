/**
 * Jun 30, 2012
 */
package org.cellang.webcommon.mvc;

import java.util.List;

import org.cellang.webcore.client.widget.WebWidget;

/**
 * @author wu
 * 
 */
public interface CompositeI extends WebWidget {

	public CompositeI child(WebWidget w);

	public List<WebWidget> getChildWidgetList();

}
