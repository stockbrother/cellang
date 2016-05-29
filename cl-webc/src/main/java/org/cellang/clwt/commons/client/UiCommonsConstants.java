/**
 * Jul 19, 2012
 */
package org.cellang.clwt.commons.client;

import org.cellang.clwt.core.client.lang.Path;

/**
 * @author wu
 * 
 */
public class UiCommonsConstants {

	public static final String AK_FORMS_VIEW = "formsView";// action property

	public static final Path P_TAB = Path.valueOf("tab");
	public static final Path P_CONSOLE_VIEW = Path.valueOf("console");
	
	public static final Path LOGIN_VIEW = Path.valueOf("login");

	public static final Path PASSWORDRESET_VIEW = P_TAB.getSubPath("password-reset");

	public static String RK_COMET_HEARTBEATINTERVAL = "cometHeartBeatIntervalMs";

	public static final int MENU_HIDE_TIMEOUT_MS = 100;
	// client parameters
	public static final String CPK_TEXT_INPUT_LENGTH_LIMIT = "textInputLengthLimit";

	// css class name
	public static final String CN_VLIST_ITEM = "vlist-item";

	public static final String CN_HLIST_ITEM = "hlist-item";

}
