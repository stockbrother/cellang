/**
 * Jul 19, 2012
 */
package org.cellang.webc.main.client;

import org.cellang.clwt.core.client.lang.Path;
import org.cellang.clwt.core.client.lang.Size;

/**
 * @author wu
 * 
 */
public class UiClientConstants {

	public static final String PK_MESSAGE_QUERY_LIMIT = "messageQueryLimit";

	public static final String PK_EXP_QUERY_LIMIT = "expQueryLimit";

	public static final String IS_LOCAL = "_is_local";
	public static final String AK_FORMS_MODEL = "formsModel";

	public static final String NK_ID = "id_";
	public static final Path P_SIGNUP = Path.valueOf("/signup");

	public static final Path P_HTML = Path.valueOf("/open/resource/html");

	public static final Path RP_USERINFO_GET = Path.valueOf("/uinfo/get");

	@Deprecated
	public static final Path P_HTML_ABOUT = P_HTML.getSubPath("about-us.html");

	public static final Path P_CONTACT_US = Path.valueOf("/contact-us");

	public static final String PK_URL_ABOUTUS = "aboutUsUrl";

	public static final Path AP_COOPER = Path.valueOf("/action/exps/cooper");

	public static final Path AP_EXPS_SEARCH = Path.valueOf("/action/exps/search");

	public static final Path AP_EXPS_MORE = Path.valueOf("/action/exps/more");

	public static final Path AP_EXPM_MORE = Path.valueOf("/action/expm/more");

	public static final Path AP_EXPM_SEND = Path.valueOf("/action/expm/send");

	public static final Path AP_COOPER_CONFIRM = Path.valueOf("/action/cooper/confirm");

	public static final Path AP_COOPER_REJECT = Path.valueOf("/action/cooper/reject");

	public static final Path AP_EXPE_CLOSE = Path.valueOf("/action/myexp/close");

	public static final Size EXPEDIT_IMAGE_SIZE = Size.valueOf(4 * 64, 64);

	public static final Size USER_ICON_SIZE = Size.valueOf(52, 52);

	public static final double EXPEDIT_INNER_BOX_ZOOM = 1.0d;
	// public static final Integer MESSAGE_LIMIT = 25;//

}
