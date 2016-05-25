/**
 * Jun 13, 2012
 */
package org.cellang.webcore.client.widget;

import org.cellang.webcore.client.Container;
import org.cellang.webcore.client.lang.HasProperties;
import org.cellang.webcore.client.lang.WebElement;

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
