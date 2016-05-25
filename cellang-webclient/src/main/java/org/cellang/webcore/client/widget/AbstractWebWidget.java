/**
 * Jun 13, 2012
 */
package org.cellang.webcore.client.widget;

import org.cellang.webcore.client.Container;
import org.cellang.webcore.client.event.HideEvent;
import org.cellang.webcore.client.lang.AbstractWebElement;
import org.cellang.webcore.client.lang.HasProperties;

import com.google.gwt.user.client.Element;

/**
 * @author wuzhen
 * 
 */
public abstract class AbstractWebWidget extends AbstractWebElement implements WebWidget {

	protected WebWidgetFactory factory;

	protected AbstractWebWidget(Container c, String name, Element element) {
		this(c, name, element, null);
	}

	protected AbstractWebWidget(Container c, String name, Element element, HasProperties<Object> pts) {
		super(c, name, element, pts);
		this.factory = c.get(WebWidgetFactory.class, true);
	}

	@Override
	protected String getClassNamePrefix() {
		return "wgt-";
	}

	@Override
	public void setVisible(boolean vis) {
		super.setVisible(vis);
		if (!this.visible) {
			new HideEvent(this).dispatch();
		}
	}

}
