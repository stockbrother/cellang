/**
 * Jul 16, 2012
 */
package org.cellang.webframework.client.widget;

/**
 * @author wu
 * 
 */
public abstract class AbstractWebWidgetCreater<T extends WebWidget> implements
		WebWidget.CreaterI<T> {
	protected Class<T> widgetType;

	public AbstractWebWidgetCreater(Class<T> wt) {
		this.widgetType = wt;
	}

	/* */
	@Override
	public Class<T> getWidgetType() {

		return this.widgetType;

	}

}
