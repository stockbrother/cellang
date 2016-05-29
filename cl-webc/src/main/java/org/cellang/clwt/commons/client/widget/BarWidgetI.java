/**
 * All right is from Author of the file,to be explained in comming days.
 * Nov 6, 2012
 */
package org.cellang.clwt.commons.client.widget;

import org.cellang.clwt.core.client.lang.Position;
import org.cellang.clwt.core.client.widget.WebWidget;

/**
 * @author wu
 * 
 */
public interface BarWidgetI extends WebWidget{

	public static final Position P_LEFT = Position.valueOf("left");

	public static final Position P_RIGHT = Position.valueOf("right");

	public static final Position P_CENTER = Position.valueOf("center");

	public void addItem(Position pos, WebWidget cw);

}
