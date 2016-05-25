/**
 * Jun 29, 2012
 */
package org.cellang.webcommon.mvc.widget;

import org.cellang.webcore.client.lang.State;

/**
 * @author wu
 * 
 */
public interface ButtonI extends BasicI {

	public static State DOWN = State.valueOf("down");

	public static State UP = State.valueOf("up");

	public State getState();

	public void setText(boolean toloc, String text);

	public void disable(boolean dis);

}
