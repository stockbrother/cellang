/**
 * All right is from Author of the file,to be explained in comming days.
 * Nov 24, 2012
 */
package org.cellang.clwt.commons.client.frwk;

import org.cellang.clwt.commons.client.mvc.Control;
import org.cellang.clwt.core.client.lang.Path;
import org.cellang.webc.main.client.LoginViewI;

/**
 * @author wu
 */
public interface FrwkControlI extends Control {

	public void open();

	public void tryRemoveHeaderItem(Path path);

	public void addHeaderItemIfNotExist(Path path);

	public void addHeaderItem(Path path);

	public void addHeaderItem(Path path, boolean left);

	public HeaderViewI getHeaderView();

	public BottomViewI getBottomView();

	@Deprecated // remove
	public LoginViewI openLoginView(boolean show);

	public ConsoleViewI openConsoleView(boolean show);

}
