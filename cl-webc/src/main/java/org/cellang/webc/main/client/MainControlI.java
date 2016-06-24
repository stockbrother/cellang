/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 17, 2012
 */
package org.cellang.webc.main.client;

import org.cellang.clwt.commons.client.mvc.Control;
import org.cellang.webc.main.client.widget.LoginViewI;
import org.cellang.webc.main.client.widget.SignupViewI;

/**
 * @author wu
 * 
 */
public interface MainControlI extends Control {

	public SignupViewI openSignup();

	public void closeSignup();
	
	public LoginViewI openLoginView(boolean show);
	
	public void closeLoginView();
	
	public void closeAll();

	public void openCreateTableView(boolean show);
}
