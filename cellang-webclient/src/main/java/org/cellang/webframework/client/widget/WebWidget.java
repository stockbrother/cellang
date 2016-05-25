/**
 * Jun 13, 2012
 */
package org.cellang.webframework.client.widget;

import org.cellang.webframework.client.Container;
import org.cellang.webframework.client.lang.HasProperties;
import org.cellang.webframework.client.lang.WebElement;

/**
 * @author wu
 * 
 */
public interface WebWidget extends WebElement {
	

	public static interface CreaterI<T extends WebWidget> {
		
		public Class<T> getWidgetType();

		public T create(Container c, String name, HasProperties<Object> pts);

	}

	public void setVisible(boolean v);

	public boolean isVisible();

	@Deprecated
	// use ElementWrapper.click()
	public void _click();

}
