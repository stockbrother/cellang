/**
 * All right is from Author of the file,to be explained in comming days.
 * Jan 10, 2013
 */
package org.cellang.webc.main.client;

import org.cellang.clwt.core.client.lang.Path;

/**
 * @author wu
 * 
 */
public class Actions {
	

	public static final Path A_SIGNUP = Path.valueOf("signup");

	public static final Path A_SIGNUP_SUBMIT = A_SIGNUP.getSubPath("submit");

	/** LOGIN **/
	public static final Path A_LOGIN = Path.valueOf("login");
	
	/** PASSWORD**/
	public static final Path A_PASSWORD = Path.valueOf("password");
	
	// create anonymous
	// account.

	public static final Path A_LOGIN_LOGOUT = A_LOGIN.getSubPath("logout");
	// logout and open login
	// view?.

	public static final Path A_LOGIN_SUBMIT = A_LOGIN.getSubPath("submit");
	
	public static final Path A_LOGIN_SUBMIT_SUCCESS = A_LOGIN_SUBMIT.getSubPath("success");
	
	public static final Path A_LOGIN_FACEBOOK = A_LOGIN.getSubPath("facebook");

	public static final Path A_PASSWORD_FORGOT = A_PASSWORD.getSubPath("forgot");
	
	public static final Path A_PASSWORD_RESET = A_PASSWORD.getSubPath("reset");

	
	// profile
	public static final Path A_PROFILE = Path.valueOf("profile");

	public static final Path A_PROFILE_SUBMIT = A_PROFILE.getSubPath("submit");

	public static final Path A_PROFILE_INIT = A_PROFILE.getSubPath("init");
	
	public static final Path A_TABLE_ADDCOLUMN = Path.valueOf("table.add-column");
	
	public static final Path A_TABLE_SAVE = Path.valueOf("table.save");

}
