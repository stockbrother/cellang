/**
 * Jun 25, 2012
 */
package org.cellang.webcommon.mvc.widget;

import org.cellang.webcore.client.widget.WebWidget;

/**
 * @author wuzhen
 * 
 */
public interface EditorI<T> extends WebWidget {

	public T getData();
	
	public void setData(T d);

	public void input(T d);

}
