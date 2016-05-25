/**
 * Jun 13, 2012
 */
package org.cellang.webframework.client.widget;

import org.cellang.webframework.client.lang.HasProperties;

/**
 * @author wu
 * 
 */
public interface WebWidgetFactory {

	public <T extends WebWidget> T create(Class<T> cls);
	
	public <T extends WebWidget> T create(Class<T> cls, String name);
	
	public <T extends WebWidget> T create(Class<T> cls, String name, Object...pts);

	public <T extends WebWidget> T create(Class<T> cls, String name, HasProperties<Object> pts);

	public <T extends WebWidget> void addCreater(WebWidget.CreaterI<T> wic);

}
