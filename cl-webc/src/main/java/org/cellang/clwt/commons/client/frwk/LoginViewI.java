/**
 * All right is from Author of the file,to be explained in comming days.
 * Feb 1, 2013
 */
package org.cellang.clwt.commons.client.frwk;

import org.cellang.clwt.commons.client.mvc.ViewI;

/**
 * @author wu
 * 
 */
public interface LoginViewI extends ViewI {
	public static final String FK_SAVINGACCOUNT = "savingAccount";// save in
	// client
	// side for
	// auto
	// auth.

	public static final String FK_EMAIL = "email";

	public static final String FK_PASSWORD = "password";

	/**
	 * @return
	 */
	public String getEmail();

	/**
	 * @return
	 */
	public String getPassword();

	public boolean isSavingAccount();

	public void setEmail(String email);
	
	public void setPassword(String password);
	/**
	 * 
	 */

}
