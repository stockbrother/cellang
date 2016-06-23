/**
 * Jun 30, 2012
 */
package org.cellang.clwt.commons.client.widget;

import org.cellang.clwt.core.client.Container;
import org.cellang.clwt.core.client.UiException;
import org.cellang.clwt.core.client.event.Event;
import org.cellang.clwt.core.client.event.Event.Type;
import org.cellang.clwt.core.client.lang.HasProperties;
import org.cellang.clwt.core.client.lang.WebObject;
import org.cellang.clwt.core.client.widget.AbstractWebWidget;
import org.cellang.clwt.core.client.widget.WebWidget;

import com.google.gwt.user.client.Element;

/**
 * @author wu
 * 
 */
public class WidgetSupport extends AbstractWebWidget {

	public WidgetSupport(Container c, String name, Element ele) {
		super(c, name, ele);

	}

	public WidgetSupport(Container c, String name, Element ele, HasProperties<Object> pts) {
		super(c, name, ele, pts);
	}


	//TODO
	protected void onSetParent(WebWidget old, WebWidget newP) {

		if (old != null && newP != null) {
			throw new UiException("changing parent not suported, current parent:" + old + ",new parent:" + newP
					+ " for widget:" + this + "");
		}
//
//		if (old != null && old.isAttached()) {
//			this.detach();
//		}
//
//		if (newP != null && newP.isAttached()) {
//			this.attach();
//		}

	}
//
//	@Override
//	public boolean isAttached() {
//		return attached;
//	}

	protected String localized(String key) {
		return this.getClient(true).localized(key);
	}

}
