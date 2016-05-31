/**
 * Jun 13, 2012
 */
package org.cellang.clwt.core.client.widget;

import java.util.HashMap;
import java.util.Map;

import org.cellang.clwt.core.client.Container;
import org.cellang.clwt.core.client.UiException;
import org.cellang.clwt.core.client.lang.AbstractHasProperties;
import org.cellang.clwt.core.client.lang.HasProperties;
import org.cellang.clwt.core.client.widget.WebWidget.CreaterI;

/**
 * @author wuzhen
 * 
 */
public class DefaultWebWidgetFactory implements WebWidgetFactory {
	/*
	
	 */
	private Map<Class, WebWidget.CreaterI> createrMap;

	private Container container;

	public DefaultWebWidgetFactory(Container c) {
		this.container = c;
		this.createrMap = new HashMap<Class, WebWidget.CreaterI>();
	}

	@Override
	public <T extends WebWidget> T create(Class<T> cls) {
		return create(cls, null);
	}

	@Override
	public <T extends WebWidget> T create(Class<T> cls, String name) {
		return this.create(cls, name, new Object[] {});
	}

	@Override
	public <T extends WebWidget> T create(Class<T> cls, String name, Object... pts) {

		HasProperties<Object> pts2 = AbstractHasProperties.valueOf(pts);

		return this.create(cls, name, pts2);

	}

	@Override
	public <T extends WebWidget> T create(Class<T> cls, String name, HasProperties<Object> pts) {
		WebWidget.CreaterI<T> wic = this.createrMap.get(cls);
		if (wic == null) {
			throw new UiException("no creater found for widget type:" + cls);
		}

		T rt = wic.create(this.container, name, pts);
		return rt;
	}

	/* */
	@Override
	public void addCreater(CreaterI wic) {
		this.createrMap.put(wic.getWidgetType(), wic);
	}

}
