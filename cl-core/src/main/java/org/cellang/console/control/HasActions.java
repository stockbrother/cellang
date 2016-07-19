package org.cellang.console.control;

import java.util.List;

import org.cellang.console.view.View;

/**
 * 
 * @author wu
 *
 */
public interface HasActions {
	/**
	 * Add additional action to the list argument.
	 * 
	 * @param al
	 * @return
	 */
	public List<Action> getActions(View view, List<Action> al);

}
