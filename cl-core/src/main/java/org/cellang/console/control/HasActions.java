package org.cellang.console.control;

import java.util.List;

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
	public List<Action> getActions(List<Action> al);

}
