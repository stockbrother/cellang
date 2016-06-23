/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 4, 2012
 */
package org.cellang.clwt.commons.client.widget;

import org.cellang.clwt.commons.client.mvc.CompositeI;
import org.cellang.clwt.core.client.lang.WebElement;

/**
 * @author wu
 *
 */
public interface PanelWI extends CompositeI{

	public void setClosable(boolean b);
	
	public WebElement getContent();

}
