/**
 * Jul 16, 2012
 */
package org.cellang.clwt.commons.client;

import org.cellang.clwt.core.client.widget.WebWidget;

/**
 * @author wu
 * 
 */
public abstract class WidgetCreaterSupport<T extends WebWidget> implements WebWidget.CreaterI<T> {
	protected Class<T> widgetType;

	public WidgetCreaterSupport(Class<T> wt) {
		this.widgetType = wt;
	}

	/* */
	@Override
	public Class<T> getWidgetType() {

		return this.widgetType;

	}

}
