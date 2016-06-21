/**
 * Jun 25, 2012
 */
package org.cellang.clwt.commons.client.widget;

import org.cellang.clwt.core.client.widget.WebWidget;

/**
 * @author wuzhen
 * 
 */
public interface EditorI<T> extends WebWidget {

	public T getData();
	
	public void setData(T d);

	public void input(T d);

}
