/**
 * Jun 13, 2012
 */
package org.cellang.clwt.core.client.lang;

import org.cellang.clwt.core.client.core.ElementWrapper;

import com.google.gwt.user.client.Element;

/**
 * @author wu
 * 
 */
public interface WebElement extends WebObject {
	
	public Element getElement();

	public ElementWrapper getElementWrapper();

	public boolean isVisible();

	public void setVisible(boolean v);

	public void appendElement(WebElement we);
	
	public void removeElement(WebElement we);
	
	@Deprecated
	// use ElementWrapper.click()
	public void _click();
	
}
