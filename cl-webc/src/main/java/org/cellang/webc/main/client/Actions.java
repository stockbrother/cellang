/**
 * All right is from Author of the file,to be explained in comming days.
 * Jan 10, 2013
 */
package org.cellang.webc.main.client;

import org.cellang.clwt.commons.client.mvc.ActionEvent;
import org.cellang.clwt.core.client.lang.Path;

/**
 * @author wu
 * 
 */
public class Actions {
	
	/** ACTION ROOT **/
	public static final Path ACTION = ActionEvent.TYPE.getAsPath();

	/** LOGIN **/
	public static final Path A_LOGIN = ACTION.getSubPath("login");
	
	/** PASSWORD**/
	public static final Path A_PASSWORD = ACTION.getSubPath("password");
	
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

	/** GCHAT **/

	public static final Path A_GCHAT = ACTION.getSubPath("gchat");

	public static final Path A_GCHAT_JOIN = A_GCHAT.getSubPath("join");

	public static final Path A_GCHAT_SEND = A_GCHAT.getSubPath("send");
	

	// user exp
	public static final Path A_UEL_ITEM = ACTION.getSubPath("uexpi");

	public static final Path A_UEXPI_OPEN = A_UEL_ITEM.getSubPath("open");

	public static final Path A_UEXPI_SELECT = A_UEL_ITEM.getSubPath("select");

	// activities

	public static final Path A_ACTS = ACTION.getSubPath("activities");

	public static final Path A_ACTS_ACTIVITIES = A_ACTS.getSubPath("activities");

	// activity
	public static final Path A_ACT = ACTION.getSubPath("activity");

	public static final Path A_ACT_OPEN_CHAT_ROOM = A_ACT.getSubPath("openChatRoom");

	// cooper

	public static final Path A_EXPE = ACTION.getSubPath("expe");

	public static final Path A_EXPE_SUBMIT = A_EXPE.getSubPath("submit");

	// exps
	public static final Path A_EXPS = ACTION.getSubPath("exps");

	public static final Path A_EXPS_COOPER = A_EXPS.getSubPath("cooper");
	
	public static final Path A_EXPS_GETUSERINFO = A_EXPS.getSubPath("get-uinfo");

	public static final Path A_EXPS_SEARCH = A_EXPS.getSubPath("search");
	
	// profile
	public static final Path A_PROFILE = ACTION.getSubPath("profile");

	public static final Path A_PROFILE_SUBMIT = A_PROFILE.getSubPath("submit");

	public static final Path A_PROFILE_INIT = A_PROFILE.getSubPath("init");

	// contact us
	public static final Path A_CONTACTUS_SUBMIT = ACTION.getSubPath("cttmsg").getSubPath("submit");
	// signup
	
	public static final Path A_SIGNUP = ACTION.getSubPath("signup");

	public static final Path A_SIGNUP_SUBMIT = A_SIGNUP.getSubPath("submit");


	public static final Path A_UELIST = ACTION.getSubPath("uelist");

	public static final Path A_UEL_CREATE = A_UELIST.getSubPath("create");

	public static final Path A_UEL_SELECT = A_UELIST.getSubPath("open");
}
